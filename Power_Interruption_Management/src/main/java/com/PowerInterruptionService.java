package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.PowerInterruption;

@Path("/PowerInterruption")

public class PowerInterruptionService {
	PowerInterruption inquiryObj = new PowerInterruption();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPowerInterruption() {
		return inquiryObj.readPowerInterruption();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insert(@FormParam("customerName") String customerName, 
			@FormParam("phoneNumber") int phoneNumber,
			@FormParam("inquiryType") String inquiryType, 
			@FormParam("inquiryLocation") String inquiryLocation,
			@FormParam("remarks") String remarks)

	{
		String output = inquiryObj.insertPowerInterruption(customerName, phoneNumber, inquiryType, inquiryLocation, remarks);
		
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePowerInterruption(String inquiryData) 
	{
		// Convert the input string to a JSON object
		JsonObject inquiryObject = new JsonParser().parse(inquiryData).getAsJsonObject();
		// Read the values from the JSON object
		
		int inquiryID = inquiryObject.get("inquiryID").getAsInt();
		String customerName = inquiryObject.get("customerName").getAsString();
		int phoneNumber = inquiryObject.get("phoneNumber").getAsInt();
		String inquiryType = inquiryObject.get("inquiryType").getAsString();
		String inquiryLocation = inquiryObject.get("inquiryLocation").getAsString();
		String remarks = inquiryObject.get("remarks").getAsString();
		
		String output = inquiryObj.updatePowerInterruption(inquiryID, customerName, phoneNumber, inquiryType, inquiryLocation, remarks);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePowerInterruption(String inquiryData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(inquiryData, "", Parser.xmlParser());
		//Read the value from the element <itemID>
		String itemID = doc.select("inquiryID").text();
		String output = inquiryObj.deletePowerInterruption(itemID);
		return output;
	}
}
