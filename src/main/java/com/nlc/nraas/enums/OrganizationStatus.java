package com.nlc.nraas.enums;

public enum OrganizationStatus {
	START(0, "开启"), STOP(1, "停止");
	private int code;
	private String name;

	OrganizationStatus(int code, String name) {
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

	public static OrganizationStatus getMyEnum(String status) {
		for (OrganizationStatus st : OrganizationStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}

}
