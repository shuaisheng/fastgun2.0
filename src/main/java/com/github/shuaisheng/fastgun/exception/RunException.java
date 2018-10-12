package com.github.shuaisheng.fastgun.exception;

import com.github.shuaisheng.fastgun.enums.IStatusEnums;

public class RunException extends  RuntimeException{
		
		private static final long serialVersionUID = -2154652971880150123L;
			
			private  Integer code;

	        public  RunException(IStatusEnums statusEnums) {
	            super(statusEnums.getMsg() );
	            this.code=statusEnums.getCode();
	        }

	        public RunException(Integer code,String Msg) {
	            super(Msg);
	            this.code = code;
	        }
	    }
