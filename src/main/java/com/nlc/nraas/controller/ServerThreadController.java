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

import com.nlc.nraas.domain.ServerThread;
import com.nlc.nraas.enums.ServerStatus;
import com.nlc.nraas.repo.ServerThreadRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 
 * 
 */
@RequestMapping("/api/serverthreads")
@RestController
public class ServerThreadController {
	
	@Autowired
	private ServerThreadRepository serverThreadRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<ServerThread> findPage(String name, String taskName, String serverId, 
			String status,Pageable pageable) {
		return serverThreadRepository.findAll(new Specification<ServerThread>() {

			@Override
			public Predicate toPredicate(Root<ServerThread> root, CriteriaQuery<?> 
			query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root.join("task").get("name"), taskName));
				int sid = StringTransform.TransfromInt(serverId);
				if (sid != -1)
					list.add(cb.equal(root.join("server").get("id"), sid));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ServerStatus.getMyEnum(status)));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ServerThread get(@PathVariable(value = "id") Long id) {
		return serverThreadRepository.findOne(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ServerThread save(@RequestBody ServerThread serverThread) {
		return serverThreadRepository.save(serverThread);
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ServerThread update(@RequestBody ServerThread serverThread) {
		return serverThreadRepository.save(serverThread);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable(value = "id") Long id) {
		serverThreadRepository.delete(id);
		return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteAll(@RequestBody List<ServerThread> tList) {
		serverThreadRepository.delete(tList);
		return null;
	}
}