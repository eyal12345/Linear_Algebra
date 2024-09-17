package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Forward_Backward_Method extends System_Linear_Equations {

    public Forward_Backward_Method(float[][] nA, float[][] nb, String fn, String ne, PrintWriter fr) {
        super(nA, nb, fn, ne, fr);
    }

    // calculate ranking of a matrix by upper triangular
    private static float[][] Ranking_Matrix(float[][] M) {
        int n = M.length;
        float[][] rM = Copy_Matrix(M);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rM[i][i] == 0) {
                    int r = (i + 1) % n;
                    for (int k = 0; k < n; k++) {
                        float t = rM[i][k];
                        rM[i][k] = rM[r][k];
                        rM[r][k] = t;
                    }
                } if (rM[j][i] != 0) {
                    float c = rM[j][i] / rM[i][i];
                    for (int k = 0; k < n; k++) {
                        rM[j][k] = rM[j][k] - rM[i][k] * c;
                    }
                }
            }
        }
        return rM;
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

    private void Retreat_Elementary_Action(float[][] L, float[][] b) {
        int n = b.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (L[i][i] == 0) {
                    fr.println("retreat rows which are zeros in main diagonal:");
                    Retreat_Elementary_Description(i,j);
                    Retreat_Rows_System(L,b,i,j);
                    fr.println(Display_Status_Matrix(L,fn));
                    break;
                }
            }
        }
    }

    // calculate y vector for from system: Ly = b
    private float[][] Calculate_Y_Vector(float[][] L, float[][] b) {
        int n = b.length;
        float[][] y = new float[n][1];
        Write_Status_System(L,b);
        for (int i = 0; i < n; i++) {
            y[i][0] = b[i][0];
            for (int j = 0; j < i; j++) {
                y[i][0] -= L[i][j] * y[j][0];
            }
            y[i][0] /= L[i][i];
        }
        return y;
    }

    // calculate x vector for from system: Ux = y
    private float[][] Calculate_X_Vector(float[][] U, float[][] y) {
        int n = b.length;
        x = new float[n][1];
        Write_Status_System(U,y);
        for (int i = n - 1; i >= 0; i--) {
            x[i][0] = y[i][0];
            for (int j = i + 1; j < n; j++) {
                x[i][0] -= U[i][j] * x[j][0];
            }
            x[i][0] /= U[i][i];
            x[i][0] = (float)(Math.round(x[i][0] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b by forward backward method: Ly = b and then Ux = y
    public float[][] Forward_Backward_Action(float[][] A, float[][] b) {
        float det = Determinant(A);
        if (det != 0) {
            fr.println("first, we will calculate upper ranking of A:");
            float[][] U = Ranking_Matrix(A);
            fr.println(Display_Status_Matrix(U,fn));
            fr.println("second, we will calculate lower ranking of A:");
            float[][] L = Mul_Mats(A,Invertible(U));
            fr.println(Display_Status_Matrix(L,fn));
            Retreat_Elementary_Action(L,b);
            fr.println("third, we will solve forward system Ly = b:");
            float[][] y = Calculate_Y_Vector(L,b);
            fr.println("finally, we will solve backward system Ux = y:");
            x = Calculate_X_Vector(U,y);
            return x;
        }
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }
}
