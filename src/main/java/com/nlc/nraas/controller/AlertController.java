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

import com.nlc.nraas.domain.IndexerAlert;
import com.nlc.nraas.domain.ServerAlert;
import com.nlc.nraas.enums.IndexType;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.repo.IndexerAlertRepository;
import com.nlc.nraas.repo.ServerAlertRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 异常警报模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class AlertController {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ServerAlertRepository sar;
	@Autowired
	private IndexerAlertRepository indexerAlertRepository;

	/**
	 * 服务器报警 分页查询
	 * 
	 * @param serverId
	 * @param status
	 * @param rank
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/serverAlerts", method = RequestMethod.GET)
	public Page<ServerAlert> page(String serverId, String status, String rank, String start, String stop,
			Pageable pageable) {
		return sar.findAll(new Specification<ServerAlert>() {

			@Override
			public Predicate toPredicate(Root<ServerAlert> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				int sid = StringTransform.TransfromInt(serverId);
				if (sid != -1)
					list.add(cb.equal(root.join("task").join("server").get("id"), sid));
				int r = StringTransform.TransfromInt(rank);
				if (r != -1)
					list.add(cb.equal(root.get("rank"), r));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ReadStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.get("alarmAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/serverAlerts/{id}", method = RequestMethod.GET)
	public ServerAlert getServerAlert(@PathVariable Long id) {
		return sar.findOne(id);
	}

	@RequestMapping(value = "/serverAlerts", method = RequestMethod.POST)
	public ServerAlert save(@RequestBody ServerAlert serverAlert) {
		return sar.save(serverAlert);
	}

	@RequestMapping(value = "/serverAlerts", method = RequestMethod.PUT)
	public ServerAlert update(@RequestBody ServerAlert serverAlert) {
		return sar.save(serverAlert);
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/serverAlerts/delete", method = RequestMethod.POST)
	public Object deleteServerAlert(@RequestBody List<ServerAlert> list) {
		sar.delete(list);
		return null;
	}

	@RequestMapping(value = "/serverAlerts/{id}", method = RequestMethod.DELETE)
	public Object deleteServerAlert(@PathVariable Long id) {
		sar.delete(id);
		return null;
	}

	/**
	 * 索引异常报警消息 分页查询
	 * 
	 * @param taskName
	 *            任务名称
	 * @param status
	 *            状态
	 * @param type
	 *            索引类型
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/indexerAlerts", method = RequestMethod.GET)
	public Page<IndexerAlert> pageIndexerAlert(String taskName, String status, String type, String start, String stop,
			Pageable pageable) {
		return indexerAlertRepository.findAll(new Specification<IndexerAlert>() {

			@Override
			public Predicate toPredicate(Root<IndexerAlert> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root.join("task").get("name"), taskName));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ReadStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(type))
					list.add(cb.equal(root.get("type"), IndexType.getMyEnum(type)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.get("alarmAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	/**
	 * 异常警报消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/indexerAlerts/alert", method = RequestMethod.GET)
	public int getCount() {
		return indexerAlertRepository.findByStatus(ReadStatus.NOT_READ).size()
				+ sar.findByStatus(ReadStatus.NOT_READ).size();
	}

	@RequestMapping(value = "/indexerAlerts/{id}", method = RequestMethod.GET)
	public IndexerAlert getIndexerAlert(@PathVariable Long id) {
		return indexerAlertRepository.findOne(id);
	}

	@RequestMapping(value = "/indexerAlerts", method = RequestMethod.POST)
	public IndexerAlert save(@RequestBody IndexerAlert IndexerAlert) {
		return indexerAlertRepository.save(IndexerAlert);
	}

	@RequestMapping(value = "/indexerAlerts", method = RequestMethod.PUT)
	public IndexerAlert update(@RequestBody IndexerAlert IndexerAlert) {
		return indexerAlertRepository.save(IndexerAlert);
	}

	@RequestMapping(value = "/indexerAlerts/delete", method = RequestMethod.POST)
	public Object deleteIndexerAlert(@RequestBody List<IndexerAlert> list) {
		indexerAlertRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/indexerAlerts/{id}", method = RequestMethod.DELETE)
	public Object deleteIndexerAlert(@PathVariable Long id) {
		indexerAlertRepository.delete(id);
		return null;
	}
}
