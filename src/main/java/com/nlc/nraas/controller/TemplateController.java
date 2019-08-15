package com.nlc.nraas.controller;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlc.nraas.domain.Profile;
import com.nlc.nraas.repo.ProfileRepository;
import com.nlc.nraas.tools.HeritrixXmlUtils;
import com.nlc.nraas.tools.PageUtils;

/**
 * 
 * 
 */
@RequestMapping("/api/profiles")
@RestController
public class TemplateController {
	
	@Autowired
	private ProfileRepository profileRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<Profile> findPage(String name, String ip, String status, Pageable pageable) {
		return profileRepository.findAll(new Specification<Profile>() {

			@Override
			public Predicate toPredicate(Root<Profile> root, CriteriaQuery<?> 
			query, CriteriaBuilder cb) {
				if (StringUtils.isNotBlank(name))
					query.where(cb.like(root.get("name"), "%" + name + "%"));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable(value = "id") Long id) throws Exception {
		
		Profile profile = profileRepository.findOne(id);
		
		if (profile == null) { 
			return ResponseEntity.notFound().build();
		} else {
			return HeritrixXmlUtils.getProfile(profile.getName());
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public Profile save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String profileName = request.getParameter(HeritrixXmlUtils.PROFILE_NAME_PATH);
		Assert.hasLength(profileName, "模版名称不能为空");
		
		Profile profile = new Profile();
		profile.setName(profileName);
		profile.setDescription(request.getParameter(HeritrixXmlUtils.PROFILE_DESC_PATH));
		
		profile = profileRepository.save(profile);
		
		HeritrixXmlUtils.writeProfile(request);
		
		return profile;
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
	public Profile update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.save(request, response);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(@RequestBody List<Profile> tList) throws Exception {
		
		for (Profile profile : tList) {
			HeritrixXmlUtils.removeProfile(profile.getName());

			profileRepository.delete(profile);
		}
		profileRepository.delete(tList);
		
		return null;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable(value = "id") Long id) throws Exception {
		
		Profile profile = profileRepository.findOne(id);
		
		if (profile == null) { 
			return ResponseEntity.notFound().build();
		} 
			
		HeritrixXmlUtils.removeProfile(profile.getName());

		profileRepository.delete(profile);
		
		return null;
	}
}