package com.nlc.nraas.controller;

import java.text.ParseException;
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

import com.nlc.nraas.domain.CategoryList;
import com.nlc.nraas.domain.TaskSave;
import com.nlc.nraas.enums.SaveStatus;
import com.nlc.nraas.repo.CategoryListRepository;
import com.nlc.nraas.repo.TaskSaveRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

@RestController
@RequestMapping("/api")
public class SaveDataController {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private CategoryListRepository categoryListRepository;
	@Autowired
	private TaskSaveRepository taskSaveRepository;

	@RequestMapping(value = "/categoryLists", method = RequestMethod.GET)
	private Page<CategoryList> page(String name, Pageable pageable) {
		return categoryListRepository.findAll(new Specification<CategoryList>() {

			@Override
			public Predicate toPredicate(Root<CategoryList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(cb.like(root.get("name"), "%" + name + "%"));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/categoryLists/{id}", method = RequestMethod.GET)
	public CategoryList getCategoryList(@PathVariable Long id) {
		return categoryListRepository.findOne(id);
	}

	@RequestMapping(value = "/categoryLists", method = RequestMethod.POST)
	public CategoryList save(@RequestBody CategoryList categoryList) {
		return categoryListRepository.save(categoryList);
	}

	@RequestMapping(value = "/categoryLists", method = RequestMethod.PUT)
	public CategoryList update(@RequestBody CategoryList categoryList) {
		return categoryListRepository.save(categoryList);
	}

	@RequestMapping(value = "/categoryLists/{id}", method = RequestMethod.DELETE)
	public Object deleteCategoryList(@PathVariable Long id) {
		categoryListRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/categoryLists/delete", method = RequestMethod.POST)
	public Object deleteCategoryList(@RequestBody List<CategoryList> list) {
		categoryListRepository.delete(list);
		return null;
	}

	/**
	 * 
	 * @param taskTid
	 * @param taskName
	 * @param status
	 * @param dataTypeId
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/taskSaves", method = RequestMethod.GET)
	public Page<TaskSave> pageTaskSave(String taskTid, String taskName, String status, String dataTypeId, String start,
			String stop, Pageable pageable) {
		return taskSaveRepository.findAll(new Specification<TaskSave>() {

			@Override
			public Predicate toPredicate(Root<TaskSave> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(taskTid))
					list.add(cb.like(root
							.join("doneIndexList")
							.join("urlList")
							.join("task")
							.get("tid"), "%" + taskTid + "%"));
				if (StringUtils.isNotBlank(taskName))
					list.add(cb.like(root
							.join("doneIndexList")
							.join("urlList")
							.join("task")
							.get("name"), "%" + taskName + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("saveStatus"), SaveStatus.getMyEnum(status)));
				int cid = StringTransform.TransfromInt(dataTypeId);
				if (cid != -1)
					list.add(cb.equal(root.join("categoryList").get("id"), cid));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.get("saveAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/taskSaves/{id}", method = RequestMethod.GET)
	public TaskSave getTaskSave(@PathVariable Long id) {
		return taskSaveRepository.findOne(id);
	}

	@RequestMapping(value = "/taskSaves", method = RequestMethod.POST)
	public TaskSave save(@RequestBody TaskSave taskSave) {
		return taskSaveRepository.save(taskSave);
	}

	@RequestMapping(value = "/taskSaves", method = RequestMethod.PUT)
	public TaskSave update(@RequestBody TaskSave taskSave) {
		return taskSaveRepository.save(taskSave);
	}

	@RequestMapping(value = "/taskSaves/{id}", method = RequestMethod.DELETE)
	public Object deleteTaskSave(@PathVariable Long id) {
		taskSaveRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/taskSaves/delete", method = RequestMethod.POST)
	public Object deleteTaskSave(@RequestBody List<TaskSave> list) {
		taskSaveRepository.delete(list);
		return null;
	}
}
