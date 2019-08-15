package com.nlc.nraas.enums;

public enum IndexType {
	URL_INDEX(0, "url异常"), FULL_TEXT_INDEX(1, "空文件异常");
	private int code;
	private String name;

	IndexType(int code, String name) {
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

	public static IndexType getMyEnum(String status) {
		for (IndexType st : IndexType.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
