package testcase.cxcb;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: tanlin
 * @Date: 2021/02/01/18:20
 * @Description:
 */
public class DaKaJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            new Script_JD_003().main1();
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
