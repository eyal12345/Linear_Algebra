package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Invertible_Method extends System_Linear_Equations {

    public Invertible_Method(float[][] nA, float[][] nb, String fn, String ne, PrintWriter fr) {
        super(nA, nb, fn, ne, fr);
    }

    // calculate invertible matrix of a matrix
    private static float[][] Invertible(float[][] A) {
        int n = A.length;
        float det = Determinant(A);
        float[][] invM = new float[n][n];
        float[][] adj = Adjoint(A);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invM[i][j] = (1 / det) * adj[i][j];
            }
        }
        return invM;
    }

    // calculate x vector by invertible multiplication: x = Inv(A)*b
    private float[][] Calculate_X_Vector(float[][] invA, float[][] b) {
        int n = A.length;
        x = new float[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[i][0] += b[j][0] * invA[i][j];
            }
            x[i][0] = (float)(Math.round(x[i][0] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b
    public float[][] Invertible_Action(float[][] A, float[][] b) {
        float det = Determinant(A);
        if (det != 0) {
            fr.println("implement the solution by multiplication of b in invertible A: x = b*Inv(A)");
            float[][] invA = Invertible(A);
            fr.println("Inv(A) = ");
            fr.println(Display_Status_Matrix(invA,fn));
            x = Calculate_X_Vector(invA,b);
            return x;
        }
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }
}
