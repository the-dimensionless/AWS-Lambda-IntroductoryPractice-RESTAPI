package com.handy.aws.samplefunctions.pojos;

public class Product {
	
	private int id;
	
	private String toolType;
	
	private String brand;
	
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product(int id, String toolType, String brand, int count) {
		super();
		this.id = id;
		this.toolType = toolType;
		this.brand = brand;
		this.count = count;
	}
	
	public Product() {
		
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", toolType=" + toolType + ", brand=" + brand + ", count=" + count + "]";
	}
	

}
