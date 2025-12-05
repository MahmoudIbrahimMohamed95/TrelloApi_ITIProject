package listeners;

import Reports.AllureGenerator;
import Reports.AllureSetupEnvironment;
import assertion.Assertions;
import listenerUtils.ListenersAssistant;
import org.testng.*;
import org.testng.asserts.Assertion;
import readers.Log;
import readers.PropertyReader;

public class customListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {


    @Override
    public void onExecutionStart() {
        PropertyReader.loadProperties();
        ListenersAssistant.cleanTestOutputDirectories()
                    .createTestOutputDirectories();
        AllureSetupEnvironment.setAllureEnvironment();
    }
    @Override
    public void onExecutionFinish() {
        AllureGenerator.copyHistory()
                .generateSingleReport()
                .generateFullReport()
                .openReport();
        Log.info("Test Execution Finished");


    }
    @Override
    public void onTestStart(ITestResult result) {
        Log.info(result.getMethod().getMethodName() , "started");
    }
    @Override
    public void onTestSuccess(ITestResult result) {

        Log.info(result.getMethod().getMethodName() , "passed");
    }
    @Override
    public void onTestFailure(ITestResult result) {

        Log.info(result.getMethod().getMethodName() , "failed");
    }
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        Assertions.assertAll(testResult);
    }
}
