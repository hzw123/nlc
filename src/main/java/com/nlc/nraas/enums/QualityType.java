package com.nlc.nraas.enums;

public enum QualityType {

	QUALIFIED(0, "合格"),
	NOT_QUALITY(1, ""),
	QUALIFIED_SAVE(2, "合格已储存"),
	NOT_QUALIFIED(3, "不合格");
	private int code;
	private String name;

	QualityType(int code, String name) {
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

	public static QualityType getMyEnum(String status) {
		for (QualityType st : QualityType.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
