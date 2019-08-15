package com.nlc.nraas.controller;

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

import com.nlc.nraas.domain.CataloQualityNot;
import com.nlc.nraas.domain.UrlList;
import com.nlc.nraas.enums.CatalogStatus;
import com.nlc.nraas.enums.QualityType;
import com.nlc.nraas.repo.CataloQualityNotRepository;
import com.nlc.nraas.repo.UrlListRepository;

/**
 * 编目模块
 * 
 * @author Dell
 *
 */
@RestController
@RequestMapping("/api")
public class CatalogueController {

	@Autowired
	private UrlListRepository urlListRepository;
	@Autowired
	private CataloQualityNotRepository cataloQualityNotRepository;

	/**
	 * url列表分页查询
	 * 
	 * @param taskName
	 * @param createName
	 * @param status
	 * @param type
	 * @param falg
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/urlLists", method = RequestMethod.GET)
	public Page<UrlList> pageUrlList(String taskName, String createName, String status, String type, String falg,
			Pageable pageable) {
		return urlListRepository.findAll(new Specification<UrlList>() {
			@Override
			public Predicate toPredicate(Root<UrlList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root.join("task").get("name"), "%" + taskName + "%"));
				if (StringUtils.isNotBlank(createName))
					list.add(cb.like(root.get("createName"), createName));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), CatalogStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(type))
					list.add(cb.equal(root.get("result"), QualityType.getMyEnum(type)));
				if (StringUtils.isNotBlank(falg)) {
					if (falg.equals("true")) {
						list.add(cb.equal(root.get("falg"), true));
					} else if (falg.equals("false")) {
						list.add(cb.equal(root.get("falg"), false));
					}
				}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, pageable);
	}

	@RequestMapping(value = "/urlLists/{id}", method = RequestMethod.GET)
	public UrlList getUrlList(@PathVariable Long id) {
		return urlListRepository.findOne(id);
	}

	@RequestMapping(value = "/urlLists", method = RequestMethod.POST)
	public UrlList save(@RequestBody UrlList urlList) {
		return urlListRepository.save(urlList);
	}

	@RequestMapping(value = "/urlLists", method = RequestMethod.PUT)
	public UrlList update(@RequestBody UrlList urlList) {
		return urlListRepository.save(urlList);
	}

	@RequestMapping(value = "/urlLists/delete", method = RequestMethod.POST)
	public Object deleteUrlList(@RequestBody List<UrlList> list) {
		urlListRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/urlLists/{id}", method = RequestMethod.DELETE)
	public Object deleteUrlList(@PathVariable Long id) {
		urlListRepository.delete(id);
		return null;
	}

	/**
	 * 编目不合格URL列表 分页查询
	 * 
	 * @param taskName
	 * @param createName
	 * @param type
	 * @param falg
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/cataloQualityNots", method = RequestMethod.GET)
	public Page<CataloQualityNot> pageCataloQualityNot(String taskName, String createName, String type, String falg,
			String reason, Pageable pageable) {
		return cataloQualityNotRepository.findAll(new Specification<CataloQualityNot>() {

			@Override
			public Predicate toPredicate(Root<CataloQualityNot> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root.join("catalogue").join("urlList").join("task").get("name"),
							"%" + taskName + "%"));
				if (StringUtils.isNotBlank(createName))
					list.add(cb.like(root.join("catalogue").join("urlList").get("createName"), createName));
				if (StringUtils.isNotBlank(type))
					list.add(cb.equal(root.join("catalogue").join("urlList").get("result"),
							QualityType.getMyEnum(type)));
				if (StringUtils.isNotBlank(falg)) {
					if (falg.equals("true")) {
						list.add(cb.equal(root.join("catalogue").join("urlList").get("falg"), true));
					} else if (falg.equals("false")) {
						list.add(cb.equal(root.join("catalogue").join("urlList").get("falg"), false));
					}
				}
				if (StringUtils.isNotBlank(reason))
					list.add(cb.like(root.get("reason"), reason));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, pageable);
	}

	@RequestMapping(value = "/cataloQualityNots/{id}", method = RequestMethod.GET)
	public CataloQualityNot getCataloQualityNot(@PathVariable Long id) {
		return cataloQualityNotRepository.findOne(id);
	}

	@RequestMapping(value = "/cataloQualityNots", method = RequestMethod.POST)
	public CataloQualityNot save(@RequestBody CataloQualityNot cataloQualityNot) {
		return cataloQualityNotRepository.save(cataloQualityNot);
	}

	@RequestMapping(value = "/cataloQualityNots", method = RequestMethod.PUT)
	public CataloQualityNot update(@RequestBody CataloQualityNot cataloQualityNot) {
		return cataloQualityNotRepository.save(cataloQualityNot);
	}

	@RequestMapping(value = "/cataloQualityNots/delete", method = RequestMethod.POST)
	public Object deleteCataloQualityNot(@RequestBody List<CataloQualityNot> list) {
		cataloQualityNotRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/cataloQualityNots/{id}", method = RequestMethod.DELETE)
	public Object deleteCataloQualityNot(@PathVariable Long id) {
		cataloQualityNotRepository.delete(id);
		return null;
	}
}
