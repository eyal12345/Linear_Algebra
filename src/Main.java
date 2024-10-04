import Features.ContentReader;
import java.io.FileInputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String title = prop.getProperty("TITLE");
            String method = prop.getProperty("METHOD");
            String space = prop.getProperty("SPACE");
            String exercise = prop.getProperty("EXERCISE");
            String format = prop.getProperty("FORMAT");
            ContentReader cr = new ContentReader(title,method,space,exercise,format);
            cr.Run_Progress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
