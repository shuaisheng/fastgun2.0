package com.xuexibao.fastgun.exception;

import com.xuexibao.fastgun.enums.IStatusEnums;

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
