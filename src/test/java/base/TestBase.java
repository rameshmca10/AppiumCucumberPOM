package base;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utilities.AppiumServer;
import utilities.CommonUtils;

public class TestBase {

	public AppiumDriver<MobileElement> driver;
	public static String loadPropertyFile = "Android_flipboard.properties";
	public static Logger log = Logger.getLogger(TestBase.class);

	public void takeScreenshot() {

		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		String filePath = System.getProperty("user.dir")+"\\reports\\"+fileName;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ExtentCucumberAdapter.getCurrentStep().addScreenCaptureFromPath(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setUp() {

		if (driver == null) {

			
			if (loadPropertyFile.startsWith("Android_")) {

				AppiumServer.start();
				log.info("Appium server started");
				CommonUtils.loadAndriodConfProp(loadPropertyFile);
				log.info(loadPropertyFile + " properties file loaded !!!");
				CommonUtils.setAndroidCapabilities();
				driver = CommonUtils.getAndroidDriver();

			} else if (loadPropertyFile.startsWith("IOS_")) {

			}

		}

	}

	public void quit() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.quit();
		log.info("Test case execution completed");

		AppiumServer.stop();
		log.info("Appium server stopped !!!");

	}

}
