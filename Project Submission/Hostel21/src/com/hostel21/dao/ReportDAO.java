package com.hostel21.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hostel21.beans.Report;
import com.hostel21.util.ConnectionUtil;

public class ReportDAO {

	

	
	public static Report getData(String hostelName,String fromDate, String toDate) {
		Connection con=null;
		Report report=null;
    	try {
    		con=ConnectionUtil.getConnection();	
    		StringBuffer sql=new StringBuffer();
    		sql.append(" select hostel_name,sum(bookings) bookings,sum(revenue) revenue,sum(cancelled) cancelled,sum(ocupancy_rate) ocupancy_rate  ");
    		sql.append(" from  (   ");
    		sql.append(" select hostel_name hostel_name, 0 bookings,0.0 revenue,0 cancelled, "); 
    		sql.append(" round((sum(case when status=0 then 1.0 else 0.0 end)/count(*))*100,2) ocupancy_rate ");  
    		sql.append(" from availability    where upper(hostel_name)=upper(?) and date >= ? and date < ? "); 
    		sql.append(" group by hostel_name   ");
    		sql.append(" UNION    ");
    		sql.append(" select hostel_name,sum(case when status='Booked' then 1 else 0 end) bookings, ");  
    		sql.append(" sum(case when status='Booked' then booking_price else 0 end) revenue,    ");
    		sql.append(" sum(case when status='Cancelled' then 1 else 0 end) cancelled ,0.0 ocupancy_rate ");  
    		sql.append(" from booking   ");
    		sql.append(" where upper(hostel_name)=upper(?) and checkin_date >= ? and checkin_date < ? "); 
    		sql.append(" group by hostel_name  )   ");
    		sql.append(" group by hostel_name ");
			
    		PreparedStatement st=con.prepareStatement(sql.toString()); 
			st.setString(1, hostelName);
			st.setString(2, fromDate);
			st.setString(3, toDate);
			st.setString(4, hostelName);
			st.setString(5, fromDate);
			st.setString(6, toDate);
			ResultSet rs=st.executeQuery(); 
			if(rs.next()) {
				report=new Report(rs.getInt("hostel_name"), rs.getInt("bookings"), rs.getDouble("revenue"), rs.getInt("cancelled"), rs.getDouble("ocupancy_rate"));
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}  	
    	return report;
			
	}	
}
