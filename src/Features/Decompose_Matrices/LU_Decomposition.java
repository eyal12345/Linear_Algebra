package Features.Decompose_Matrices;

import java.io.PrintWriter;

public class LU_Decomposition extends Decompose_Matrices {

    public LU_Decomposition(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
    }

    // get the LU decomposition of M (first algorithm)
    public void From_M_To_LU_V1(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        fr.println("transform M matrix to U by an upper ranking:");
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                L[j][i] = M[j][i] / M[i][i];
                Sum_Elementary_Description(L[j][i],j,i);
                for (int k = 0; k < n; k++) {
                    M[j][k] -= M[i][k] * L[j][i];
                }
                M[j][i] = 0;
                if (L[j][i] != 0) {
                    fr.println(Display_Status_Matrix(M,fn));
                }
            }
            for (int k = i; k < n; k++) {
                U[i][k] = M[i][k];
            }
        }
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }

    // get the LU decomposition of M (second algorithm)
    public void From_M_To_LU_V2(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = 0; j < n; j++) {
                int m = Math.min(i,j);
                for (int k = 0 ;k < m ;k++) {
                    M[i][j] -= L[i][k] * U[k][j];
                }
                if (i <= j || U[j][j] == 0) {
                    U[i][j] = M[i][j];
                } else {
                    L[i][j] = M[i][j] / U[j][j];
                }
            }
        }
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }
}
