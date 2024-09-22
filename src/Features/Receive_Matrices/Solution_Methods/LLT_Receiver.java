package Features.Receive_Matrices.Solution_Methods;

import Features.Receive_Matrices.Receive_Matrices;

import java.io.PrintWriter;

public class LLT_Receiver extends Receive_Matrices {

    public LLT_Receiver(float[][] L, String fn, String ne, PrintWriter fr) {
        super(L, fn, ne, fr);
    }

    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    public void From_LLT_To_M_V1(float[][] L, float[][] LT) {
        float[][] M = Mul_Mats(L,LT);
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }

    // get the LL' decomposition by multiplication of L and L' (second algorithm)
    public void From_LLT_To_M_V2(float[][] L, float[][] LT) {
        int n = L.length;
        float[][] M = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int m = Math.min(i,j);
                for (int k = 0; k <= m; k++) {
                    M[i][j] += L[i][k] * LT[k][j];
                }
            }
        }
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }
}
