package com.samruk.component;

public class EmpDevices {
	
	private Employee employee;
	private Device[] devices;
	
	public EmpDevices() {}
	
	public EmpDevices(Employee employee, Device[] devices) {
		super();
		this.employee = employee;
		this.devices = devices;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Device[] getDevices() {
		return devices;
	}
	public void setDevices(Device[] devices) {
		this.devices = devices;
	}
	
	

}
