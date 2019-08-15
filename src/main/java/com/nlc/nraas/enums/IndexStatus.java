package com.nlc.nraas.enums;

public enum IndexStatus {

	NOT_BUILD(0, "未建立"), SUSPEND(1, "暂停"), WAIT(2, "等待"), BUILDING(3, "建立中"), ALERM_SUSPEND(4, "报警暂停"), FINISH(5,
			"已完成"), ABNORMAL(6, "异常"), ABNORMAL_HIGHLIGHT(7, "异常高亮");
	private int code;
	private String name;

	IndexStatus(int code, String name) {
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

	@Override
	public String toString() {
		return this.name;
	}

	public static IndexStatus getMyEnum(String status) {
		for (IndexStatus st : IndexStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}

}
