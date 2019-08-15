package com.nlc.nraas.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlc.nraas.domain.Server;
import com.nlc.nraas.domain.ServerThread;
import com.nlc.nraas.enums.ServerStatus;
import com.nlc.nraas.repo.ServerRepository;
import com.nlc.nraas.repo.ServerThreadRepository;
import com.nlc.nraas.tools.HeritrixJmxClient;
import com.nlc.nraas.tools.PageUtils;

/**
 * 
 * 
 */
@RequestMapping("/api/servers")
@RestController
public class ServerController {
	
	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private ServerThreadRepository serverThreadRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<Server> findPage(String name, String ip, String status, Pageable Pageable) {
		return serverRepository.findAll(new Specification<Server>() {
			@Override
			public Predicate toPredicate(Root<Server> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("ip"), "%" + ip + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), ServerStatus.getMyEnum(status)));
				Predicate[] arrayPredicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(arrayPredicates)));
				return null;
			}

		}, PageUtils.getPageRequest(Pageable));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Server get(@PathVariable(value = "id") Long id) {
		return serverRepository.findOne(id);
	}
	
	@Transactional
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Server save(@RequestBody Server server) throws Exception {
		
		Server s = serverRepository.save(server);
		Set<ServerThread> threads = new LinkedHashSet<ServerThread>();
		try {
			List<Map<String, String>> instances = HeritrixJmxClient.getInstances(server.getIp(), server.getJmxport());
			for (Map<String, String> instance : instances) {
				ServerThread st = new ServerThread();
				st.setName(instance.get("name"));
				st.setBeanName(instance.get("beanName"));
				st.setServer(server);
				threads.add(serverThreadRepository.save(st));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);		//只有RuntimeException才能@Transactional
		}
		s.setServerThreads(threads);
		return s;
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Server update(@RequestBody Server server) {
		return serverRepository.save(server);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable(value = "id") Long id) {
		serverRepository.delete(id);
		return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteAll(@RequestBody List<Server> tList) {
		serverRepository.delete(tList);
		return null;
	}
	
	@RequestMapping(value = "/{id}/modifyThreadAmountTo/{to}", method = RequestMethod.GET)
	public Object modifyThreadAmountTo(@PathVariable(value = "id") Long id, @PathVariable(value = "to") Integer to) throws Exception {
		Assert.notNull(id, "服务器id不能为空");
		Assert.notNull(to, "修改线程数量不能为空");
		Assert.isTrue(to > 0, "修改线程数量必须大于0");
		
		Server server = serverRepository.findOne(id);
		if (server == null) {
			return ResponseEntity.notFound().build();
		}
		
		int diff = server.getServerThreads().size() - to;
		if (diff > 0 ) {	//减少空闲线程
			for(int i=0; i<diff; i++) {
				ServerThread st = (ServerThread) server.getServerThreads().toArray()[server.getServerThreads().size() - i - 1];
				HeritrixJmxClient.deleteInstance(server.getIp(), server.getJmxport(), st.getBeanName());
				serverThreadRepository.delete(st);
			}
		} else if (diff < 0) {	//增加空闲线程
			ServerThread firstServerThread = (ServerThread) server.getServerThreads().toArray()[0];
			ServerThread lastServerThread = (ServerThread) server.getServerThreads().toArray()[server.getServerThreads().size() - 1];
			for(int i=0; i<Math.abs(diff); i++) {
				ServerThread st = new ServerThread();
				int index = 0;
				try {
					index = Integer.parseInt(lastServerThread.getName().replaceAll("线程", ""));
				} catch (NumberFormatException e) {
					
				}
				String threadName = "线程" + (index + i + 1);
				String beanName = firstServerThread.getBeanName().replace("name="+firstServerThread.getName(), "name="+threadName);
				st.setName(threadName);
				st.setBeanName(beanName);;
				st.setServer(server);
				HeritrixJmxClient.createInstance(server.getIp(), server.getJmxport(), beanName);
				serverThreadRepository.save(st);
			}
		}
		
		return null;
	}
}
