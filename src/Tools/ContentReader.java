package Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContentReader {

    public String Build_Path_Exercise(Properties prop) {
        String title = prop.getProperty("TITLE");
        String space = prop.getProperty("SPACE");
        String exercise = prop.getProperty("EXERCISE");
        String path = "Exercises/" + title + "/" + space + "/" + exercise + ".txt";
        File file = new File(path);
        if (file.exists()) {
            return path;
        } else {
            System.out.println("File not found!");
            return null;
        }
    }

    public float[][] Read_Exercise(String path) throws IOException {
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

    public float[][] Extract_Matrix_Component(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] A = new float[m][n - 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n - 1; j++) {
                A[i][j] = M[i][j];
            }
        }
        return A;
    }

    public float[][] Extract_Vector_Component(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] b = new float[m][1];
        for (int i = 0; i < m; i++) {
            b[i][0] = M[i][n - 1];
        }
        return b;
    }
}
