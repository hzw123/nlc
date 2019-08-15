package com.nlc.nraas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlc.nraas.domain.ArchivedTask;
import com.nlc.nraas.domain.DoneIndexList;
import com.nlc.nraas.domain.IndexFile;
import com.nlc.nraas.enums.IndexStatus;
import com.nlc.nraas.repo.ArchivedTaskRepository;
import com.nlc.nraas.repo.DoneIndexListRepository;
import com.nlc.nraas.repo.IndexFileRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 回放模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class PlaybackController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ArchivedTaskRepository archivedTaskRepository;
	@Autowired
	private IndexFileRepository indexFileRepository;
	@Autowired
	private DoneIndexListRepository doneIndexListRepository;

	/***
	 * 已归档任务分页查询
	 * 
	 * @param taskName
	 * @param urlStatus
	 * @param fullStatus
	 * @param type
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/archivedTasks", method = RequestMethod.GET)
	public Page<ArchivedTask> pageArchivedTask(String taskName, String urlStatus, String fullStatus, String type,
			String start, String stop, Pageable pageable) {
		return archivedTaskRepository.findAll(new Specification<ArchivedTask>() {

			@Override
			public Predicate toPredicate(Root<ArchivedTask> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root
							.join("urlList")
							.join("task")
							.get("name"), "%" + taskName + "%"));
				if (StringUtils.isNotBlank(urlStatus))
					list.add(cb.equal(root.get("urlStatus"), urlStatus));
				if (StringUtils.isNotBlank(fullStatus))
					list.add(cb.equal(root.get("fullStatus"), fullStatus));
				if (StringUtils.isNotBlank(type))
					if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
						try {
							list.add(cb.between(root
									.join("urlList")
									.join("task")
									.get("completeAt"), sdf.parse(start),sdf.parse(stop)));
						} catch (ParseException e) {
							e.printStackTrace();
						}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/archivedTasks/{id}", method = RequestMethod.GET)
	public ArchivedTask getArchivedTask(@PathVariable Long id) {
		return archivedTaskRepository.findOne(id);
	}

	@RequestMapping(value = "/archivedTasks", method = RequestMethod.POST)
	public ArchivedTask save(@RequestBody ArchivedTask archivedTask) {
		return archivedTaskRepository.save(archivedTask);
	}

	@RequestMapping(value = "/archivedTasks", method = RequestMethod.PUT)
	public ArchivedTask update(@RequestBody ArchivedTask archivedTask) {
		return archivedTaskRepository.save(archivedTask);
	}

	@RequestMapping(value = "/archivedTasks", method = RequestMethod.DELETE)
	public Object deleteArchivedTask() {
		long id = 0;
		archivedTaskRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/archivedTasks/{id}/del", method = RequestMethod.DELETE)
	public Object deleteArchivedTask(@PathVariable Long id) {
		archivedTaskRepository.delete(id);
		return null;
	}

	/**
	 * 查看索引详情分页查询
	 * 
	 * @param taskId
	 * @param name
	 * @param urlStatus
	 * @param fullStatus
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/indexFiles", method = RequestMethod.GET)
	public Page<IndexFile> pageIndexFile(String taskId, String name, String urlStatus, String fullStatus,
			Pageable pageable) {
		return indexFileRepository.findAll(new Specification<IndexFile>() {

			@Override
			public Predicate toPredicate(Root<IndexFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				int tid = StringTransform.TransfromInt(taskId);
				if (tid != -1)
					list.add(cb.equal(root.join("task").get("id"), tid));
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(urlStatus))
					list.add(cb.equal(root.get("urlStatus"), IndexStatus.getMyEnum(urlStatus)));
				if (StringUtils.isNotBlank(fullStatus))
					list.add(cb.equal(root.get("fullStatus"), IndexStatus.getMyEnum(fullStatus)));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/indexFiles/{id}", method = RequestMethod.GET)
	public IndexFile getIndexFile(@PathVariable Long id) {
		return indexFileRepository.findOne(id);
	}

	@RequestMapping(value = "/indexFiles", method = RequestMethod.POST)
	public IndexFile save(@RequestBody IndexFile indexFile) {
		return indexFileRepository.save(indexFile);
	}

	@RequestMapping(value = "/indexFiles", method = RequestMethod.PUT)
	public IndexFile update(@RequestBody IndexFile indexFile) {
		return indexFileRepository.save(indexFile);
	}

	@RequestMapping(value = "/indexFiles", method = RequestMethod.DELETE)
	public Object deleteIndexFile() {
		long id = 0;
		indexFileRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/indexFiles/{id}/del", method = RequestMethod.DELETE)
	public Object deleteIndexFile(@PathVariable Long id) {
		indexFileRepository.delete(id);
		return null;
	}

	/**
	 * 已完成索引分类查询
	 * 
	 * @param taskName
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/doneIndexLists", method = RequestMethod.GET)
	public Page<DoneIndexList> pageIndexComplete(String taskName, String start, String stop, Pageable pageable) {
		return doneIndexListRepository.findAll(new Specification<DoneIndexList>() {

			@Override
			public Predicate toPredicate(Root<DoneIndexList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root
							.join("urlList")
							.join("task")
							.get("name"), "%" + taskName + "%"));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root
								.join("urlList")
								.join("task")
								.get("completeAt"), sdf.parse(start),sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/doneIndexLists/{id}", method = RequestMethod.GET)
	public DoneIndexList getDoneIndexList(@PathVariable Long id) {
		return doneIndexListRepository.findOne(id);
	}

	@RequestMapping(value = "/doneIndexLists", method = RequestMethod.POST)
	public DoneIndexList save(@RequestBody DoneIndexList doneIndexList) {
		return doneIndexListRepository.save(doneIndexList);
	}

	@RequestMapping(value = "/doneIndexLists", method = RequestMethod.PUT)
	public DoneIndexList update(@RequestBody DoneIndexList doneIndexList) {
		return doneIndexListRepository.save(doneIndexList);
	}

	@RequestMapping(value = "/doneIndexLists", method = RequestMethod.DELETE)
	public Object deleteDoneIndexList() {
		long id = 0;
		doneIndexListRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/doneIndexLists/{id}/del", method = RequestMethod.DELETE)
	public Object deleteDoneIndexList(@PathVariable Long id) {
		doneIndexListRepository.delete(id);
		return null;
	}
}
