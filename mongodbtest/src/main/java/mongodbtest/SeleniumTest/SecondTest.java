package mongodbtest.SeleniumTest;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SecondTest
 * @Description TODO
 * @Author WXY
 * @Date 2019-01-24 17:17
 **/
public class SecondTest {

    private static String targetElement = "更多>>";
    private static String driverAddress = "D:\\chromedriver.exe";

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", driverAddress);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);

        List<String> urlList = new ArrayList<>();
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BT73WSNJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BN733MNJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-B0746Q8J");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-B97472MJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BN733MNJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BD75ORCJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-B074445J");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BX74XKFJ");
        urlList.add("http://xueshu.baidu.com/scholarID/CN-BO74QO5J");


        File writePath = new File("D:\\test.txt");
        writePath.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writePath));
        StringBuilder output = new StringBuilder();

        for (String url : urlList) {
            String unit = function(driver, url, targetElement);
            output.append(unit);
        }
        out.write(output.toString());
        out.flush();
        out.close();

        driver.quit();
    }

    private static String function(WebDriver driver, String sourceUrl, String targetElement) {
        StringBuilder unit = new StringBuilder();
        unit.append("========================================================================\r\n").
                append(new Date(System.currentTimeMillis())).append("\r\n");
//        System.out.println("====================================\n" + new Date(System.currentTimeMillis()));
        driver.navigate().to(sourceUrl);
        driver.findElement(By.linkText(targetElement)).click();

        WebDriverWait wait = new WebDriverWait(driver, Integer.MAX_VALUE);
        wait.until(new ExpectedCondition<WebElement>() {
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver driver) {
                return driver.findElement(By.xpath("//*[@id=\"co_rel_map\"]/h3"));
            }
        });

        List<WebElement> nameList = driver.findElements(By.className("co_person_name"));
        for (WebElement webElement : nameList) {
            String name = webElement.getText().replace(" ", "");
            String paperCount = webElement.getAttribute("paper-count").replace(" ", "");
            String affiliate = webElement.getAttribute("affiliate").replace(" ", "");
            unit.append(name).append(" ").append(affiliate).append(" ").append(paperCount).append("\r\n");
//            System.out.println(name + " " + affiliate + " " + paperCount);
        }
        unit.append(new Date(System.currentTimeMillis())).append("\r\n");
//        System.out.println(new Date(System.currentTimeMillis()));
        return unit.toString();
    }


}
