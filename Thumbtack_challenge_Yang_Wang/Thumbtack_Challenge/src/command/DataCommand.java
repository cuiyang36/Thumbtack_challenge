package command;
import database.Database;
import database.Version;
import util.Log;

/**
 * the DataCommand class, it can handle and parse data commands
 * 
 * @author Yang Wang
 *
 */

public class DataCommand implements Command{
	
	private final static String SET = "SET";
	private final static String UNSET = "UNSET";
	private final static String NUMEQUALTO = "NUMEQUALTO";
	private final static String GET = "GET";
	private final static String NAME = "DataCommand";
	
	public DataCommand(){
		// we don't need to initial anything
		return;
	}
	
	public void execute(String command, Database database){
		if (command == null || database == null){
			Log.e(NAME, "Invalid parameters in 'execute' method!");
		}
		Version version;
		/* If committed, the version should be the final version*/
		if (database.hasCommit()){
			version = database.getFinalVersion();
		}else{
			/* Else, the version should be the tmp version */
			version = database.getTmpVersion();
			if (version == null){
				/* For the first call, if without BEGIN signal*/
				version = new Version(database.getNextId());
			}
		}
		String[] paras = command.split(" ");
		String header = paras[0];
		/* Sub by sub, for the command that can revise the database*/
		/* we should determine whether it's the final version*/
		if (header.equals(SET)){
			set(paras, version, database.hasCommit());
		}else if (header.equals(UNSET)){
			unset(paras, version, database.hasCommit());
		}else if (header.equals(NUMEQUALTO)){
			numequalto(paras, version);
		}else if (header.equals(GET)){
			get(paras, version);
		}else{
			/* The code should never reach here*/
			Log.e(NAME, "Invalid data command!");
		}
		if (!database.hasCommit()){
			database.setTmpVersion(version);
		}
	}
	
	public boolean isCommand(String command){
		String header = command.split(" ")[0];
		return header.equals(SET) || header.equals(GET) ||
				header.equals(UNSET) || header.equals(NUMEQUALTO);
	}
	
	/**
	 * set method
	 * @param paras, the input parameters
	 * @param version
	 * @param isFinal
	 */
	private void set(String[] paras, Version version, boolean isFinal){
		if (isFinal){
			Log.e(NAME, "You can not 'set' a committed database!");
			return;
		}
		if (!isValidPara(paras, version, "set", 3)){
			return;
		}
		String target = paras[1];
		String value = paras[2];
		version.set(target, value);
	}
	
	/**
	 * get method
	 * @param paras, the input parameters
	 * @param version
	 */
	private void get(String[] paras, Version version){
		if (!isValidPara(paras, version, "get", 2)){
			return;
		}
		String target = paras[1];
		version.get(target);
	}
	
	/**
	 * unset method
	 * @param paras, the input parameters
	 * @param version
	 * @param isFinal
	 */
	private void unset(String[] paras, Version version, boolean isFinal){
		if (isFinal){
			Log.e(NAME, "You can not 'unset' a committed database!");
			return;
		}
		if (!isValidPara(paras, version, "unset", 2)){
			return;
		}
		String target = paras[1];
		version.unset(target);
	}
	
	/**
	 * numequalto method
	 * @param paras, the input parameters
	 * @param version
	 */
	private void numequalto(String[] paras, Version version){
		if (!isValidPara(paras, version, "numequalto", 2)){
			return;
		}
		String value = paras[1];
		version.numequalto(value);
	}
	
	/**
	 * pre validation of the parameters
	 * @param paras, the input parameters
	 * @param version
	 * @param name
	 * @param number, the correct number of the parameters
	 * @return
	 */
	private boolean isValidPara(String[] paras, Version version, String name, int number){
		if (paras == null || version == null){
			Log.e(NAME, "Invalid parameters for '" + name + "' method!");
			return false;
		}
		if (paras.length != number){
			Log.e(NAME, "Invalid parameter numbers for '" + name + "' method!");
			return false;
		}
		for (String para: paras){
			if (para.equals("")){
				Log.e(NAME, "Invalid parameter values for '" + name + "' method!");
				return false;
			}
		}
		return true;
	}
}
