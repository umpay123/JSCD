package com.util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerUtil {

	public static Logger getLoggerByName(String path,String name) {
		// 生成新的Logger
		// 如果已經有了一個Logger實例返回現有的
		Logger logger = Logger.getLogger(name);
//		Logger logger = Logger.getRootLogger();
		// 清空Appender。特別是不想使用現存實例時一定要初期化
		logger.removeAllAppenders();
		// 設定Logger級別。
		logger.setLevel(Level.INFO);
		// 設定是否繼承父Logger。
		// 默認為true。繼承root輸出。
		// 設定false後將不輸出root。
		logger.setAdditivity(false);
		// 生成新的Appender
		FileAppender appender = new DailyRollingFileAppender();
		PatternLayout layout = new PatternLayout();
		ConsoleAppender appenderConsole = new ConsoleAppender();

		// log的输出形式
		String conversionPattern = "%d{yyyy/MM/dd HH:mm:ss:SSS} %5p %c:%L - %m%n";
		layout.setConversionPattern(conversionPattern);
		appender.setLayout(layout);
		// log输出路径
		// 这里使用了环境变量[catalina.home]，只有在tomcat环境下才可以取到
//		String tomcatPath = java.lang.System.getProperty("catalina.home");
		appender.setFile(path+"/"+name+"/" + name + ".log");
		// log的文字码
		appender.setEncoding("UTF-8");
		// true:在已存在log文件后面追加 false:新log覆盖以前的log
		appender.setAppend(true);
		// 适用当前配置
		appender.activateOptions();
		// 将新的Appender加到Logger中
		logger.addAppender(appender);
		
		appenderConsole.setEncoding("UTF-8");
		appenderConsole.setThreshold(Level.INFO);
		appenderConsole.setLayout(layout);
		appenderConsole.activateOptions();
		logger.addAppender(appenderConsole);
		return logger;
	}
}
