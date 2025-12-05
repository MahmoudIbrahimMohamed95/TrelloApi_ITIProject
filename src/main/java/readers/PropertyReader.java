package readers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    public static Properties loadProperties() {
        try {

            //1- create object from properties class
            Properties properties = new Properties();

            //get all files in collection
            Collection<File> propertiesFiles;

            //any file with extension properties make get to it  (FileUtils is from commons io dependency)
            propertiesFiles = FileUtils.listFiles(new File("src/main/resources"),
                    new String[]{"properties"}, true);

            propertiesFiles.forEach(fie->
            {
                try {
                    properties.load(FileUtils.openInputStream(fie));
                 } catch (Exception e) {
                    System.out.println("Exception in load properties " + e.getMessage());
                }
                //this will be added outside loop
            });
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);
            Log.info("Properties loaded Successfully");
            return properties;
        } catch (Exception e) {
            System.out.println("Exception in load properties " + e.getMessage());
            return null;
        }
    }
    public static String getProperty(String key){
        //return value for any key from system properties
        try {
            return System.getProperty(key);
        }
        catch (Exception e){
            System.out.println("Exception in get property "+e.getMessage());
            return null;
        }

    }
}
