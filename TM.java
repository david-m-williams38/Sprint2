/**
*
*	Created by: David M Williams
*	Program's function: To keep track of certain and all tasks, allowing for data to be stored on each task
*	Team: TBA (Currently a solo project)
*	Class: Sacramento State University CSC 131
*
**/

/**
*
*	Import time functions for timing the operations,
*	as well as use them for calculations on timing for tasks
*	Import standard java.io for regular usage functions
*	Use java.util for certain operations to complete summary of tasks
*	as well as perhaps other smaller operations
*
**/
import java.io.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;



// The whole program relies on this class to run appropriately
public class TM {
	
	// Initialize an arraylist with name 'al'
	// This was originally to be used for other operations,
	// but its current usage is to be a placeholder in case it is needed
	// Maybe used later in the development of the program....
	// It is also placed here for usage in all methods
	ArrayList<String> al = new ArrayList<String>();

	// Initialize Size, for use in declaring tasks as L, XS, S, M, and so on...
	// set the String size to "" so it is initialized and compiles/runs accordingly
	public String Size = "";

	// This Usage() function is to be called once an error occurs in the program
	// It will state how to use the program correctly when called
	void Usage() {
		System.err.println("To use this program you must run it by one of the following:");
		System.err.println("TM Start <Task>");
		System.err.println("TM Stop <Task>");
		System.err.println("TM Describe <Task> <Description, Using Quotes> [Optional]<Size>[Optional]");
		System.err.println("TM Summary <Task>");
		System.err.println("TM Summary");
		System.err.println("TM Size <Task>");
	}


	// This is the portion of the application that runs first
	public static void main(String[] args) throws IOException {
		// The only function of this main(...) is to run the next set of tasks
		// This is a simplified statement that creates a new TM() function
		// It then runs appMain with the args array that is supplied when the program is run
		new TM().appMain(args);
	}

	// appMain is called through the main(...) method
	// If there is an error it will throw an IOException,
	// Basically a try catch function without the need for all catch statements 
	void appMain(String [] args) throws IOException {

		// for the following comments: "~" = "Stands For"/"Represents"
		// Initialize cmd with empty quotes so it has a starting value in memory
		// cmd ~ command
		String cmd = "";
		// Initialize data with empty quotes so it has a starting value in memory
		// data ~ Name
		// It should represent the Task name in the current state of this application,
		// yet might change its function to be for multiple scenarios
		String data = "";
		// Initialize desc with empty quotes so it has a starting value in memory
		// desc ~ description
		String desc = "";


		// Create a new Log() function
		// This will allow all commands, task name, descriptions, etc. to be written to a log file and be stored
		Log log = new Log();

		// Create a new LocalDateTime with name of timeRN, this will get the exact current time when called
		// This will allow for time calculations and start and end times to be completed
		LocalDateTime timeRN = LocalDateTime.now();

		// created a try/catch block that will output the Usage() statement if there is an error
		try {
			// set the command as the first argument in the running of the program
			cmd = args[0];

			// make the command argument all uppercase
			// This will allow any mixture of uppercase and lower case in the command argument to be used
			cmd = cmd.toUpperCase();

			// Check to see if the command equals uppercase "DESCRIBE"
			// It is uppercase because the command value should be set to uppercase in a previous statement.
			if(cmd.equals("DESCRIBE")){

				// If DESCRIBE is the command, set the third argument (as it starts at 0) to be the description
				// This will set the "desc" as the actual description to be saved, later in the program
				desc = args[2];

			}
			// If it is not equal to DESCRIBE, set the description to an empty value of a String
			// This will make sure that it won't have an error if there is no description
			else {
				desc = "";
			}

			// Check to see if the command equals SUMMARY (uppper case once again, as I set it to all uppercase previously)
			// If that is true, it will check the next part of the IF statement,
			// which checks to see if there are less than 2 argument statments
			// Kept the two if statements separate due to the ending else statements being different
			if(cmd.equals("SUMMARY") && args.length < 2){

				// If this is true set the name (aka 'data') to be null, as it is not needed if these are true
				data = null;

			}
			// Every other case that doesn't follow this statement will then make the name (aka 'data'),
			// to be the second argument, allowing for the continuation of the program
			else {
				data = args[1];
			}

		}
		// If any of these fail due to an exception of too many or too few arguments it will print out the Usage() statement
		// This will allow the user to understand how to run the program
		catch (ArrayIndexOutOfBoundsException err) {
			Usage();
		}

		// Set up a SWITCH statement to check for each of the UPPERCASE values of the command
		switch(cmd){
			// If the case is STOP, pass in the following:
			// data, also known as the name of the task
			// log, the initialized log file that will handle the data, and record all of it
			// cmd, aka the UPPERCASE command that was run
			// timeRN, aka the LocalDateTime.now() command which gets the current time down past the second
			case "STOP": cmdStop(data, log, cmd, timeRN);
				break;
			// if the case is START, pass in the following:
			// data, also known as the name of the task
			// log, the initialized log file that will handle the data, and record all of it
			// cmd, aka the UPPERCASE command that was run
			// timeRN, aka the LocalDateTime.now() command which gets the current time down past the second
			case "START": cmdStart(data, log, cmd, timeRN);
				break;
			// If the case is SUMMARY, it will run into and if/else scenario
			case "SUMMARY": 
				// If the name of the task is unknown, it means that the user is requesting the full summary/log file
				if(data == null) {
					// This will just pass the log only, which will run into the appropriate method,
					// to return all summary data
					cmdSummary(log);
				}
				else {
					// if there is any other data, it will send the name of the task,
					// as well as the log file previously created
					cmdSummary(data, log);
				}
				break;
			case "DESCRIBE":
				// if there are exactly 4 arguments in the case of the DESCRIBE command, 
				// it means there is a size parameter also being passed into the according method 
				if(args.length == 4) {
					// Set the Size as the fourth element of the arguments provided 
					Size = args[3];
					// Send the same data, log, cmd, and timeRN into the correct method as the other case statements
					// Also pass into the cmdDescribe method, the description of the task that was supplied
					// As well as the Size of the task that was also supplied
					cmdDescribe(data, log, cmd, timeRN, desc, Size);
				} else {
					// Send all the same data as the previous describe case, except exclude the Size, as this was not supplied
					cmdDescribe(data, log, cmd, timeRN, desc);
				}
				break;
			// If the SIZE is supplied, go to this case, and then enter the below if/else statement
			case "SIZE":
				// This says if there are exactly 3 arguments enter this statement
				if(args.length == 3) {
					// if there are the correct number of arguments, use these to set the Size parameter as the third argument
					Size = args[2];
					// Send all the data (Previously described), into the cmdSize method as shown
					cmdSize(data, log, cmd, timeRN, Size);
				}
				break;
			// NO NEED FOR DEFAULT CASE, AS THIS WAS ALREADY HANDLED PREVIOUSLY
			// If I put another Usage() case, it will the same this out twice, TESTED AND CONFIRMED
		}
	}

