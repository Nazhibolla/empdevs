package com.samruk.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.samruk.component.Department;
import com.samruk.component.Device;
import com.samruk.component.EmpDeviceCount;
import com.samruk.component.EmpDevices;
import com.samruk.component.Employee;

@Repository
public class EmpDevsDao {
	
	public static Connection con;
	
	public EmpDevsDao() {
		if(con == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/company?useTimezone=true&serverTimezone=UTC";
				String user = "root";
				String password = "mysql";
				con = DriverManager.getConnection(url, user, password);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public EmpDevices getEmpDevices(int e_id) {
		EmpDevices res = new EmpDevices();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee where id=" + e_id);
			while(rs.next()) {
				Employee emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5).equals("f")? 2: 1, rs.getInt(6));
				res.setEmployee(emp);
			}
			
			List<Device> devs = new ArrayList<Device>();
			rs = st.executeQuery("select * from devices where e_id =" + e_id);			
			while(rs.next()) {
				Device dev = new Device(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5));
				devs.add(dev);
			}
			Device[] devArray = new Device[devs.size()];
			int i = 0;
			for(Device dev: devs) {
				devArray[i] = dev;
				i++;
			}
			res.setDevices(devArray);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public List<EmpDeviceCount> getEmpList() throws SQLException{
		List<EmpDeviceCount> res = new ArrayList<EmpDeviceCount>();
		Statement st = con.createStatement();
		String sql = "select e.id, e.fullName, count(a.id), sum(a.cost) from employee as e left join devices as a on e.id = a.e_id group by e.id";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			EmpDeviceCount obj = new EmpDeviceCount(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
			res.add(obj);
		}
		return res;
	}
	
	public List<Department> getDeps() throws SQLException {
		List<Department> res = new ArrayList<Department>();
		Statement st = con.createStatement();
		String sql = "select * from departments";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			Department obj = new Department(rs.getInt("id"), rs.getString("name"));
			res.add(obj);
		}
		return res;
	}
	
	public void deleteByEid(int e_id) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate("delete from devices where e_id = " + e_id);
			st.executeUpdate("delete from employee where id =" + e_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String deleteEmployee(int e_id) {
		String name = "";
		int count = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select fullName from employee where id=" + e_id);
			while (rs.next()) {
				name = rs.getString(1);
			}
			rs = st.executeQuery("select count(*) from devices where e_id =" +e_id);
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
			deleteByEid(e_id);
			
			if(count > 0) {
				if(count > 1) {
					return ("Deleted employee: " + name + " with id: " + e_id + " and his " + count + " devices" );
				}
				else {
					return ("Deleted employee: " + name + " with id: " + e_id + " and his " + count + " device" );
				}
			} else {
				return ("Deleted employee: " + name + " with id: " + e_id + ". But he didn't have any devices");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String addEmployee(EmpDevices ed) {
		Employee emp = ed.getEmployee();
		Device[] dvs = ed.getDevices();
		String res="";
		int e_id = ed.getEmployee().getId();
		if(e_id ==0) {
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select max(id) from employee");
				while(rs.next()) {
					e_id = rs.getInt(1) + 1;
				}
				PreparedStatement ps = con.prepareStatement("insert into employee values (?,?,?,?,?,?); ");
				ps.setInt(1, e_id);
				ps.setString(2, emp.getFullName());
				ps.setString(3, emp.getEmail());
				ps.setString(4, emp.getMobile());
				ps.setString(5, (emp.getGender() == 2)?"f":"m");
				ps.setInt(6, emp.getDepartment());				
				int result = ps.executeUpdate();	
				if(result == 1)
					res+= "Added an Employee by name " + emp.getFullName() +"\n";
				
				rs = st.executeQuery("select max(id) from devices");
				int d_id = 0;
				while(rs.next()) {
					d_id = rs.getInt(1);
				}
				for (Device dev: dvs) {
					d_id++;
					PreparedStatement ps2 = con.prepareStatement("insert into devices values(?,?,?,?,?);");
					ps2.setInt(1, d_id);
					ps2.setString(2, dev.getCategory());
					ps2.setString(3, dev.getDevice_name());
					ps2.setInt(4,e_id);
					ps2.setDouble(5, dev.getCost());
					
					result = ps2.executeUpdate();
				}
				if(result == 1)
					res += "and added " + dvs.length + " device/s ";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}
		} else {
			try {
				deleteByEid(e_id);
								
				PreparedStatement ps = con.prepareStatement("insert into employee values (?,?,?,?,?,?); ");
				ps.setInt(1, e_id);
				ps.setString(2, emp.getFullName());
				ps.setString(3, emp.getEmail());
				ps.setString(4, emp.getMobile());
				ps.setString(5, (emp.getGender() == 2)?"f":"m");
				ps.setInt(6, emp.getDepartment());				
				int result = ps.executeUpdate();	
				if(result == 1)
					res+= "Changed information of an Employee by name " + emp.getFullName() +"\n";
				
				
				for (Device dev: dvs) {
					PreparedStatement ps2 = con.prepareStatement("insert into devices values(?,?,?,?,?);");
					ps2.setInt(1, dev.getId());
					ps2.setString(2, dev.getCategory());
					ps2.setString(3, dev.getDevice_name());
					ps2.setInt(4,e_id);
					ps2.setDouble(5, dev.getCost());
					
					result = ps2.executeUpdate();
				}
				if(result == 1) {
					if(dvs.length > 0) {
						if(dvs.length > 1) {
							res += "and now he has " + dvs.length + " devices ";
						} else {
							res += "and now he has " + dvs.length + " device ";
						}
						
					} else {
						res += "and now he doesn't have any devices ";
					}
					
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}
		}
		
		return res;
	}	

}
