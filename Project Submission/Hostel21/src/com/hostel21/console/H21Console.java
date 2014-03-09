package com.hostel21.console;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.hostel21.beans.Booking;
import com.hostel21.beans.Customer;
import com.hostel21.beans.Report;
import com.hostel21.beans.Search;
import com.hostel21.beans.SearchResult;
import com.hostel21.beans.User;
import com.hostel21.dao.BookingDAO;
import com.hostel21.dao.CustomerDAO;
import com.hostel21.dao.ReportDAO;
import com.hostel21.dao.SearchDAO;
import com.hostel21.dao.UserDAO;
import com.hostel21.facade.BookingFacade;
import com.hostel21.load.LoadDataFromXML;
import com.hostel21.util.DateUtil;


public class H21Console {
	User user=null;
	Scanner userInput = new Scanner(System.in);
	public H21Console() {
	}
	
	public void start() {
		try {
		int attempts=0;
		while(true) {
			try {
			if(user==null || attempts == 0) {
				attempts++;
				
				if(attempts == 1) {
					System.out.println("Welcome to Hostel21 Console application. You can use our website @ http://localhost:8080/Hostel21Web/ for GUI Interface. \n");
					System.out.println("All the date used in this system are in YYYY-MM-DD format. Please enter the dates in this format only.\n");
					System.out.println("You can exit teh applciation any time by entring 'exit' command \n ");
				}
				System.out.println("Please enter user name.\n");
			    String username = userInput.nextLine();
			    //if the name of the coin is empty, exit.
			    if(username==null || username.trim().equals("")) {
			    	System.out.println("\nInvalid username.\n");
			    }
			    else {
			    	System.out.println("\nPlease entre password.\n");
			    	String password= userInput.nextLine();
			    	if(password==null || password.trim().equals("")) {
			    		System.out.println("\nInvalid password.\n");
			    	}
			    	else {
			    		user=UserDAO.getById(username);
			    		if(user!=null && user.isUserValid(password)) {
			    			System.out.println("\nWelcome "+user.getFirstName()+" "+user.getLastName()+"\n\n");
			    			help();
			    		}
			    		else {
			    			user=null;
			    		}
			    	}
			    }
			}
			
			if(user==null && attempts==3) exit();
		    String command = userInput.nextLine();
		    if("help".equals(command)) {
		    	help();
		    }
		    else if("exit".equals(command)) {
		    	exit();
		    }
		    else if(command.startsWith("search")) {
		    	search(command);
		    }
		    else if(command.startsWith("cancel_booking")) {
		    	cancel(command);
		    }
		    else if(command.startsWith("view_booking")) {
		    	viewBooking(command);
		    }
		    else if(command.startsWith("add_customer")) {
		    	addCustomer(command);
		    }
		    else if(command.startsWith("modify_customer")) {
		    	changeCustomer(command);
		    }
		    else if(command.startsWith("view_customer")) {
		    	viewCustomer(command);
		    }
		    else if(command.startsWith("show_report")) {
		    	showReport(command);
		    }
		    else if(command.startsWith("show_report")) {
		    	showReport(command);
		    }
		    else if(command.startsWith("load_data")) {
		    	loadData(command);
		    }		    
		    else {
		    	System.out.println("\nInvalid Command. Enter 'help' to see valid commands\n");
		    }
			}catch(Exception e) {
				System.out.println("\nSYSTEM ERROR!!! PLEASE TRY THE COMMAND AGAIN WITH VALID DATA. ISSUE: "+e);
			}		    
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void exit() {
		System.out.println("\nThank you for using Hostel21. Goodbye...\n\n");
    	System.exit(1);
	}
	
	private void help() {
		 System.out.println("*****************************   HOSTEL21 COMMANDS *****************************  \n");
		 System.out.println("ADD CUSTOMER");
		 System.out.println("------------");
		 System.out.println("SYNTAX: add_customer,first_name,last_name email,cc_number,expiration_date,security_code,phone");
		 System.out.println("Required Fields:  first_name,last_name,email");
		 System.out.println("Optional Fields: [cc_number,expiration_date,security_code,phone] \n");

		 System.out.println("MODIFY CUSTOMER");
		 System.out.println("---------------");
		 System.out.println("SYNTAX: modify_customer,customer_id,first_name,last_name email,cc_number,expiration_date,security_code,phone");
		 System.out.println("Required Fields:  customer_id,first_name,last_name,email");
		 System.out.println("Optional Fields: [cc_number,expiration_date,security_code,phone]\n");

		 System.out.println("VIEW CUSTOMER");
		 System.out.println("-------------");
		 System.out.println("You can use one of the following commands to view a customer");
		 System.out.println("SYNTAX: view_customer,customer_id");
		 System.out.println("SYNTAX: view_customer,customer_email \n");

		 System.out.println("SEARCH");
		 System.out.println("------");
		 System.out.println("This command lets you search for rooms in hostels and then book it");
		 System.out.println("SYNTAX: search,hostelname,checkindate,checkoutdate,noofbeds(optional) \n");
		 
		 System.out.println("VIEW BOOKING");
		 System.out.println("------------");
		 System.out.println("SYNTAX: view_booking booking_id \n");
		 
		 System.out.println("CANCEL BOOKING");
		 System.out.println("--------------");
		 System.out.println("SYNTAX: cancel_booking booking_id \n");	
		 
		 System.out.println("REVENUE, BOOKINGS, CANCELS and OCCUPANCY");
		 System.out.println("----------------------------------------");
		 System.out.println("SYNTAX: show_report,hostelname,startdate,enddate \n");		
		 
		 System.out.println("LOAD DATA");
		 System.out.println("----------------------------------------");
		 System.out.println("This command loads hostel and availability data from XML files in the format given in assignment.");
		 System.out.println("SYNTAX: load_data,xml_file_name \n");				 
		 System.out.println("*******************************************************************************  \n");

	}
	
	private void addCustomer(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<4) {
			 System.out.println("SYNTAX: add_customer,first_name,last_name email,cc_number,expiration_date,security_code,phone \n");
			 System.out.println("Required Fields:  first_name,last_name,email \n");
			 System.out.println("Optional Fields: [cc_number,expiration_date,security_code,phone] \n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String firstName=tokens.nextToken();
			 String lastName=tokens.nextToken();
			 String email=tokens.nextToken();
			 String ccNumber=null;
			 String ccExpireDate=null;
			 String ccSecurityCode=null;
			 String phone=null;
			 if(tokens.hasMoreTokens()) ccNumber=tokens.nextToken();
			 if(tokens.hasMoreTokens()) ccExpireDate=tokens.nextToken();
			 if(tokens.hasMoreTokens()) ccSecurityCode=tokens.nextToken();
			 if(tokens.hasMoreTokens()) phone=tokens.nextToken();
			 Customer customer=CustomerDAO.getByEmailId(email);
			 if(customer==null) {
				 customer=new Customer(null, email, firstName, lastName, phone, ccNumber, ccExpireDate, ccSecurityCode);
				 customer=CustomerDAO.add(customer);
				 customer=CustomerDAO.getByCustomerId(customer.getCustomerId());
				 System.out.println("New customer has been added. Here is the information.");
			 }
			 else {
				 System.out.println("A customer with this email is already present. Here is the information.");
			 }
			 displayCustomer(customer);
		 }
	}
	
	private void changeCustomer(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<5) {
			 System.out.println("SYNTAX: modify_customer,customer_id,first_name,last_name email,cc_number,expiration_date,security_code,phone \n");
			 System.out.println("Required Fields:  customer_id,first_name,last_name,email \n");
			 System.out.println("Optional Fields: [cc_number,expiration_date,security_code,phone] \n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String customerId=tokens.nextToken();
			 String firstName=tokens.nextToken();
			 String lastName=tokens.nextToken();
			 String email=tokens.nextToken();
			 String ccNumber=null;
			 String ccExpireDate=null;
			 String ccSecurityCode=null;
			 String phone=null;
			 if(tokens.hasMoreTokens()) ccNumber=tokens.nextToken();
			 if(tokens.hasMoreTokens()) {
				 ccExpireDate=tokens.nextToken();
				 ccExpireDate=DateUtil.converToSQLDate(ccExpireDate);
			 }
			 if(tokens.hasMoreTokens()) ccSecurityCode=tokens.nextToken();
			 if(tokens.hasMoreTokens()) phone=tokens.nextToken();
			 Customer customer=CustomerDAO.getByCustomerId(Integer.parseInt(customerId));
			 if(customer==null) {
				 System.out.println("Customer does not exists. Here is the information.");
			 }
			 else {
				 CustomerDAO.modify(Integer.parseInt(customerId), firstName, lastName, email,ccNumber, ccExpireDate, ccSecurityCode, phone,null,null);
				 customer=CustomerDAO.getByCustomerId(customer.getCustomerId());
				 System.out.println("Customer information has been modified!");
			 }
			 displayCustomer(customer);
		 }
	}	
	
	private void viewCustomer(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<2) {
			 System.out.println("You can use one of the following commands to view a customer\n");
			 System.out.println("SYNTAX: view_customer,customer_id \n");
			 System.out.println("SYNTAX: view_customer,customer_email \n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String key=tokens.nextToken();
			 Customer customer=null;
			 if(key.contains("@")) {
				 customer=CustomerDAO.getByEmailId(key);
			 }
			 else {
				 customer=CustomerDAO.getByCustomerId(Integer.parseInt(key));
			 }
			 if(customer==null) {
				 System.out.println("\nInvalid customer id or email. \n");
			 }
			 else {
				 displayCustomer(customer);
			 }
		 }
	}

	private void displayCustomer(Customer customer) {
		System.out.println("\n");
		 System.out.println("customer id: "+customer.getCustomerId()+"\n");
		 System.out.println("Name: "+customer.getFirstName()+" "+customer.getLastName()+"\n");
		 System.out.println("Email: "+customer.getEmailId()+"\n");
		 System.out.println("Date Created: "+customer.getCreateDate()+"\n");
	}	
	
	private void search(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<4) {
			 System.out.println("SYNTAX: search,hostelname,checkindate,checkoutdate,noofbeds(optional) \n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String hostelName=tokens.nextToken();
			 String checkinDate=tokens.nextToken();
			 checkinDate=DateUtil.converToSQLDate(checkinDate);
			 String checkoutDate=tokens.nextToken();
			 checkoutDate=DateUtil.converToSQLDate(checkoutDate);
			 String noofbeds=null;
			 if(tokens.hasMoreTokens()) noofbeds=tokens.nextToken();
			 SearchResult result=null;
			 if(noofbeds!=null && !"".equals(noofbeds)) {
				 Integer noOfBeds=Integer.parseInt(noofbeds);
				 result= SearchDAO.search(checkinDate, checkoutDate, hostelName, noOfBeds);
				 List<Search> list=result.getList();
				 if(list==null || list.size()==0) {
					 System.out.println("\nSorry, not enough beds available between "+result.getFromDate()+" and "+result.getToDate());
				 }
				 else {
					 Search search=list.get(0);
					 System.out.println(result.getFromDate()+" to "+result.getToDate()+" "+noofbeds+" bed(s) "+" for $"+search.getTotalPrice()+" "+search.getDetails());
					 System.out.println("\nBook this room? Please enter 'Yes' to proceed and 'No' to cancel");
					 String confirm=userInput.nextLine();
					 if(confirm.equalsIgnoreCase("Yes")) {
						 book(search);
					 }
					 else {
						 
					 }
				 }
			 }
			 else {
				 result=SearchDAO.search(checkinDate, checkoutDate, hostelName);
				 List<Search> list=result.getList();
				 if(list==null || list.size()==0) {
					 System.out.println("\nSorry, not enough beds available between "+result.getFromDate()+" and "+result.getToDate()+"\n");
				 }
				 else {
					 for(int i=0;i<list.size();i++) {
						 Search search=list.get(i);
						 if(search.getNoOfBeds()==0) {
							 System.out.println(search.getFromDate()+" to "+search.getToDate()+" "+search.getNoOfBeds()+" beds available.");
						 }
						 else {
							 System.out.println(search.getFromDate()+" to "+search.getToDate()+" "+search.getNoOfBeds()+" beds available between "+search.getMinPrice()+" and "+search.getMaxPrice()+". Room details: "+search.getDetails());
						 }
					 }
				 }
			 
			 }
		 }
	}
	
	private void book(Search search) {
		System.out.println("\nPlease enter customer information.\n");
		System.out.println("SYNTAX for exisitng customers: customer_id or customer_email \n");
		System.out.println("SYNTAX for new customers: email firstname lastname phone(optional) facebook(optional) twitter(optional)\n");
		String customer=userInput.nextLine();
		if(customer==null || customer.trim().equals("")) {
			book(search);
		}
		else {
			 StringTokenizer tokens=new StringTokenizer(customer," ");
			 if(tokens.countTokens()==0) {
				 book(search);
			 }
			 else {
				 String emailOrId=tokens.nextToken();
				 Customer cust=null;
				 try {
					 Integer custId=Integer.parseInt(emailOrId);
					 cust=CustomerDAO.getByCustomerId(custId);
				 }
				 catch(Exception e) {
					 cust=CustomerDAO.getByEmailId(emailOrId);
				 }
				 
				 if(cust==null) {
					 if(tokens.countTokens()<3) {
						 book(search);
					 }
					 else {
						 String firstname=tokens.nextToken();
						 String lastName=tokens.nextToken();
						 String phone=tokens.nextToken();
						 String facebook=null;
						 if(tokens.hasMoreTokens()) facebook=tokens.nextToken();
						 String twitter=null;
						 if(tokens.hasMoreTokens()) twitter=tokens.nextToken();
						 cust=new Customer(null,emailOrId, firstname, lastName, phone);
					 }
				 }
				 else {
					String checkinDate=search.getFromDate();
					String checkoutDate=search.getToDate();
					String hostelName=search.getHostelName();
					Double price=search.getTotalPrice();
					String bedIds=search.getAvailableBedIds();
			        Booking booking=BookingFacade.book(checkinDate, checkoutDate,hostelName,search.getNoOfBeds(), search.getAvailableBedIds(),  price, cust, user.getUserId());
			        System.out.println("\nBooking successful! Here's the detail of your booking:\n");
			        displayBooking(booking);
					search=null;
				 }
				 
			 }
			 
		}
	}

	private void displayBooking(Booking booking) {
		System.out.println("Hostel #1, "+booking.getHostel().getName()+"\n");
		System.out.println("Check-in date: "+booking.getCheckinDate()+" \n");
		System.out.println("Check-out date: "+booking.getCheckoutDate()+" \n");
		System.out.println("Beds: "+booking.getRoomsBooked().size()+" \n"); 
		System.out.println("Booking ID: "+booking.getBookingId()+" \n");
		System.out.println("Name: "+booking.getCustomer().getFirstName()+" "+booking.getCustomer().getLastName()+" \n");
		System.out.println("Price: "+booking.getBookingPrice()+" \n");
		System.out.println("Status: "+booking.getStatus()+" \n");
	}

	private void cancel(String command) {
		StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<2) {
			 System.out.println("\nPlease enter a valid command and booking_id. \nSYNTAX: cancel_booking booking_id \n");
		 }
		 else {
			 tokens.nextToken();//command
			 String bookingId=tokens.nextToken();

			 Booking booking=BookingDAO.getByBookingId(Integer.parseInt(bookingId));
			 if(booking==null) {
				 System.out.println("\nPlease enter a valid command and booking_id. \nSYNTAX: cancel_booking booking_id \n");
			 }
			 else {
				 Date checkinDateNew=DateUtil.convertToDate(booking.getCheckinDate(), "yyyy-MM-dd");
				 int daysRemaining=DateUtil.daysBetween(new Date(),checkinDateNew);
				 int daysToCancel=booking.getHostel().getCancellationDeadline()/24;
				 boolean canbeCancelled=true;
				 if(daysRemaining>=daysToCancel) canbeCancelled=false;
				 
				 if(canbeCancelled) {
					 booking=BookingFacade.cancel(Integer.parseInt(bookingId), user.getUserId());
					 System.out.println("\nBooking Cancel successful! Here's the detail of your cancelled booking :\n");
				 }
				 else {
					 System.out.println("\nCANCEL NOT ALLOWED for this booking! Here's the detail of your cancelled booking :\n");
				 }
				 displayBooking(booking);
			 }
		 }		 
	}

	private void viewBooking(String command) {
		StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<2) {
			 System.out.println("\nPlease enter a valid command and booking_id. \nSYNTAX: view_booking booking_id \n");
		 }
		 else {
			 tokens.nextToken();//command
			 String bookingId=tokens.nextToken();
			 Booking booking=BookingDAO.getByBookingId(Integer.parseInt(bookingId));
			 if(booking==null) {
				 System.out.println("\nPlease enter a valid command and booking_id. \nSYNTAX: view_booking booking_id \n");
			 }
			 else {
				 displayBooking(booking);
			 }
		 }
	}

	private void showReport(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<4) {
			 System.out.println("\nInvalid data entered.  SYNTAX: show_report,hostelname,startdate,enddate\n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String hostelName=tokens.nextToken();
			 String startDate=tokens.nextToken();
			 startDate=DateUtil.converToSQLDate(startDate);
			 String endDate=tokens.nextToken();
			 endDate=DateUtil.converToSQLDate(endDate);
			 Report report=ReportDAO.getData(hostelName, startDate, endDate);
			 if(report==null) {
				 System.out.println("\nNo data found. \n");
			 }
			 else {
					System.out.println("*********** REPORT **************");
					System.out.println("Total Bookings: "+report.getBookings()+" \n");
					System.out.println("Revenue: "+report.getRevenue()+" \n");
					System.out.println("Total Cancelled: "+report.getCancelled()+" \n");
					System.out.println("Occupancy: "+report.getOcupancy_rate()+" \n");
					System.out.println("*********************************");
			 }
		 }
	}
	
	
	private void loadData(String command) {
		 StringTokenizer tokens=new StringTokenizer(command,",");
		 if(tokens.countTokens()<2) {
			 System.out.println("Please entered the command in correct syntax. SYNTAX: load_data,xml_file_name \n");
		 }
		 else {
			 tokens.nextToken();//for command
			 String fileName=tokens.nextToken();
			 LoadDataFromXML.load(fileName);
			 System.out.println("Data has been loaded in to database succesfully.");
		 }
	}	
	public static void main(String[] args) {
		H21Console console=new H21Console();
		console.start();
	}	
	

	
	
}
