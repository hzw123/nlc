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

import com.nlc.nraas.domain.Programa;
import com.nlc.nraas.domain.Template;
import com.nlc.nraas.enums.Status;
import com.nlc.nraas.repo.ProgramaRepository;
import com.nlc.nraas.repo.TemplateRepository;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

/**
 * 栏目管理模块
 * 
 * @author Dell
 *
 */
@RequestMapping("/api")
@RestController
public class ProgramasManageController {
	@Autowired
	private ProgramaRepository programaRepository;
	@Autowired
	private TemplateRepository templateRepository;

	@RequestMapping(value = "/programas/{id}/moveUp", method = RequestMethod.PUT)
	public synchronized Programa moveUpPrograma(@PathVariable Long id) {
		Programa p = programaRepository.findOne(id);
		if (p.getLocal() > 1) {
			int local = p.getLocal();
			Programa pb = programaRepository.getBefore(p.getPid(), p.getLocal());
			p.setLocal(pb.getLocal());
			programaRepository.upLocal(id, p.getLocal());
			programaRepository.upLocal(pb.getId(), local);
		}
		return p;
	}

	@RequestMapping(value = "/programas/{id}/moveDown", method = RequestMethod.PUT)
	public synchronized Programa moveDownPrograma(@PathVariable Long id) {
		Programa p = programaRepository.findOne(id);
		if (p.getLocal() < programaRepository.maxLocal(p.getPid())) {
			int local = p.getLocal();
			Programa pb = programaRepository.getAfter(p.getPid(), p.getLocal());
			p.setLocal(pb.getLocal());
			programaRepository.upLocal(id, p.getLocal());
			programaRepository.upLocal(pb.getId(), local);
		}
		return p;
	}

	@RequestMapping(value = "/programas/{id}/after", method = RequestMethod.GET)
	public List<Programa> after(@PathVariable Long id) {
		Programa p = programaRepository.findOne(id);
		return programaRepository.after(p.getPid(), p.getLocal());
	}

	@RequestMapping(value = "/programas/{id}/before", method = RequestMethod.GET)
	public List<Programa> before(@PathVariable Long id) {
		Programa p = programaRepository.findOne(id);
		return programaRepository.before(p.getPid(), p.getLocal());
	}

	@RequestMapping(value = "/programas/{id}/moveAfter", method = RequestMethod.PUT)
	public synchronized Programa moveAfter(@PathVariable Long id, Long bid) {
		Programa p = programaRepository.findOne(id);
		long pid = p.getPid();
		Programa pb = programaRepository.findOne(bid);
		p.setPid(pb.getPid());
		List<Programa> list = programaRepository.after(pb.getPid(), pb.getLocal());
		int local=list.get(0).getLocal();
		if(list.size()>0){
			for (int i = 0; i < list.size() - 1; i++) {
				programaRepository.upLocal(list.get(i).getId(), list.get(i + 1).getLocal());
			}
			programaRepository.upLocal(list.get(list.size()-1).getId(), list.get(list.size()-1).getLocal() + 1);
		}
		p.setLocal(local);
		programaRepository.upLocal(id, p.getPid(), p.getLocal());
		programaRepository.upSet(pid, id, p.getPid());
		return p;
	}

	@RequestMapping(value = "/programas/{id}/moveBefore", method = RequestMethod.PUT)
	public synchronized Programa moveBefore(@PathVariable Long id, Long bid) {
		Programa p = programaRepository.findOne(id);
		long pid = p.getPid();
		Programa pb = programaRepository.findOne(bid);
		int local=pb.getLocal();
		List<Programa> list = programaRepository.after(pb.getPid(), pb.getLocal());
		programaRepository.upLocal(pb.getId(), list.get(0).getLocal());
		for (int i = 0; i < list.size(); i++) {
			System.err.println(list.get(i).getId()+"\t:"+list.get(i).getLocal());
		}
		if(list.size()>0){
			for (int i = 0; i < list.size() - 1; i++) {
				programaRepository.upLocal(list.get(i).getId(), list.get(i + 1).getLocal());
			}
			programaRepository.upLocal(list.get(list.size()-1).getId(), list.get(list.size()-1).getLocal() + 1);
		}
		p.setPid(pb.getPid());
		p.setLocal(local);
		programaRepository.upLocal(id, p.getPid(), p.getLocal());
		programaRepository.upSet(pid, id, p.getPid());
		return p;
	}

