package database;
import java.util.HashMap;
import java.util.Map;

import command.DataCommand;
import command.TransationCommand;
import util.Log;

/**
 * the Database class, 
 * @author Yang Wang
 *
 */

public class Database {

	/* fake head of the Version LinkedList*/
	private Version head;
	/* fake tail of the Version LinkedList*/
	private Version tail;
	/* tmp version and final version*/
	private Version tmpVersion, finalVersion;
	/* data command parser/worker */
	private DataCommand dataCommand;
	/* transation command parser/worker */
	private TransationCommand transationCommand;
	/* Version id generator */
	private int nextId;
	/* map that can quickly locate specified Version by id */
	private Map<Integer, Version> map;

	/**
	 * constructer, initial parameters
	 */
	public Database() {
		head = new Version(0);
		tail = new Version(0);
		head.setNext(tail);
		tail.setPrev(head);
		tmpVersion = null;
		finalVersion = null;
		dataCommand = new DataCommand();
		transationCommand = new TransationCommand();
		nextId = 1;
		map = new HashMap<Integer, Version>();
	}

	/**
	 * getDataCommand method
	 * @return
	 */
	public DataCommand getDataCommand(){
		return dataCommand;
	}
	
	
	/**
	 * getTransationCommand method
	 * @return
	 */
	public TransationCommand getTransationCommand(){
		return transationCommand;
	}
	
	
	/**
	 * getNextId method, get the next Version id waited for being used
	 * @return
	 */
	public int getNextId() {
		return nextId;
	}

	/**
	 * begin method, store the previous Version into the list
	 */
	public void begin() {
		if (tmpVersion == null) {
			return;
		}
		map.put(tmpVersion.getId(), tmpVersion);
		tail.getPrev().setNext(tmpVersion);
		tmpVersion.setPrev(tail.getPrev());
		tmpVersion.setNext(tail);
		tail.setPrev(tmpVersion);
		updateId();
		tmpVersion = new Version(getNextId(), tmpVersion);
	}

	/**
	 * rollback method, retrieve the previous Version
	 */
	public void rollback() {
		/*if has commited or there is no open transation*/
        if (finalVersion != null || (map.size() < 1 && tmpVersion == null)){
        	Log.i("NO TRANSACTION");
        	return;
        }
        if (map.size() < 1){
        	tmpVersion = null;
        	return;
        }
        tmpVersion = new Version(tmpVersion.getId(), tail.getPrev());
        map.remove(tail.getPrev().getId());
        tail.getPrev().getPrev().setNext(tail);
        tail.setPrev(tail.getPrev().getPrev());
	}

	
	/**
	 * commit method, perminantly apply the change
	 */
	public void commit() {
		if (tmpVersion == null){
			Log.i("NO TRANSACTION");
		}
		clearList();
		finalVersion = new Version(tmpVersion.getId(), tmpVersion);
		tmpVersion = null;
	}

	
	/**
	 * setTmpVersion method, apply the change to the tmp Version
	 * @param tmpVersion
	 */
	public void setTmpVersion(Version tmpVersion) {
		if (tmpVersion == null) {
			return;
		}
		this.tmpVersion = new Version(tmpVersion.getId(), tmpVersion);
	}

	
	/**
	 * hasCommit method, determine whether the database has been summited
	 * @return
	 */
	public boolean hasCommit(){
		return finalVersion != null;
	}
	
	
	/**
	 * getFinalVersion method, return the final version
	 * @return
	 */
	public Version getFinalVersion(){
		return finalVersion;
	}
	
	
	/**
	 * getTmpVersion method, return the tmp Version
	 * @return
	 */
	public Version getTmpVersion() {
		return tmpVersion;
	}

	
	/**
	 * updateId method, update the nextId
	 */
	private void updateId() {
		nextId += 1;
	}

	
	/**
	 * clearList method, clear the LinkedList and the map
	 */
	private void clearList() {
		head.setNext(tail);
		tail.setPrev(head);
		map.clear();
	}

}
