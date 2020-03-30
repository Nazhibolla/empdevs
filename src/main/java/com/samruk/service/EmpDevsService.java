package com.samruk.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samruk.component.Department;
import com.samruk.component.EmpDeviceCount;
import com.samruk.component.EmpDevices;
import com.samruk.repository.EmpDevsDao;

@Service
public class EmpDevsService {
	
	@Autowired
	EmpDevsDao edDao;
	
	public List<EmpDeviceCount> getEmpList() throws SQLException{
		return edDao.getEmpList();
	}
	
	public String deleteEmployee(int e_id) {
		return edDao.deleteEmployee(e_id);
	}
	
	public EmpDevices getEmpDevices(int e_id){
		return edDao.getEmpDevices(e_id);
	}
	
	public String addEmployee(EmpDevices ed) {
		return edDao.addEmployee(ed);
	}
	
	public List<Department> getDeps() throws SQLException {
		return edDao.getDeps();
	}

}
