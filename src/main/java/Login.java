import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Login {
    public static void main(String args[]) throws IOException {
        // For security username and password to public
        Properties prop = new Properties();
        InputStream inputStream = new FileInputStream("/home/beter/Selenium_Java/src/main/java/config.properties");
        prop.load(inputStream);

        // get the property value and print it out
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        // Conect
        System.setProperty("webdriver.chrome.driver","/home/beter/Selenium_Java/Documents/chromedriver_linux64/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.chess.com/login_and_go");

        // Login
        driver.findElement(By.name("_username")).sendKeys(username);
        driver.findElement(By.name("_password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        // To play
//        driver.findElement(By.id("quick-link-computer")).click();
//        driver.findElement(By.xpath("//div[@id='board-layout-sidebar']/div/div[2]/div/div/div/div/div/button")).click();

        while (driver.getCurrentUrl() != "https://www.chess.com/live"){
            driver.get("https://www.chess.com/live");
        }


//        (ExpectedConditions.presenceOfElementLocated(By.className("upgrade-modal"))
//        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CLASS_NAME, 'upgrade-modal')))
//        elem = driver.find_element(By.CLASS_NAME, 'upgrade-modal')
//        elem = elem.find_element_by_xpath('/html/body/div[1]/div[4]/div/div[2]/div[2]/span')
//        elem.click()
//        elem = driver.find_element(By.CLASS_NAME, 'tab-nav-challenge')
//        elem.click()
//        except TimeoutException:
//        pass
//
//                button = driver.find_element(By.CSS_SELECTOR, ".form-button-component")
//        button.click()

    }
}
