package com.orderproject.util;

/*DTO (Data Transfer Object) to encapsulate the response data,
status code, and message. 
*/
public class ApiResponse<T> {

	private int statusCode;
	private String message;
	private T data;

	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiResponse(int statusCode, String message, T data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", message=" + message + ", data=" + data + "]";
	}

}
