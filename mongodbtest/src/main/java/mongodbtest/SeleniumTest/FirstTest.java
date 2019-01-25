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
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName FirstTest
 * @Description TODO
 * @Author WXY
 * @Date 2019-01-25 13:16
 **/
public class FirstTest {

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
        for (int i = 0; i < pageCounts; i++) {
            Thread.sleep(2000);
            List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id=\"articlelist_container\"]/div[2]/div[1]/div/div[1]/h3/a"));
            for (WebElement webElement : webElementList) {
                webElement.click();
                List<String> windows = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(windows.get(1));
                System.out.println(driver.getCurrentUrl());
                if (isElementPresent(driver, By.xpath("//*[@id=\"container\"]/div[2]/div/span[1]/h4"))) {
                    WebDriverWait wait = new WebDriverWait(driver, 2);
                    wait.until(new ExpectedCondition<WebElement>() {
                        @NullableDecl
                        @Override
                        public WebElement apply(@NullableDecl WebDriver driver) {
                            assert driver != null;
                            return driver.findElement(By.xpath("//*[@id=\"container\"]/div[2]/div/span[1]/h4"));
                        }
                    });
                    //获取题目
                    String title = driver.findElement(By.xpath("//*[@id=\"dtl_l\"]/div[1]/h3/a")).getText();
                    //获取作者
                    String authors = "";
                    if (isElementPresent(driver, By.className("author_text"))) {
                        authors = driver.findElement(By.className("author_text")).getText();
                    }
                    //获取摘要
                    String abstractInfo = "";
                    if (isElementPresent(driver, By.className("abstract"))) {
                        abstractInfo = driver.findElement(By.className("abstract")).getText();
                    }
                    //获取被引量
                    String quoteTimes = "";
                    if (isElementPresent(driver, By.className("ref-wr-num"))) {
                        quoteTimes = driver.findElement(By.className("ref-wr-num")).getText();
                    }
                    System.out.println(title + "\n" + authors + "\n" + abstractInfo.length() + "\t被引量" + quoteTimes + "\n================");
                } else {
                    System.out.println("paper dosen't exist!");
                }
                driver.close();
                driver.switchTo().window(mainHandle);
            }
            System.out.println("==============" + new Date(System.currentTimeMillis()) + "=============");
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
