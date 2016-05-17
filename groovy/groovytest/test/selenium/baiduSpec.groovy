package selenium

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver

//import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static com.google.common.base.Preconditions.checkNotNull

@Mixin(SearchFeature)
class BaiduPage {
    WebDriver browser

    public BaiduPage(WebDriver driver) {
        this.browser = driver
    }
}

class SearchFeature {
    def search(String keyword, Closure closure = null) {
        browser.findElement(By.name("wd")).sendKeys(keyword)
        browser.findElement(By.id("su")).click()
        closure?.call()
    }
}

/**
 * Created by buha on 10/31/2015.
 */
class baiduSpec extends Specification {
    //    def driver = new FirefoxDriver();
    def "search keyword in Internet Explorer"() {
        setup:
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("ignoreProtectedModeSettings", true)
        def driver = new InternetExplorerDriver(capabilities)
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

    def "search keyword in BaiduPage"() {
        setup:
        //ChromeDriver ,FirefoxDriver
        def driver = new FirefoxDriver(null)
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

    def "search keyword"() {
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
        driver.close()
    }

//    def "search keyword in PhantomJs"(){
//        setup:
//        DesiredCapabilities capabilities =  DesiredCapabilities.phantomjs()
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setCapability("takesScreenshot", false);
//        def driver = new PhantomJSDriver(capabilities)
//        driver.get("http://www.baidu.com")
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
//        when:
//        driver.findElement(By.name("wd")).sendKeys("hp service Manager")
//        driver.findElement(By.id("su")).click()
//        WebDriverWait wait = new WebDriverWait(driver, 3);
//        wait.until(ExpectedConditions.titleContains("hp"))
//        then:
//        driver.getTitle().contains("hp")
//        cleanup:
//        driver.quit()
//    }


}