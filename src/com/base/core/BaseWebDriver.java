package com.base.core;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
//import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.util.DataBaseUtil;
import com.util.DateTimeUtil;
import com.util.FileUtil;
import com.util.LoggerUtil;
import com.util.PropUtil;

public class BaseWebDriver {
	private WebDriver driver;
	private String testCaseName;
	private static PropUtil props = new PropUtil("./config/config.properties");
	// private Logger logger = Logger.getLogger(BaseWebDriver.class);
	long pageLoadTimeout = Integer.valueOf(props.get("conf.loadTime.second"));
	long waitTimeout = Integer.valueOf(props.get("conf.timeOut.second"));
	long scriptTimeout = Integer.valueOf(props.get("conf.pauseTime.second"));
	Connection conn = null;
	Logger logger = Logger.getLogger(BaseWebDriver.class);
	private List<WebElement> findElements;

	public BaseWebDriver(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	

	public BaseWebDriver() {
		super();
	}

	/**
	 * 
	 * @return webdriver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	
	
	

	public void executeScriptDOMBYID(String idStr, String attrStr, String valueStr) {
		try {
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+idStr+"\").setAttribute(\""+attrStr+"\",\""+valueStr+"\"); ");
			
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}
	/**
	 * @param millisecond
	 *            time to wait, in millisecond
	 */
	public void pause(long millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			// logger.error(e);
			logger().error(e);
		}
	}

	/**
	 * rewrite findElement method
	 * 
	 * @param loc
	 *            the locator of the element to be find
	 * @return WebElement
	 */
	
	public WebElement findElement(By loc) {
		WebElement element = null;
		try {
			element = driver.findElement(loc);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error("Can not find element:", e);
			logger().error("Can not find element:", e);
		}
		return element;
	}

