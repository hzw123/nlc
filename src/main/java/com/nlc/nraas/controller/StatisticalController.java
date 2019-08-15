package com.nlc.nraas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.nlc.nraas.domain.AccessList;
import com.nlc.nraas.repo.AccessListRepository;
import com.nlc.nraas.tools.PageUtils;

/**
 * 统计模块
 * 
 * @author Dell
 *
 */
@RestController
@RequestMapping("/api")
public class StatisticalController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private AccessListRepository accessListRepository;

	/**
	 * 访问记录分页查询
	 * 
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/accessLists", method = RequestMethod.GET)
	public Page<AccessList> page(String start, String stop, Pageable pageable) {
		return accessListRepository.findAll(new Specification<AccessList>() {

			@Override
			public Predicate toPredicate(Root<AccessList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop)) {
					try {
						query.where(cb.between(root.get("time"), sdf.parse(start), sdf.parse(start)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/accessLists/{id}", method = RequestMethod.GET)
	public AccessList getAccessList(@PathVariable Long id) {
		return accessListRepository.findOne(id);
	}

	@RequestMapping(value = "/accessLists", method = RequestMethod.POST)
	public AccessList save(@RequestBody AccessList accessList) {
		return accessListRepository.save(accessList);
	}

	@RequestMapping(value = "/accessLists", method = RequestMethod.PUT)
	public AccessList update(@RequestBody AccessList accessList) {
		return accessListRepository.save(accessList);
	}

	@RequestMapping(value = "/accessLists/{id}", method = RequestMethod.DELETE)
	public Object deleteAccessList(@PathVariable Long id) {
		accessListRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/accessLists/delete", method = RequestMethod.POST)
	public Object deleteAccessList(@RequestBody List<AccessList> list) {
		accessListRepository.delete(list);
		return null;
	}

}
