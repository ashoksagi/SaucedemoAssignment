package cucumber.pages;

import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.commonutils.CommonUtilities;
import cucumber.testbase.TestBase;

public class Homepage {

	String NumberofItems = "//div[@class='inventory_item']";

	String RemoveItems = "//button[contains(text(),'Remove')]";

	String addToCart = "(//button[contains(text(),'Add to cart')])[xxxx]";

	String price = "(//button[contains(text(),'Add to cart')])[xxxx]//parent::div/div";

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	WebElement cartIcon;

	public Homepage() {
		PageFactory.initElements(TestBase.driver, this);
	}

	public void addItemsToCart(int numOfItems) {
		// get list of items
		if (CommonUtilities.getSizeofWebelements(NumberofItems) > numOfItems) {
			for (int i = 0; i < numOfItems; i++) {
				WebElement addTocart = CommonUtilities.prepareWebElementWithDynamicXpath(addToCart, "\"+i+1+\"");
				addTocart.click();
			}
		}
	}

	public void addItemsToCartByPrice(int min,int max) {
           int TotalValue=0;
		for (int i = 0; i < CommonUtilities.getSizeofWebelements(NumberofItems); i++) {
				WebElement addTocart = CommonUtilities.prepareWebElementWithDynamicXpath(addToCart, "\"+i+1+\"");
				TotalValue	=	TotalValue+(int) Float.parseFloat(CommonUtilities.prepareWebElementWithDynamicXpath(price, "\"+i+1+\"").getText().replace("$", "").trim());
				addTocart.click();
				if(TotalValue>30&&TotalValue<60) {
					break;
				}
			}
	}

	public void goToCart() {
		cartIcon.click();
	}

}