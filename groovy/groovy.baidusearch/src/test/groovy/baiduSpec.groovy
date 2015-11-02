import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.remote.DesiredCapabilities
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Mixin(SearchFeature)
class BaiduPage
{
    WebDriver browser

    public BaiduPage(WebDriver driver)
    {
        this.browser = driver
    }

}
class  Person{
    String name,address
}

class SearchFeature
{
    def search(String keyword, Closure closure = null)
    {
        browser.findElement(By.name("wd")).sendKeys(keyword)
        browser.findElement(By.id("su")).click()
        closure?.call()
    }
}

/**
 * Created by buha on 10/31/2015.
 */
class baiduSpec extends Specification
{
//    def "search keyword in IE"()
//    {
//        setup:
//        System.setProperty("webdriver.ie.driver",
//                "C:\\Tools\\IEDriverServer_Win32_2.47.0\\IEDriverServer.exe");
//        DesiredCapabilities capability=DesiredCapabilities.internetExplorer();
//        capability.setCapability(
//                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        def driver = new InternetExplorerDriver(capability)
//        driver.get("http://www.baidu.com")
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
//        when:
//        def page = new BaiduPage(driver)
//        page.search("hp service manager") { ->
//            WebDriverWait wait = new WebDriverWait(driver, 3);
//            wait.until(ExpectedConditions.titleContains("hp"))
//        }
//
//        then:
//        driver.getTitle().contains("hp")
//        cleanup:
//        driver?.quit()
//    }

    def "search keyword in Chrome"()
    {
        setup:
        def driver = new ChromeDriver()
        driver.get("http://www.baidu.com")
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
        when:
        driver.findElement(By.name("wd")).sendKeys("hp service Manager")
        driver.findElement(By.id("su")).click()
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.titleContains("hp"))
        then:
        driver.getTitle().contains("hp")
        cleanup:
        driver.quit()
    }

    def "search keyword in Firefox"()
    {
        setup:
        //ChromeDriver ,FirefoxDriver
        def driver = new FirefoxDriver()
        driver.get("http://www.baidu.com")
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
        when:
        def page = new BaiduPage(driver)
        page.search("hp service manager") { ->
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.titleContains("hp"))
        }

        then:
        driver.getTitle().contains("hp")
        cleanup:
        driver.quit()
    }


}