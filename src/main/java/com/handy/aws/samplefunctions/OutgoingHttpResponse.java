package com.handy.aws.samplefunctions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.handy.aws.samplefunctions.pojos.Product;

public class OutgoingHttpResponse {
	
	private String body;
	private String statusCode = "200";
	private Map<String, String> headers = new HashMap<String, String>();
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public OutgoingHttpResponse(Product product) {
		this();
		Gson gson = new Gson();
		this.body = gson.toJson(product);
	}
	public OutgoingHttpResponse() {
		super();
		this.headers.put("Content-Type", "application/json");
	}

}