	/**
	 * 把一个放入另一个的下级
	 * @param id
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/programas/{id}/this", method = RequestMethod.PUT)
	public synchronized Programa movePidDown(@PathVariable Long id,Long pid){
		Programa p=programaRepository.findOne(id);
		int local=programaRepository.maxLocal(pid)+1;
		programaRepository.upLocal(id, pid,local );
		programaRepository.upSet(p.getPid(), id, pid);
		p.setPid(pid);
		p.setLocal(local);
		return p;
	}
	
	@RequestMapping(value = "/programas", method = RequestMethod.GET)
	public Page<Programa> pagePrograma(String name, String typeId, String programa, String template, String status,
			Pageable pageable) {
		return programaRepository.findAll(new Specification<Programa>() {
			@Override
			public Predicate toPredicate(Root<Programa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), Status.getMyEnum(status)));
				int tyid = StringTransform.TransfromInt(typeId);
				if (tyid != -1) {
					list.add(cb.equal(root.join("typeName").get("id"), tyid));
				}
				int t = StringTransform.TransfromInt(template);
				if (t != -1)
					list.add(cb.equal(root.join("template").get("id"), t));
				int p = StringTransform.TransfromInt(programa);
				if (p != -1)
					list.add(cb.equal(root.get("pid"), p));
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/programas/{id}", method = RequestMethod.GET)
	public Programa getPrograma(@PathVariable Long id) {
		return programaRepository.findOne(id);
	}

	@RequestMapping(value = "/programas", method = RequestMethod.POST)
	public synchronized Programa save(@RequestBody Programa programa) {
		programa.setLocal(programaRepository.maxLocal(programa.getPid()) + 1);
		Programa p = programaRepository.save(programa);
		if ((p.getProgramas() == null || p.getProgramas().size() == 0) && p.getPid() > 0) {
			programaRepository.addSet(programa.getPid(), p.getId());
		}
		return p;
	}

	@RequestMapping(value = "/programas", method = RequestMethod.PUT)
	public Programa update(@RequestBody Programa programa) {
		return programaRepository.save(programa);
	}

	@RequestMapping(value = "/programas/delete", method = RequestMethod.POST)
	public Object deletePrograma(@RequestBody List<Programa> list) {
		programaRepository.delete(list);
		return null;
	}

	@RequestMapping(value = "/programas/{id}", method = RequestMethod.DELETE)
	public String deletePrograma(@PathVariable Long id) {
		programaRepository.deleteSet(id);
		programaRepository.delete(id);
		return "delete success";
	}

	@RequestMapping(value = "/templates/{id}", method = RequestMethod.GET)
	public Template getTemplate(@PathVariable Long id) {
		return templateRepository.findOne(id);
	}

	@RequestMapping(value = "/templates", method = RequestMethod.POST)
	public Template save(@RequestBody Template template) {
		return templateRepository.save(template);
	}

	@RequestMapping(value = "/templates", method = RequestMethod.PUT)
	public Template update(@RequestBody Template template) {
		return templateRepository.save(template);
	}

	/**
	 * 删除当前
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/templates/{id}", method = RequestMethod.DELETE)
	public Object deleteTemplate(@PathVariable Long id) {
		templateRepository.delete(id);
		return null;
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/templates/delete", method = RequestMethod.POST)
	public Object deleteTemplate(@RequestBody List<Template> list) {
		templateRepository.delete(list);
		return null;
	}

	/**
	 * 全部删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/templates/delete/all", method = RequestMethod.DELETE)
	public Object deleteTemplate() {
		templateRepository.deleteAll();
		return null;
	}

	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public List<Template> pageTemplate(String name) {
		return templateRepository.findAll(new Specification<Template>() {

			@Override
			public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isNotBlank(name)) {
					query.where(cb.like(root.get("skinname"), "%" + name + "%"));
				}
				return null;
			}
		});
	}
}
