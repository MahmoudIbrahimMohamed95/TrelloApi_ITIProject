package Reports;

import fileUtils.FileAndTerminalManager;
import org.apache.commons.io.FileUtils;
import readers.Log;
import readers.PropertyReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static readers.PropertyReader.getProperty;

public class AllureGenerator {

    public static final String VERSION = "2.35.1";
    private static final Path Allure_DIR =
            Paths.get(System.getProperty("user.home"), ".m2", "repository", "allure");


    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static Path getExecutable() {
        Path exe = Allure_DIR
                .resolve("allure-" + VERSION)
                .resolve("bin")
                .resolve("allure");
        return isWindows()
                ? exe.resolveSibling("allure.bat")
                : exe;
    }
    public static AllureGenerator generateSingleReport(){

        FileAndTerminalManager.executeTerminalCommand(
                    getExecutable().toString(),
                    "generate",
                    (System.getProperty("user.dir") + File.separator + PropertyReader.getProperty("allure.results.directory"))
                    , "-o"
                    , (System.getProperty("user.dir") + File.separator + PropertyReader.getProperty("AllureReportSinglePath"))
                    , "--clean"
                    , "--single-file"
            );
        Log.info("Required Report is generated");
        return new AllureGenerator();
    }
    public static AllureGenerator generateFullReport(){
        FileAndTerminalManager.executeTerminalCommand(
                getExecutable().toString(),
                "generate",
                (System.getProperty("user.dir")+ File.separator+PropertyReader.getProperty("allure.results.directory"))
                ,"-o"
                ,(System.getProperty("user.dir")+File.separator+PropertyReader.getProperty("AllureFullReportPath"))
                ,"--clean"
        );
        Log.info("Full Report is generated");
        return new AllureGenerator();
    }
    public static AllureGenerator openReport() {
        String reportName = "AllureReport"+ ".html";
        FileAndTerminalManager.renameFile((System.getProperty("user.dir")+File.separator
                +PropertyReader.getProperty("AllureReportSinglePath")+("index.html")), reportName);

        if (!getProperty("executionType").toLowerCase().contains("local")) return new AllureGenerator();

        String reportPath =(System.getProperty("user.dir")+File.separator+
                            PropertyReader.getProperty("AllureReportSinglePath")+reportName);
        String oS=System.getProperty(("os.name")).toLowerCase();
        if (oS.contains("win")) {
            FileAndTerminalManager.executeTerminalCommand("cmd.exe", "/c", "start", reportPath);

        }
        else if (oS.contains("mac") ||oS.contains("nix") || oS.contains("nux")) {
            FileAndTerminalManager.executeTerminalCommand("open", reportPath);
        }
        else{
            Log.error("Unsupported OS");
        }
        return new AllureGenerator();
    }

    public static AllureGenerator copyHistory() {
        try {
            FileUtils.copyDirectory(Paths.get(System.getProperty("user.dir"),"test-output", "full-report", "history", File.separator).toFile(),
                                    Paths.get(System.getProperty("user.dir"),"test-output", "allure-results", "history", File.separator).toFile()); // copy generated history to it;
            Log.info("History copied from Full Report to Single one");

        } catch (Exception e) {
            Log.error("Error copying history files", e.getMessage());
        }
        return new AllureGenerator();
    }
}
