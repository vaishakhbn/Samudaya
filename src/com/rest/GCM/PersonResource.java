package com.rest.GCM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
 
 
@Path("/person")
public class PersonResource {
 
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String EMAIL = "email";
    private final static String REG_ID = "regId";
    
    
	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyB-qjs7VBjb3IUJ66uVpzPiwe9E_-egGe4";
	static final String MESSAGE_KEY = "message";
	
         
    private Person person = new Person(1, "Sample", "Person", "sample_person@jerseyrest.com");
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }
 
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getSamplePerson() {
         
        System.out.println("Returning sample person: " + person.getFirstName() + " " + person.getLastName());
         
        return person;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(
            MultivaluedMap<String, String> personParams
            ) throws IOException {
        String firstName = personParams.getFirst(FIRST_NAME);
        String lastName = personParams.getFirst(LAST_NAME);
        String email = personParams.getFirst(EMAIL);
        String regID = personParams.getFirst(REG_ID);
        System.out.println("Storing posted " + firstName + " " + lastName + "  " + email);
         
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setRegId(regID);
        
        System.out.println("person info: " + person.getFirstName() + " " + person.getLastName() + " " + person.getEmail());
        
        
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\ASHWIN\\Middleware\\GCM\\GCMRegId.txt", true) ) );
			writer.println(regID);
			writer.close();
		

			try {
				BufferedReader br = new BufferedReader(new FileReader(
						"C:\\Users\\ASHWIN\\Middleware\\GCM\\GCMRegId.txt"));
				
					while ((regID = br.readLine()) != null) {
				String userMessage = firstName;
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
				System.out.println("regId: " + regID);
				sender.send(message, regID, 1);
			}
			
			} catch (IOException ioe) {
				ioe.printStackTrace();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        return person;
                         
    }
    
}