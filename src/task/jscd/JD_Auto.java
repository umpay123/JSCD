package task.jscd;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import com.base.core.BaseCase;
import com.util.ExcelUtil;

import object.jscd.CommonObject;

public class JD_Auto extends BaseCase{
	CommonObject comObject = new CommonObject();
	/*JD-资讯demo*/
	public void shiMing(String pin){
		basedriver.sendKeys(comObject.userName(), "ext.caotianbao");
		basedriver.sendKeys(comObject.passWord(), "Liyanyan.0");
		basedriver.click(comObject.loginButton());
		basedriver.pause(1000);
		basedriver.click(comObject.createMing());
		basedriver.sendKeys(comObject.writePin(), pin);
		basedriver.executeScriptByElement(comObject.radomCreate());
		basedriver.click(comObject.radomCreate());
		basedriver.pause(2000);
		basedriver.click(comObject.submitMing());
		basedriver.pause(8000);
	}
	
	public void bangDingBank(String pin){
		basedriver.click(comObject.bankCoreMenu());
		basedriver.pause(3000);
		basedriver.sendKeys(comObject.writePin(), pin);
		basedriver.click(comObject.selectShiming());
		basedriver.pause(1000);
		basedriver.executeScriptByElement(comObject.radomInfo());
		String realName = comObject.realName().getText();
		String realShenfen = comObject.realShenfen().getText();
		String realTelephone = comObject.realTelephone().getText();
		String nameInfo = realTelephone+realName+realShenfen;
		System.out.println(nameInfo);
		String[] nameinfo = nameInfo.split(",");
		
		String telephoneString =nameinfo[0].replace("\"", "");
		String nameString =nameinfo[1].replace("\"", "");
		String infoString =nameinfo[2].replace("\"", "");
		basedriver.click(comObject.radomInfo());
		basedriver.pause(2000);
		comObject.nameInput().clear();
		basedriver.sendKeys(comObject.nameInput(), nameString);
		comObject.shenfenNo().clear();
		basedriver.sendKeys(comObject.shenfenNo(), infoString);
		comObject.telephoneNo().clear();
		basedriver.sendKeys(comObject.telephoneNo(), telephoneString);
		basedriver.click(comObject.submitMing());
		String getStatus = comObject.getStatus().getText();
		System.out.println(getStatus);
		if (getStatus.contains("成功")) {
			System.err.println("绑定银行卡成功");
		}else {
			System.out.println("我擦，又失败了");
		}
		
		basedriver.pause(5000);
		
	}
	
	public void fengPing(String pin){
		System.out.println("风评的PIN："+pin);
		List<WebElement> findElements = basedriver.findElements(By.xpath("//div[@class='my-list']/div[last()]"));
		System.out.println("获取风评选项成功");
		System.out.println(findElements);
		for (WebElement webElement : findElements) {
			basedriver.pause(500);
			webElement.click();
		}
//		for (int i = 0; i < findElements.size(); i++) {
//			findElements.get(i).click();
//			System.out.println(findElements.get(i));
//			System.out.println(i);
//		}
		basedriver.pause(2000);
		basedriver.findElement(By.xpath("//button[text()='提交']")).click();
		basedriver.pause(1000);
	}
}
