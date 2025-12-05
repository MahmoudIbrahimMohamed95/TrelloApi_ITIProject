package listenerUtils;

import fileUtils.FileAndTerminalManager;
import readers.Log;
import readers.PropertyReader;

import java.io.File;

public class ListenersAssistant {

    public static ListenersAssistant cleanTestOutputDirectories() {

        FileAndTerminalManager.cleanDirectory(new File(System.getProperty("user.dir") +
                "/test-output/allure-results" + File.separator));
        Log.info("allure-results directory cleaned");

        FileAndTerminalManager.forceDelete(new File(PropertyReader.getProperty("user.dir") +
                File.separator + "test-output/Logs" + File.separator + "logs.log"));
        Log.info("Logs directory cleaned");
        return new ListenersAssistant();
    }

    public static ListenersAssistant createTestOutputDirectories() {

        return new ListenersAssistant();
    }
}