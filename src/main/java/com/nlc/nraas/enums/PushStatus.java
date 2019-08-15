package com.nlc.nraas.enums;

public enum PushStatus {
	PUSH(0, "发布"), NOT_PUSH(1, "未发布");
	private int code;
	private String name;

	PushStatus(int code, String name) {
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

	public static PushStatus getMyEnum(String status) {
		for (PushStatus st : PushStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
