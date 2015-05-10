package com.rest.GCM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
public class SamudayaServer { 
 
    private final static String COMMUNITY_ID = "communityId";
    private final static String COMMUNITY_PWD = "communityPassword";
    private final static String USER_NAME = "userName";
    private final static String INTEREST_ID = "interestId";
    private final static String INTEREST_IDS = "interestIds";
    private final static String MESSAGE = "message";
    private final static String DEVICE_ID = "deviceId";
    
    Connection conn = null; 
    Statement stmt = null;
    
	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyB-qjs7VBjb3IUJ66uVpzPiwe9E_-egGe4";
	static final String MESSAGE_KEY = "message";
	
	public SamudayaServer(){
        try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localHost:3306/Dynco","root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) { 
			e.printStackTrace();
			
		}
	}
     
    @POST
    @Path("communityRegister")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postCommunity(MultivaluedMap<String, String> communityParams){
    	String communityId = communityParams.getFirst(COMMUNITY_ID);
        String communityPassword = communityParams.getFirst(COMMUNITY_PWD);
        
        try {
			stmt = conn.createStatement();
        String query = "Insert into Community (CommunityId, CommunityPwd) values (?, ?)";
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, communityId);
        preparedStmt.setString (2, communityPassword);
        
        preparedStmt.execute();
        }
        catch (SQLException e) {
			return "Community Name already exist";
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
        return "Community" + communityId + "created";
    }
    
    
    @POST
    @Path("userRegister")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postUser(MultivaluedMap<String, String> userParams){
    	String communityId = userParams.getFirst(COMMUNITY_ID);
        String userName = userParams.getFirst(USER_NAME);
        String deviceId = userParams.getFirst(DEVICE_ID);
        
        try {
			stmt = conn.createStatement();
        String query = "Insert into User (CommunityId, UserName, DeviceId) values (?, ?, ?)";
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, communityId);
        preparedStmt.setString (2, userName);
        preparedStmt.setString (3, deviceId);
        
        preparedStmt.execute();
        }
        catch (SQLException e) {
			return "User Name already exist";
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
        return "User" + userName + "created";
    }
    
    @POST
    @Path("interest")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postInterest(MultivaluedMap<String, String> interestParams){
    	String communityId = interestParams.getFirst(COMMUNITY_ID);
        String userName = interestParams.getFirst(USER_NAME);
        List<Integer> interestId = interestParams.getFirst(INTEREST_IDS);
        
        try {
			stmt = conn.createStatement();
        String query = "Insert into InterestGroup (CommunityId, UserName, InterestId) values (?, ?, ?)";
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, communityId);
        preparedStmt.setString (2, userName);
        preparedStmt.setString (3, interestId);
        
        preparedStmt.execute();
        }
        catch (SQLException e) {
			return "User Name already exist";
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
        return "User" + userName + "created";
    }
    
    @POST
    @Path("message")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postMessage(MultivaluedMap<String, String> messageParams){
    	int interestId = messageParams.getFirst(INTEREST_ID);
        String userName = messageParams.getFirst(USER_NAME);
        String communityId = messageParams.getFirst(COMMUNITY_ID);
        String message = messageParams.getFirst(MESSAGE);
        
        try {
			stmt = conn.createStatement();
        String query = "Select DeviceId from User u left join InterestGroup ig on "
        		+ "u.CommunityId = ig.CommunityId and u.UserName = ig.UserName "
        		+ "where u.CommunityId = ? and u.userName <> ? and ig.InterestId = ?";
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, communityId);
        preparedStmt.setString (2, userName);
        preparedStmt.setInt(3, interestId);
        preparedStmt.execute();
        }
        
        catch (SQLException e) {
			return "User Name already exist";
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
        return "User" + userName + "created";
    }
    
    
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(
            MultivaluedMap<String, String> personParams
            ) throws IOException, ClassNotFoundException, SQLException { 
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
        
        
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null; 
        Statement stmt = null;
        conn = DriverManager.getConnection("jdbc:mysql://hostname:port/dbname","root", "root");
        
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql = "";
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            int id  = rs.getInt("id");
            String first = rs.getString("first");
            String last = rs.getString("last");

         }
         rs.close();
         stmt.close();
         conn.close();
         
        
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