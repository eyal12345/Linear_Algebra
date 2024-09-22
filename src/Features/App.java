package Features;

import Features.System_Linear_Equations.System_Linear_Equations;
import Features.Invertible_Matrices.Invertible_Matrices;
import Features.Decompose_Matrices.Decompose_Matrices;
import Features.Receive_Matrices.Receive_Matrices;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class App {

    private static PrintWriter Create_Exercise_Path(float[][] M, String title, String exercise) throws IOException {
        int n = M[0].length;
        LocalDateTime cur = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        File file = new File("Results/" + title + "/R" + n + "/" + exercise.split("\\.")[0] + "_" + cur.format(formatter) + ".txt");
        return new PrintWriter(new FileWriter(file, true));
    }

    private static String Build_Path_Exercise(String title, String space, String exercise) {
        String path = "Exercises/" + title + "/" + space + "/" + exercise + ".txt";
        File file = new File(path);
        if (file.exists()) {
            return path;
        } else {
            System.out.println("File not found!");
            return null;
        }
    }

    private static float[][] Read_Exercise(String path) throws IOException {
        List<Float> list = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            int rows = 0, cols = line.split(",|\\|").length;
            while (line != null) {
                String[] elements = line.split(",|\\|");
                for (int j = 0; j < cols; j++) {
                    list.add(Float.parseFloat(elements[j]));
                }
                rows++;
                line = reader.readLine();
            }
            reader.close();
            float[][] M = new float[rows][cols];
            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    M[i][j] = list.get(index++);
                }
            }
            return M;
        } catch (IOException e) {
            System.out.println("File not found!");
            return null;
        }
    }

    private static float[][] Extract_Matrix_Component(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] A = new float[m][n - 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n - 1; j++) {
                A[i][j] = M[i][j];
            }
        }
        return A;
    }

    private static float[][] Extract_Vector_Component(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] b = new float[m][1];
        for (int i = 0; i < m; i++) {
            b[i][0] = M[i][n - 1];
        }
        return b;
    }

    private static void Choose_Mathematical_Branch(float[][] M, String title, String exercise, String format) throws Exception {
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
            float[][] A = Extract_Matrix_Component(M);
            float[][] b = Extract_Vector_Component(M);
            PrintWriter fr = Create_Exercise_Path(A,title,exercise);
            System_Linear_Equations sle = new System_Linear_Equations(A,b,format,exercise,fr);
            sle.Progress_Run();
        } else {
            throw new Exception("you entered an invalid value of title subject");
        }
    }

    public static void Run_Progress() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String title = prop.getProperty("TITLE");
            String space = prop.getProperty("SPACE");
            String exercise = prop.getProperty("EXERCISE");
            String format = prop.getProperty("FORMAT");
            String path = Build_Path_Exercise(title,space,exercise);
            if (path != null) {
                float[][] M = Read_Exercise(path);
                if (M != null) {
                    Choose_Mathematical_Branch(M,title,exercise,format);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
