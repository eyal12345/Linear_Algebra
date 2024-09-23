import Features.ContentReader;
import java.io.FileInputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String title = prop.getProperty("TITLE");
            String space = prop.getProperty("SPACE");
            String exercise = prop.getProperty("EXERCISE");
            String format = prop.getProperty("FORMAT");
            ContentReader.Run_Progress(title,space,exercise,format);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
