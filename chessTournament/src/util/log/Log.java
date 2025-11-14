package util.log;

import java.util.logging.*;

public class Log {

	protected static Logger logger = Logger.getLogger("mylog");
	
	public Log() {
		
		FileHandler fh;
		
		try {
			
			fh = new FileHandler("C:\\Users\\chang\\git\\chess-repository\\chessTournament\\res\\logging\\myLog.txt");
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
