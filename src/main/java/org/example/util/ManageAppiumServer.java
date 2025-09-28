package org.example.util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ManageAppiumServer {
    private static final Logger logger = LogManager.getLogger(ManageAppiumServer.class);
    private static AppiumDriverLocalService appiumDriverLocalService;

    public static void startAppiumServer() {
        if (appiumDriverLocalService != null && appiumDriverLocalService.isRunning()) {
            logger.info("Appium server is already running.");
            return;
        }

        killExistingNodeProcesses();

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug");

        appiumDriverLocalService = AppiumDriverLocalService.buildService(builder);
        appiumDriverLocalService.start();
        logger.info("Appium server started at: {}", appiumDriverLocalService.getUrl());
    }

    public static void stopAppiumServer() {
        if (appiumDriverLocalService != null && appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService.stop();
            logger.info("Appium server stopped.");
        }
    }

    private static void killExistingNodeProcesses() {
        String[] command = new String[]{"taskkill", "/F", "/IM", "node.exe"};

        try {
            Runtime.getRuntime().exec(command);
            logger.info("Killed existing Node.js processes for Appium.");
        } catch (IOException e) {
            logger.warn("Failed to kill existing Node.js processes: {}", e.getMessage());
        }
    }
}
