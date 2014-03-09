package com.hostel21.load;
import java.io.File;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hostel21.beans.Availability;
import com.hostel21.beans.Hostel;
import com.hostel21.dao.AvailabilityDAO;
import com.hostel21.dao.HostelDAO;
import com.hostel21.dao.SequenceIdDAO;
import com.hostel21.util.DateUtil;

public class LoadDataFromXML {
	public static void main(String args[]) {
		load("/tmp/hostel-inventory-1-20131117.xml");
		load("/tmp/hostel-inventory-2-20131117.xml");
		load("/tmp/hostel-inventory-3-20131117.xml");
	}
	public static void load(String fileName) {
		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
	
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			Hostel hostel=new Hostel();
			hostel.setName(doc.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());
			
			NodeList nList = doc.getElementsByTagName("address");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					hostel.setStreet(eElement.getElementsByTagName("street").item(0).getTextContent());
					hostel.setCity(eElement.getElementsByTagName("city").item(0).getTextContent());
					hostel.setState(eElement.getElementsByTagName("state").item(0).getTextContent());
					hostel.setPostalCode(eElement.getElementsByTagName("postal_code").item(0).getTextContent());
					hostel.setCountry(eElement.getElementsByTagName("country").item(0).getTextContent());
				}
			}

			nList = doc.getElementsByTagName("contact");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					hostel.setPhone(eElement.getElementsByTagName("phone").item(0).getTextContent());
					hostel.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
					hostel.setFacebook(eElement.getElementsByTagName("facebook").item(0).getTextContent());
					hostel.setWeb(eElement.getElementsByTagName("web").item(0).getTextContent());
				}
			}

			nList = doc.getElementsByTagName("restrictions");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					hostel.setCheckinTime(eElement.getElementsByTagName("check_in_time").item(0).getTextContent());
					hostel.setCheckoutTime(eElement.getElementsByTagName("check_out_time").item(0).getTextContent());
					hostel.setSmoking(eElement.getElementsByTagName("smoking").item(0).getTextContent());
					hostel.setAlcohol(eElement.getElementsByTagName("alcohol").item(0).getTextContent());
					hostel.setCancellationDeadline(Integer.parseInt(eElement.getElementsByTagName("cancellation_deadline").item(0).getTextContent()));
					StringTokenizer tokens=new StringTokenizer(eElement.getElementsByTagName("cancellation_penalty").item(0).getTextContent(),"%");
					hostel.setCancellPenalty(Double.parseDouble(tokens.nextToken()));
				}
			}
			
			HostelDAO.add(hostel);

			nList = doc.getElementsByTagName("availability");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Availability availability=new Availability();
					Integer availabilityId=SequenceIdDAO.createNextId("AVAILABILITY");
					if(availabilityId==null) availabilityId=1000; //first record
					availability.setAvailabilityId(availabilityId);
					availability.setBed(Integer.parseInt(eElement.getElementsByTagName("bed").item(0).getTextContent()));
					availability.setRoom(Integer.parseInt(eElement.getElementsByTagName("room").item(0).getTextContent()));
					String date=eElement.getElementsByTagName("date").item(0).getTextContent();
					availability.setDate(DateUtil.convert(date, "yyyyMMdd","yyyy-MM-dd"));
					availability.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
					availability.setHostel(hostel);
					AvailabilityDAO.add(availability);
				}
			}

		} catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
