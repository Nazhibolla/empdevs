package com.samruk.component;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmpDeviceCount {
	
	int e_id;
	String name;
	int count;
	double totalCost;
	
	public EmpDeviceCount() {}
	public EmpDeviceCount(int e_id, String name, int count, double totalCost) {
		this.e_id = e_id;
		this.name = name;
		this.count = count;
		this.totalCost = totalCost;
	}

	public int getE_id() {
		return e_id;
	}

	public void setE_id(int e_id) {
		this.e_id = e_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}


	@Override
	public String toString() {
		return "EmpDeviceCount [e_id=" + e_id + ", name=" + name + ", count=" + count + ", totalCost=" + totalCost + "]";
	}
	
	
	
	

}
