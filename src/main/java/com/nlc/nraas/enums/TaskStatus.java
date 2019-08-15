package com.nlc.nraas.enums;

/***
 * 采集任务状
 */
public enum TaskStatus {

	CREATED(0, "未开始"), RUNNING(1, "采集中"), PAUSED(2, "暂停"), FINISHED_ABNORMAL(3, "采集中异常"), PENDING(4,
			"等待中"), PAUSED_ABNORMAL(5, "暂停异常"), FINISHED(6, "已完成"), ABORTED(7, "已终止");
	/** Job was terminted by user input while crawling */

	private int code;
	private String name;

	TaskStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static TaskStatus getMyEnum(String status) {
		for (TaskStatus st : TaskStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
