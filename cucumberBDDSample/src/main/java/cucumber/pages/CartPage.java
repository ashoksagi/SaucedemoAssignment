package cucumber.pages;

import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.commonutils.CommonUtilities;
import cucumber.testbase.TestBase;

public class CartPage {

	String cartItems = "//div[@class='cart_item']";

	String RemoveItems = "(//button[contains(text(),'Remove')])[xxxx]";

	@FindBy(id = "checkout")
	WebElement checkout;

	public CartPage() {
		PageFactory.initElements(TestBase.driver, this);
	}

	public void validateItemsInCart(int numOfItems) {

		Assert.assertTrue(CommonUtilities.getSizeofWebelements(cartItems) == numOfItems);

	}

	public void removeItemsInCart(int itemsToBuy) {

		int itemsInCart = CommonUtilities.getSizeofWebelements(cartItems);

		for (int i = 0; i < (itemsInCart - itemsToBuy); i++) {

			CommonUtilities.prepareWebElementWithDynamicXpath(RemoveItems, "\"+i+1+\"").click();

		}

		Assert.assertTrue(CommonUtilities.getSizeofWebelements(cartItems) == itemsToBuy);
	}

	public void clickCheckout() {

		checkout.click();

	}

}
