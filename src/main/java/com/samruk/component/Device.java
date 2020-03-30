package com.samruk.component;

public class Device {
	
	private int id;
	private String category;
	private String device_name;
	private double cost;
	
	public Device() {}
	
	public Device(int id, String category, String device_name, double cost) {
		super();
		this.id = id;
		this.category = category;
		this.device_name = device_name;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
	

}
