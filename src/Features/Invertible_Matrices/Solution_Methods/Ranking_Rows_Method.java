package Features.Invertible_Matrices.Solution_Methods;

import Features.Invertible_Matrices.Invertible_Matrices;

import java.io.PrintWriter;

public class Ranking_Rows_Method extends Invertible_Matrices {

    public Ranking_Rows_Method(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
    }

    // invert the M matrix by parallel ranking
    public float[][] Ranking_Rows_Action(float[][] M) {
        fr.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = this.InvM;
        while (!Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                    int r = Index_UnZero_Value(M,i);
                    Retreat_Elementary_Description(i,r);
                    Retreat_Rows_Matrices(M,InvM,i,r);
                    Write_Status_Matrices(M,InvM);
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && M[j][i] != 0) {
                        float c = M[j][i] / M[i][i];
                        Sum_Elementary_Description(c,j,i);
                        for (int k = 0; k < n; k++) {
                            M[j][k] -= M[i][k] * c;
                            InvM[j][k] -= InvM[i][k] * c;
                        }
                        M[j][i] = 0;
                        Write_Status_Matrices(M,InvM);
                    }
                    if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                        fr.println("this is a singular matrix");
                        return null;
                    } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                        float c = 1 / M[j][j];
                        Mul_Elementary_Description(c,j);
                        for (int k = 0; k < n; k++) {
                            InvM[j][k] /= M[j][j];
                        }
                        M[j][j] = 1;
                        Write_Status_Matrices(M,InvM);
                    }
                }
            }
        }
        return InvM;
    }
}
