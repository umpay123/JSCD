package testcase.cxcb;

import java.util.Map;

import org.junit.Test;

import com.base.core.BaseCase;
import com.util.ExcelUtil;
import com.util.FileUtil;
import com.util.VinRandomUtil;

import task.jscd.JD_Auto;

public class Script_JD_001 extends BaseCase{
	/*资讯*/
	private JD_Auto jD_Auto;
	private String pin;
	@SuppressWarnings("static-access")
	@Test
	public void main() throws Exception{
		jD_Auto = new JD_Auto();
		FileUtil file = new FileUtil();
		pin = file.readLineContent("D:/DeskTop/1.txt");
		System.out.println("_______________________");
//		file.deleLinesContent("./data/JD_pin");
		file.deleLinesContent("D:/DeskTop/1.txt");
		/*实名操作*/
		jD_Auto.wirteToDataFile("Script_JD_003", "pin", pin);
		Map<String, String> data1 = ExcelUtil.getSpecifySheetByTCName(props.get("conf.data.path"),"data",
				"Script_JD_002");
		String selectPin = data1.get("Url");
		System.out.println("___________________"+selectPin);
		jD_Auto.wirteToDataFile("Script_JD_003", "Url", selectPin+pin);
		jD_Auto.shiMing(pin);
		//绑定银行卡操作
		jD_Auto.bangDingBank(pin);
		
		
	}
}


