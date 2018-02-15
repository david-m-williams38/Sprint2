import java.io.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;



public class TM {

	ArrayList<String> al = new ArrayList<String>();
	public String Size = "";
	void Usage() {

		System.err.println("To use this program you must run it by one of the following:");
		System.err.println("TM Start <Task>");
		System.err.println("TM Stop <Task>");
		System.err.println("TM Describe <Task> <Description, Using Quotes>");
		System.err.println("TM Summary <Task>");
		System.err.println("TM Summary");

	}

	void appMain(String [] args) throws IOException {

	String cmd = "";
	String data = "";
	String desc = "";


	Log log = new Log();

	LocalDateTime timeRN = LocalDateTime.now();

	try {
		cmd = args[0];



		cmd = cmd.toUpperCase();

		if(cmd.equals("DESCRIBE")){

			desc = args[2];

		}
		else {
			desc = "";
		}
		
		if(cmd.equals("SUMMARY") && args.length < 2){

			data = null;

		}
		else {

			data = args[1];

		}
	}
	catch (ArrayIndexOutOfBoundsException err) {

		Usage();

	}

		switch(cmd){

			case "STOP": cmdStop(data, log, cmd, timeRN);
				break;
			case "START": cmdStart(data, log, cmd, timeRN);
				break;
			case "SUMMARY": 
				if(data == null) {
					cmdSummary(log);
				}
				else {
					cmdSummary(data, log);
				}
				break;
			case "DESCRIBE": if(args.length == 4)
			{
				Size = args[3];
				cmdDescribe(data, log, cmd, timeRN, desc, Size);
			} else {

				cmdDescribe(data, log, cmd, timeRN, desc);

			}
				break;
			case "SIZE": 
				if(args.length == 3) {
					Size = args[2];
					cmdSize(data, log, cmd, timeRN, Size);
				}
				break;
			}
		}



	void cmdSize(String data, Log log, String cmd, LocalDateTime timeRN, String Size) throws IOException {

		String line = (timeRN + " " + data + " " + cmd + " " + Size);
		log.writeLine(line);

	}


	void cmdStart(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{

		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);


	}

	void cmdStop(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{

		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);

	}


	void cmdSummary(Log log) throws IOException{
/**
		long totTime = 0;
		LinkedList<TaskLogEntry> lines = log.readFile();
		
		TreeSet<String> nameData = new TreeSet<String>();

		for(TaskLogEntry entry : lines) {

			nameData.add(entry.data);

		}
		*/

		Scanner mine = null;
		File myfile = new File("TM.log");
		mine = new Scanner(myfile);
		while(mine.hasNext()) {

			String lineOfFile = mine.nextLine();
			if(!(lineOfFile.contains("null"))) {

				//I don't remember why i have this, but removing it works YEA!
//				if(lineOfFile.contains(" DESCRIBE ")) {

					System.out.println(lineOfFile);

//				}

			}

		}
		//log.writeLine(line);

		
		log.readFile();

		
	}
		
	long cmdSummary(String todo, Log log) throws IOException {

		// Read log file, gather entries
		LinkedList<TaskLogEntry> lines = log.readFile();
		
		// Create Task object based on task name, with entries from log
		Task sumTask = new Task(todo, lines);
		
		// Display 
		System.out.println(sumTask.toString());
		return sumTask.totTime;
		
	}
		
/**
testing other code, this is currently useless

		Scanner mine = null;
		File myfile = new File("TM.log");
		mine = new Scanner(myfile);
		while(mine.hasNext()) {

			String lineOfFile = mine.nextLine();
			if(lineOfFile.contains(data)) {

				if(lineOfFile.contains(" DESCRIBE ")) {

					System.out.println(lineOfFile);

				}

			}

		}
		log.writeLine(line);

		
		log.readLog();
*/
	


	void cmdDescribe(String data, Log log, String cmd, LocalDateTime timeRN, String desc) throws IOException {

		String line = (timeRN + " " + data + " " + cmd + " " + desc);
		log.writeLine(line);

	}


	void cmdDescribe(String data, Log log, String cmd, LocalDateTime timeRN, String desc, String Size) throws IOException {

		String line = (timeRN + " " + data + " " + cmd + " " + desc + " " + Size);
		log.writeLine(line);

	}


	public static void main(String[] args) throws IOException {

		new TM().appMain(args);

		/**System.out.println("Test Number 1");

		System.out.println("args[0] = " + args[0]);

		System.out.println("args[1] = " + args[1]);
		*/
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

/**

This is crap code for the time being...

			public LogEntry(String taskLine) {

				StringTokenizer stok = new StringTokenizer(taskLine, " ");
				int nTokens = stok.countTokens();
				timeRN = LocalDateTime.parse(stok.nextToken());

			}
			
*/
}
