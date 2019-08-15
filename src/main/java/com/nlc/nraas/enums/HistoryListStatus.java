package com.nlc.nraas.enums;

public enum HistoryListStatus {
	SUCCESS(0, "成功"), DEFEATED(1, "失败"), RELEASE(2, "发布中");
	private int code;
	private String name;

	HistoryListStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static HistoryListStatus getMyEnum(String status) {
		for (HistoryListStatus st : HistoryListStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
