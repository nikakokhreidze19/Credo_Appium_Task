package org.example.util;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestContext {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public TestContext() {
        this.driver = BaseConfig.getDriver(); // always singleton driver
        this.wait = BaseConfig.getWait();
    }

    public AndroidDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
