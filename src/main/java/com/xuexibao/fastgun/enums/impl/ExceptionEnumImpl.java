package com.xuexibao.fastgun.enums.impl;

import com.xuexibao.fastgun.enums.IStatusEnums;

public enum ExceptionEnumImpl implements IStatusEnums{
	
	UNKNOW_ERROR(100000,"未知错误"),
	ERROR_PARAME(100001,"参数错误"),
	EMPTY_PARAME(100002,"参数为空");
	
	private int code;
	private String msg;
	
	private ExceptionEnumImpl(int code, String msg) {
		this.code =code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
