package object.jscd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.base.core.BaseCase;

public class CommonObject extends BaseCase{
	/*-通用元素*/
	public CommonObject(){
	}
	
	/*财经日历*/
	public WebElement cJRL(){
		return basedriver.waitAndGetElement(By.xpath("//span[text()='财经日历']"), 100);
	}
	
	/*数据中心*/
	public WebElement sJZX(){
		return basedriver.waitAndGetElement(By.xpath("//span[text()='数据中心']"), 100);
	}
	
	/*ETF*/
    public WebElement ETF_CC(){
    	return basedriver.findElement(By.xpath("//p[text()='ETF持仓']"));
    }

    /*白银ETF*/
    public WebElement BY_ETF(){
    	return basedriver.waitAndGetElement(By.xpath("//div[text()='白银ETF']"), 100);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	/*确认按钮*/
    public WebElement InsuranceButton(){
		return basedriver.waitAndGetElement(By.xpath("//*[@id='layui-layer1']/div[3]/a"), 100);
	}
    
	/*菜单-投保*/
	public WebElement InsurancePolicy(){
		return basedriver.waitAndGetElement(By.id("insurance_btn"), 100);
	}
	//续保
	public WebElement renewalButton(){
		return basedriver.waitAndGetElement(By.id("renewal_btn"), 100);
	}
	//上一年保单号
	public WebElement lastYearAppPolicyNo(){
		return basedriver.waitAndGetElement(By.id("lastYearAppPolicyNo"), 100);
	}
	//查询 
	public WebElement chaxun(){
		return basedriver.waitAndGetElement(By.xpath("//*[normalize-space(text())='续保查询']/parent::*//following-sibling::*//span[text()='查询']"), 100);
	}
	//续保确认  
	public WebElement renewalSureBt(){
		return basedriver.waitAndGetElement(By.id("renewalSureBt"), 100);
	}

	public WebElement userName() {
		return basedriver.waitAndGetElement(By.id("username"), 100);
	}
	public WebElement passWord() {
		return basedriver.waitAndGetElement(By.id("password"), 100);
	}
	public WebElement loginButton() {
		return basedriver.waitAndGetElement(By.xpath("//input[contains(@value,'登')]"), 100);
	}
	public WebElement createMing() {
		return basedriver.waitAndGetElement(By.xpath("//span[text()='创建实名']"), 100);
	}
	public WebElement writePin() {
		return basedriver.waitAndGetElement(By.xpath("//label[text()='Pin']//following::input[1]"), 100);
	}
	public WebElement radomCreate() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'随机生成')]"), 100);
	}
	public WebElement submitMing() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'提交')]"), 100);
	}
	public WebElement bankCoreMenu() {
		return basedriver.waitAndGetElement(By.xpath("//li[@role='menuitem']/span[text()='金融卡中心']"), 100);
	}
	public WebElement selectShiming() {
		return basedriver.waitAndGetElement(By.xpath("//span[text()='查询实名']"), 100);
	}
	public WebElement radomInfo() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'随机信息生成')]"), 100);
	}
	public WebElement realName() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'姓名')]//following::span[1]"), 100);
	}
	public WebElement realShenfen() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'证件号')]//following::span[1]"), 100);
	}
	public WebElement realTelephone() {
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'手机号')]//following::span[1]"), 100);
	}
	public WebElement nameInput() {
		return basedriver.waitAndGetElement(By.xpath("//label[text()='姓名']//following::input[1]"), 100);
	}
	public WebElement shenfenNo() {
		return basedriver.waitAndGetElement(By.xpath("//label[text()='身份证号码']//following::input[1]"), 100);
	}
	public WebElement telephoneNo() {
		return basedriver.waitAndGetElement(By.xpath("//label[text()='手机号']//following::input[1]"), 100);
	}

	public WebElement getStatus() {
		// TODO Auto-generated method stub
		return basedriver.waitAndGetElement(By.xpath("//span[contains(text(),'info')]//following::span[1]"), 100);
	}

	public WebElement zhanghuLogin() {
		// TODO Auto-generated method stub
		return basedriver.waitAndGetElement(By.xpath("//a[text()='账户登录']"), 100);
	}

	public WebElement loginname() {
		// TODO Auto-generated method stub
		return basedriver.waitAndGetElement(By.id("loginname"), 100);
	}
	public WebElement nloginpwd() {
		// TODO Auto-generated method stub
		return basedriver.waitAndGetElement(By.id("nloginpwd"), 100);
	}

	public WebElement loginSubmit() {
		// TODO Auto-generated method stub
		return basedriver.waitAndGetElement(By.id("loginsubmit"), 100);
	}
}
