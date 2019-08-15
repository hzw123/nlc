package com.nlc.nraas.enums;

/***
 * 服务器状态
 * 
 * @author Dell
 *
 */
public enum ServerStatus {
	NORMAL(0, "正常"), OFF_LINE(1, "离线"), ABNORMAL(2, "异常"), COLLECT(3, "采集中"), FREE(4, "空闲");
	private int code;
	private String name;

	ServerStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ServerStatus getMyEnum(String status) {
		for (ServerStatus st : ServerStatus.values()) {
			if (st.toString().equals(status)||st.name().equals(status)||status.equals(String.valueOf(st.getCode()))) {
				return st;
			}
		}
		return null;
	}

}
