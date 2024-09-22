package Features.Receive_Matrices.Solution_Methods;

import Features.Receive_Matrices.Receive_Matrices;

import java.io.PrintWriter;

public class LU_Receiver extends Receive_Matrices {

    public LU_Receiver(float[][] L, String fn, String ne, PrintWriter fr) {
        super(L, fn, ne, fr);
    }

    // get the LU decomposition by multiplication of L and U (first algorithm)
    public void From_LU_To_M_V1(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(L,U);
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    public void From_LU_To_M_V2(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            int n = L.length;
            float[][] M = new float[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int m = Math.min(i,j);
                    for (int k = 0; k <= m; k++) {
                        M[i][j] += L[i][k] * U[k][j];
                    }
                }
            }
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }
}
