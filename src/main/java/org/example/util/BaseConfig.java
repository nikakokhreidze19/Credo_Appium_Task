package org.example.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseConfig {

    private static AndroidDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(BaseConfig.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream input = BaseConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            props.load(input);
            logger.info("Properties loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load properties file", e);
            throw new RuntimeException("Cannot load configuration", e);
        }
    }

    public static void startApp() {
        if (driver != null) {
            logger.info("Driver already initialized. Skipping startApp.");
            return;
        }

        logger.info("Starting Appium session...");
        String appiumServerUrl = props.getProperty("appium.server.url");

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("platformName", props.getProperty("platform.name"));
        dc.setCapability("appium:automationName", props.getProperty("automation.name"));
        dc.setCapability("appium:appPackage", props.getProperty("app.package"));
        dc.setCapability("appium:appActivity", props.getProperty("app.activity"));
        dc.setCapability("appium:noReset", Boolean.parseBoolean(props.getProperty("no.reset")));
        dc.setCapability("appium:deviceName", props.getProperty("device.name"));

        ManageAppiumServer.startAppiumServer();

        try {
            driver = new AndroidDriver(new URL(appiumServerUrl), dc);
            logger.info("Appium driver initialized successfully.");
        } catch (MalformedURLException e) {
            logger.error("Invalid Appium server URL:{} ", appiumServerUrl, e);
        } catch (Exception e) {
            logger.error("Error while initializing Appium driver: ", e);
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(props.getProperty("default.wait.seconds"))));
        logger.info("WebDriverWait initialized with 10 seconds timeout.");

        handlePermissions();
        handleOnboarding();
    }

    private static void handlePermissions() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.id("com.android.permissioncontroller:id/permission_deny_button")
            )).click();
            logger.info("Permission dialog denied.");
        } catch (TimeoutException ignored) {
            logger.info("No permission dialog appeared.");
        }
    }

    private static void handleOnboarding() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"skipButton\")")
            )).click();
            logger.info("Onboarding 'Skip' button clicked.");

            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"finishButton\")")
                )).click();
                logger.info("Onboarding 'Finish' button clicked.");
            } catch (TimeoutException ignored) {
                logger.info("No onboarding 'Finish' button appeared.");
            }

        } catch (TimeoutException ignored) {
            logger.info("No onboarding 'Skip' button appeared.");
        }
    }

    public static AndroidDriver getDriver() {
        logger.debug("Returning Appium driver instance.");
        return driver;
    }

    public static WebDriverWait getWait() {
        logger.debug("Returning WebDriverWait instance.");
        return wait;
    }

    public static void stopApp() {
        logger.info("Stopping Appium session...");
        if (driver != null) {
            try {
                driver.quit();
                logger.info("Driver quit successfully.");
            } catch (Exception e) {
                logger.warn("Error during driver quit (may be expected if server already stopped): {}", e.getMessage());
            } finally {
                driver = null;
                wait = null;
            }
        }
        try {
            ManageAppiumServer.stopAppiumServer();
        } catch (Exception e) {
            logger.error("Error stopping Appium server: {}", e.getMessage(), e);
        }

        logger.info("Appium session cleanup completed.");
    }
}
