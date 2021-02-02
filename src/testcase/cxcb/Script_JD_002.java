package testcase.cxcb;

import org.junit.Test;
import com.base.core.BaseCase;
import task.jscd.JD_Auto;

public class Script_JD_002 extends BaseCase{
	/*资讯*/
	private JD_Auto jD_Auto;
	@Test
	public void main() throws ClassNotFoundException{
		jD_Auto = new JD_Auto();
		/*资讯操作*/
		jD_Auto.fengPing(data.get("pin"));
	}
}




