package Tools;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContentReader {

    public float[][] Read_Exercise(String file) throws IOException {
        List<Float> list = new ArrayList<>();
        InputStream inputStream = ContentReader.class.getResourceAsStream("/Exercises/" + file);
        if (inputStream == null) {
            System.out.println("File not found!");
            return null;
        }
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
    }

    public float[][] Matrix_Values(float[][] M) {
        int m = M.length, n = M[0].length;
        float[][] A = new float[m][n - 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n - 1; j++) {
                A[i][j] = M[i][j];
            }
        }
        return A;
    }

    public float[] Vector_Values(float[][] M) {
        int m = M.length, n = M[0].length;
        float[] b = new float[m];
        for (int i = 0; i < m; i++) {
            b[i] = M[i][n - 1];
        }
        return b;
    }
}
