package org.octopus.dashboard.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {

	private String code;
	private String msg;

	public Message(String code) {
		this.code = code;
	}

	public Message(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Message message = (Message) o;

		return this.code.equals(message.code);
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
