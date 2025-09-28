
package steps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.page.LoginPage;
import org.testng.Assert;

public class LoginSteps {

    private final LoginPage loginPage;

    public LoginSteps(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @When("user changes language to {string}")
    public void userChangesLanguageTo(String language) {
        loginPage.changeLanguageViaUI(language);
    }
    @And("user enters username {string} and password {string}")
    public void enterUsernameAndPassword(String username, String password) {
        if (!username.isEmpty()) loginPage.enterUserName(username);
        if (!password.isEmpty()) loginPage.enterPassword(password);
    }

    @And("user clears the {string} field")
    public void clearField(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) return;
        switch (fieldName.toLowerCase()) {
            case "username" -> loginPage.clearUserName();
            case "password" -> loginPage.clearPassword();
        }
    }

    @Then("login button should be {string} state")
    public void validateButtonState(String buttonCondition) {
        if (buttonCondition.equals("disabled")) {
            Assert.assertFalse(loginPage.isLoginButtonEnabled());
        } else {
            Assert.assertTrue(loginPage.isLoginButtonEnabled());
        }
    }

    @And("{string} error text should be {string}")
    public void verifyErrorText(String fieldName, String expectedError) {
        if (fieldName == null || fieldName.isEmpty()) return;
        String actual = switch (fieldName.toLowerCase()) {
            case "username" -> loginPage.getUsernameError();
            case "password" -> loginPage.getPasswordError();
            case "login" -> loginPage.getLoginError();
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
        Assert.assertEquals(actual, expectedError);
    }

    @And("click login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("appear error with text {string}")
    public void verifyLoginErrorText(String errorText) {
        Assert.assertEquals(loginPage.getLoginError(), errorText);
    }

}

