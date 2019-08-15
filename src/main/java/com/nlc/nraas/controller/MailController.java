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

import com.nlc.nraas.domain.Mail;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.repo.MailRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 用户来信
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class MailController {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MailRepository mailRepository;

	@RequestMapping(value = "/mails", method = RequestMethod.GET)
	public Page<Mail> pageMail(String userId, String name, String email, String phone, String start, String stop,
			Pageable pageable) {
		return mailRepository.findAll(new Specification<Mail>() {

			@Override
			public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(email))
					list.add(cb.equal(root.get("email"), email));
				int uid = StringTransform.TransfromInt(userId);
				if (uid != -1)
					list.add(cb.equal(root.join("user").get("id"), uid));
				if (StringUtils.isNotBlank(phone))
					list.add(cb.equal(root.get("phone"), phone));
				if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(stop))
					try {
						list.add(cb.between(root.get("letterAt"), sdf.parse(start), sdf.parse(stop)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/mails/notRead", method = RequestMethod.GET)
	public int getCount() {
		return mailRepository.findByStatus(ReadStatus.NOT_READ).size();
	}

	@RequestMapping(value = "/mails/{id}", method = RequestMethod.GET)
	public Mail getMail(@PathVariable Long id) {
		return mailRepository.findOne(id);
	}

	@RequestMapping(value = "/mails", method = RequestMethod.POST)
	public Mail save(@RequestBody Mail mail) {
		return mailRepository.save(mail);
	}

	@RequestMapping(value = "/mails", method = RequestMethod.PUT)
	public Mail update(@RequestBody Mail mail) {
		return mailRepository.save(mail);
	}

	@RequestMapping(value = "/mails/delete", method = RequestMethod.POST)
	public Object deleteMail(@RequestBody List<Mail> list) {
		mailRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/mails/{id}", method = RequestMethod.DELETE)
	public Object deleteMail(@PathVariable Long id) {
		mailRepository.delete(id);
		return null;
	}
}
