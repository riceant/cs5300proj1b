package com.project1b;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A view is a tuple of <SvrID, status, time>
 * Stored as a list locally on a server, but propagated as a string
 * @author rayweng
 *
 */
public class View implements Serializable {
	public enum ServerStatus { UP, DOWN };
	
	public String svrID;
	public ServerStatus status;
	public long time;
	
	public View() {
		// default constructor for serialization
	}
	
	private static List<View> _localViews = null;
	
	/**
	 * Get an instance (serialiazable) of our list of local views
	 * @return List<view>
	 */
	public static List<View> getInstance() {
		if (_localViews == null) {
			_localViews = new ArrayList<View>();
		}
		return _localViews;
	}
	
	/**
	 * This function will be called by RPCServer to handle an exchangeview() request
	 * Expected behavior: merge callerViews with local views
	 * as discussed in section 3.8
	 * 
	 * @param callerViews list<view> of the other server
	 * @return
	 */
	public static boolean mergeViews(List<View> callerViews) {
		// TODO: implement me!
		// note: may need to synchronize changes to _localViews
		return true;
	}
	
	
}
