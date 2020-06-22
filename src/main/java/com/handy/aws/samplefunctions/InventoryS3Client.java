package com.handy.aws.samplefunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.handy.aws.samplefunctions.pojos.Product;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

public class InventoryS3Client {
	
	/**
	 * Fetched all products to be used for further processing
	 * @return
	 */
	protected Product[] getAllProducts() {
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
		
		return products;
	}
	
	/**
	 * Utility to convert array to ArrayList
	 * @return ArrayList<Product>
	 */
	protected ArrayList<Product> getAllProductsList(){
		return new ArrayList<Product>(Arrays.asList(getAllProducts()));
	}	
	
	/**
	 * Update and Overrides file
	 * @param products : Product
	 * @return boolean
	 */
	protected boolean updateAllProducts(Product [] products) {
		
		Gson gson = new Gson(); 
        String jsonString = gson.toJson(products);
		
		
		Region region = Region.AP_SOUTH_1;
        S3Client s3Client = S3Client.builder().region(region).build();
        
        PutObjectResponse putResponse = s3Client.putObject(PutObjectRequest.builder()
        		.bucket("handy-inventory-data-sample")
        		.key("tool-catalog.json").build(),
        		RequestBody.fromString(jsonString));
        
        return putResponse.sdkHttpResponse().isSuccessful();
        
	}
	
	/**
	 * Utility to convert List to array
	 * @param productList : List<Product>
	 * @return boolean
	 */
	protected boolean updateAllProducts(List<Product> productList) {
		Product [] products = (Product[]) productList.toArray(new Product[productList.size()]);
		return updateAllProducts(products);
	}
	

}
