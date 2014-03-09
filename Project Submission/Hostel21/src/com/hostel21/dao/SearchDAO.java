package com.hostel21.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.hostel21.beans.Availability;
import com.hostel21.beans.Search;
import com.hostel21.beans.SearchResult;
import com.hostel21.util.ConnectionUtil;
import com.hostel21.util.DateUtil;

public class SearchDAO {
	
	
	private static Map<String,Search> get(String fromDate, String toDate,String hostelName,int noOfBeds) {
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		Map<String,Search> list=new HashMap<String,Search>();
    	try {
    		con=ConnectionUtil.getConnection();
    		StringBuffer sql=new StringBuffer();
    		sql.append(" SELECT hostel_name,date from_date,date(date,'1 day') to_date,sum(status) no_of_beds, ");
    		sql.append(" group_concat(case when status=1 then availability_id||'|'||room||'|'||bed||'|'||price||'|'||status end) as details ");
    		sql.append(" ,min(price) min_price, max(price) max_price FROM availability WHERE date >= ? AND date < ? and upper(hostel_name)=upper(?) ") ;
    		sql.append(" GROUP BY hostel_name,date HAVING sum(status=1)>=? ");
    		st=con.prepareStatement(sql.toString());
    		st.setString(1, fromDate);
    		st.setString(2, toDate);
    		st.setString(3, hostelName);
    		st.setInt(4, noOfBeds);
    		rs=st.executeQuery();
    		
    		while(rs.next()) {
    			Search search=new Search(rs.getString("from_date"),rs.getString("to_date"),rs.getString("hostel_name"),rs.getInt("no_of_beds"), rs.getString("details"),rs.getDouble("min_price"),rs.getDouble("max_price"));
    			list.put(search.getFromDate(), search);
    		}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		}  	
    	return list;
			
	}
	

	
	public static SearchResult search(String fromDate, String toDate,String hostelName,int noOfBedsRequired) {
		Map<String,Search> map=get(fromDate, toDate, hostelName,noOfBedsRequired);
		List<Date> dates=DateUtil.datesBetweenTwoDates(DateUtil.convertToDate(fromDate, "yyyy-MM-dd") , DateUtil.convertToDate(toDate, "yyyy-MM-dd"));
		List<Search> results=new ArrayList<Search>();
		// used to find common beds available during stay.
		List<List<String>> commonBedList = new ArrayList<List<String>>();
		
		boolean insufficientBeds=false;
		for(int i=0;i<dates.size();i++) {
			Date date=dates.get(i);
			String dateString=DateUtil.convertToString(date, "yyyy-MM-dd");
			if(map.containsKey(dateString)) {
				Search search=map.get(dateString);
				List<Availability> detailList=null;
				if(search.getNoOfBeds()<noOfBedsRequired) {
					insufficientBeds=true;
					break;
				}
				else {
					String details=search.getDetails();
					StringTokenizer firstTokens=new StringTokenizer(details,",");
					detailList=new ArrayList<Availability>();
					List<String> bedList=new ArrayList<String>();
					while(firstTokens.hasMoreTokens()) {
						String firstTokenItem=firstTokens.nextToken();
						StringTokenizer secondTokens=new StringTokenizer(firstTokenItem,"|");
						while(secondTokens.hasMoreTokens()) {
							Integer bedId=Integer.parseInt(secondTokens.nextToken());
							Integer room=Integer.parseInt(secondTokens.nextToken());
							Integer bed=Integer.parseInt(secondTokens.nextToken());
							Double price=Double.parseDouble(secondTokens.nextToken());
							Integer status=Integer.parseInt(secondTokens.nextToken());
							if(status>=1) {
								Availability sDetail=new Availability();
								sDetail.setAvailabilityId(bedId);
								sDetail.setBed(bed);
								sDetail.setRoom(room);
								sDetail.setPrice(price);
								sDetail.setStatus(status);
								detailList.add(sDetail);
								bedList.add(room+"|"+bed);
								commonBedList.add(bedList);
							}
						}
					}
					
				}
				if(insufficientBeds) detailList=new ArrayList<Availability>();
				Collections.sort(detailList);
				search.setSearchDetails(detailList);
				search.setSearchId(i);
				results.add(search);
			}
			else {
				insufficientBeds=true;
				break;
			}
		}
		if(insufficientBeds) results=new ArrayList<Search>();
		SearchResult sResult=new SearchResult(hostelName,fromDate, toDate, results);
		sResult.setNoOfBedsRequested(noOfBedsRequired);
		if(insufficientBeds==false && noOfBedsRequired>0) {
			Set<String> commonBeds=getCommonElements(commonBedList);
			sResult.setList(getQuote(commonBeds, fromDate, toDate,noOfBedsRequired,hostelName));
		}
		return sResult;
	}
	
