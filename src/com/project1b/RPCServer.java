package com.project1b;

import java.io.*;
import java.lang.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Handles RPC operations
 * also maintains Views integrity through a separate thread.
 * @author rayweng
 *
 */
public class RPCServer {
	private static String SERVER_IP_LOCAL = "127.0.0.1";
	// opcodes
	public static final int SESSION_READ = 111;
	public static final int SESSION_WRITE = 222;
	public static final int EXCHANGE_VIEWS = 333;
	
	// TODO: somewhere to save local views data
	
	/**
	 * get the ip address of the server on startup
	 * 
	 * and populate local views with information from simpleDB
	 */
	static {
		// use Runtime.getRuntime().exec("/opt/aws/bin/ec2-metadata");
		
		// or alternately:
		URL url = null;
		try {
			url = new URL("http://checkip.amazonaws.com/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(url.openStream()));
				String amazonIP = br.readLine();
				SERVER_IP_LOCAL = amazonIP;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		// TODO: ask simpleDB for current views
	} // end static
	
	
	/**
	 * Receives a request from the RPCListener and interprets what to do with it.
	 * @param request unique callID, opcode and arguments of the RPC request
	 * @return CallID and results
	 */
	public String handleRPCRequest(String request) {
		// TODO: have yet to determine request string format
		int operationCode = Integer.parseInt(request.substring(0, 4)); // most likely, have yet to determine
		
		switch (operationCode) {
		case SESSION_READ:
			return "";
			// TODO: etc.
		}
		
		return "";
	}

	/**
	 * get existing session data
	 * @param sessID tuple of <sess_num, svr_id> to identify entry in session data table
	 * @return string tuple of <found_version, data>
	 */
	protected String sessionRead(String sessID) {
		// TODO: implement me!
		return "";
	}
	
	/**
	 * write [new] session data
	 * @param sessID tuple of <sess_num, svr_id>
	 * @param version version of the session to be stored
	 * @param data session data to be stored
	 * @param discard_time time after which this stored session may be garbage collected
	 * @return acknowledgement of successful save or failure
	 */
	protected String sessionWrite(String sessID, int version, String data, Date discard_time) {
		// TODO: implement me!
		return "";
	}
	
	/**
	 * merge incoming view with local view on server, in response to RPC request
	 * @param v current view of the caller
	 * @return current view stored locally by callee
	 */
	protected String exchangeViews(String v) {
		// TODO: implement me!
		
		String callerArgument = "";
		List<View> gossipPartnerViews = null;
		/*
		 * deserialize callerArgument string into list<view>
		 */
		try {
			byte b[] = callerArgument.getBytes();
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			gossipPartnerViews = (List<View>) si.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * barring any errors, gossipPartnerViews should now be the list of views
		 * of our gossip partner
		 */
		
		// merge caller views with my local ones
		View.mergeViews(gossipPartnerViews);
		
		
		String sLocalViews = "";
		/* 
		 * get a string representation of local views
		 */
		List<View> localViews = View.getInstance();
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(localViews);
			so.flush();
			sLocalViews = bo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * now sLocalViews should contain string version of our local views
		 * merged with caller's views
		 */
		
		return sLocalViews;
	}
	
	/**
	 * periodically execute gossip protocol
	 * and exchange local views with a random other server
	 * 
	 * called by a separate gossip protocol thread that runs every x seconds
	 */
	protected void exchangeViews() {
		// TODO: implement me!
		
		String sLocalViews = "";
		/* 
		 * get a string representation of local views
		 */
		List<View> localViews = View.getInstance();
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(localViews);
			so.flush();
			sLocalViews = bo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * now sLocalViews should contain string version of our local views
		 */
		
		// TODO 1: pick random partner for gossip
		
		// TODO 2: make RPC call or simpleDB call
		
		// TODO 3: get response, also a serialized list of views
		String response = "";
		List<View> gossipPartnerViews = null;
		/*
		 * deserialize response string into list<view>
		 */
		try {
			byte b[] = response.getBytes();
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			gossipPartnerViews = (List<View>) si.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * barring any errors, gossipPartnerViews should now be the list of views
		 * of our gossip partner
		 */
		
		// merge views together / save
		View.mergeViews(gossipPartnerViews);
	}
	
}
