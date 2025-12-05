package Reports;

import fileUtils.FileAndTerminalManager;
import readers.Log;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureSetupEnvironment {



    public static AllureSetupEnvironment setAllureEnvironment() {
        Map<String, String> env = new HashMap<>();
        env.put("OS", System.getProperty("os.name"));
        env.put("Java version:", System.getProperty("java.runtime.version"));
        env.put("Browser", System.getProperty("browserType"));
        env.put("Execution Type", System.getProperty("executionType"));
        env.put("URL", System.getProperty("baseUrlWeb"));


        File envFile=new File(System.getProperty("user.dir")+"/test-output"+"/allure-results/environment.properties");
        envFile.getParentFile().mkdirs();
        try(FileWriter writer=new FileWriter(envFile)){
            for(Map.Entry<String , String>entry:env.entrySet()){
                writer.write(entry.getKey()+"="+entry.getValue()+"\n");
            }
            Log.info("Environment properties file created: "+envFile);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return new AllureSetupEnvironment();
    }

}
