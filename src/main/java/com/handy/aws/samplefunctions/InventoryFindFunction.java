package com.handy.aws.samplefunctions;

import java.util.Arrays;
import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.handy.aws.samplefunctions.pojos.Product;
import com.handy.aws.samplefunctions.wrappers.IncomingHttpRequest;
import com.handy.aws.samplefunctions.wrappers.OutgoingHttpResponse;

public class InventoryFindFunction 
extends InventoryS3Client
implements RequestHandler<IncomingHttpRequest, OutgoingHttpResponse> {

    @Override
    public OutgoingHttpResponse handleRequest(IncomingHttpRequest incomingRequest, Context context) {
        context.getLogger().log("Input: " + incomingRequest);
        
        String id = (String)incomingRequest.getQueryStringParameters().get("id");
        
        if (id.equals("all")) {
        	Product [] products = getAllProducts();
        	OutgoingHttpResponse response = new OutgoingHttpResponse(products);
        	return response;
        }
        
        Integer productId = Integer.parseInt(id);
        
        Product product = getProductById(productId);
        
        return new OutgoingHttpResponse(product);
    }

	private Product getProductById(Integer productId) {
		Product [] products = getAllProducts();

		Optional<Product> optional = Arrays.stream(products).filter(product -> product.getId() == productId).findFirst();
		
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

}
