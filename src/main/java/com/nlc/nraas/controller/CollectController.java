package com.nlc.nraas.controller;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.management.InvalidAttributeValueException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlc.nraas.domain.CheckList;
import com.nlc.nraas.domain.NotQuality;
import com.nlc.nraas.domain.Task;
import com.nlc.nraas.enums.CheckListStatus;
import com.nlc.nraas.enums.TaskStatus;
import com.nlc.nraas.repo.CheckListRepository;
import com.nlc.nraas.repo.NotQualityRepository;
import com.nlc.nraas.repo.TaskRepository;
import com.nlc.nraas.tools.HeritrixJmxClient;
import com.nlc.nraas.tools.HeritrixMonitor;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 采集模块控制器
 * 
 */
@RequestMapping("/api/tasks")
@RestController
public class CollectController {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final List<Predicate> list = new ArrayList<Predicate>();
	
	@Value("${heritrix.profiles.url}") 
	private String heritrixProfilesUrl;
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CheckListRepository checkListRepository;
	@Autowired
	private NotQualityRepository nqr;
	Map<String, ScheduledThreadPoolExecutor> executors = new HashMap<String, ScheduledThreadPoolExecutor>();

	/**
	 * 任务多条件分页查询
	 * 
	 * @param task
	 * @param time
	 * @param Pageable
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<Task> findPage(String name, String status, String thread, String url, String server, String start,
			String stop, Pageable Pageable) {
		return taskRepository.findAll(new Specification<Task>() {
			@Override
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				list.clear();
				if (StringUtils.isNotBlank(url))
					list.add(cb.equal(root.get("url"), url));
				int sid = StringTransform.TransfromInt(server);
				if (sid != -1) {
					list.add(cb.equal(root.join("server").get("id"), sid));
				}
				int threadId = StringTransform.TransfromInt(thread);
				if (threadId != -1)
					list.add(cb.equal(root.join("serverThread").get("id"), threadId));
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), TaskStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop)) {
					try {
						list.add(cb.between(root.get("createAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Predicate[] arrayPredicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(arrayPredicates)));
				return null;
			}

		}, PageUtils.getPageRequest(Pageable));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable(value = "id") Long id) throws Exception {
		Task task = taskRepository.findOne(id);
		
		if (task == null) { 
			return ResponseEntity.notFound().build();
		}
		
		return task;
	}

	/**
	 * 保存任务表 , 同时与某个heritrix服务器上的某个名称的实例添加一个抓取任务,任务创建成功后，，会启动一个监听进程，，监控并获得抓取中的状态信息
	 * 
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Task saveTask(@RequestBody Task task) throws Exception {
		if (StringUtils.isBlank(task.getDescription())) {
			task.setDescription(task.getName());
		}
		task.setCreateAt(new Date());
		task = taskRepository.save(task);
		
		try {
//			Object obj = HeritrixJmxClient.addJobBasedon(task.getServer().getIp(), task.getServer().getJmxport(),
//					task.getServerThread().getBeanName(), task.getProfile().getName(), task.getName(),
//					task.getDescription(), task.getUrl());
			
			//使用外部文件方式添加任务便于统一管理
			String profileUrl = heritrixProfilesUrl + URLEncoder.encode (task.getProfile().getName(), "utf-8") + "/order.jar";
			
			Object obj = HeritrixJmxClient.addJob(task.getServer().getIp(), task.getServer().getJmxport(),
					task.getServerThread().getBeanName(), profileUrl, task.getName(),
					task.getDescription(), task.getUrl());
			
			//当profile不存在或者为空时，，HeritrixJmxClient并不会抛出异常，，而是返回类似这样的错误信息："Exception on " + jobUidOrProfile + ": " + e.getMessage()
			//所以此处需要特殊处理
			if (obj.toString().startsWith("Exception on")) {
				throw new InvalidAttributeValueException(obj.toString());
			}
			
			task.setJobId(obj.toString());
			
			if (task.getAutoStart()) {
				HeritrixJmxClient.startCrawl(task.getServer().getIp(), task.getServer().getJmxport(),
						task.getServerThread().getBeanName());
				task.setStatus(TaskStatus.PENDING);
			}

			task = taskRepository.save(task);
			
			//确认监听线程
			ScheduledThreadPoolExecutor exec = executors.get(task.getServerThread().getBeanName());
			if (exec == null) {
				exec = new ScheduledThreadPoolExecutor(1);
				exec.scheduleWithFixedDelay(
						new HeritrixMonitor(taskRepository, exec, task.getServer().getIp(),
								task.getServer().getJmxport(), task.getServerThread().getBeanName()),
						20, 20, TimeUnit.SECONDS);
				executors.put(task.getServerThread().getBeanName(), exec);
			}
		} catch (Exception e) {
			
			task.setStatus(TaskStatus.FINISHED_ABNORMAL);
			task = taskRepository.save(task);
			
			throw e;
		}
		
		return task;
	}

	/**
	 * 更新任务表
	 * 
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Task update(@RequestBody Task task) {
		return taskRepository.save(task);
	}

	/**
	 * 删除任务
	 * 
	 * @param tList
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable(value = "id") Long id) throws Exception {
		
		Task task = taskRepository.findOne(id);
		if (task == null) { 
			return ResponseEntity.notFound().build();
		}
		
		HeritrixJmxClient.teminate(task.getServer().getIp(), task.getServer().getJmxport(),
				task.getServerThread().getBeanName(), task.getJobId());
		taskRepository.delete(task);
		return null;
	}
	
	/**
	 * 批量删除任务
	 * 
	 * @param tList
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteAll(@RequestBody List<Task> tList) throws Exception {
		for(Task task : tList) {
			HeritrixJmxClient.teminate(task.getServer().getIp(), task.getServer().getJmxport(),
					task.getServerThread().getBeanName(), task.getJobId());
			taskRepository.delete(task);
		}
		return null;
	}
	
	/**
	 * 暂停一个进行中的任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{id}/pause", method = RequestMethod.GET)
	public Object pause(@PathVariable(value = "id") Long id) throws Exception {
		Task task = taskRepository.findOne(id);
		
		if (task == null) { 
			return ResponseEntity.notFound().build();
		}
		task.setStatus(TaskStatus.PAUSED);
		
		try {
			HeritrixJmxClient.pause(task.getServer().getIp(), task.getServer().getJmxport(),
					task.getServerThread().getBeanName(), task.getName(), task.getJobId());
			taskRepository.save(task);
		} catch (Exception e) {
			
			task.setStatus(TaskStatus.PAUSED_ABNORMAL);
			task = taskRepository.save(task);
			
			throw e;
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 继续执行一个处于暂停状态的任务
	 * 
	 * @param id
	 * @throws Exception
	 * @return
	 */
	@RequestMapping(value = "/{id}/resume", method = RequestMethod.GET)
	public Object resume(@PathVariable(value = "id") Long id) throws Exception {
		Task task = taskRepository.findOne(id);
		
		if (task == null) { 
			return ResponseEntity.notFound().build();
		}
		task.setStatus(TaskStatus.RUNNING);
		
		try {
			HeritrixJmxClient.resume(task.getServer().getIp(), task.getServer().getJmxport(),
					task.getServerThread().getBeanName(), task.getName(), task.getJobId());
			taskRepository.save(task);
		} catch (Exception e) {
			
			task.setStatus(TaskStatus.FINISHED_ABNORMAL);
			taskRepository.save(task);
			
			throw e;
		}
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * 终止正在进行中的任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{id}/teminate", method = RequestMethod.GET)
	public Object teminate(@PathVariable(value = "id") Long id) throws Exception {
		Task task = taskRepository.findOne(id);
		if (task == null) { 
			return ResponseEntity.notFound().build();
		}
		task.setStatus(TaskStatus.ABORTED);
		task.setCompleteAt(new Date());
		try {
			HeritrixJmxClient.teminate(task.getServer().getIp(), task.getServer().getJmxport(),
					task.getServerThread().getBeanName(), task.getJobId());
			taskRepository.save(task);
		} catch (Exception e) {
			
			task.setStatus(TaskStatus.FINISHED_ABNORMAL);
			task = taskRepository.save(task);
			
			throw e;
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * 保存检查列表
	 * 
	 * @param checkList
	 * @return
	 */
	@RequestMapping(value = "/save/checkLists", method = RequestMethod.POST)
	public CheckList saveCheckList(CheckList checkList) {
		return checkListRepository.save(checkList);
	}

	/**
	 * 更新检查列表
	 * 
	 * @param checkList
	 * @return
	 */
	@RequestMapping(value = "/update/checkLists", method = RequestMethod.PUT)
	public CheckList upCheckList(CheckList checkList) {
		return checkListRepository.save(checkList);
	}

	/**
	 * 批量删除检查列表
	 * 
	 * @param clist
	 * @return
	 */
	@RequestMapping(value = "/delete/checkLists", method = RequestMethod.DELETE)
	public String delCheckList(List<CheckList> clist) {
		checkListRepository.delete(clist);
		return "ok";
	}

	/**
	 * 检查点列表 分页查询
	 * 
	 * @param name
	 * @param status
	 * @param start
	 * @param stop
	 * @param serverId
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/tasks/{id}/checkLists",method = RequestMethod.GET)
	public Page<CheckList> page(@PathVariable(value = "id") Long id,String name, 
			String status,String start,String stop,String serverId,Pageable pageable){
		return checkListRepository.findAll(new Specification<CheckList>() {
			@Override
			public Predicate toPredicate(Root<CheckList> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				list.clear();
				list.add(cb.equal(root.join("task").get("id"),id));
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), CheckListStatus.getMyEnum(status)));
				int sid = StringTransform.TransfromInt(serverId);
				if (sid != -1)
					list.add(cb.equal(root.join("task").join("server").get("id"), sid));
				try {
					if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
						list.add(cb.between(root.get("createAt"), sdf.parse(start), sdf.parse(stop)));

					Predicate[] predicates = new Predicate[list.size()];
					query.where(cb.and(list.toArray(predicates)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	/**
	 * 质检不合格任务列表 分页查询
	 * 
	 * @param taskName
	 * @param status
	 * @param serverId
	 * @param reason
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/page/notQuality")
	public Page<NotQuality> pageNotQuality(String taskName, String status, String serverId, String reason, String start,
			String stop, Pageable pageable) {
		return nqr.findAll(new Specification<NotQuality>() {

			@Override
			public Predicate toPredicate(Root<NotQuality> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				list.clear();
				try {
					if (StringUtils.isNotBlank(taskName))
						list.add(cb.like(root
								.join("task")
								.get("name"), "%" + taskName + "%"));
					if (StringUtils.isNotBlank(reason))
						list.add(cb.equal(root.get("reason"), reason));
					int sid = StringTransform.TransfromInt(serverId);
					if (sid != -1) {
						list.add(cb.equal(root
								.join("task")
								.join("server").get("id"), sid));
					}
					if (StringUtils.isNotBlank(status))
						list.add(cb.equal(root
								.join("task")
								.get("status"), TaskStatus.getMyEnum(status)));
					if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
						list.add(cb.between(root
								.join("task")
								.get("createAt"), sdf.parse(start),sdf.parse(stop)));
					Predicate[] predicates = new Predicate[list.size()];
					query.where(cb.and(list.toArray(predicates)));
				} catch (Exception e) {
				}
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}
}