	public static SearchResult search(String fromDate, String toDate,String hostelName) {
		Map<String,Search> map=get(fromDate, toDate, hostelName,1);
		List<Date> dates=DateUtil.datesBetweenTwoDates(DateUtil.convertToDate(fromDate, "yyyy-MM-dd") , DateUtil.convertToDate(toDate, "yyyy-MM-dd"));
		List<Search> results=new ArrayList<Search>();
		
		for(int i=0;i<dates.size();i++) {
			Date date=dates.get(i);
			
			String dateString=DateUtil.convertToString(date, DateUtil.sqliteDateFormat);
			if(map.containsKey(dateString)) {
				Search search=map.get(dateString);
				List<Availability> detailList=null;
				String details=search.getDetails();
				StringTokenizer firstTokens=new StringTokenizer(details,",");
				detailList=new ArrayList<Availability>();
				List<String> bedIdList=new ArrayList<String>();
					
				StringBuffer detailsSB=new StringBuffer();
				while(firstTokens.hasMoreTokens()) {
					String firstTokenItem=firstTokens.nextToken();
					StringTokenizer secondTokens=new StringTokenizer(firstTokenItem,"|");
					while(secondTokens.hasMoreTokens()) {
						Integer bedId=Integer.parseInt(secondTokens.nextToken()); //availabilityid
						Integer room=Integer.parseInt(secondTokens.nextToken());
						Integer bed=Integer.parseInt(secondTokens.nextToken());
						Double price=Double.parseDouble(secondTokens.nextToken());
						Integer status=Integer.parseInt(secondTokens.nextToken());
						if(status>=1) {
							Availability sDetail=new Availability();
							sDetail.setAvailabilityId(bedId);
							sDetail.setBed(bed);
							sDetail.setRoom(room);
							sDetail.setPrice(price);
							sDetail.setStatus(status);
							detailList.add(sDetail);
							
							detailsSB.append("Room "+sDetail.getRoom()+", Bed "+sDetail.getBed()+", Price "+sDetail.getPrice()+"   ");
							detailList.add(sDetail);
							bedIdList.add(room+"|"+bed);
						}
					}
				}
				search.setDetails(detailsSB.toString());
				Collections.sort(detailList);
				search.setSearchDetails(detailList);
				search.setSearchId(i);
				results.add(search);
			}
			else {
				String toDateString=DateUtil.convertToString(DateUtil.addDaysToDate(date,1), DateUtil.sqliteDateFormat);
				Search newSearch=new Search(dateString, toDateString, hostelName, 0, "", 0.0, 0.0);
				results.add(newSearch);
			}
		}
		SearchResult sResult=new SearchResult(hostelName,fromDate, toDate, results);
		sResult.setNoOfBedsRequested(0);
		return sResult;
		
		
	}	

	private static List<Search> getQuote(Set<String> availableBeds,String fromDate,String toDate,int totalBeds,String hostelName) {
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List<Search> list=new ArrayList<Search>();
    	try {
    		con=ConnectionUtil.getConnection();
    		StringBuffer query=new StringBuffer();
    		query.append(" SELECT min(date) from_date,max(date) to_date,sum(price) total_price, ");
    		query.append(" group_concat(distinct 'Room '||room||' Bed '||bed) details,  ");
    		query.append(" group_concat(distinct date||','||'Room '||room||' Bed '||bed||','||price) alldetails, ");
    		query.append(" group_concat(availability_id,',') availableBedIds ");
    		query.append(" ,hostel_name FROM availability  ");
    		query.append(" where upper(hostel_name)=upper(?)   ");
    		query.append(" and date >= ? AND date < ?  ");
    		query.append(" and status=1  ");
    		query.append(" and (" );
    		
    		Iterator<String> iter=availableBeds.iterator();
    		
    		
    		int j=0;
    		while(iter.hasNext()) {
    			j++;
    			String bedroomString=iter.next();
    			StringTokenizer tokens=new StringTokenizer(bedroomString,"|");
    			String room=tokens.nextToken();
    			String bed=tokens.nextToken();
    			query.append("(room="+room+" and bed="+bed+")");
    			if(j==totalBeds) break;
    			if(iter.hasNext()) query.append(" or ");
    		}
    		query.append(" ) ");
    		
    		st=con.prepareStatement(query.toString());
    		st.setString(1, hostelName);
    		st.setString(2, fromDate);
    		st.setString(3, toDate);
    		rs=st.executeQuery();
    		
    		if(rs.next()) {
    			Search search=new Search(fromDate,toDate,rs.getString("hostel_name"), totalBeds, rs.getString("details"));
    			search.setTotalPrice(rs.getDouble("total_price"));
    			search.setAvailableBedIds(rs.getString("availableBedIds"));    			
    			list.add(search);
    		}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		finally {
			try {
				con.close(); 
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		}  	
    	return list;		
	}

	public static Set<String> getCommonElements(Collection<? extends Collection<String>> collections) {
			Set<String> common = new LinkedHashSet<String>();
			if (!collections.isEmpty()) {
				Iterator<? extends Collection<String>> iterator = collections.iterator();
				common.addAll(iterator.next());
				while (iterator.hasNext()) {
					common.retainAll(iterator.next());
				}
			}
			return common;
		}
	

}
