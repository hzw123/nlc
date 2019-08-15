package com.nlc.nraas.enums;

public enum SaveStatus {

	IN_CONFIRMATION(0, "验证中"), VERIFY_INTERRUPT(1, "验证中断"), IN_SAVE(2, "保存中"), STORE(3, "已储存"), NOT_STORE(4, "未储存");
	private int code;
	private String name;

	SaveStatus(int code, String name) {
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