	// This is the method called once the command specifies that it is adding a Size to a task
	// It will throw an IOException once there is one, which removes the requirement of try/catch block
	void cmdSize(String data, Log log, String cmd, LocalDateTime timeRN, String Size) throws IOException {
		// This saves all the handled variables into a String
		// Thus allowing it to be printed into the log file with ease
		String line = (timeRN + " " + data + " " + cmd + " " + Size);
		// This takes the previous String named 'line' and saves the data into the log file,
		// and it does this by using the initiated log file and using writeLine with the String as the input of the function
		log.writeLine(line);
	}

	// This method takes the data from the switch statement of START
	// It then defines the data types, such as String data, Log log, and so on
	// It will throw an IOException in an error is encountered, allowing for the exclusion of a try/catch block
	void cmdStart(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{
		// This behaves exactly as the previous String line in the other methods, just might handle different variables
		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);


	}

	// Handles the STOP command from the switch statement
	// It will receive all the data that is provided in the switch statement
	// If there is an error, it should throw an IOException, eliminating the need for a try/catch block
	void cmdStop(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{
		// This behaves exactly as the previous String line in the other methods, just might handle different variables
		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);

	}

	// Handles one part of the DESCRIBE in the switch statement
	// This one will only run if there isn't a size parameter
	// If there is an IOException error, it will be thrown, therefore no need for a try/catch block
	void cmdDescribe(String data, Log log, String cmd, LocalDateTime timeRN, String desc) throws IOException {
		// This behaves exactly as the previous String line in the other methods, just might handle different variables
		String line = (timeRN + " " + data + " " + cmd + " " + desc);
		log.writeLine(line);

	}

	// This is the alternate method for the DESCRIBE operation handled by the switch statement
	// This will run if there IS a size parameter added at the end of the command when the application is run
	// No need for a try/catch block due to the 'throws IOException' statement
	void cmdDescribe(String data, Log log, String cmd, LocalDateTime timeRN, String desc, String Size) throws IOException {
		// This behaves exactly as the previous String line in the other methods, just might handle different variables
		String line = (timeRN + " " + data + " " + cmd + " " + desc + " " + Size);
		log.writeLine(line);

	}


