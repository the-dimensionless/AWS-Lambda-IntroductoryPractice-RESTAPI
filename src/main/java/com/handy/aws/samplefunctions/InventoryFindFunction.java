package com.handy.aws.samplefunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.handy.aws.samplefunctions.pojos.Product;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class InventoryFindFunction implements RequestHandler<IncomingHttpRequest, OutgoingHttpResponse> {

    @Override
    public OutgoingHttpResponse handleRequest(IncomingHttpRequest incomingRequest, Context context) {
        context.getLogger().log("Input: " + incomingRequest);
        
        String id = (String)incomingRequest.getQueryStringParameters().get("id");
        Integer productId = Integer.parseInt(id);
        
        Product product = getProductById(productId);
        
        return new OutgoingHttpResponse(product);
    }

	private Product getProductById(Integer productId) {
		
		// Specify Region of access
        Region region = Region.AP_SOUTH_1;
        
        // Build a client
        S3Client s3Client = S3Client.builder().region(region).build();
        
        // Store the response
        ResponseInputStream<?> objectData = s3Client.getObject(GetObjectRequest.builder()
        		.bucket("handy-inventory-data-sample")
        		.key("tool-catalog.json")
        		.build());
        
        InputStreamReader isr = new InputStreamReader(objectData);
        BufferedReader br = new BufferedReader(isr);
        
        // Reading json array with GSON
        
        Product [] products = null;
        
        Gson gson = new Gson();
        products = gson.fromJson(br, Product[].class);
        

		Optional<Product> optional = Arrays.stream(products).filter(product -> product.getId() == productId).findFirst();
		
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

}
