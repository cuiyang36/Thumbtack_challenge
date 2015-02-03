package database;
import java.util.HashMap;
import java.util.Map;

import util.Log;

/**
 * the Version class, represents the Version node 
 * in the Version LinkedList in database
 * 
 * @author Yang Wang
 *
 */

public class Version {
	/* record the name of the class*/
	private final static String NAME = "Version";
	/* record the prev and next versions*/
	private Version prev, next;
	/* record the id of the Version*/
	private int id;
	/* record the value of the key (target)*/
	private Map<String, String> valueCache;
	/* record the number of the value in the valueCache*/
	private Map<String, Integer> numCache;
	
	
    /** constructer for Version
     * 
     * @param id
     */
	public Version(int id){
		this.id = id;
		prev = null;
		next = null;
		valueCache = new HashMap<String, String>();
		numCache = new HashMap<String, Integer>();
	}
	
	
	/**
	 * constructer for copying the data from an exist version
	 * @param id
	 * @param version
	 */
	public Version(int id, Version version){
		if (version == null){
			return;
		}
		this.id = id;
		prev = null;
		next = null;
		valueCache = new HashMap<String, String>(version.getValueData());
		numCache = new HashMap<String, Integer>(version.getNumData());
	}
	
	
	/**
	 * return the valueCache data
	 * @return
	 */
	public Map<String, String> getValueData(){
		return valueCache;
	}
	
	
	/**
	 * return the numCache data
	 * @return
	 */
	public Map<String, Integer> getNumData(){
		return numCache;
	}
	
	
	/**
	 * return the id of current version
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	
	/**
	 * return the prev Version
	 * @return
	 */
	public Version getPrev(){
		return prev;
	}
	
	
	/**
	 * return the next Version
	 * @return
	 */
	public Version getNext(){
		return next;
	}
	
	
	/**
	 * set method, set the target to be the specified value
	 * @param target
	 * @param value
	 */
	public void set(String target, String value){
		if (target == null || value == null){
			Log.e(NAME, "Invalid parameters for 'set' method!");
			return;
		}
		if (valueCache.containsKey(target)){
			String oldValue = valueCache.get(target);
			deleteOne(oldValue);
		}
		valueCache.put(target, value);
		addOne(value);
	}
	
	/**
	 * get method, get the value for the target
	 * @param target
	 */
	public void get(String target){
		if (target == null){
			Log.e(NAME, "Invalid parameters for 'get' method!");
			return;
		}
		String value = valueCache.containsKey(target)? valueCache.get(target): "NULL";
		Log.i(value);
	}
	
	/**
	 * unset method, unset the value of the target
	 * @param target
	 */
	public void unset(String target){
		if (target == null){
			Log.e(NAME, "Invalid parameters for 'unset' method!");
			return;
		}
		if (valueCache.containsKey(target)){
			String value = valueCache.get(target);
			deleteOne(value);
			valueCache.remove(target);
		}
	}
	
	/**
	 * numequalto method, calculate the number of the value
	 * @param value
	 */
	public void numequalto(String value){
		if (value == null){
			Log.e(NAME, "Invalid parameters for 'numequalto' method!");
			return;
		}
		int result =  numCache.containsKey(value)? numCache.get(value): 0;
		Log.i(result + "");
	}
	
	/**
	 * setPrev method, setting the prev Version of current one
	 * @param prev
	 */
	public void setPrev(Version prev){
		if (prev == null){
			Log.e(NAME, "Invalid parameters for 'setPrev' method!");
			return;
		}
		this.prev = prev;
	}
	
	/**
	 * setNext method, setting the next Version of current one
	 * @param next
	 */
	public void setNext(Version next){
		if (next == null){
			Log.e(NAME, "Invalid parameters for 'setNext' method!");
			return;
		}
		this.next = next;
	}
	
	
	/**
	 * delete one in the numCache for the specified value
	 * @param value
	 */
	private void deleteOne(String value){
		numCache.put(value, numCache.get(value) - 1);
		if (numCache.get(value) == 0){
			numCache.remove(value);
		}
	}
	
	/**
	 * add one in the numCache for the specified value
	 * @param value
	 */
	private void addOne(String value){
		if (numCache.containsKey(value)){
			numCache.put(value, numCache.get(value) + 1);
		}else{
			numCache.put(value, 1);
		}
	}
	

}
