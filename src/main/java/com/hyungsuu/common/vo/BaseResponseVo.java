package com.hyungsuu.common.vo;


import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class BaseResponseVo implements Serializable {

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@NotNull
	private String code = "";

    @NotNull
    private String message;

	public void setSuccess() {
		this.code = "0000";
		this.message = "success";
	}

   public void setFail(String code, String message) {
		this.code = code;
		this.message = message;   
   }
}
