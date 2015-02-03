package command;

import database.Database;
/**
 * the command interface
 * 
 * @author Yang Wang
 *
 */


public interface Command {
	
	/**
	 * execute method, execute command for current database
	 * @param command
	 * @param database
	 */
	public void execute(String command, Database database);
	
	
	/**
	 * isCommand method, determine which type of command it 
	 * is belongs to
	 * @param command
	 * @return
	 */
	public boolean isCommand(String command);

}
