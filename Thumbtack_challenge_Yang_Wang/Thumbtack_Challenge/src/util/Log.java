package util;

public class Log {
	/**
	 * Log class, recording the info and error
	 * 
	 */
	
	/* Output the result for the Command class*/
	public static void i(String info){
		System.out.println(info);
	}
	
	/* Error handling*/
	public static void e(String classname, String error){
		System.out.println("Error happens in class " + classname + ":");
		System.out.println(error);
	}
	
	/* Instructed info about the input parameters */
	public static void f(){
		StringBuffer sb = new StringBuffer();
		sb.append("Formatted input parameters should be: \n");
		sb.append("----------------------------\n");
		sb.append("SET name value\n");
		sb.append("GET name\n");
		sb.append("UNSET name\n");
		sb.append("NUMEQUALTO value\n");
		sb.append("END\n");
		sb.append("BEGIN\n");
		sb.append("ROLLBACK\n");
		sb.append("COMMIT\n");
		sb.append("----------------------------\n");
		System.out.println(sb.toString());
	}

}
