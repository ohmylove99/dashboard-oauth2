package org.octopus.dashboard.api.support;

public class ErrorResult {

	public int code;
	public String message;
	public String error;

	public ErrorResult() {
	}

	public ErrorResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorResult(int code, String error, String message) {
		this.code = code;
		this.error = error;
		this.message = message;
	}
}