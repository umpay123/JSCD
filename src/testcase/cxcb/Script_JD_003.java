package testcase.cxcb;

import com.base.core.BaseCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import task.jscd.JD_Auto;

public class Script_JD_003 extends BaseCase{
	/*资讯*/
	private JD_Auto jD_Auto;

	public void main1() throws ClassNotFoundException, InterruptedException {
		int max=100,min=1;
		long randomNum = System.currentTimeMillis();
		int ran3 = (int) (randomNum%(max-min)+min);
		Thread.sleep(ran3);
		System.out.println(ran3);
		before();

		jD_Auto = new JD_Auto();
		/*资讯操作*/
		driver.get("http://erp.jd.com/");
		//String cookiestr="TrackerID=xggxVo3qAGHHph3o53eUzgiYp9a988MjN3WV2z_hyOEBTwHUjkn-J3hPV_yqrhlsN_vlNvBbLp9MhCS01aH7lnvvCvhZ6ym3clIBklX5J8HYE8ChxDDQcSvgFrYejfQP; jd.erp.lang=zh_CN; jdd69fo72b8lfeoe=OHPOQOZ3TOABP7B4MV2O33VGUVNAFSNCMLZJ2TAMGUKBIQD6ZZIBZM5AJO4O5XB42FEK3WOHYW6DO7T74QHH75EDII; __jdv=50436146|direct|-|none|-|1610596094272; __jdu=16105960942711767427997; qd_uid=KK3W3QXX-T1N9X9YES3F089CC61KR; qd_fs=1611054249764; qd_ls=1611054249764; TrackID=1pZrqDFhjsY57XKrR7ScMDmtU45aEjLPOxmAGoKkRtirngxZsi8O3VFxrO3wHh2z09sEqW9r68qs90RNKgIDwNMU7jvyxZH1zXLpgcLrwskA; pinId=EFKHk-yqf-OzvP4iktnj4A; pin=tanxiao0421; unick=%E5%B0%8F%E5%B0%8F%E9%9B%A8%E8%88%9F; _tp=5TPTOHbWnSl%2BfJ%2FTryYp%2FA%3D%3D; _pst=tanxiao0421; jdc_art=1611058910749-BjPok-S1hbvYYTkpxmc1ZyRc1xVt8-jn; jdc_art.sig=r0cDlTROsGAeX19uiDgGG7Ij6HQ; 3AB9D23F7A4B3C9B=WYCDSQW7MHQOPIHLGBZ5237CYFCVIGWY7MSQJ4NTMLLAOYT5AUS6K2JFWRWNCB5U7Y2WLZ2MXS7JQTW4CNMG7HHH7M; qd_ts=1611132367110; qd_sq=2; uasLogin=8067wmgpby58dHJ8iCeaHm1OW6jZ+kO1TBP8hQAcDSx8t1Mlk3Bha23jX4yrkJA/E+4JLzLcdd5MYABL7NSDEKjmDO3FS0iC1vVYzHxfYxfeD2AIsAyx/C+LjOuWQWbzQpev9qxa5lVLBx1b4gCiDSJqCIuK7A9uiDbbkCLPtNW5Ne5ieBwV7ug7GLra787H4X9LTvlKNulL/IExVXanMqxFTbs6336bpZ18vfTGFvQlar5dREyKiRb/DumF0kCkkupqh6/MDiiiIaPx4fkaEkBpgYtyY0KGotn+4SzTmEz6yj6FVnYldWpbSBLzp2RX6ShNWwOzrOsgqvtQ6MadLr5XLW3ZPp+ElcNd8pncgZ4o3SqPqZ3tputR6rbqQ1moU/qMTYnt3FC8xHny4yEdUG8Z5FV33Lo8n8IHAGhsjYekaBLS+84+sq6L+z5KUnhALBWtm20VCtPA/iLdvJky3EqrtICYrB1Uw46ehy4NBfBKRhuJ4Z1IgkqhD0PmTa7VIhB0wB91iOvOgH9JrsgTNGoWWRrdTchN8h4tC7IZpWkQEw6NoSHMK/Hm9uX5K1KQQnNjTbQLYvkLnfHrE8jvEp3E6DQfV1mpUK53B+cb2WoR+h/CvzSW9CyWkJ+I+aGH8i9a6SYtl2Cu7quKqr/qsu+brH8+C8F3FYbkOMfWKWfSh0kwR26NPJWugUr9I+HSwwDjioJtS4LrP7dDImCWM7H7+b48B3QttEfrIPRARhU8PWzKjtK4EPOTic0gCQPlSkOfMr0uR5tsaUJ3aGCDFKkPhmD/O6sqjJLx/lSt0FleTOhoG1fie9X+T+PJ49luxrCQQTGPGxrNLVXD8WVAvo/Sr4JnABEM44U8DOIW+kh9PBMXnruj95lz2iCmI+qepwcX96pucQUfjM/lrE5ylCAOBkuuT4WtkeEjgmhZ+oge5GHCQ4a19y+2oTCVA73A7ZGnxG4EYCScRfhfR2TS+4yW9Opv1dGacJworxen; shshshfpb=ciCwGOiQs%2FD0vT9hbcrPk9g%3D%3D; shshshfp=980dd52d21d00f9c8e26a6e50b622a8d; shshshfpa=42ecccaa-7a60-f527-f642-e83ba28e0cec-1593563846; jrapp_jsfGateway_testPin=hlltest9; __jda=137720036.16105960942711767427997.1610596094.1611557450.1611561752.29; __jdc=137720036; erp1.jd.com=1EE0AD5FBA33B1D6ED47ED2301C63CD0CEA3686D2DD466FF3691C5C589B8168881A4C91EBCD3F0B0AED1270220D948D0FD17CFF05DCCDD9338F23C84AF1A05D213BCB8D86141890770389709269892AA; sso.jd.com=BJ.bfc0c4efcf3947f08990f8bf384fed90; __jdb=137720036.3.16105960942711767427997|29.1611561752";
		Cookie cookie = new Cookie("jdd69fo72b8lfeoe","Y3PNUX4KIOYYS2QM7FSO7PEUGWRKBZYLIUXYNJAG7XAOFHBQBWL7PQRGZZSKFLPW6ETRNRL7S4PDA3DC6THFJXE4KA");
		Cookie cookie2 = new Cookie("jd.erp.lang","zh_CN");
		Cookie cookie3 = new Cookie("erp1.jd.com","815474F443F699821BF80E060351D6F07BEB1F9BE717AFB8531863CFCF3C2555A2F7EEC2DDB920B354F432BD1B63D0E29DC43B1E6D66F41DD94B4631D03CC096E81B11E52BF5DF7D5842EE62E9A72C62");
		Cookie cookie4 = new Cookie("sso.jd.com","BJ.34b909dff85c4956b27cc38e9ad5d115");
		driver.manage().addCookie(cookie);
		driver.manage().addCookie(cookie2);
		driver.manage().addCookie(cookie3);
		driver.manage().addCookie(cookie4);
		basedriver.sendKeys(basedriver.findElement(By.id("username")),"tanlin41");
		basedriver.sendKeys(basedriver.findElement(By.id("password")),"Tl20200407!!!!!");
		basedriver.click(basedriver.findElement(By.xpath("//input[contains(@value,'登')]")));
		basedriver.pause(6000);
		basedriver.click(basedriver.findElement(By.xpath("//button[text()='打卡']")));
        after();

	}

	public static void main(String[] args) throws SchedulerException, InterruptedException, ClassNotFoundException {
		//new Script_JD_003().main1();
		Script_JD_003.schedulerJob();
	}
	//创建调度器
	public static Scheduler getScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		return schedulerFactory.getScheduler();
	}


	public static void schedulerJob() throws SchedulerException{
		//创建任务
		JobDetail jobDetail = JobBuilder.newJob(DaKaJob.class).withIdentity("job1", "group1").build();
		//创建触发器 每3秒钟执行一次
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
				.withSchedule(CronScheduleBuilder.cronSchedule("00 00 9,21 ? * 1,2,3,4,5"))
				.build();

		Scheduler scheduler = getScheduler();

		//将任务及其触发器放入调度器
		scheduler.scheduleJob(jobDetail, trigger);
		//调度器开始调度任务
		scheduler.start();

	}
}




