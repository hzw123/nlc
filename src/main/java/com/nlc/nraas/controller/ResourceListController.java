package com.nlc.nraas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

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
import com.nlc.nraas.domain.ResourceList;
import com.nlc.nraas.enums.PushStatus;
import com.nlc.nraas.repo.ResourceListRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/***
 * 资源列表模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api/resourceLists")
@RestController
public class ResourceListController {

	@Autowired
	private ResourceListRepository resourceListRepository;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<ResourceList> page(String name, String tid, String status, String otherName, String tsName,
			String webAdrees, String url, String falg, String specialName, String specialStartAt, String pushTime,
			String pushAdress, String createName, String language, String nationality, String resourceType,
			String level, String code, String specialStopAt, String organization, String note, String resourceFormat,
			String acquisitionAt, String info, Pageable pageable, HttpServletRequest request) {
		Page<ResourceList> pager = resourceListRepository.findAll(new Specification<ResourceList>() {

			@Override
			public Predicate toPredicate(Root<ResourceList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();

				int rid = StringTransform.TransfromInt(resourceFormat);
				if (rid != -1) {
					list.add(cb.equal(root.join("resourceFormat").get("id"), rid));
				}
				if (StringUtils.isNotBlank(tid))
					list.add(cb.equal(root.join("catalogue").join("urlList").join("task").get("tid"), tid));
				if (StringUtils.isNotBlank(name))
					list.add(cb.equal(root.join("catalogue").join("urlList").join("task").get("name"),
							"%" + name + "%"));
				if (StringUtils.isNotBlank(status)) {
					list.add(cb.equal(root.join("catalogue").get("status"), PushStatus.getMyEnum(status)));
				}
				if (StringUtils.isNotBlank(otherName))
					list.add(cb.like(root.join("catalogue").get("otherName"), "%" + otherName + "%"));
				if (StringUtils.isNotBlank(tsName))
					list.add(cb.like(root.join("catalogue").get("translatedName"), "%" + tsName + "%"));
				if (StringUtils.isNotBlank(webAdrees))
					list.add(cb.equal(root.join("catalogue").get("webAdrees"), webAdrees));
				if (StringUtils.isNotBlank(url))
					list.add(cb.equal(root.join("catalogue").join("urlList").join("task").get("url"), url));
				if (StringUtils.isNotBlank(specialName))
					list.add(cb.like(root.join("catalogue").get("specialName"), "%" + specialName + "%"));
				try {
					if (StringUtils.isNotBlank(specialStartAt) && StringUtils.isNotBlank(specialStopAt)) {
						list.add(cb.lessThan(root.get("specialStopAt"), sdf.parse(specialStopAt)));
						list.add(cb.greaterThan(root.get("specialStartAt"), sdf.parse(specialStartAt)));
					}
					if (StringUtils.isNotBlank(pushTime))
						list.add(cb.equal(root.join("catalogue").get("publishAt"), sdf.parse(pushTime)));
					if (StringUtils.isNotBlank(pushAdress))
						list.add(cb.equal(root.join("catalogue").get("publishAdr"), pushAdress));
					if (StringUtils.isNotBlank(createName))
						list.add(cb.like(root.join("catalogue").join("urlList").get("createName"),
								"%" + createName + "%"));
					if (StringUtils.isNotBlank(language))
						list.add(cb.equal(root.join("catalogue").get("language"), language));
					if (StringUtils.isNotBlank(nationality))
						list.add(cb.equal(root.join("catalogue").get("nationality"), nationality));
					if (StringUtils.isNotBlank(level))
						list.add(cb.equal(root.join("catalogue").get("administrativeLevel"), level));
					if (StringUtils.isNotBlank(code))
						list.add(cb.equal(root.join("catalogue").get("code"), code));
					if (StringUtils.isNotBlank(note))
						list.add(cb.equal(root.join("catalogue").get("note"), note));
					if (StringUtils.isNotBlank(acquisitionAt))
						list.add(cb.equal(root.join("catalogue").get("acquisitionAt"), sdf.parse(acquisitionAt)));
					if (StringUtils.isNotBlank(info))
						list.add(cb.equal(root.join("catalogue").get("copyrightInformation"), info));
					int id = StringTransform.TransfromInt(resourceType);
					if (id != -1)
						list.add(cb.equal(root.join("catalogue").join("resourceType").get("id"), id));
					if (StringUtils.isNotBlank(organization))
						list.add(cb.equal(root.join("catalogue").join("organization").get("name"), organization));
					if (StringUtils.isNotBlank(info) && (falg.equals("true") || falg.equals("false"))) {
						boolean f = false;
						if (falg.endsWith("true")) {
							f = true;
						}
						list.add(cb.equal(root.join("catalogue").get("falg"), f));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Direction.ASC, "local"))));
		request.getSession().setAttribute("listr", pager.getContent());
		return pager;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResourceList getResourceList(@PathVariable Long id) {
		return resourceListRepository.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public synchronized ResourceList save(@RequestBody ResourceList resourceList) {
		resourceList.setLocal(resourceListRepository.maxLocal() + 1);
		return resourceListRepository.save(resourceList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteResourceList(@PathVariable Long id) {
		resourceListRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteResourceList(@RequestBody List<ResourceList> list) {
		resourceListRepository.delete(list);
		return null;
	}

	/**
	 * 上移
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.GET)
	public synchronized ResourceList moveUp(@PathVariable Long id, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ResourceList> list = (List<ResourceList>) request.getSession().getAttribute("listr");
		ResourceList resourceListUp = null;
		ResourceList resourceListThis = null;
		if (list != null && list.size() > 0) {
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getId() == id) {
					resourceListUp = list.get(i - 1);
					resourceListThis = list.get(i);
					int local = resourceListUp.getLocal();
					resourceListUp.setLocal(resourceListThis.getLocal());
					resourceListThis.setLocal(local);
					list.add(i - 1, resourceListUp);
					list.add(i, resourceListThis);
					resourceListRepository.upLocal(resourceListUp.getId(), resourceListUp.getLocal());
					resourceListRepository.upLocal(resourceListThis.getId(), resourceListThis.getLocal());
					break;
				}
			}
			request.getSession().setAttribute("listr", list);
		}
		return resourceListThis;
	}

	/**
	 * 往下移动
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/down", method = RequestMethod.GET)
	public synchronized ResourceList moveDown(@PathVariable Long id, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ResourceList> list = (List<ResourceList>) request.getSession().getAttribute("listr");
		ResourceList resourceListDown = null;
		ResourceList resourceListThis = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size() - 1; i++) {
				if (list.get(i).getId() == id) {
					resourceListDown = list.get(i + 1);
					resourceListThis = list.get(i);
					int local = resourceListDown.getLocal();
					resourceListDown.setLocal(resourceListThis.getLocal());
					resourceListThis.setLocal(local);
					list.add(i + 1, resourceListDown);
					list.add(i, resourceListThis);
					resourceListRepository.upLocal(resourceListDown.getId(), resourceListDown.getLocal());
					resourceListRepository.upLocal(resourceListThis.getId(), resourceListThis.getLocal());
					break;
				}
			}
			request.getSession().setAttribute("listr", list);
		}
		return resourceListThis;
	}

	/**
	 * 移到每页的最上边
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/min", method = RequestMethod.GET)
	public synchronized ResourceList moveMin(@PathVariable Long id, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ResourceList> list = (List<ResourceList>) request.getSession().getAttribute("listr");
		ResourceList resourceListThis = null;
		ResourceList resourceList = null;
		if (list != null && list.size() > 0) {
			int local = list.get(0).getLocal();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId() == id) {
					resourceListThis = list.get(i);
					resourceListThis.setLocal(local);
					list.add(i, resourceListThis);
					resourceListRepository.upLocal(id, local);
					break;
				}
				resourceList = list.get(i);
				resourceList.setLocal(list.get(i - 1).getLocal());
				list.add(i, resourceList);
				resourceListRepository.upLocal(resourceList.getId(), resourceList.getLocal());
			}
			request.getSession().setAttribute("listr", list);
		}
		return resourceListThis;
	}

	/**
	 * 移到每页的最下边
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/Max", method = RequestMethod.GET)
	public synchronized ResourceList moveMax(@PathVariable Long id, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ResourceList> list = (List<ResourceList>) request.getSession().getAttribute("listr");
		ResourceList resourceListThis = null;
		ResourceList resourceList = null;
		if (list != null && list.size() > 0) {
			int local = list.get(list.size() - 1).getLocal();
			for (int i = list.size(); i < 0; i--) {
				if (list.get(i).getId() == id) {
					resourceListThis = list.get(i);
					resourceListThis.setLocal(local);
					list.add(i, resourceListThis);
					resourceListRepository.upLocal(id, local);
					break;
				}
				resourceList = list.get(i);
				resourceList.setLocal(list.get(i - 1).getLocal());
				list.add(i, resourceList);
				resourceListRepository.upLocal(resourceList.getId(), resourceList.getLocal());
			}
			request.getSession().setAttribute("listr", list);
		}
		return resourceListThis;
	}
}
