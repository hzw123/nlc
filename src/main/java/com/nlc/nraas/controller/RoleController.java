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
import com.nlc.nraas.domain.Role;
import com.nlc.nraas.enums.UserStatus;
import com.nlc.nraas.repo.RoleRepository;
import com.nlc.nraas.tools.PageUtils;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	@Autowired
	private RoleRepository rr;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<Role> page(String name, String status, Pageable pageable) {
		return rr.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if(StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"),UserStatus.getMyEnum(status)));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(list.toArray(predicates));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Role getRole(@PathVariable Long id) {
		return rr.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Role save(@RequestBody Role role) {
		return rr.save(role);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Role update(@RequestBody Role role) {
		return rr.save(role);
	}

	@RequestMapping(value = "/{id}/up", method = RequestMethod.PUT)
	public Role update(@PathVariable Long id, @RequestBody Role role) {
		Role r = getRole(id);
		if (StringUtils.isNotBlank(role.getName()) && rr.findByName(role.getName()) == null) {
			r.setName(role.getName());
		}
		if (role.getNumber() > 0) {
			r.setNumber(role.getNumber());
		}
		if (role.getStatus() != null) {
			r.setStatus(role.getStatus());
		}
		return rr.save(r);
	}

	@RequestMapping(value = "/{id}/upStatus", method = RequestMethod.PUT)
	public Role update(@PathVariable Long id) {
		Role r = getRole(id);
		if (r.getStatus() == UserStatus.DISABLE) {
			r.setStatus(UserStatus.ENABLE);
		} else {
			r.setStatus(UserStatus.DISABLE);
		}
		return rr.save(r);
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public Object deleteRole(@RequestBody List<Role> list) {
		rr.delete(list);
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteRole(@PathVariable Long id) {
		rr.delete(id);
		return "delete succeed,";
	}
}
