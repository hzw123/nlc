package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.TaskStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;
import com.nlc.nraas.tools.JsonLongTimeSerializer;

/***
 * 采集任务
 * 
 * @author Dell
 *
 */
@Entity
@Table
public class Task {
	/** 任务id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 路径 */
	@Column(nullable = false)
	@NotEmpty(message = "url不能为空")
	private String url;
	/** 任务名称 */
	@NotEmpty(message = "任务名称不能为空")
	@Column(unique = true, nullable = false)
	private String name;
	/** 任务id ID规则由23位数字和英文字符组成：机构代码4位，年4位，月2位，日2位，时2位，分2位，秒2位，操作人员代码2位，流水号3位 */
	@NotEmpty(message = "任务id不能为空")
	@Column(unique = true, nullable = false)
	private String tid;
	/** 任务状态 */
	private TaskStatus status = TaskStatus.CREATED;
	/** 任务描述 */
	private String description;
	/** 备注 */
	private String remarks;
	/** 开始采集时间 */
	private Date startAt;
	/** 任务创建时间 */
	private Date createAt;
	/** 已采集时间 */
	private Long crawlTime;
	/** 已抓取文档数量 */
	private Long downloadedCount;
	/** 已发现总文档数量 */
	private Long discoveredCount;
	/** 未下载总文档数量 */
	private Long notDownloadedCount;
	/** 已抓取文档大小 */
	private Long totalData;
	/** 当前下载速度 */
	private Long currentKbRate;
	/** 平均下载速度 */
	private Long avgKbRate;
	/** heritrix 进行抓取时创建的唯一jobid */
	private String jobId;
	/** 是否自动开始抓取任务 */
	private Boolean autoStart = true;
	/** 完成时间或终止时间 */
	private Date completeAt;
	/** 创建任务的用户 */
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	/** 所选择的服务器 */
	@OneToOne(fetch = FetchType.EAGER)
	private Server server;
	/** 线程即heritrix中的实例 */
	@OneToOne(fetch = FetchType.EAGER)
	private ServerThread serverThread;
	/** 抓取任务的heritrix模版 */
	@OneToOne(fetch = FetchType.EAGER)
	private Profile profile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@JsonSerialize(using=JsonLongTimeSerializer.class)
	public Long getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(Long crawlTime) {
		this.crawlTime = crawlTime;
	}

	public Long getDownloadedCount() {
		return downloadedCount;
	}

	public void setDownloadedCount(Long downloadedCount) {
		this.downloadedCount = downloadedCount;
	}

	public Long getDiscoveredCount() {
		return discoveredCount;
	}

	public void setDiscoveredCount(Long discoveredCount) {
		this.discoveredCount = discoveredCount;
	}

	public Long getCurrentKbRate() {
		return currentKbRate;
	}

	public void setCurrentKbRate(Long currentKbRate) {
		this.currentKbRate = currentKbRate;
	}

	public Long getAvgKbRate() {
		return avgKbRate;
	}

	public void setAvgKbRate(Long avgKbRate) {
		this.avgKbRate = avgKbRate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ServerThread getServerThread() {
		return serverThread;
	}

	public void setServerThread(ServerThread serverThread) {
		this.serverThread = serverThread;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Boolean getAutoStart() {
		return autoStart;
	}

	public void setAutoStart(Boolean autoStart) {
		this.autoStart = autoStart;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCompleteAt() {
		return completeAt;
	}

	public void setCompleteAt(Date completeAt) {
		this.completeAt = completeAt;
	}

	public Long getTotalData() {
		return totalData;
	}

	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	public Long getNotDownloadedCount() {
		return notDownloadedCount;
	}

	public void setNotDownloadedCount(Long notDownloadedCount) {
		this.notDownloadedCount = notDownloadedCount;
	}
}
