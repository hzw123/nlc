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

import com.nlc.nraas.domain.Organization;
import com.nlc.nraas.domain.User;
import com.nlc.nraas.enums.OrganizationStatus;
import com.nlc.nraas.repo.OrganizationRepository;
import com.nlc.nraas.repo.UserRepository;
import com.nlc.nraas.tools.PageUtils;

/**
 * 机构管理
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class OrganizationController {
	private final List<Predicate> list = new ArrayList<Predicate>();
	@Autowired
	private OrganizationRepository or;
	@Autowired
	private UserRepository ur;

	/**
	 * 验证机构名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/organizations/checkName")
	public String checkName(String name) {
		if (or.findByName(name) != null) {
			return "The name of the organization has been registered";
		}
		return null;
	}

	/**
	 * 查询机构下的用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/organizations/{id}/users", method = RequestMethod.GET)
	public List<User> selUser(@PathVariable(value = "id") Long id) {
		return ur.findByOrganizationId(id);
	}

	/**
	 * 机构分页查询
	 * 
	 * @param name
	 * @param code
	 * @param status
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/organizations", method = RequestMethod.GET)
	public Page<Organization> page(String name, String code, String status, Pageable pageable) {
		return or.findAll(new Specification<Organization>() {

			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				list.clear();

				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(code))
					list.add(cb.equal(root.get("code"), code));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), OrganizationStatus.getMyEnum(status)));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/organizations/{id}", method = RequestMethod.GET)
	public Organization getOrganization(@PathVariable Long id) {
		return or.findOne(id);
	}

	@RequestMapping(value = "/organizations", method = RequestMethod.POST)
	public Organization save(@RequestBody Organization organization) {
		return or.save(organization);
	}

	@RequestMapping(value = "/organizations", method = RequestMethod.PUT)
	public Organization update(@RequestBody Organization organization) {
		return or.save(organization);
	}

	@RequestMapping(value = "/organizations/delete", method = RequestMethod.POST)
	public Object deleteOrganization() {
		long id = 0;
		or.delete(id);
		return null;
	}

	@RequestMapping(value = "/organizations/{id}", method = RequestMethod.DELETE)
	public Object deleteOrganization(@PathVariable Long id) {
		or.delete(id);
		return null;
	}
}
