package com.project1b;

import java.util.HashMap;

/**
 * implements a singleton hashtable of session data
 * exists in memory so data is lost whenever the server is shut down
 * use static methods to modify data table for synchronization
 * 
 * <Key, Value> : <SessID, <sessID,version,expiration,data>>
 * @author rayweng
 *
 */
public class SessionDataTable {
	private static Object syn = new Object();
	
	/**
	 * right now dataTable just stores <sessionID, message> pairs
	 */
	private static HashMap<String, SessionTuple> _dataTable = null;;
	
	protected SessionDataTable() {
		_dataTable = new HashMap<String, SessionTuple>();
		// exists to prevent external instantiation
	}
	
	public static HashMap<String, SessionTuple> getInstance() {
		if (_dataTable == null)
			_dataTable = new HashMap<String, SessionTuple>();
		return _dataTable;
	}
	
	public static boolean addSession(HashMap<String, SessionTuple> sdt, String key, SessionTuple value) {
		synchronized(syn) {
			sdt.put(key, value);
		}
		return true;
	}
	
	public static boolean deleteSession(HashMap<String, SessionTuple> sdt, String key) {
		synchronized(syn) {
			sdt.remove(key);
		}
		return true;
	}
	
	public static SessionTuple getSession(HashMap<String, SessionTuple> sdt, String key) {
		synchronized(syn) {
			return sdt.get(key);
		}
	}
	
	/**
	 * cleans stale entries and returns number of entries removed
	 * @param sdt
	 * @return
	 */
	public static int cleanStaleSessions(HashMap<String, SessionTuple> sdt) {
		if (sdt == null || sdt.size() == 0)
			return 0;
		int k = 0;
		synchronized(syn) {
			for (String sessID : sdt.keySet()) {
				if (sdt.get(sessID).isExpired()) {
					sdt.remove(sessID);
					k += 1;
				}
			}
		}
		return k;
	}
	
}