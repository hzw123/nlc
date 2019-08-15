package com.nlc.nraas.enums;

public enum AlertType {

	SERVER_ALERT(0, "服务器异常"), THREAD_ALERT(1, "线程异常"), INVALID_DOCUMENT(2, "无效文件"), NOT_MEMORY(3,
			"内存不足"), HARD_DIST_ERROR(4, "硬盘出错");
	private int code;
	private String name;

	AlertType(int code, String name) {
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

	public static AlertType getMyEnum(String status) {
		for (AlertType st : AlertType.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
