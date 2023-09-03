import Tools.ContentReader;
import Features.Receive_Matrices;
import Features.Decompose_Matrices;
import Features.Invertible_Matrices;
import Features.System_Linear_Equations;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;

public class Main {

    //////////////////////////////////////////// Build Exercise Path ////////////////////////////////////////////

    private static String Build_Path_Exercise(Properties prop) {
        String title = prop.getProperty("TITLE");
        String space = prop.getProperty("SPACE");
        String exercise = prop.getProperty("EXERCISE");
        String path = "/Exercises/" + title + "/" + space + "/" + exercise + ".txt";
        File file = new File("src" + path);
        if (file.exists()) {
            return path;
        } else {
            System.out.println("File not found!");
            return null;
        }
    }

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            FileInputStream propsInput = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(propsInput);
            String path = Build_Path_Exercise(prop);
            if (path != null) {
                ContentReader cr = new ContentReader();
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
                        float[][] A = cr.Matrix_Values(M);
                        float[] v = cr.Vector_Values(M);
                        float[][] b = new float[v.length][1];
                        for (int i = 0; i < v.length; i++) {
                            b[i][0] = v[i];
                        }
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
