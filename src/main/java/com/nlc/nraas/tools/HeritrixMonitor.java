package com.nlc.nraas.tools;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.Attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nlc.nraas.domain.Task;
import com.nlc.nraas.enums.TaskStatus;
import com.nlc.nraas.repo.TaskRepository;

public class HeritrixMonitor implements Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private TaskRepository taskRepository;
	private ExecutorService exec;
	private String host;
	private String port;
	private String beanName;
	private long previousTaskId = -1;

	public HeritrixMonitor(TaskRepository taskRepository, ExecutorService exec, String host, String port, String beanName) {
		this.exec = exec;
		this.taskRepository = taskRepository;
		this.host = host;
		this.port = port;
		this.beanName = beanName;
	}

	@Override
	public void run() {
		try {
			List<Attribute> attrs = HeritrixJmxClient.getAttributes(host, port, beanName, new String[]{"CurrentJob"});
			if (attrs.get(0).getValue() != null) {		//当前有正在运行的任务
				String jobName = attrs.get(0).getValue().toString();
				Task task = taskRepository.findByName(jobName.substring(0, jobName.indexOf("-")));
				if (task != null) {
					
					//获取相关信息
					List<Attribute> jobAttrs = HeritrixJmxClient.getJobReport(host,
							port, beanName, task.getName(),
							task.getJobId());
			        for (Attribute att : jobAttrs) {
			            Object value = att.getValue();
			            switch (att.getName()) {
			            case "CrawlTime":
			                if (value != null) {
			                	Long elapsedSeconds = (Long) value;
			                    task.setCrawlTime(elapsedSeconds);
			                }
			                break;
			            case "CurrentDocRate":
			                break;
			            case "CurrentKbRate":
			                // NB Heritrix seems to store the average value in
			                // KbRate instead of CurrentKbRate...
			                // Inverse of doc rates.
			                if (value != null) {
			                	Long currentKbRate = (Long) value;
			                    task.setCurrentKbRate(currentKbRate);
			                }
			                break;
			            case "DiscoveredCount":
			                if (value != null) {
			                	Long discoveredCount = (Long) value;
			                    task.setDiscoveredCount(discoveredCount);
			                }
			                break;
			            case "DocRate":
			                break;
			            case "DownloadedCount":
			                if (value != null) {
			                	Long downloadedCount = (Long) value;
			                	task.setDownloadedCount(downloadedCount);
			                }
			                break;
			            case "FrontierShortReport":
			                break;
			            case "KbRate":
			                // NB Heritrix seems to store the average value in
			                // KbRate instead of CurrentKbRate...
			                // Inverse of doc rates.
			                if (value != null) {
			                    Long kbRate = (Long) value;
			                    task.setAvgKbRate(kbRate);
			                }
			                break;
			            case "Status":
			                if (value != null) {
			                    String newStatus = (String) value;
			                    if ("PAUSED".equals(newStatus)) {
			                    	task.setStatus(TaskStatus.PAUSED);
			                    } else if ("RUNNING".equals(newStatus)) {
			                    	task.setStatus(TaskStatus.RUNNING);
			                    } else if ("FINISHED".equals(newStatus)) {
			                    	task.setStatus(TaskStatus.FINISHED);
			                    } else {
			                    	logger.error("\n==================Unexpected status===================== -> : "+newStatus);
			                    }
			                }
			                break;
			            case "ThreadCount":
			                break;
			            case "ThreadsShortReport":
			                break;
			            case "TotalData":
			            	if (value != null) {
			                    Long totalData = (Long) value;
			                    task.setTotalData(totalData);
			                }
			                break;
			            case "UID":
			                break;
			            default:
			                logger.error("Unhandled attribute: " + att.getName());
			            }
			        }
			        task.setNotDownloadedCount(task.getDiscoveredCount() - task.getDownloadedCount());
			        taskRepository.save(task);
			        
			        if (previousTaskId < 0) {
			        	previousTaskId = task.getId();
			        }
			        
			        //如果当前任务id不同，说明之前的任务已经完成或者终止， 需要更新之前的任务的状态
			        if (previousTaskId > 0 && previousTaskId != task.getId()) {
			        	
			        	updateTaskStatus(taskRepository, previousTaskId);
				        previousTaskId = task.getId();
					}
				} else {
					logger.error("Task does not found by name: " + jobName.substring(0, jobName.indexOf("-")));
				}
			} else {	//没有正在运行的任务
				if (previousTaskId > 0) {	//之前有任务，，需要更新之前的任务的状态
					updateTaskStatus(taskRepository, previousTaskId);
					previousTaskId = -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			exec.shutdown();
//			exec = null;
		}
	}
	
	private void updateTaskStatus(TaskRepository taskRepository, long previousTaskId) throws Exception {
		
		logger.debug("\n================ 检测到某一任务已完成 ================= ->" + previousTaskId);
		Task previousTask = taskRepository.findById(previousTaskId);
		if (previousTask != null) {
			//查询已经完成的任务的信息，获取其终止状态
			/**
			 	Crawl Name: t111111
				Crawl Status: Finished
				Duration Time: 2m29s537ms
				Total Seeds Crawled: 1
				Total Seeds not Crawled: 0
				Total Hosts Crawled: 2
				Total Documents Crawled: 62
				Documents Crawled Successfully: 62
				Novel Documents Crawled: 62
				Processed docs/sec: 0.42
				Bandwidth in Kbytes/sec: 2
				Total Raw Data Size in Bytes: 359557 (351 KB) 
				Novel Bytes: 359557 (351 KB)
			 */
			String endReport = (String) HeritrixJmxClient.executeOperation(host, port, beanName, "crawlendReport", previousTask.getJobId(), "crawl-report");
			Pattern pattern = Pattern.compile("(.*): (.*)");  
			for (String line : endReport.split("\n")) {
				Matcher matcher = pattern.matcher(line);
				matcher.matches();
				String name = matcher.group(1).trim();
				String value = matcher.group(2).trim();
				switch (name) {
				case "Crawl Status":
					logger.debug("\n====== Crawl Status ======= -> :" + value);
					if ("Finished".equals(value)) {
						previousTask.setStatus(TaskStatus.FINISHED);
					} else if ("Finished - Ended by operator".equals(value)) {
						previousTask.setStatus(TaskStatus.ABORTED);
					} else if ("Finished - Abnormal exit from crawling".equals(value)) {
						previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
					} else if ("Finished - Timelimit hit".equals(value)) {
						previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
					} else if ("Finished - Maximum amount of data limit hit".equals(value)) {
						previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
					} else if ("Finished - Maximum number of documents limit hit".equals(value)) {
						previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
					} else {
						logger.debug("\n====== Unexpected Crawl Status ======= -> :" + value);
						previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
					}
					break;
				case "Total Documents Crawled":
					if (value != null) {
						Long downloadedCount = Long.parseLong(value);
						previousTask.setDownloadedCount(downloadedCount);
						previousTask.setNotDownloadedCount(previousTask.getDiscoveredCount() - downloadedCount);
					}
					break;
				case "Total Raw Data Size in Bytes":
					if (value != null) {
						Long totalData = Long.parseLong(value.split(" ")[0]);
						previousTask.setTotalData(totalData);
					}
					break;
				}
			}
			
			previousTask.setCompleteAt(new Date());
			taskRepository.save(previousTask);
			
//			TabularData doneJobs =  (TabularData) HeritrixJmxClient.executeOperation(host, port, beanName, "completedJobs");
//			CompositeData cdata = doneJobs.get(new String[]{previousTask.getJobId()});
//			if (cdata != null) {
//				String status = cdata.get("status").toString();
//				logger.debug("\n======completedJobs======= -> :" + status);
//				if ("Finished".equals(status)) {
//					previousTask.setStatus(TaskStatus.FINISHED);
//				} else if ("Finished - Ended by operator".equals(status)) {
//					previousTask.setStatus(TaskStatus.ABORTED);
//				} else if ("Finished - Abnormal exit from crawling".equals(status)) {
//					previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
//				} else if ("Finished - Timelimit hit".equals(status)) {
//					previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
//				} else if ("Finished - Maximum amount of data limit hit".equals(status)) {
//					previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
//				} else if ("Finished - Maximum number of documents limit hit".equals(status)) {
//					previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
//				} else {
//					previousTask.setStatus(TaskStatus.FINISHED_ABNORMAL);
//				}
//				taskRepository.save(previousTask);
//			}
		}
	}
}