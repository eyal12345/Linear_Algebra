package Features;

import Features.Mathematical_Matrices.*;
import Features.System_Linear_Equations.System_Linear_Equations;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ContentReader {

    private static PrintWriter Create_Exercise_Path(String title, String space, String exercise, String format) throws IOException {
        LocalDateTime cur = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String name = exercise.split("\\.")[0] + "_results_(" + format + ")_" + cur.format(formatter) + ".txt";
        File file = new File("Exercises/" + title + "/" + space + "/" + name);
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

    ///////////////////////////////////////////// Extract Components /////////////////////////////////////////////
    // extract A matrix component from M matrix
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

    // extract b vector component from M matrix
    private static float[][] Extract_Vector_Component(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] b = new float[m][1];
        for (int i = 0; i < m; i++) {
            b[i][0] = M[i][n - 1];
        }
        return b;
    }

    private static void Choose_Mathematical_Branch(float[][] M, String title, String space, String exercise, String format) throws Exception {
        PrintWriter fr = Create_Exercise_Path(title,space,exercise,format);
        switch (title) {
            case "Calculate_Determinant" -> {
                MenuActions run = new Determinant_Calculate(M,format,fr);
                run.Run_Progress();
            }
            case "Receive_Matrices" -> {
                MenuActions run = new Receive_Matrices(M,format,fr);
                run.Run_Progress();
            }
            case "Decompose_Matrices" -> {
                MenuActions run = new Decompose_Matrices(M,format,fr);
                run.Run_Progress();
            }
            case "Invertible_Matrices" -> {
                MenuActions run = new Invertible_Matrices(M,format,fr);
                run.Run_Progress();
            }
            case "System_Linear_Equations" -> {
                float[][] A = Extract_Matrix_Component(M);
                float[][] b = Extract_Vector_Component(M);
                MenuActionsSLE run = new System_Linear_Equations(A,b,format,fr);
                run.Run_Progress();
            }
            default -> throw new Exception("you entered an invalid value of title subject");
        }
    }

    public static void Run_Progress(String title, String space, String exercise, String format) throws Exception {
        String path = Build_Path_Exercise(title,space,exercise);
        if (path != null) {
            float[][] M = Read_Exercise(path);
            if (M != null) {
                Choose_Mathematical_Branch(M,title,space,exercise,format);
            }
        }
    }
}
