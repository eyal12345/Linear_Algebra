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
                    String[] items = path.split("/");
                    String format = prop.getProperty("FORMAT");
                    if (items[2].equals("Receive_Matrices")) {
                        Receive_Matrices re = new Receive_Matrices(M,format,items[4]);
                        re.Progress_Run();
                    } else if (items[2].equals("Decompose_Matrices")) {
                        Decompose_Matrices de = new Decompose_Matrices(M,format,items[4]);
                        de.Progress_Run();
                    } else if (items[2].equals("Invertible_Matrices")) {
                        Invertible_Matrices inv = new Invertible_Matrices(M,format,items[4]);
                        inv.Progress_Run();
                    } else if (items[2].equals("System_Linear_Equations")) {
                        float[][] A = cr.Matrix_Values(M);
                        float[] v = cr.Vector_Values(M);
                        float[][] b = new float[v.length][1];
                        for (int i = 0; i < v.length; i++) {
                            b[i][0] = v[i];
                        }
                        System_Linear_Equations sle = new System_Linear_Equations(A,b,format,items[4]);
                        sle.Progress_Run();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