	/**
	 * rewrite select selectByVisibleText method
	 * 
	 * @param element
	 *            web select
	 * @param visibleText
	 *            select visibleText
	 */
	public void selectByVisibleText(WebElement element, String visibleText) {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(visibleText);
			logger().info("Select'" + visibleText + "'on'" + element + "'element");
		} catch (Exception e) {
			destopshotToLocal(testCaseName);
			logger().error(e);
		}
	}
	
	/**
	 * rewrite findElements method
	 * 
	 * @param loc
	 *            the locator of the element to be find
	 * @return List include WebElement
	 */
	public List<WebElement> findElements(By loc) {
		List<WebElement> elementlist = null;
		try {
			elementlist =driver.findElements(loc);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			logger().error("Can not find element list:", e);
		}
		return elementlist;
	}
	public void selectByIndex(WebElement element, String index) {
		try {
			Select sel = new Select(element);
			sel.selectByIndex(2);
			logger().info("Select'" + index + "'on'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite the WebDriverWait method
	 *
	 * @param loc
	 *            the locator of the element to be find
	 * @param timeout
	 *            second
	 * @return the first element accord your locator until it is appeared in the
	 *         time range
	 */
	public WebElement waitAndGetElement(final By loc, long timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			element = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d.findElement(loc);
				}
			});
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			logger().error("Can not find element:", e);
		}
		return element;
	}

	/**
	 * rewrite select selectByValue method
	 * 
	 * @param element
	 *            web select
	 * @param value
	 *            select option value
	 */
	public void selectByValue(WebElement element, String value) {
		try {
			Select sel = new Select(element);
			sel.selectByValue(value);
			logger().info("Select'" + value + "'on'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite sendKeys method
	 * 
	 * @param element
	 *            web element
	 * @param content
	 *            input content
	 */
	public void sendKeys(WebElement element, String content) {
		try {
			element.sendKeys(content);
			logger().info("Input'" + content + "'on'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite click method
	 * 
	 * @param element
	 *            web element
	 */
	public void click(WebElement element) {
		try {
			element.click();
			logger().info("Click'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite action click method
	 * 
	 * @param element
	 *            web element
	 */
	public void actionClick(WebElement element) {
		try {
			action().click(element).perform();
			logger().info("Simulate mouse-Click'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite action doubleClick method
	 * 
	 * @param element
	 *            web element
	 */
	public void actionDoubleClick(WebElement element) {
		try {
			action().doubleClick(element).perform();
			logger().info("Simulate mouse-double click'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite action moveToElement method
	 * 
	 * @param element
	 *            web element
	 */
	public void actionMoveToElement(WebElement element) {
		try {
			action().moveToElement(element).perform();
			logger().info("Simulate mouse-move to'" + element + "'element");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * rewrite action sendKeys method
	 * 
	 * @param element
	 *            web element
	 * @param keysToSend
	 *            key
	 */
	public void actionSendKeys(WebElement element, Keys keysToSend) {
		try {
			action().sendKeys(element, keysToSend).perform();
			logger().info("Simulate key-on'" + element + "'element and click" + keysToSend.name() + "key");
		} catch (Exception e) {
			logger().error(e);
		}
	}

	/**
	 * Is this element displayed or not? This method avoids the problem of
	 * having to parse an element's "style" attribute.
	 * 
	 * @param element
	 *            web element
	 * @return true or false, true is displayed
	 */
	public boolean isElementDisplayed(WebElement element) {
		boolean result = false;
		result = element.isDisplayed();
		return result;
	}

	/**
	 * Is the element currently enabled or not? This will generally return true
	 * for everything but disabled input elements.
	 * 
	 * @param element
	 *            web element
	 * @return true or false, true is enabled
	 */
	public boolean isElementEnabled(WebElement element) {
		boolean result = false;
		result = element.isEnabled();
		return result;
	}

	/**
	 * Determine whether or not this element is selected or not. This operation
	 * only applies to input elements such as checkboxes, options in a select
	 * and radio buttons.
	 * 
	 * @param element
	 *            web element
	 * @return true or false, true is selected
	 */
	public boolean isElementSelected(WebElement element) {
		boolean result = false;
		result = element.isSelected();
		return result;
	}

	/**
	 * @param timeout
	 *            time to wait, in second
	 * @param locator
	 *            position the element
	 */
	public boolean isElementPresent(By locator, long timeout) {

		boolean isFine = false;

		long timeBegins = System.currentTimeMillis();

		do {
			try {
				driver.findElement(locator);
				isFine = true;
				break;
			} catch (Exception e) {
				screenshotToLocal(testCaseName);
				// logger.error("isElementPresent error :", e);
				logger().error("isElementPresent error :", e);
			}
			pause(2000);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		return isFine;
	}

	/**
	 * judge if the alert is present in specified seconds
	 *
	 * @param timeout
	 *            timeout in seconds
	 */
	public boolean isAlertExists(long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.switchTo().alert();
				isSucceed = true;
				break;
			} catch (Exception e) {
				screenshotToLocal(testCaseName);
				// logger.error(e);
				logger().error(e);
			}
			pause(2000);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		return isSucceed;
	}
	
	
	
	public boolean doesWebElementExist(By selector)
		{ 
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			try { 
			driver.findElement(selector); 
			              return true; 
			   } 
			 catch (NoSuchElementException e){ 
			 return false; 
			} 
		}         



	
	
	
	
	
	

	/**
	 * choose OK/Cancel button's OK on alerts
	 *
	 * @param timeout
	 *            seconds
	 */
	public void chooseOKOnAlert(long timeout) {
		try {
			if (isAlertExists(timeout)) {
				System.out.println(driver.switchTo().alert().getText()+"===================================");
				driver.switchTo().alert().accept();
				// logger.debug("click OK button on alert");
				logger().info("Click OK button on alert");
			}
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}
	
    public WebDriver switchPage(WebDriver IEDriver){
    	ArrayList<String> hs = new ArrayList<String>(IEDriver.getWindowHandles());
    	String hs1 = hs.get(hs.size()-1);
    	IEDriver.switchTo().window(hs1);
    	return IEDriver;
    }
    
	

	/**
	 * choose OK/Cancel button's OK on alerts
	 */
	public void chooseOKOnAlert() {
		chooseOKOnAlert(0);
	}

	/**
	 * 
	 * @return true when page is loaded within time return false when timeout
	 */
	public boolean waitPageIsReady() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			// System.out.println("Page Is loaded.");
			return true;
		}

		// This loop will rotate for pageLoadTimeout to check If page Is ready
		// after every 1 second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < this.pageLoadTimeout; i++) {
			try {
				Thread.sleep(1000);
				// System.out.println("wait:" + String.valueOf(i)) ;
			} catch (InterruptedException e) {
				screenshotToLocal(testCaseName);
				// logger.error(e);
				logger().error(e);
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				// System.out.println("Page Is loaded.");
				return true;
			}
		}
		return false; // timeout
	}

	/**
	 * navigate to the new open window
	 */
	public void navigateToNewWindow() {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		try {
			driver.switchTo().window(tabs.get(1));
		} catch (Exception e) {
			destopshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
		waitPageIsReady();
		pause(1000);
	}

	/**
	 * navigate to the Last open window
	 */
	public void navigateToLastWindow() {
		// ArrayList<String> tabs = new
		// ArrayList<String>(driver.getWindowHandles());
		String[] tabs = new String[driver.getWindowHandles().size()];
		int indTabs = tabs.length;
		// pause(3000);
		driver.getWindowHandles().toArray(tabs);
		System.out.println(indTabs + "+++++++++++++++++++++");
		try {
			// driver.switchTo().window(tabs.get(indTabs-1));
			driver.switchTo().window(tabs[indTabs - 1]);
			driver.manage().window().maximize();
			System.out.println(driver.getTitle() + indTabs);
		} catch (Exception e) {
			destopshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
		waitPageIsReady();
		pause(1000);

	}

	/**
	 * navigate to the nominated window by title
	 * 
	 * @param title
	 *            window's title
	 * @param timeout
	 *            second
	 */
	public void navigateToWindowByTitle(final String title, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(new ExpectedCondition<String>() {
				@Override
				public String apply(WebDriver d) {
					System.out.println(driver.getWindowHandles().size());
					for (String windowHandle : d.getWindowHandles()) {
						System.out.println(windowHandle);
						d.switchTo().window(windowHandle);
						System.out.println(d.getTitle());
						if (d.getTitle().contains(title))
							//d.manage().window().maximize();
							return d.getWindowHandle();
					}
					return null;
				}
			});
		} catch (Exception e) {
			destopshotToLocal(testCaseName);
			// logger.error("Can not find element:", e);
			logger().error("Can not find element:", e);
		}
	}
	
	/*怎么判断页面有特定的文字*/
	public  boolean isTextPresent(String what) {
		try{
		return driver.findElement(By.tagName("body")).getText().contains(what);// 遍历body节点下的所有节点 取其文本值 并判断是否包含 文本 what
		}catch (Exception e) {
		return false;// 没有该文本 则 返回 false
		}
		}
	
	
	/**
	 * focus on the nominated frame
	 * 
	 * @param framename
	 *            frame name or id
	 */
	public void switchToFrame(String framename) {
		try {
			driver.switchTo().frame(framename);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}
	
	/**
	 * navigate to the nominated window by url
	 *
	 */
	public void navigateToWindowByUrl(final String url, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(new ExpectedCondition<String>() {
				@Override
				public String apply(WebDriver d) {
					for (String windowHandle : d.getWindowHandles()) {
						d.switchTo().window(windowHandle);
						if (d.getCurrentUrl().contains(url)){
							 d.manage().window().maximize();
							System.out.println("【"+url+"】");
							return d.getWindowHandle();
						}

					}
					return null;
				}
			});
		} catch (Exception e) {
			destopshotToLocal(testCaseName);
			// logger.error("Can not find element:", e);
			logger().error("Can not find element:", e);
		}
	}
	
/*	public void switchToFrame(WebElement webElement) {
		try {
			driver.switchTo().frame(webElement);
			System.out.println("跳转到："+webElement+"->title:");
		} catch (Exception e) {
			try{
				screenshotToLocal(testCaseName);//
			}catch (Exception ex) {
				destopshotToLocal(testCaseName);//
				logger().error(ex);
			}
			// logger.error(e);
			logger().error(e);
		}
	}*/

	public void switchToFrame(WebElement frame) {
		try {
			driver.switchTo().frame(frame);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}
	
	public boolean switchToFrame(String framename, long timeout) {

		boolean isFine = false;

		long timeBegins = System.currentTimeMillis();

		do {
			try {
				driver.switchTo().frame(framename);
				isFine = true;
				break;
			} catch (Exception e) {
				screenshotToLocal(testCaseName);
				logger().error("switchToFrame error :", e);
			}
			pause(2000);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		return isFine;
	}
	
	public void switchToFrame2(String framename) {
		try {
			driver.switchTo().frame(framename);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}

	/**
	 * focus on the default content
	 */
	public void swithToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}

	/**
	 * 
	 * @return Actions
	 */
	public Actions action() {
		Actions action = new Actions(driver);
		return action;
	}

	/**
	 * send click command to element
	 * 
	 * @param element
	 *            the nominated element
	 */
	public void sendClickCommandToElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			logger().info("Click'" + element + "'element");
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}

	/**
	 * run the nominated js code
	 * 
	 * @param js
	 * @param element
	 */
	public void runJS(String js, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript(js, element);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}

	/**
	 * create capture and save to local
	 * 
	 * @param testCaseName
	 */
	public void screenshotToLocal(String testCaseName) {
		File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtil.copy(screenShotFile.getAbsolutePath(), createCaptureFolder(testCaseName) + "/" + testCaseName
					+ DateTimeUtil.getCurrentDateTime() + ".png");
		} catch (Exception e) {
			// logger.error("Save screencapture failed：", e);
			logger().error("Save screencapture failed：", e);
		}
	}
	


	/**
	 * create whole destop capture and save to local
	 * 
	 * @param testCaseName
	 */
	public void destopshotToLocal(String testCaseName) {
		try {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
			File f = new File(testCaseName);
			ImageIO.write(screenshot, "png", f);
			FileUtil.copy(f.getAbsolutePath(), createCaptureFolder(testCaseName) + "/" + testCaseName
					+ DateTimeUtil.getCurrentDateTime() + ".png");
		} catch (Exception e) {
			logger().error("Save destop capture failed：", e);
		}
	}

	/**
	 * create capture folder by test case name
	 * 
	 * @param testcasename
	 * @return folder path
	 */
	private String createCaptureFolder(String testcasename) {

		String folderName = null;
		try {
			File report_file = new File(props.get("conf.screenshot.path") + "/" + testcasename);

			if (!report_file.exists()) {
				report_file.mkdirs();
			}
			folderName = report_file.getAbsolutePath();
		} catch (Exception e) {
			// logger.error("createCaptureFolder error :", e);
			logger().error("createCaptureFolder error :", e);
			e.printStackTrace();
		}
		return folderName;

	}
	
	/**
	        * return the nominated table cell
	        * 
	        * @param table
	        * @param row
	        * @param col
	        * @return
	        */
	public WebElement getTableCell(WebElement table, int row, int col){
		return table.findElements(By.tagName("tr")).get(row).findElements(By.tagName("td")).get(col);
	}

	public void insertCaptureToDB(String businessno) {
		String rs = "";
		// String sql = "SELECT COUNT(*) AS COUNT FROM prplcontent where
		// serialno = 10";
		String sql = "insert into prplcontent (id, businessno, serialno, sitecode, policyno, operatorcode, operatorname, inputtime, remark, typecode, parenttypecode, sysfilename, fileaddress, servicename, filepath, suspiciousflag, uploadnodeind, filename, imgsize, collectorname, displayname, description, sourceflag, syncstatus, sysuser, comcode, macaddr, manufacturer, cameratype, photoresolution, phototime, photosize, inserttimeforhis, operatetimeforhis, validflag) values (seq_PrpLcontent.nextval, '"
				+ businessno.trim()
				+ "', 1, '0001', ' ', '1100000000', 'sql直接插入数据 ', '2015-02-14 16:19:02', null, '1001', null, '1.jpg', 'http://192.168.20.84:8888', 'ImageWeb/FileServerService ', '/home/weblogic/image/2012/2/14/RDAA201232100000000165/1001', '2', null, '1.jpg', 976, '北京 ', null, '', null, null, null, null, null, null, null, null, null, null, '2015-02-14 16:03:09', '2015-02-14 16:03:09', null);";
		conn = DataBaseUtil.getDatabaseConnection(props.get("conf.db.jdbc.url"), props.get("conf.db.jdbc.driver"),
				props.get("conf.db.user"), props.get("conf.db.pwd"));
		rs = DataBaseUtil.executeSql(sql);
		System.out.println(rs);
		DataBaseUtil.closeConnection();
	}
	// 关闭新打开的网页
	public void close() {
		driver.close();
	}
	/**
	 * click save button on IE popup
	 */
	public void clickIEPopSave() {
		try {
			Robot rt = new Robot();
			rt.keyPress(KeyEvent.VK_ALT);
			rt.keyPress(KeyEvent.VK_S);
			rt.keyRelease(KeyEvent.VK_ALT);
			rt.keyRelease(KeyEvent.VK_S);
			pause(2000);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * if element attribute is usable
	 * 
	 * @param element
	 *            web element
	 * @param attr
	 *            attributes name
	 * @param timeout
	 *            time
	 * @return
	 */
	public boolean isAttributeUsable(WebElement element, String attr, int timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				if (!element.getAttribute(attr).equals("") && element.getAttribute(attr) != null) {
					isSucceed = true;
					break;
				}
			} catch (Exception e) {
				screenshotToLocal(testCaseName);
				logger().error(e);
			}
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		return isSucceed;
	}
	
	/**
	 * deal text
	 * 
	 * @param loc
	 *            the locator of the element to be find
	 * @return String
	 */
	public String elementTextFrist(String srcText, String fristStr) {
		String resText = null;
		try {
//			srcText = driver.findElement(loc).getText();
			resText = srcText.substring(srcText.indexOf(fristStr)+1);
			System.out.println(resText);
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error("Can not find element:", e);
			logger().error("Can not find element:", e);
		}
		return resText;
	}
	
    /**
     * input value with js
     * 
     * @paramvalue
     * @paramelement
     */
    public void inputValue(String value, WebElement element) {
            try{
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1]", element, value);
            } catch(Exception e) {
                    screenshotToLocal(testCaseName);
                    logger().error(e);
            }
    }

	/**
	 * return element attributes value
	 * 
	 * @param element
	 *            web element
	 * @param attr
	 *            attributes name
	 * @param timeout
	 *            time
	 * @return
	 */
	public String returnAttributeValue(WebElement element, String attr, int timeout) {
		if (isAttributeUsable(element, attr, timeout)) {
			return element.getAttribute(attr);
		}
		return null;
	}

	public Logger logger() {
		return LoggerUtil.getLoggerByName(props.get("conf.log.path"), testCaseName);
	}

	public WebDriver getFirefoxDriver() {
		System.setProperty("webdriver.firefox.bin", props.get("conf.firefoxdriver.path"));
		try {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new FirefoxDriver(capabilities);
			driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			// logger.error("Loading Firefox Driver Error : " + e);
			logger().error("Loading Firefox Driver Error : ", e);
		}

		return driver;
	}

	public WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", props.get("conf.chromedriver.path"));
		try {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			// logger.error("Loading Chrome Driver Error : " + e);
			logger().error("Loading Chrome Driver Error : ", e);
		}

		return driver;
	}

	public WebDriver getIEDriver() {
		System.setProperty("webdriver.ie.driver", props.get("conf.iedriver.path"));
		try {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
			// logger.info("start InternetExplorerDriver");
			logger().info("start InternetExplorerDriver");
			driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
			// logger.debug("set pageLoadTimeout : " + pageLoadTimeout);
			logger().debug("set pageLoadTimeout : " + pageLoadTimeout);
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
			// logger.debug("set waitTimeout : " + waitTimeout);
			logger().debug("set waitTimeout : " + waitTimeout);
			driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);
			// logger.debug("set scriptTimeout : " + scriptTimeout);
			logger().debug("set scriptTimeout : " + scriptTimeout);
			driver.manage().window().maximize();
		} catch (Exception e) {
			// logger.error("Loading IE Driver Error : " + e);
			logger().error("Loading IE Driver Error : ", e);
		}

		return driver;
	}
	
	/**
	 * 处理打开页面后直接弹出JS框的问题
	 * @param 
	 * @return
	 */
	public void alertSP() {//markwj
		int times = 0;
		do {
			try {
				for (String str : driver.getWindowHandles()) {
					driver.switchTo().window(str);
					driver.getTitle();
				}
			} catch (UnhandledAlertException e) {
				break;
			} catch (Exception e) {
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (times++ < 30);
	}
	
	/**
	  * 判断是否有alert弹窗
	  * 
	  */
	 public boolean IsAlertPresent() {
	  boolean findFlag = false;
	  int countIndex = 0;
	  do {
	   try {
	    driver.switchTo().alert();
	    findFlag = true;
	   } catch (NoAlertPresentException e) {
	   }
	   int sleepCount = 1000;
	   if (!findFlag) {
	    try {
	     Thread.sleep(sleepCount);
	    } catch (InterruptedException e) {
	    }
	   }
	   if (countIndex > 0) {
	    String sleepInfo = "判断是否有alert弹窗智能等待第：" + countIndex + "次，本次等待时间:" + sleepCount;
	    System.out.println(sleepInfo);
	    logger.info(sleepInfo);
	   }
	  } while (!findFlag && countIndex++ < 3);
	  return findFlag;
	 }
	 
	 public Alert alert() {
		  Alert alert = null;
		  boolean findFlag = false;
		  int countIndex = 0;
		  do {
		   try {
		    alert = driver.switchTo().alert();
		    findFlag = true;
		   } catch (NoAlertPresentException e) {
		   }
		   int sleepCount = 1000;
		   if (!findFlag) {
		    try {
		     Thread.sleep(sleepCount);
		    } catch (InterruptedException e) {
		    }
		   }
		   if (countIndex > 0) {
		    String sleepInfo = "driver.switchTo().alert智能等待第：" + countIndex + "次，本次等待时间:" + sleepCount;
		    System.out.println(sleepInfo);
		    logger.info(sleepInfo);
		   }
		  } while (!findFlag && countIndex++ < 30);
		  return alert;
		 }


	public void refresh() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
		driver.get(driver.getCurrentUrl());
		driver.navigate().to(driver.getCurrentUrl());
		
	}
	
	public boolean checkIfPresentAndClick(By by ){
		WebElement sertchEle = null;
    	try{
	  	sertchEle = this.findElement(by);
	  	
		}catch(Exception e) {
	 	 e.printStackTrace();
		}
    	if(sertchEle!=null){
        //点击该页面元素

		this.findElement(by).sendKeys("");
		this.findElement(by).click();
    
        	//actionName:
        	String flowButtonName = "";
            return true;
     	}else{
		//actionName:
		String flowButtonName = "";
		//没找到页面元素
        return false;
       	}
	}
	
	
	public void executeScript(String shuzi) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, "+shuzi+")");
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			// logger.error(e);
			logger().error(e);
		}
	}
	
	public void executeScriptByElement(WebElement el) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", el);  
		} catch (Exception e) {
			screenshotToLocal(testCaseName);
			logger().error(e);
		}
	}
	public void sel(WebElement element, String value,String value1) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].oldval=\""+value+"\"", element);
		    js.executeScript("arguments[0].defval=\""+value1+"\"", element);
		} catch (Exception e) {
			logger().error(e);
		}
	}
	public void runjs(WebElement element, String value) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].value=\""+value+"\"", element);
		} catch (Exception e) {
			logger().error(e);
		}
	}
	public void runjs1(WebElement element, String value) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].checked=\""+value+"\"", element);
		} catch (Exception e) {
			logger().error(e);
		}
	}
	public void executeScriptremove(String idStr) {
		try {
			 JavascriptExecutor removeAttribute = (JavascriptExecutor)driver;  
		      //remove readonly attribute
		      removeAttribute.executeScript("var setDate=document.Id(\""+idStr+"\");setDate.removeAttribute('readonly');") ;
			
		} catch (Exception e) {
			logger().error(e);
		}
	}
	public void removeReadonly(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].removeAttribute('readonly')", element);
		} catch (Exception e) {
			logger().error(e);
		}
	}
	
}