	// This is one of the TWO cmdSummary methods, this one only handles the whole output of the log file
	// There is no task supplied in order to run this command
	// Once again, no need for a try/catch block due to the error that will be thrown, if there is one
	void cmdSummary(Log log) throws IOException{

		// Set the initial value of 'null' so it can be used correctly (And will allow it to compile)
		// This is the scanner, I named it 'mine' in order to differentiate that it is reading MY log file that was supplied
		Scanner mine = null;
		// This sets a file named 'myfile' as a new File
		// This means it set the file, that we named "TM.log", as a type of File
		// This will then allow cmdSummary to correctly read the file
		File myfile = new File("TM.log");
		// Set 'mine' as a Scanner, with the File that we set previously
		// This allows the File, 'myfile', to be read by the Scanner class/operator
		mine = new Scanner(myfile);
		// This while loop will loops until there is no more data left to display out of the log file
		while(mine.hasNext()) {

			// This String is newly created every loop
			// Once created, it will set its value as the next line of the log file
			String lineOfFile = mine.nextLine();
			// This statement allows the String 'lineOfFile' to not display null data, allowing everything to be printed correctly
			// It is a fail safe to make sure that we don't try to display empty lines of the log file supplied
			if(!(lineOfFile.contains("null"))) {
				// Once verified the data is not 'null' print the line of the log file we are currently on
				System.out.println(lineOfFile);
			}
		}
//HELP
		// This is confusing I NEED TO COME BACK TO WHY THIS IS HERE
//HELP
		log.readFile();

	}

	// This will handle the other method of cmdSummary
	// It takes the data of the log file, a well as the Task that is requested to be summarized
	// If there is an IOException, it will be thrown once encountered, thus, ONCE AGAIN no need for a try/catch block
	long cmdSummary(String todo, Log log) throws IOException {

		// Read log file, gather entries
		LinkedList<TaskLogEntry> lines = log.readFile();
		
		// Create Task object based on task name, with entries from log
		Task sumTask = new Task(todo, lines);
		
		// Display 
		System.out.println(sumTask.toString());
		return sumTask.totTime;
		
	}
	






	public class Log{

		public FileWriter fwriter;
		public PrintWriter outFile; 

		public Log() throws IOException {

			fwriter = new FileWriter("TM.log", true);
			outFile = new PrintWriter(fwriter);

		}


		void writeLine(String line) throws IOException{

			outFile.println(line);
			outFile.close();

		}

		LinkedList<TaskLogEntry> readFile() throws IOException {

			LinkedList<TaskLogEntry> LineL = new LinkedList<TaskLogEntry>();
			
			File logF = new File("TM.log");
			Scanner file = new Scanner(logF);
			
			String thisLine;
			while(file.hasNextLine()) {

				TaskLogEntry entry = new TaskLogEntry();
				thisLine = file.nextLine();
				StringTokenizer stringTok = new StringTokenizer(thisLine, " ");
				entry.timeRN = LocalDateTime.parse(stringTok.nextToken());
				entry.data = stringTok.nextToken();
				entry.cmd = stringTok.nextToken();

				if(stringTok.hasMoreTokens())
					entry.desc = stringTok.nextToken();
				while(stringTok.hasMoreTokens()) {
					entry.desc += (" " + stringTok.nextToken());
				}
				LineL.add(entry);

			}
			file.close();
			return LineL;

		}



	}



		class TaskLogEntry{

			LocalDateTime timeRN;
			String cmd;
			String data;
			String desc;

		}
	







	static class TimeUtil {

		static String toElapsedTime(long totSecs) {

			long hours = totSecs/3600;
			long mins = (totSecs % 3600) / 60;
			long secs = (totSecs % 60);

			String timeNow = (String.format("%02d:%02d:%02d", hours, mins, secs));
			return timeNow;

		}
	}

	class Task {

		private String name = "";
		private String desc = "";
		private String timeAhora = "";
		private long totTime = 0;

		public Task(String name, LinkedList<TaskLogEntry> entries) {
			this.name = name;
			//WHY IS THIS BEING WRITTEN AS THE GODDAMN DESCRIPTION
			this.desc = "";
			LocalDateTime lastStart = null;
			long timeOverall = 0;
			for(TaskLogEntry entry : entries) {
				if(entry.data.equals(name)) {
					switch(entry.cmd) {
						case "START":
							lastStart = entry.timeRN;
							break;
						case "STOP":
							if(lastStart != null) {
								timeOverall += taskDuration(lastStart, entry.timeRN);
							}
							lastStart = null;
							break;
						case "DESCRIBE":
							desc += " " + entry.desc;
					}
				}
			}
			this.timeAhora = TimeUtil.toElapsedTime(timeOverall);
			this.totTime = timeOverall;
		}


		public String toString() {
			String stringbean = ("\nSummary for Task: " + this.name + "\nDescription for Task: " + this.desc + Size + "\nDuration for Task: " + this.timeAhora );
			return stringbean;
		}

		long taskDuration(LocalDateTime start, LocalDateTime stop){
				long dur = ChronoUnit.SECONDS.between(start, stop);
				return dur;

		}

	}

}
