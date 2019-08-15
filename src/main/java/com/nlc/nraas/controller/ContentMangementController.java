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

import com.nlc.nraas.domain.CataloQualityOk;
import com.nlc.nraas.domain.HistoryList;
import com.nlc.nraas.enums.HistoryListStatus;
import com.nlc.nraas.enums.PushStatus;
import com.nlc.nraas.repo.CataloQualityOkRepository;
import com.nlc.nraas.repo.HistoryListRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 内容管理模块
 * 
 * @author Dell
 */
@RestController
@RequestMapping("/api")
public class ContentMangementController {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private CataloQualityOkRepository cqo;
	@Autowired
	private HistoryListRepository historyListRepository;

	@RequestMapping(value = "/cataloQualityOks/{id}", method = RequestMethod.GET)
	public CataloQualityOk getCataloQualityOk(@PathVariable Long id) {
		return cqo.findOne(id);
	}

	@RequestMapping(value = "/cataloQualityOks", method = RequestMethod.POST)
	public CataloQualityOk save(@RequestBody CataloQualityOk cataloQualityOk) {
		return cqo.save(cataloQualityOk);
	}

	@RequestMapping(value = "/cataloQualityOks", method = RequestMethod.PUT)
	public CataloQualityOk update(@RequestBody CataloQualityOk cataloQualityOk) {
		return cqo.save(cataloQualityOk);
	}

	@RequestMapping(value = "/cataloQualityOks/delete", method = RequestMethod.POST)
	public Object deleteCataloQualityOk(@RequestBody List<CataloQualityOk> list) {
		cqo.delete(list);
		return null;
	}

	@RequestMapping(value = "/cataloQualityOks/{id}", method = RequestMethod.DELETE)
	public Object deleteCataloQualityOk(@PathVariable Long id) {
		cqo.delete(id);
		return null;
	}

	/**
	 * 编目质检合格任务列表分页
	 * 
	 * @param name
	 *            任务名称
	 * @param uid
	 *            任务ID
	 * @param status
	 * @param otherName
	 * @param tsName
	 * @param webAdrees
	 * @param url
	 * @param falg
	 * @param specialName
	 * @param specialStartTime
	 * @param specialStoptime
	 * @param pushTime
	 * @param pushAdress
	 * @param createName
	 * @param language
	 * @param nationality
	 * @param resourceFormatid
	 * @param level
	 * @param code
	 * @param organization
	 * @param note
	 * @param acquisitionAt
	 * @param info
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/cataloQualityOks", method = RequestMethod.GET)
	public Page<CataloQualityOk> page(String name, String tid, String status, String otherName, String tsName,
			String webAdrees, String url, String falg, String specialName,String specialAt,
			String pushTime, String pushAdress, String createName, String language,
			String nationality, String resourceType, String level, String code, String organization, String note,
			String acquisitionAt, String info, Pageable pageable) {
		return cqo.findAll(new Specification<CataloQualityOk>() {
			@Override
			public Predicate toPredicate(Root<CataloQualityOk> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
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
					if(StringUtils.isNotBlank(specialAt)){
						list.add(cb.lessThan(root.get("specialStopAt"), sdf.parse(specialAt)));
						list.add(cb.greaterThan(root.get("specialStartAt"), sdf.parse(specialAt)));
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
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/historyLists/{id}", method = RequestMethod.GET)
	public HistoryList getHistoryList(@PathVariable Long id) {
		return historyListRepository.findOne(id);
	}

	@RequestMapping(value = "/historyLists", method = RequestMethod.POST)
	public HistoryList save(@RequestBody HistoryList historyList) {
		return historyListRepository.save(historyList);
	}

	@RequestMapping(value = "/historyLists", method = RequestMethod.PUT)
	public HistoryList update(@RequestBody HistoryList historyList) {
		return historyListRepository.save(historyList);
	}

	@RequestMapping(value = "/historyLists/delete", method = RequestMethod.POST)
	public Object deleteHistoryList(@RequestBody List<HistoryList> list) {
		historyListRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/historyLists/{id}", method = RequestMethod.DELETE)
	public Object deleteHistoryList(@PathVariable Long id) {

		historyListRepository.delete(id);
		return null;
	}

	/**
	 * 发布历史分页
	 * 
	 * @param name
	 * @param address
	 * @param status
	 * @param start
	 * @param stop
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/historyLists", method = RequestMethod.GET)
	public Page<HistoryList> page(String name, String address, String status, String start, String stop,
			Pageable pageable) {
		return historyListRepository.findAll(new Specification<HistoryList>() {

			@Override
			public Predicate toPredicate(Root<HistoryList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("publishName"), "%" + name + "%"));
				if (StringUtils.isNotBlank(address))
					list.add(cb.equal(root.get("publishAddr"), address));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("publishStatus"), HistoryListStatus.getMyEnum(status)));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.get("publishAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}
}
