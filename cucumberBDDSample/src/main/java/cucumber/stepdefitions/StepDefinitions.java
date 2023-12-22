package cucumber.stepdefitions;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.commonutils.CommonUtilities;
import cucumber.pages.CartPage;
import cucumber.pages.CheckoutPage;
import cucumber.pages.Homepage;
import cucumber.pages.Login;


public class StepDefinitions {

	Login login = new Login();
	Homepage homepage = new Homepage();
	CartPage cartpage = new CartPage();
	CheckoutPage checkoutPg = new CheckoutPage();

	@Given("^User launches \"([^\"]*)\"$")
	public void User_launches(String arg1) {
		CommonUtilities.openBrowser(arg1);
	}

	@Then("^verify page title as \"([^\"]*)\"$")
	public void verify_page_title_as(String arg1) {
		login.ValidateTitle(arg1);		
	}

	@Given("^enter \"([^\"]*)\" and \"([^\"]*)\" and click login$")
	public void enter_and_and_click_login(String arg1, String arg2) throws InterruptedException {
		login.loginintoSauseLabs(arg1, arg2);
	}

	@Then("^validate error message \"([^\"]*)\"$")
	public void validate_error_message(String arg1) {
			login.validateErrorMessage(arg1);	
	}

	@When("^user adds (\\d+) items to basket and goes to cart$")
	public void user_adds_items_to_basket_and_goes_to_cart(int arg1) {

		homepage.addItemsToCart(arg1);
		homepage.goToCart();
		cartpage.validateItemsInCart(arg1);

	}

	@Then("^user buys (\\d+) items from cart page$")
	public void user_buys_items_from_cart_page(int arg1) {
		cartpage.removeItemsInCart(arg1);
		cartpage.clickCheckout();

	}

	@And("^complete checkout for \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void complete_checkout_for(String arg1, String arg2, String arg3) {
			checkoutPg.complete_DetailsOnCheckout(arg1, arg2, arg3);
			checkoutPg.VerifyOrderComplete();
	}

	@Then("^user total purchase price should be with in (\\d+) and (\\d+)$")
	public void user_total_purchase_price_should_be_with_in_and(int arg1, int arg2) {
		homepage.addItemsToCartByPrice(arg1, arg2);
		homepage.goToCart();
		cartpage.clickCheckout();
	}
}
