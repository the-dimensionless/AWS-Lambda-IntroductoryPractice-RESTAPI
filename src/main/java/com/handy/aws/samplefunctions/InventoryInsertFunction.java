package com.handy.aws.samplefunctions;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.handy.aws.samplefunctions.pojos.Product;
import com.handy.aws.samplefunctions.wrappers.HttpRequest;
import com.handy.aws.samplefunctions.wrappers.OutgoingHttpResponse;

public class InventoryInsertFunction 
extends InventoryS3Client
implements RequestHandler<HttpRequest, OutgoingHttpResponse> {

	@Override
	public OutgoingHttpResponse handleRequest(HttpRequest req, Context context) {
		context.getLogger().log("input : " + req);
		
		String body = req.getBody();
		
		Gson gson = new Gson();
		Product newProduct = gson.fromJson(body, Product.class);
		
		List<Product> listOfProducts = getAllProductsList();
		listOfProducts.add(newProduct);
		
		if (updateAllProducts(listOfProducts)) {
			return new OutgoingHttpResponse();
		}
		
		OutgoingHttpResponse res = new OutgoingHttpResponse();
		res.setStatusCode("500");
		return res;
	}

  
}