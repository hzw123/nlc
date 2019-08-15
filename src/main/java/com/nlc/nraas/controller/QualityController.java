package com.nlc.nraas.controller;

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

import com.nlc.nraas.domain.NotQuality;
import com.nlc.nraas.enums.TaskStatus;
import com.nlc.nraas.repo.NotQualityRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 质检模块
 * 
 * @author Dell
 */
@RestController
@RequestMapping("/api/notQualitys")
public class QualityController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private NotQualityRepository nqr;

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
				final List<Predicate> list = new ArrayList<Predicate>();
				try {
					if (StringUtils.isNotBlank(taskName))
						list.add(cb.like(root.join("task").get("name"), "%" + taskName + "%"));
					if (StringUtils.isNotBlank(reason))
						list.add(cb.equal(root.get("reason"), reason));
					int sid = StringTransform.TransfromInt(serverId);
					if (sid != -1) {
						list.add(cb.equal(root.join("task").join("server").get("id"), sid));
					}
					if (StringUtils.isNotBlank(status))
						list.add(cb.equal(root.join("task").get("status"), TaskStatus.getMyEnum(status)));
					if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
						list.add(cb.between(root.join("task").get("createAt"), sdf.parse(start), sdf.parse(stop)));
					Predicate[] predicates = new Predicate[list.size()];
					query.where(cb.and(list.toArray(predicates)));
				} catch (Exception e) {
				}
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public NotQuality NotQuality(@PathVariable Long id) {
		return nqr.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public NotQuality save(@RequestBody NotQuality notQuality) {
		return nqr.save(notQuality);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public NotQuality update(@RequestBody NotQuality notQuality) {
		return nqr.save(notQuality);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteNotQuality(@PathVariable Long id) {
		nqr.delete(id);
		return null;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteNotQuality(@RequestBody List<NotQuality> list) {
		nqr.delete(list);
		return null;
	}
}
