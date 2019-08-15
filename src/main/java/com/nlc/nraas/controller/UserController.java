package com.nlc.nraas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nlc.nraas.domain.User;
import com.nlc.nraas.enums.UserStatus;
import com.nlc.nraas.repo.UserRepository;
import com.nlc.nraas.tools.Method;
import com.nlc.nraas.tools.PageUtils;
import com.nlc.nraas.tools.StringTransform;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final Map<String, Integer> map = new HashMap<String, Integer>();
	private final Timer timer = new Timer();
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	private static boolean falg = true;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 修给用户密码并加密
	 * 
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value = "/upPwd", method = RequestMethod.PUT)
	public String upPassWord(String oldPwd, String newPwd) {
		if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			User user = getUser();
			if (!bCryptPasswordEncoder.matches(oldPwd, user.getPassword()))
				return "旧密码输入错误！";
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(newPwd));
			user.setUpdateAt(new Date());
			userRepository.save(user);
			return "修给成功！";
		} else {
			return "请输入密码！";
		}
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/checkName")
	public String checkName(String name) {
		if (userRepository.findByName(name) != null) {
			return "This user already exists";
		}
		return null;
	}

	/**
	 * 获得登录用户
	 * 
	 * @return
	 */
	@RequestMapping("/user")
	public User getUser() {
		return userRepository.findByName(getUserName());
	}

	@RequestMapping("/name")
	public String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name = "";
		if (principal instanceof UserDetails) {
			name = ((UserDetails) principal).getUsername();
		} else {
			name = principal.toString();
		}
		return name;
	}

	/**
	 * 用户分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<User> page(String name, String organizationId, String code, String nickName, String email,
			String status, String roleId, String createAt, Pageable pageable) {
		return userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name))
					list.add(cb.like(root.get("name"), "%" + name + "%"));
				int oid = StringTransform.TransfromInt(organizationId);
				if (oid != -1)
					list.add(cb.equal(root.join("organization").get("id"), oid));
				if (StringUtils.isNotBlank(code))
					list.add(cb.equal(root.get("code"), code));
				if (StringUtils.isNotBlank(nickName))
					list.add(cb.like(root.get("nickName"), "%" + nickName + "%"));
				if (StringUtils.isNotBlank(email))
					list.add(cb.like(root.get("email"), "%" + email + "%"));
				if (StringUtils.isNotBlank(status))
					list.add(cb.equal(root.get("status"), UserStatus.getMyEnum(status)));
				int rid = StringTransform.TransfromInt(roleId);
				if (rid != -1)
					list.add(cb.equal(root.join("roles").get("id"), rid));
				if (StringUtils.isNotBlank(createAt))
					try {
						list.add(cb.equal(root.get("createAt"), sdf.parse(createAt)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				Predicate[] predicates = new Predicate[list.size()];
				query.where(cb.and(list.toArray(predicates)));
				return null;
			}
		}, PageUtils.getPageRequest(pageable));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public User save(@RequestBody User user) {
		return userRepository.save(user);
	}

	@RequestMapping(value = "/{id}/up", method = RequestMethod.PUT)
	public User upUser(@PathVariable Long id, @RequestBody User user) {
		User u = getUser(id);
		boolean falg = false;
		if (user.getEmail() != null) {
			u.setEmail(user.getEmail());
			falg = true;
		}
		if (StringUtils.isNotBlank(user.getCode()) && user.getCode().length() == 2) {
			u.setCode(user.getCode());
			falg = true;
		}
		if (StringUtils.isNotBlank(user.getNickName())) {
			u.setNickName(user.getNickName());
			falg = true;
		}
		if (user.getRoles() != null && user.getRoles().size() > 0) {
			u.setRoles(user.getRoles());
			falg = true;
		}
		if (user.getCreateAt() != null) {
			u.setCreateAt(user.getCreateAt());
			falg = true;
		}
		if (user.getOrganization() != null) {
			u.setOrganization(user.getOrganization());
			falg = true;
		}
		if (falg) {
			u.setUpdateAt(new Date());
		}
		return userRepository.save(u);
	}

	@RequestMapping(value = "/{id}/upStatus", method = RequestMethod.PUT)
	public User upStatus(@PathVariable Long id) {
		User u = userRepository.findOne(id);
		if (u.getStatus().equals(UserStatus.DISABLE)) {
			u.setStatus(UserStatus.ENABLE);
		} else {
			u.setStatus(UserStatus.DISABLE);
		}
		return userRepository.save(u);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteOne(@PathVariable Long id){
		userRepository.delete(id);
		return null;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Object delete(@RequestBody List<User> list){
		userRepository.delete(list);
		return null;
	}
	/**
	 * 自动生成任务id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTid", method = RequestMethod.GET)
	public synchronized String getTid() {
		if (falg) {
			setMap();
			falg = false;
		}
		User user = getUser();
		String code = user.getOrganization().getCode();
		String name = user.getName();
		String userCode = user.getCode();
		int n = 0;
		if (map.get(name) == null) {
			map.put(name, 1);
		} else {
			n = map.get(name);
			map.put(name, n + 1);
		}
		return Method.getTaskId(code, userCode, n);
	}

	/**
	 * 定时执行每天流水号更新
	 */
	private void setMap() {
		try {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					for (String key : map.keySet()) {
						map.put(key, 0);
					}
				}
			}, sdf1.parse("24:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
