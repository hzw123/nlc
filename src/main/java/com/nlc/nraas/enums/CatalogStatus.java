package com.nlc.nraas.enums;

public enum CatalogStatus {

	QUALIFIED(0, "合格"), NOT_CATALOG(1, "未编目"), NOT_CHECK(2, "未质检");
	private int code;
	private String name;

	CatalogStatus(int code, String name) {
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

	public static CatalogStatus getMyEnum(String status) {
		for (CatalogStatus st : CatalogStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}
}
