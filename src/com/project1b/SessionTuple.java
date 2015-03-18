package com.project1b;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SessionTuple implements Serializable {
	private static final long serialVersionUID = 1L;
	public String sessionID; // <sess_num, svr_ID>
	public String data;
	public int version;
	public Date expiration;
	
	public SessionTuple() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR,  7);
		expiration = c.getTime();
	}
	
	public SessionTuple(String sessID, String mess, int v) {
		this();
		this.sessionID = sessID;
		this.data = mess;
		this.version = v;
	}
	
	public boolean isExpired() {
		Calendar c = Calendar.getInstance();
		return c.after(expiration);
	}
}
