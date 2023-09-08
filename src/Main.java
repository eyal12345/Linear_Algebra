import Tools.ContentReader;
import Features.Receive_Matrices;
import Features.Decompose_Matrices;
import Features.Invertible_Matrices;
import Features.System_Linear_Equations;
import java.util.Properties;
import java.io.FileInputStream;

public class Main {

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            ContentReader cr = new ContentReader();
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String path = cr.Build_Path_Exercise(prop);
            if (path != null) {
                float[][] M = cr.Read_Exercise(path);
                if (M != null) {
                    String title = prop.getProperty("TITLE");
                    String exercise = prop.getProperty("EXERCISE");
                    String format = prop.getProperty("FORMAT");
                    if (title.equals("Receive_Matrices") || title.equals("Receive Matrices")) {
                        Receive_Matrices re = new Receive_Matrices(M,format,exercise);
                        re.Progress_Run();
                    } else if (title.equals("Decompose_Matrices") || title.equals("Decompose Matrices")) {
                        Decompose_Matrices de = new Decompose_Matrices(M,format,exercise);
                        de.Progress_Run();
                    } else if (title.equals("Invertible_Matrices") || title.equals("Invertible Matrices")) {
                        Invertible_Matrices inv = new Invertible_Matrices(M,format,exercise);
                        inv.Progress_Run();
                    } else if (title.equals("System_Linear_Equations") || title.equals("System Linear Equations")) {
                        float[][] A = cr.Extract_Matrix_Component(M);
                        float[][] b = cr.Extract_Vector_Component(M);
                        System_Linear_Equations sle = new System_Linear_Equations(A,b,format,exercise);
                        sle.Progress_Run();
                    } else {
                        throw new Exception("you entered an invalid value of title subject");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
