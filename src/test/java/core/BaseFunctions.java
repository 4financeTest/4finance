package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * This class helps to work with basic selenium functions
 */
public class BaseFunctions {

    WebDriver driver;
    private static final String CHROME_DRIVER_LOCATION = "C:/chromedriver.exe";
    private static final Logger LOGGER = Logger.getLogger(BaseFunctions.class);

    public BaseFunctions() {

        LOGGER.info("Setting chromedriver location: " + CHROME_DRIVER_LOCATION);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);

        LOGGER.info("Starting chromedriver");
        this.driver = new ChromeDriver();

        LOGGER.info("Maximize browser window size");
        driver.manage().window().maximize();
    }

    /**
     * This method goes to specific URL
     *
     * @param url web address
     */
    public void goToUrl(String url) {
        if(!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        LOGGER.info("User goes to: " + url);
        driver.get(url);
    }

    /**
     * Method to stop the webdriver
     */
    public void stopDriver() {
        LOGGER.info("Stopping driver");
        driver.close();
    }

    /**
     * Method to clock on specific element
     *
     * @param element element to click
     */
    public void click(By element) {
        driver.findElement(element).click();
    }

    /**
     * This method cleares input field and types text
     *
     * @param element - text field to fill
     * @param text - text to type in
     */
    public void fillInput(By element, String text) {
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    /**
     * This method created to pause test - needs to wait for data refresh or receiving mail message
     *
     * @param mills time to wait in milliseconds
     */
    public void pause(long mills) {
        try {
            Thread.sleep(mills);
            LOGGER.info("Test pauses for " + mills + " milliseconds to wait for data");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is waiting for element to be added in DOM
     *
     * @param element - element to wait
     * @param mills - max time to wait in milliseconds
     */
    public void waitForElement(By element, long mills) {
        WebDriverWait wait = new WebDriverWait(driver, mills);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    /**
     * Method checks if element is available in DOM
     *
     * @param element element to check
     * @return true or false
     */
    public boolean isPresentElement(By element) {
        List<WebElement> elements = driver.findElements(element);
        if(elements.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Method to move element horizontaly
     *
     * @param element element to move
     * @param to pixels amount
     */
    public void move(By element, int to) {
        WebElement webElement = driver.findElement(element);

        Actions builder = new Actions(driver);
        builder.dragAndDropBy(webElement, to, 0);
        builder.build().perform();
        builder.release();
    }

    /**
     * Method returns a list of elements with a specific locator
     *
     * @param element element locator to search
     * @return list of WebElements
     */
    public List<WebElement> findElements(By element) {
        return driver.findElements(element);
    }

    /**
     * Method returns WebElement with a specific locator
     *
     * @param element element locator to search
     * @return WebElement
     */
    public WebElement getElement(By element) {
        return driver.findElement(element);
    }
}