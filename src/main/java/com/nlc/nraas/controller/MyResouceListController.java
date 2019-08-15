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
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
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

import com.nlc.nraas.domain.MyResouceList;
import com.nlc.nraas.repo.MyResouceListRepository;
import com.nlc.nraas.tools.PageUtils;

@RequestMapping("/api/myResouceLists")
@RestClientTest
public class MyResouceListController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MyResouceListRepository myResouceListRepository;

	/**
	 * 移到最上边
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/min", method = RequestMethod.PUT)
	public synchronized MyResouceList min(@PathVariable Long id) {
		MyResouceList myResouceList = myResouceListRepository.findOne(id);
		if (myResouceList.getLocal() > 1) {
			List<MyResouceList> list = myResouceListRepository.before(myResouceListRepository.findOne(id).getLocal());
			for (int i = 0; i < list.size() - 1; i++) {
				myResouceListRepository.upLocal(list.get(i).getId(), list.get(i + 1).getLocal());
			}
			myResouceListRepository.upLocal(list.get(list.size() - 1).getId(), myResouceList.getLocal());
			myResouceListRepository.upLocal(id, 1);
			myResouceList.setLocal(1);
		}
		return myResouceList;
	}

	/**
	 * 移到最下边
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/max", method = RequestMethod.PUT)
	public synchronized MyResouceList max(@PathVariable Long id) {
		MyResouceList myResouceList = myResouceListRepository.findOne(id);
		if (myResouceListRepository.maxLocal() > myResouceList.getLocal()) {
			List<MyResouceList> list = myResouceListRepository.after(myResouceListRepository.findOne(id).getLocal());
			int local = list.get(list.size() - 1).getLocal();
			for (int i = 1; i < list.size(); i++) {
				myResouceListRepository.upLocal(list.get(i).getId(), list.get(i - 1).getLocal());
			}
			myResouceListRepository.upLocal(list.get(0).getId(), myResouceList.getLocal());
			myResouceListRepository.upLocal(id, local);
			myResouceList.setLocal(local);
		}
		return myResouceList;
	}

	/**
	 * 往上移动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.PUT)
	public synchronized MyResouceList moveUp(@PathVariable Long id) {
		MyResouceList myResouceList = myResouceListRepository.findOne(id);
		if (myResouceList.getLocal() > 1) {
			List<MyResouceList> list = myResouceListRepository.before(myResouceList.getLocal());
			MyResouceList re = list.get(list.size() - 1);
			int local = re.getLocal();
			myResouceListRepository.upLocal(re.getId(), myResouceList.getLocal());
			myResouceList.setLocal(local);
			myResouceListRepository.upLocal(id, local);
		}
		return myResouceList;
	}

	/**
	 * 往下移动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/down", method = RequestMethod.PUT)
	public synchronized MyResouceList moveDown(@PathVariable Long id) {
		MyResouceList myResouceList = myResouceListRepository.findOne(id);
		if (myResouceList.getLocal() < myResouceListRepository.maxLocal()) {
			List<MyResouceList> list = myResouceListRepository.after(myResouceList.getLocal());
			MyResouceList re = list.get(0);
			int local = re.getLocal();
			myResouceListRepository.upLocal(re.getId(), myResouceList.getLocal());
			myResouceList.setLocal(local);
			myResouceListRepository.upLocal(id, local);
		}
		return myResouceList;
	}

	/**
	 * 自定义资源列表分页查询
	 * 
	 * @param article
	 * @param author
	 * @param pushAt
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<MyResouceList> pageResouceList(String article, String author, String pushAt, Pageable pageable) {

		return myResouceListRepository.findAll(new Specification<MyResouceList>() {

			@Override
			public Predicate toPredicate(Root<MyResouceList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(article))
					list.add(cb.like(root.get("article"), article));
				if (StringUtils.isNotBlank(author))
					list.add(cb.equal(root.get("author"), author));
				if (StringUtils.isNotBlank(pushAt))
					try {
						list.add(cb.equal(root.get("PushAt"), sdf.parse(pushAt)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Direction.ASC, "local"))));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public MyResouceList getOrganization(@PathVariable Long id) {
		return myResouceListRepository.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public synchronized MyResouceList save(@RequestBody MyResouceList myResouceList) {
		myResouceList.setLocal(myResouceListRepository.maxLocal());
		return myResouceListRepository.save(myResouceList);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public MyResouceList update(@RequestBody MyResouceList myResouceList) {
		return myResouceListRepository.save(myResouceList);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object deleteCollectCompleteInform(@RequestBody List<MyResouceList> list) {
		myResouceListRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/{id}/del", method = RequestMethod.DELETE)
	public Object deleteCollectCompleteInform(@PathVariable Long id) {
		myResouceListRepository.delete(id);
		return null;
	}
}
