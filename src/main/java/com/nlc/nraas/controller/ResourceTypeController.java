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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlc.nraas.domain.ResourceType;
import com.nlc.nraas.enums.Status;
import com.nlc.nraas.repo.ResourceTypeRepository;
import com.nlc.nraas.tools.PageUtils;

/**
 * 资源模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api/resourceTypes")
@RestController
public  class ResourceTypeController {

	@Autowired
	private  ResourceTypeRepository resourceTypeRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResourceType getResourceType(@PathVariable Long id) {
		return resourceTypeRepository.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public synchronized ResourceType save(@RequestBody ResourceType resourceType) {
		resourceType.setLocal(resourceTypeRepository.maxLocal() + 1);
		return resourceTypeRepository.save(resourceType);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResourceType update(@RequestBody ResourceType resourceType) {
		return resourceTypeRepository.save(resourceType);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteResourceType(@RequestBody List<ResourceType> list) {
		resourceTypeRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteResourceType(@PathVariable Long id) {
		resourceTypeRepository.delete(id);
		return null;
	}

	
	@RequestMapping(value = "/{id}/before", method = RequestMethod.GET)
	public ResourceType before(@PathVariable Long id) {
		return resourceTypeRepository.before(resourceTypeRepository.findOne(id).getLocal());
	}
	
	@RequestMapping(value = "/{id}/after", method = RequestMethod.GET)
	public ResourceType after(@PathVariable Long id) {
		return resourceTypeRepository.after(resourceTypeRepository.findOne(id).getLocal());
	}
	
	/**
	 * 移到最上边
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/min", method = RequestMethod.PUT)
	public synchronized ResourceType min(@PathVariable Long id) {
		ResourceType resourceType=resourceTypeRepository.findOne(id);
		if(resourceType.getLocal()>1){
			List<ResourceType> list=resourceTypeRepository.getBefore(resourceTypeRepository.findOne(id).getLocal());
			for (int i = 0; i < list.size()-1; i++) {
				resourceTypeRepository.upLocal(list.get(i).getId(),list.get(i+1).getLocal());
			}
			resourceTypeRepository.upLocal(list.get(list.size()-1).getId(),resourceType.getLocal());
			resourceTypeRepository.upLocal(id, 1);
			resourceType.setLocal(1);
		}
		return resourceType;
	}
	/**
	 * 移到最下边
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/max", method = RequestMethod.PUT)
	public synchronized ResourceType max(@PathVariable Long id) {
		ResourceType resourceType=resourceTypeRepository.findOne(id);
		if(resourceTypeRepository.maxLocal()>resourceType.getLocal()){
			List<ResourceType> list=resourceTypeRepository.getAfter(resourceTypeRepository.findOne(id).getLocal());
			int local=list.get(list.size()-1).getLocal();
			for (int i = 1; i < list.size(); i++) {
				resourceTypeRepository.upLocal(list.get(i).getId(),list.get(i-1).getLocal());
			}
			resourceTypeRepository.upLocal(list.get(0).getId(),resourceType.getLocal());
			resourceTypeRepository.upLocal(id,local);
			resourceType.setLocal(local);
		}
		return resourceType;
	}
	
	/**
	 * 往上移动
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.PUT)
	public synchronized ResourceType up(@PathVariable Long id) {
		ResourceType resourceType=getResourceType(id);
		if(resourceType.getLocal()>1){
			ResourceType re=resourceTypeRepository.before(resourceType.getLocal());
			int local=re.getLocal();
			resourceTypeRepository.upLocal(re.getId(), resourceType.getLocal());
			resourceType.setLocal(local);
			resourceTypeRepository.upLocal(id, local);
		}
		return resourceType;
	}
	
	/**
	 * 往下移动
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/down", method = RequestMethod.PUT)
	public synchronized ResourceType down(@PathVariable Long id) {
		ResourceType resourceType=resourceTypeRepository.findOne(id);
		if(resourceType.getLocal()<resourceTypeRepository.maxLocal()){
			ResourceType re=resourceTypeRepository.after(resourceType.getLocal());
			int local=re.getLocal();
			resourceTypeRepository.upLocal(re.getId(), resourceType.getLocal());
			resourceType.setLocal(local);
			resourceTypeRepository.upLocal(id, local);
		}
		return resourceType;
	}
	/**
	 * 资源类型列表分页
	 * 
	 * @param type
	 * @param status
	 * @param picture
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<ResourceType> page(String type, String status, String picture, Pageable pageable) {
		return resourceTypeRepository.findAll(new Specification<ResourceType>() {
			@Override
			public Predicate toPredicate(Root<ResourceType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(type))
					list.add(cb.equal(root.get("name"), type));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), Status.getMyEnum(status)));
				if (StringUtils.isNotBlank(picture))
					list.add(cb.equal(root.get("picture"), picture));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Direction.ASC, "local"))));
	}
}
