package com.xuexibao.fastgun.enums.impl;

import com.xuexibao.fastgun.enums.IStatusEnums;

public enum ResultEnumImpl implements IStatusEnums {

		SUCCESS(0,"success");
		
		
		private int code;
		private String msg;
		
		private ResultEnumImpl(int code, String msg) {
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

