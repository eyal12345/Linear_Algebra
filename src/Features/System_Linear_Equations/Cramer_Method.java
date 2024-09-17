package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Cramer_Method extends System_Linear_Equations {

    public Cramer_Method(float[][] nA, float[][] nb, String fn, String ne, PrintWriter fr) {
        super(nA, nb, fn, ne, fr);
    }

    private float Index_Determinant(float[][] A, float[][] b, int j) {
        int n = A.length;
        float[] h = new float[n];
        for (int i = 0; i < n; i++) {
            h[i] = A[i][j];
            A[i][j] = b[i][0];
        }
        float detj = Determinant(A);
        fr.println("A" + (j + 1) + " = ");
        fr.println(Display_Status_Matrix(A,fn));
        for (int i = 0; i < n; i++) {
            A[i][j] = h[i];
        }
        return detj;
    }

    // solve system of linear equations Ax = b by cramer method (first algorithm)
    public float[][] Cramer_Action(float[][] A, float[][] b) {
        float det = Determinant(A);
        if (det != 0) {
            int n = A.length;
            fr.println("x(i) = |A(i)|/|A| for each 1 <= i <= " + n);
            fr.println("det = " + det + "\n");
            x = new float[n][1];
            for (int j = 0; j < n; j++) {
                float detj = Index_Determinant(A,b,j);
                x[j][0] = detj / det;
                fr.println("det" + (j + 1) + " = " + detj);
                fr.println("x" + (j + 1) + " = " + x[j][0] + "\n");
            }
            return x;
        }
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }
}
