package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import util.Log;
import command.DataCommand;
import command.TransationCommand;
import database.Database;

/**
 * Main class
 * 
 * @author Yang Wang
 *
 */

public class Main {
	private final static String END = "END";
	private final static String NAME = "Main";
	
	public static void main(String[] args) throws IOException{
		String command = "";
		Database db = new Database();
		DataCommand dc = db.getDataCommand();
		TransationCommand tc = db.getTransationCommand();
		
		while (!command.equals(END)){
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			command = in.readLine();
			if (tc.isCommand(command)){
				tc.execute(command, db);
			}else if (dc.isCommand(command)){
				dc.execute(command, db);
			}else if (command.equals(END)){
				continue;
			}else{
				Log.e(NAME, "Neither data command nor transation command!");
				Log.f();
			}
		}
	}
}
