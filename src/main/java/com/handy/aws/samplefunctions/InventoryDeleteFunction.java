package com.handy.aws.samplefunctions;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.handy.aws.samplefunctions.pojos.Product;
import com.handy.aws.samplefunctions.wrappers.HttpRequest;
import com.handy.aws.samplefunctions.wrappers.OutgoingHttpResponse;

public class InventoryDeleteFunction 
extends InventoryS3Client
implements RequestHandler<HttpRequest, OutgoingHttpResponse> {

    @Override
    public OutgoingHttpResponse handleRequest(HttpRequest req, Context context) {
        context.getLogger().log("Input: " + req);
        
        String id = (String) req.getPathParameters().get("id");
        Integer productId = Integer.parseInt(id);
        
        List<Product> productList = getAllProductsList();
        
        boolean isDeleted = productList.removeIf(product -> product.getId() == productId);
        
        if (isDeleted) {
        	if (updateAllProducts(productList)) {
        		return new OutgoingHttpResponse();
        	}
        }
        
        OutgoingHttpResponse response = new OutgoingHttpResponse();
        response.setBody("No such entry/item exists to delete");
        response.setStatusCode("404");
        return response;
    }

}
