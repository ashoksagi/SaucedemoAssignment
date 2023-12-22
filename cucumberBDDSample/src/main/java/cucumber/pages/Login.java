package cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.commonutils.CommonUtilities;
import cucumber.testbase.TestBase;
import junit.framework.Assert;

public class Login extends TestBase{
	
	@FindBy(id="user-name")
	WebElement UserName;
	
	@FindBy(id="password")
	WebElement Password;
	
	@FindBy(id="login-button")
	WebElement LoginButton;
	
	@FindBy(tagName="h3")
	WebElement errormessage;
	
	public Login() {
		PageFactory.initElements(driver, this);
	}
	
	
	public Homepage loginintoSauseLabs(String username,String password) throws InterruptedException {
		CommonUtilities.enterText(UserName,username);
		CommonUtilities.enterText(Password,password);
		LoginButton.click();
		return new Homepage();
	}
	
	public void ValidateTitle(String arg1) {
		try {
		Assert.assertTrue(driver.getTitle().equals(arg1));
		CommonUtilities.closebrowser();
	} finally {
		CommonUtilities.closebrowser();
	}
	
	}

	public void validateErrorMessage(String expectedText) {
		try {
		Assert.assertTrue(CommonUtilities.getText(errormessage).contentEquals(expectedText));
		CommonUtilities.closebrowser();
		} finally {
			CommonUtilities.closebrowser();
		}
	}
}
