package util.log;

import java.util.logging.*;
import java.util.logging.FileHandler.*;

public class Log {

	public Logger logger = Logger.getLogger("mylog");
	
	public void init() {
		
		FileHandler fh;
		
		try {
			
			fh = new FileHandler("/Users/chang/git/chess-repository/chessTournament/res/logging/myLog.txt");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.setLevel(Level.INFO);
			logger.info("Logger running \n" + "..................................................");
			
		}
		
		catch(Exception e) {
			logger.log(Level.WARNING, "FileNotFoundException : ", e);
		}
		
	}
	
}
