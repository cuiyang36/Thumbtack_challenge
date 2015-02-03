package command;
import database.Database;
import util.Log;

/**
 * the TransationCommand class, it can handle and parse transation commands
 * 
 * @author Yang Wang
 *
 */

public class TransationCommand implements Command{
	
	/* Transation commands*/
	private final static String BEGIN = "BEGIN";
	private final static String ROLLBACK = "ROLLBACK";
	private final static String COMMIT = "COMMIT";
	private final static String NAME = "TransationCommand";
	
	public TransationCommand(){
		// we don't need to initial anything
		return;
	}
	
	public boolean isCommand(String command){
		return command.equals(BEGIN) || command.equals(ROLLBACK) || command.equals(COMMIT);
	}
	
	public void execute(String command, Database database){
		if (command == null || database == null){
			Log.e(NAME, "Invalid parameters in 'execute' method!");
		}
		if (command.equals(BEGIN)){
			database.begin();
		}else if (command.equals(ROLLBACK)){
			database.rollback();
		}else if (command.equals(COMMIT)){
			database.commit();
		}else{
			/* The code should never reach here*/
			Log.e(NAME, "Invalid transation command!");
		}
	}

}
