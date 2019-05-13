package bc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrList {
	 public void crawl(List<String> courseList) throws InterruptedException{
//		驱动火狐浏览器
		System.setProperty("webdriver.gecko.driver", "/Users/zhijun/anaconda3/geckodriver");
	    WebDriver driver = new FirefoxDriver();
	    driver.manage().window().maximize();
		System.out.println("browser ready...");
//		与浏览器同步,等待浏览器加载完毕
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.icourse163.org/category/all");
		Thread.sleep(2000);
//		关闭广告弹窗防止后续点击不成功
		WebElement shut=driver.findElement(By.cssSelector("[class='u-icon u-icon-close']"));
		shut.click();
//		创建txt文件储存爬取的课程列表	
		try {
			File file =new File("Courses.txt");
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			int n=0;
			for(int i=1;i<=88;i++) //最多可爬88页，为了节省时间只爬2页课程
			{
				System.out.println("第"+i+"页:");
//				查找每页课程列表
				List<WebElement> ListElement=driver.findElements(By.cssSelector("[class='u-clist f-bgw f-cb f-pr j-href ga-click']"));
				for(WebElement webElement:ListElement)
				{
//					抓取全部内容
					WebElement  courseContent= webElement.findElement(By.className("g-mn1c"));
					String content=courseContent.getText();
					System.out.println("Course Number "+(n+1)+" :"+ "\r\n" + content);
					courseList.add(content);
//					fileWritter.write("Course Number "+(n+1)+" :"+ "\r\n" + content+ "\r\n");
					
//					抓取课程名称
					WebElement  courseName= webElement.findElement(By.cssSelector("[class='t1 f-f0 f-cb first-row']"));
//					System.out.println(courseName.getText());
					String cN=courseName.getText();
//					抓取课程大学
					WebElement  courseUniversity= webElement.findElement(By.cssSelector("[class='t21 f-fc9']"));
//					System.out.println(courseUniversity.getText());
					String cU=courseUniversity.getText();
//					抓取课程老师
					WebElement  courseTeacher= webElement.findElement(By.cssSelector("[class='f-fc9']"));
//					System.out.println(courseTeacher.getText());
					String cT=courseTeacher.getText();
//					抓取课程人数
					WebElement  courseNumber= webElement.findElement(By.className("hot"));
//					System.out.println(courseNumber.getText());
					String cNum=courseNumber.getText();
//					写入文件
					fileWritter.write("Course Number "+(n+1)+": "+ cN +" "+ cU+" "+ cT+" "+ cNum + "\r\n");
					++n;

//					抓取课程简介
//					WebElement  courseAbstract= webElement.findElement(By.cssSelector("[class='p5 brief f-ib f-f0 f-cb']"));
//					System.out.println(courseAbstract.getText());
//					String cA=courseAbstract.getText();
//					抓取课程进度
//					WebElement  courseProceeding= webElement.findElement(By.className("txt"));
//					System.out.println(courseProceeding.getText());	
//					String cP=courseProceeding.getText();
				}
				System.out.println("第"+i+"页加载完毕");
//				滑动到浏览器底部并点击下一页
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				WebElement nextPage=driver.findElement(By.cssSelector("[class='ux-pager_btn ux-pager_btn__next']"));
				nextPage.click();
				Thread.sleep(2000);
			}
			System.out.println("一共找到"+n+"门课");
//			关闭文件夹及浏览器
			fileWritter.close();  
			driver.quit();	
		}catch (IOException e) {
	        e.printStackTrace();
	       }
	}
}
