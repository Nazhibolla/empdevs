package com.samruk.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samruk.component.Department;
import com.samruk.component.Device;
import com.samruk.component.EmpDeviceCount;
import com.samruk.component.EmpDevices;
import com.samruk.component.Employee;
import com.samruk.repository.EmpDevsDao;
import com.samruk.service.EmpDevsService;

@RestController
public class AppServlet {
	
	@Autowired
	EmpDevsService edServ;
	
	@CrossOrigin
	@RequestMapping("/getEmpList")
	public String getEmpList() {
		JSONArray res = new JSONArray(); 
		try {
//			return eaDao.getEAList().toString();
			List<EmpDeviceCount> eacs = edServ.getEmpList();
			for(EmpDeviceCount eac : eacs) {
				JSONObject obj = new JSONObject();
				obj.append("id", eac.getE_id());
				obj.append("name", eac.getName());
				obj.append("count", eac.getCount());
				obj.append("totalCost", eac.getTotalCost());
				res.put(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}
	
	@CrossOrigin
	@RequestMapping("/getDeps")
	public String getDeps() {
		JSONArray res = new JSONArray();
		try {
			List<Department> deps = edServ.getDeps();
			for(Department dep: deps) {
				JSONObject obj = new JSONObject();
				obj.append("id", dep.getId());
				obj.append("name", dep.getName());
				res.put(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res.toString();
	}	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/getEmpDevices")
	public String getEmpDevices(@RequestBody Employee empl) {
		JSONObject res = new JSONObject(); 
		EmpDevices ed = edServ.getEmpDevices(empl.getId());
		JSONObject emp = new JSONObject();
		emp.append("id", ed.getEmployee().getId());
		emp.append("fullName", ed.getEmployee().getFullName());
		emp.append("email", ed.getEmployee().getEmail());
		emp.append("mobile", ed.getEmployee().getMobile());
		emp.append("gender", ed.getEmployee().getGender());
		emp.append("department", ed.getEmployee().getDepartment());
		res.append("employee", emp);
		JSONArray devs = new JSONArray();
		for(Device dev: ed.getDevices()) {
			JSONObject obj = new JSONObject();
			obj.append("id", dev.getId());
			obj.append("category", dev.getCategory());
			obj.append("device_name", dev.getDevice_name());
			obj.append("cost", dev.getCost());
			devs.put(obj);
		}
		res.append("devices", devs);
		
		return res.toString();
	}	

	@CrossOrigin
	@PostMapping("/addEmployee")
	public String addEmployee(@RequestBody EmpDevices ed) {
		JSONObject res = new JSONObject();
		String name = ed.getEmployee().getFullName();
		String str = edServ.addEmployee(ed);
		
		res.append("response", str );
		
		return res.toString();
	}	
	
	@CrossOrigin
	@PostMapping("/deleteEmployee")
	public String deleteEmployee(@RequestBody Employee emp) {
		JSONObject res = new JSONObject();
		int e_id = emp.getId();		
		res.append("response", edServ.deleteEmployee(e_id));	
		return res.toString();
	}

}
