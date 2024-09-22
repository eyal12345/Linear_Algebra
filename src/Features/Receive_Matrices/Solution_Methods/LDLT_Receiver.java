package Features.Receive_Matrices.Solution_Methods;

import Features.Receive_Matrices.Receive_Matrices;

import java.io.PrintWriter;

public class LDLT_Receiver extends Receive_Matrices {

    public LDLT_Receiver(float[][] L, String fn, String ne, PrintWriter fr) {
        super(L, fn, ne, fr);
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    public void From_LDLT_To_M_V1(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(Mul_Mats(L,D),LT);
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LDL' decomposition by multiplication of L, D and L' (second algorithm)
    public void From_LDLT_To_M_V2(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            int n = L.length;
            float[][] M = new float[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int m = Math.min(i,j);
                    for (int k = 0; k <= m; k++) {
                        M[i][j] += L[i][k] * D[k][k] * LT[k][j];
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
