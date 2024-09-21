import Features.ContentReader;
import Features.Receive_Matrices.Receive_Matrices;
import Features.Decompose_Matrices.Decompose_Matrices;
import Features.Invertible_Matrices.Invertible_Matrices;
import Features.System_Linear_Equations.System_Linear_Equations;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Properties;
import java.io.*;

public class Main {

    public static PrintWriter Create_Exercise_Path(float[][] M, String title, String exercise) throws IOException {
        int n = M[0].length;
        LocalDateTime cur = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        File file = new File("Results/" + title + "/R" + n + "/" + exercise.split("\\.")[0] + "_" + cur.format(formatter) + ".txt");
        return new PrintWriter(new FileWriter(file, true));
    }

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
                        PrintWriter fr = Create_Exercise_Path(M,title,exercise);
                        Receive_Matrices re = new Receive_Matrices(M,format,exercise,fr);
                        re.Progress_Run();
                    } else if (title.equals("Decompose_Matrices") || title.equals("Decompose Matrices")) {
                        PrintWriter fr = Create_Exercise_Path(M,title,exercise);
                        Decompose_Matrices de = new Decompose_Matrices(M,format,exercise,fr);
                        de.Progress_Run();
                    } else if (title.equals("Invertible_Matrices") || title.equals("Invertible Matrices")) {
                        PrintWriter fr = Create_Exercise_Path(M,title,exercise);
                        Invertible_Matrices inv = new Invertible_Matrices(M,format,exercise,fr);
                        inv.Progress_Run();
                    } else if (title.equals("System_Linear_Equations") || title.equals("System Linear Equations")) {
                        float[][] A = cr.Extract_Matrix_Component(M);
                        float[][] b = cr.Extract_Vector_Component(M);
                        PrintWriter fr = Create_Exercise_Path(A,title,exercise);
                        System_Linear_Equations sle = new System_Linear_Equations(A,b,format,exercise,fr);
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
