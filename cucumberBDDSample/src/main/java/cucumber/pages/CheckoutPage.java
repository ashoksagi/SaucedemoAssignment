package cucumber.pages;

import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.commonutils.CommonUtilities;
import cucumber.testbase.TestBase;

public class CheckoutPage {
	
	@FindBy(id="first-name")
	WebElement FirstName;

	@FindBy(id="last-name")
	WebElement LastName;
	
	@FindBy(id="postal-code")
	WebElement PostalCode;
	
	@FindBy(id="continue")
	WebElement ContinueBtn;
	
	@FindBy(id="finish")
	WebElement FinishBtn;
	
	@FindBy(xpath="//*[@class='complete-header']")
	WebElement orderMessage;
	
	public CheckoutPage() {
		PageFactory.initElements(TestBase.driver, this);
	}

	public void complete_DetailsOnCheckout(String FN, String LN, String PC) {

		CommonUtilities.enterText(FirstName, FN);
		CommonUtilities.enterText(LastName, LN);
		CommonUtilities.enterText(PostalCode, PC);
		
		
		ContinueBtn.click();
		
		FinishBtn.click();
	}
	
	public void VerifyOrderComplete() {
		
		Assert.assertTrue(orderMessage.getText().equals("Thank you for your order!"));
		CommonUtilities.closebrowser();
	}
}
