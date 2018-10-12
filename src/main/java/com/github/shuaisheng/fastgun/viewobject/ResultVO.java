package com.github.shuaisheng.fastgun.viewobject;


import lombok.Data;


	@Data
	public class ResultVO<T> {

	    /** 错误码. */
	    private  Integer code;

	    /**提示信息. */
	    private  String msg;

	    /**数据内容. */
	    private  T data;
	}
