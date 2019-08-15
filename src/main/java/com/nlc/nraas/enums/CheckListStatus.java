package com.nlc.nraas.enums;

public enum CheckListStatus {

	CREATE(0, "创建"), PAUSE(1, "暂停"), DONE(2, "完成"), FAILURE(3, "失败");
	private int code;
	private String name;

	CheckListStatus(int code, String name) {
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

	public static CheckListStatus getMyEnum(String status) {
		for (CheckListStatus st : CheckListStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
