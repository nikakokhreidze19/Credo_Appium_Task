package org.example.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.example.util.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final String usernameInput = "usernameInput";
    private final String passwordInput = "passwordInput";
    private final String loginButton = "loginButton";
    private final String userNameError = "usernameErrorText";
    private final String passwordError = "passwordErrorText";
    private final String loginError = "flashMessageText";
    private final String changeLanguageButton = "changeLanguageButton";
    private final String closeButton = "closeButton";

    public LoginPage(TestContext context) {
        this.driver = context.getDriver();
        this.wait = context.getWait();
    }

    private WebElement findById(String id) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"" + id + "\")")));
    }

    public LoginPage enterUserName(String text) {
        findById(usernameInput).sendKeys(text);
        return this;
    }

    public LoginPage clearUserName() {
        findById(usernameInput).clear();
        return this;
    }

    public LoginPage enterPassword(String text) {
        findById(passwordInput).sendKeys(text);
        return this;
    }

    public LoginPage clearPassword() {
        findById(passwordInput).clear();
        return this;
    }

    public boolean isLoginButtonEnabled() {
        return findById(loginButton).isEnabled();
    }

    public LoginPage clickLoginButton() {
        findById(loginButton).click();
        return this;
    }

    public String getUsernameError() {
        return findById(userNameError).getText();
    }

    public String getPasswordError() {
        return findById(passwordError).getText();
    }

    public String getLoginError() {
        return findById(loginError).getText();
    }

    public void changeLanguageViaUI(String languageText) {
        findById(changeLanguageButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + languageText + "\")"))).click();
        findById(closeButton).click();
    }
}
