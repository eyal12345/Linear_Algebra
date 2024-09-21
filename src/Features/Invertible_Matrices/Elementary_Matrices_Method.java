package Features.Invertible_Matrices;

import java.io.PrintWriter;

public class Elementary_Matrices_Method extends Invertible_Matrices {

    public Elementary_Matrices_Method(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
    }

    // invert the M matrix by parallel elementary matrices
    public float[][] Elementary_Matrices_Action(float[][] M) {
        fr.println("transform M matrix to I by an elementary matrices:");
        int n = M.length, i = 0, j = 0;
        float[][] InvM = this.InvM;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                int r = Index_UnZero_Value(M,i);
                Retreat_Elementary_Description(i,r);
                Retreat_Rows_Matrix(E,i,r);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
                Write_Status_Matrices(M,InvM);
            }
            if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Description(-E[j][i],j,i);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices(M,InvM);
            }
            if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                fr.println("this is a singular matrix");
                return null;
            } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Description(E[j][j],j);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
                M[j][j] = 1;
                Write_Status_Matrices(M,InvM);
            }
            if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return InvM;
    }
}
