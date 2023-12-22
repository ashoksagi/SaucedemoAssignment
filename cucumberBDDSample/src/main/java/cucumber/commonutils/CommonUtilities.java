package cucumber.commonutils;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.testbase.TestBase;

public class CommonUtilities {
	
	public static WebDriver driver=null;
	public CommonUtilities(){
		driver=TestBase.driver;
		
	}
	public static void openBrowser(String arg1) {
		driver=TestBase.driver;
		driver.get(arg1);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	}
	
	public static String getText(WebElement xpath) {
		driver=TestBase.driver;
		return xpath.getText();	 
	}
	
	public static void enterText(WebElement element,String textToBeEntered) {
		for(int retry=0;retry<2;retry++) {
		try {
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element)); // element not visible exception
		element.sendKeys(textToBeEntered);
		break;
		}catch (StaleElementReferenceException e) {
			if(retry<2) {
			driver.navigate().refresh();
			}
		}
		catch (ElementNotVisibleException e) {
		System.out.println("Element is not visible even after 20 seconds. Please increase the timeout");
		}
		catch (NoSuchElementException e) {
			System.out.println("Element not found on the page, make sure xpath is correct");
			}
		catch (ElementNotSelectableException e) {
			System.out.println("Element is present but its not selectable, please check if element is disabled");
			}
		catch (TimeoutException e) {
			System.out.println("Page maynot have loaded on given time or check Internet/browser connection");
			}
		}
	}
	public static void closebrowser() {
		driver.quit();
	}
	public static WebElement prepareWebElementWithDynamicXpath (String xpathValue, String substitutionValue ) {

        return driver.findElement(By.xpath(xpathValue.replace("xxxx", substitutionValue)));
}
	public static int getSizeofWebelements (String xpathValue) {

       return  driver.findElements(By.xpath(xpathValue)).size();
}
}
