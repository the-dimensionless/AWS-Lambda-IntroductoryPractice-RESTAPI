package com.handy.aws.samplefunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import com.google.gson.Gson;
import com.handy.aws.samplefunctions.pojos.Product;

public class InventoryFindFunction implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        
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
        
        return products[0].toString();
    }

}
