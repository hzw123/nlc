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

import com.nlc.nraas.domain.CollectCompleteInform;
import com.nlc.nraas.domain.IndexCompleteInform;
import com.nlc.nraas.domain.WarcFile;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.repo.CollectCompleteInformRepository;
import com.nlc.nraas.repo.IndexCompleteInformRepository;
import com.nlc.nraas.repo.WarcFileRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 通知功能模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class InformController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private CollectCompleteInformRepository collectCompleteInformRepository;
	@Autowired
	private IndexCompleteInformRepository indexCompleteInformRepository;
	@Autowired
	private WarcFileRepository warcFileRepository;

	@RequestMapping(value = "/collectCompleteInforms/{id}", method = RequestMethod.GET)
	public CollectCompleteInform getCollectCompleteInform(@PathVariable Long id) {
		return collectCompleteInformRepository.findOne(id);
	}

	@RequestMapping(value = "/collectCompleteInforms", method = RequestMethod.POST)
	public CollectCompleteInform save(@RequestBody CollectCompleteInform collectCompleteInform) {
		return collectCompleteInformRepository.save(collectCompleteInform);
	}

	@RequestMapping(value = "/collectCompleteInforms", method = RequestMethod.PUT)
	public CollectCompleteInform update(@RequestBody CollectCompleteInform collectCompleteInform) {
		return collectCompleteInformRepository.save(collectCompleteInform);
	}

	@RequestMapping(value = "/collectCompleteInforms/delete", method = RequestMethod.POST)
	public Object deleteCollectCompleteInform(@RequestBody List<CollectCompleteInform> list) {
		collectCompleteInformRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/collectCompleteInforms/{id}", method = RequestMethod.DELETE)
	public Object deleteCollectCompleteInform(@PathVariable Long id) {
		collectCompleteInformRepository.delete(id);
		return null;
	}

	/**
	 * 采集任务完成通知 分页查询
	 * 
	 * @param taskName
	 * @param status
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/collectCompleteInforms", method = RequestMethod.GET)
	public Page<CollectCompleteInform> page(String taskName, String status, String start, String stop,
			Pageable pageable) {
		return collectCompleteInformRepository.findAll(new Specification<CollectCompleteInform>() {

			@Override
			public Predicate toPredicate(Root<CollectCompleteInform> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root.join("task").get("name"), "%" + taskName + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ReadStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.join("task").get("completeAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, pageable);
	}

	@RequestMapping(value = "/indexCompleteInforms/{id}", method = RequestMethod.GET)
	public IndexCompleteInform getIndexCompleteInform(@PathVariable Long id) {
		return indexCompleteInformRepository.findOne(id);
	}

	@RequestMapping(value = "/indexCompleteInforms", method = RequestMethod.POST)
	public IndexCompleteInform save(@RequestBody IndexCompleteInform indexCompleteInform) {
		return indexCompleteInformRepository.save(indexCompleteInform);
	}

	@RequestMapping(value = "/indexCompleteInforms", method = RequestMethod.PUT)
	public IndexCompleteInform update(@RequestBody IndexCompleteInform indexCompleteInform) {
		return indexCompleteInformRepository.save(indexCompleteInform);
	}

	@RequestMapping(value = "/indexCompleteInforms/delete", method = RequestMethod.POST)
	public Object deleteIndexCompleteInform(@RequestBody List<IndexCompleteInform> list) {
		indexCompleteInformRepository.delete(list);
		return null;
	}

	/**
	 * 删除当前对象
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/indexCompleteInforms/{id}", method = RequestMethod.DELETE)
	public Object deleteIndexCompleteInform(@PathVariable Long id) {
		indexCompleteInformRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/indexCompleteInforms", method = RequestMethod.GET)
	public Page<IndexCompleteInform> pageIndex(String taskName, String status, String start, String stop,
			Pageable pageable) {
		return indexCompleteInformRepository.findAll(new Specification<IndexCompleteInform>() {

			@Override
			public Predicate toPredicate(Root<IndexCompleteInform> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root
							.join("doneIndexList")
							.join("urlList")
							.join("task")
							.get("name"),"%" + taskName + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ReadStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root
								.join("doneIndexList")
								.join("urlList")
								.join("task")
								.get("completeAt"),sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, pageable);
	}

	@RequestMapping(value = "/warcFiles/{id}", method = RequestMethod.GET)
	public WarcFile getWarcFile(@PathVariable Long id) {
		return warcFileRepository.findOne(id);
	}

	@RequestMapping(value = "/warcFiles", method = RequestMethod.POST)
	public WarcFile save(@RequestBody WarcFile warcFile) {
		return warcFileRepository.save(warcFile);
	}

	@RequestMapping(value = "/warcFiles", method = RequestMethod.PUT)
	public WarcFile update(@RequestBody WarcFile warcFile) {
		return warcFileRepository.save(warcFile);
	}

	@RequestMapping(value = "/warcFiles/{id}", method = RequestMethod.DELETE)
	public Object deleteWarcFile(@PathVariable Long id) {
		warcFileRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/warcFiles/delete", method = RequestMethod.POST)
	public Object deleteWarcFile() {
		long id = 0;
		warcFileRepository.delete(id);
		return null;
	}

	/**
	 * 
	 * @param taskId
	 * @param name
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/warcFiles", method = RequestMethod.GET)
	public Page<WarcFile> pageWarcFile(String taskId, String name, Pageable pageable) {
		return warcFileRepository.findAll(new Specification<WarcFile>() {

			@Override
			public Predicate toPredicate(Root<WarcFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				int tid = StringTransform.TransfromInt(taskId);
				if (tid != -1)
					list.add(cb.equal(root.join("task"), tid));
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}
}
