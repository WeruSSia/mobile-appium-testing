package com.example.project.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected AndroidDriver<AndroidElement> driver;
    protected Wait<AndroidDriver<AndroidElement>> wait;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "sdk_gphone_x86_arm");
        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "11");

        cap.setCapability("appPackage", "com.slava.buylist");
        cap.setCapability("appActivity", "com.slava.buylist.MainActivity");

        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<>(url, cap);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class)
                .ignoring(TimeoutException.class);
    }

    @BeforeMethod
    public void resetApp() {
        driver.resetApp();
        driver.findElementById("com.android.permissioncontroller:id/continue_button").click();
        driver.findElementById("android:id/button1").click();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
