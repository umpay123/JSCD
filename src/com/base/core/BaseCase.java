package com.base.core;

import java.util.Map;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;

//import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.util.ExcelUtil;
import com.util.PropUtil;

public class BaseCase {
	protected String testCaseName;
	protected static BaseWebDriver basedriver;
	protected WebDriver driver;
	protected static PropUtil props = new PropUtil("./config/config.properties");
	protected Map<String, String> data;
//	private Logger logger = Logger.getLogger(BaseCase.class);
	
	@Before
	public void before() {
		PropertyConfigurator.configure("./config/log4j.properties");
		testCaseName = this.getClass().getSimpleName();
		basedriver = new BaseWebDriver(testCaseName);
		data = ExcelUtil.getSpecifySheetByTCName(props.get("conf.data.path"), props.get("conf.data.name"),
					testCaseName);
		
		beforeclass(data.get("DriverType"), data.get("Url"));
	}

	@After
	public void after() {
		afterclass(data.get("DriverType"));
	}

	public void beforeclass(String driverType, String url) {
		if (driverType == null) {
			driverType = "chrome";
		}
		initWebDriver(driverType);
		try {
			driver.get(url);
		} catch (Exception e) {
			basedriver.screenshotToLocal(testCaseName);
//			logger.error("Navigate url error : " + e);
			basedriver.logger().error("Navigate url error : ",e);
		}

	}

	public void afterclass(String driverType) {
		if (driverType.equalsIgnoreCase("firefox")) {
			closeBrowser();
		} else if (driverType.equalsIgnoreCase("ie")) {
			try {
				closeBrowser();
//				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
//				basedriver.pause(3000);
			} catch (Exception anException) {
				anException.printStackTrace();
//				logger.error(anException);
				basedriver.logger().error(anException);
			}

		} else if (driverType.equalsIgnoreCase("chrome")) {
			try {
				closeBrowser();
//				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
//				basedriver.pause(3000);

			} catch (Exception anException) {
				anException.printStackTrace();
//				logger.error(anException);
				basedriver.logger().error(anException);
			}
		} else {
			try {
				throw new Exception("Brows er is not correct");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				logger.error(e);
				basedriver.logger().error(e);
			}
		}
	}
	
	public static void killProcess(String process) {
	  try {
	   String str = "";
	   do {
	    Process p = Runtime.getRuntime().exec("cmd.exe /c taskkill /f /im " + process);
	    p.waitFor();
	    p = Runtime.getRuntime().exec("cmd.exe /c tasklist /FI \"IMAGENAME eq " + process + "\"");
	    InputStream in = p.getInputStream();
	    str = IOUtils.toString(in, "GBK");
//		    System.out.println(str);
	   } while (str.indexOf(process) != -1);
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	}


	private void closeBrowser() {
		driver.quit();
		killProcess("IEDriverServer32.exe");
		killProcess("iexplore.exe");
//		String[] tabs = new String[driver.getWindowHandles().size()];
//		int indTabs = tabs.length;
//		driver.getWindowHandles().toArray(tabs);
//		if (indTabs > 1) {
//			for (int i = 1; i <indTabs; i--) {
//				driver.switchTo().window(tabs[i]);
//				driver.close();
//			}
//		}else{
//			driver.close();
//		}
	}

	public void initWebDriver(String driverType) {

		switch (driverType.toLowerCase().trim()) {
		case "firefox":
			driver = basedriver.getFirefoxDriver();
			break;
		case "chrome":
			driver = basedriver.getChromeDriver();
			break;
		case "ie":
			driver = basedriver.getIEDriver();
			break;
		default:
			driver = null;
		}
	}

	public void wirteToDataFile(String inputCaseName, String cellHeader, String cellValue) {
		
		ExcelUtil.appendCellValues(props.get("conf.data.path"), props.get("conf.data.name"), inputCaseName, cellHeader,
				cellValue);
		
	
	}

}
