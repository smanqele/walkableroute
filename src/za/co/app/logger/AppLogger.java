package za.co.app.logger;

import java.util.*;
import java.util.logging.*;

public class AppLogger {
	
    /**
     * Update activities are logged to a file within the logServices root
     * to safeguard operations. The ApplicationLogger class has been created for
     * just for this purpose.
     */
    static ApplicationLogger APP_LOGGER = null;
    private final String  className;
	
	private AppLogger(String classLog){
		this.className = "[" + classLog + "]"; 
		try {
			APP_LOGGER = getInstance();
		} catch (Exception e) {
			/*
        	 * OOOPS! Now where do we log this one out?
        	 */;
        	 String log = "[AppLogger] - Uhmm, we have an undesirable logger situation";
        	 printExceptionStack(e, log);
		}
	} 
	
	public static AppLogger getLogger(String classLog){
		return new AppLogger(classLog);
	}
	
	private static ApplicationLogger getInstance() throws Exception{
		if ( APP_LOGGER == null ) {		
			synchronized (ApplicationLogger.MUTEX) {
				APP_LOGGER = new ApplicationLogger("aStar.logs");
			}
		}		
		return APP_LOGGER;	
	}
	
	public static void printExceptionStack(Exception exc){
		printExceptionStack(exc, exc.getLocalizedMessage());
	}
	
	public static void printExceptionStack(Exception exc, String message){
		System.out.println(message);
		exc.printStackTrace();
	}
	
	/**
	 * If the logger is still Null then just printout the logs in the console
	 * @param log
	 * @return
	 */
	private static boolean appLoggerNull(String log){
		if (APP_LOGGER == null){
			try {
				APP_LOGGER = getInstance();
			} catch (Exception e) {
				printExceptionStack(e, log);
				return true;
			}			
		}
		return false;
	}
		
	public void log(String message, Throwable throwable) {
		String logStr = className + " - " + message;		
		if (appLoggerNull(logStr)){
			return;
		}	
		APP_LOGGER.log(logStr, throwable);	
	}
	
	public void log(LogType info, String message) {
		String logStr = className + " - " + message;
		if (appLoggerNull(logStr)){
			return;
		}		
		APP_LOGGER.log(info, logStr);	
	}
	
	public void log(String message) {
		log(LogType.INFO, message);	
	}
	
	public void log(LogType error, Exception exception) {
		String logStr = className + " - " + exception.getMessage();
		if (appLoggerNull(logStr)){
			return;
		}	
		APP_LOGGER.log(logStr, exception);	
	}
			
	/**
     * The <b>ApplicationLogger</b> class is for logging the activities
     * done within the application. The log is saved with the name
     * like "aStarAlgo_{date_number}.log". Application re-starts create a new log
     * file and leave old ones intact.
     * 
     * @author Sihle
     */
    static class ApplicationLogger {

        static FileHandler HANDLER = null;
        private static Logger LOGGER;
        private static Object MUTEX = new Object();
        
        private static String LOGS_PATH = "resources/logs/";

        Calendar setup = Calendar.getInstance();

        Date today = new Date();

        ApplicationLogger(String name) throws Exception{
            
            LOGGER = Logger.getLogger(name);
            setup.setTime(today);
            //String path = System.getProperty("user.home") + LOGS_PATH ;
            HANDLER = new FileHandler(LOGS_PATH + "aStarAlgo_" + getTimeStr() + ".log");
            HANDLER.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(HANDLER);
        }

        private String getTimeStr() {
            StringBuffer str = new StringBuffer();
            str.append(setup.get(Calendar.DATE)).append("_");
            int month = setup.get(Calendar.MONTH) + 1;
            if (month < 10) {
                str.append("0").append(month).append("_");
            } else {
                str.append(month).append("_");
            }
            str.append(setup.get(Calendar.YEAR)).append("_");
            str.append(setup.getTimeInMillis());
            return str.toString();
        }
        
    	public void log(LogType type, String message){
    		
    		switch (type){
    		    case INFO : {
    		    	LOGGER.info(message);
    			    break;
    		    }
    		    case ERROR : {
    		    	LOGGER.log(Level.SEVERE, message);
    			    break;
    		    }
    		    case WARN : {
    		    	LOGGER.log(Level.WARNING, message);
    		    }
    		default:
    			break;
    		}
    	}
    	   	
    	public void log(String message, Throwable t){		
    		LOGGER.log(Level.SEVERE, message, t);		    
    	}
    }
	
	public static enum LogType {INFO, ERROR, EXCEPTION, WARN}
}
