package com.handy.aws.samplefunctions.wrappers;

import java.util.Map;

/**
 * Marshals the input query parameter
 * to a Java Collection/POJO
 * 
 * @author sumitsingh
 *
 */
public class IncomingHttpRequest {
	
	Map<String, String> queryStringParameters;

	public Map<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}

	public void setQueryStringParameters(Map<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}
	
}
