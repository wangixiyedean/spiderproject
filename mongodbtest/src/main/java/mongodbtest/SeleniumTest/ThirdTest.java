package mongodbtest.SeleniumTest;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ThirdTest
 * @Description TODO
 * @Author WXY
 * @Date 2019-01-25 16:23
 **/
public class ThirdTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("http://xueshu.baidu.com/scholarID/CN-BT73WSNJ");
        String mainHandle = driver.getWindowHandle();
        System.out.println(mainHandle);
        WebElement pageElement = driver.findElement(By.xpath("//*[@id=\"articlelist_container\"]/div[2]/div[2]/p/span[7]"));
        Integer pageCounts = Integer.parseInt(pageElement.getText());
        for (int i = 1; i < pageCounts; i++) {
            Thread.sleep(2000);
            List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"articlelist_container\"]/div[2]/div[1]/div/div[1]/h3/a"));
            for (WebElement webElement : webElementList) {
                System.out.println(webElement.getText());
//                webElement.click();
//                List<String> windows = new ArrayList<>(driver.getWindowHandles());
//                System.out.println(driver.getCurrentUrl());
//                driver.switchTo().window(windows.get(1));
//                Thread.sleep(2000);
//                //获取题目
//                WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"dtl_l\"]/div[1]/h3/a"));
//                String title = titleElement.getText();
//                System.out.println(title);
//                driver.close();
//                driver.switchTo().window(mainHandle);
            }
            System.out.println("===========================");
            if (isElementPresent(driver, By.xpath("//*[@id=\"articlelist_container\"]/div[2]/div[2]/p/a[2]"))) {
                driver.findElement(By.xpath("//*[@id=\"articlelist_container\"]/div[2]/div[2]/p/a[2]")).click();
            }
        }


    }

    private static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
