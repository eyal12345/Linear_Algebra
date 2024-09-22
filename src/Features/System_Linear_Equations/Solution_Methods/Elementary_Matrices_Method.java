package Features.System_Linear_Equations.Solution_Methods;

import Features.System_Linear_Equations.System_Linear_Equations;

import java.io.PrintWriter;

public class Elementary_Matrices_Method extends System_Linear_Equations {

    public Elementary_Matrices_Method(float[][] nA, float[][] nb, String fn, String ne, PrintWriter fr) {
        super(nA, nb, fn, ne, fr);
    }

    private void Retreat_Elementary_Action(float[][] A, float[][] b, int c) {
        int m = A.length;
        float[][] E = Unit_Matrix(m);
        if (A[c][c] == 0 && !Is_Zero_Col(A,c)) {
            int r = Index_UnZero_Value(A,c);
            if (r >= 0 && r < m && r != c) {
                A[r][c] = (A[r][c] >= -0.0001 && A[r][c] <= 0.0001) ? 0 : A[r][c];
                Retreat_Elementary_Description(c,r);
                Retreat_Rows_Matrix(E,c,r);
                Mul_Mats_System(E,A,b);
                A = this.A; b = this.b;
                Write_Status_System(A,b);
            }
        }
    }

    private void Sum_Elementary_Action(float[][] A, float[][] b, int r, int c) {
        int m = A.length, n = A[0].length;
        float[][] E = Unit_Matrix(m);
        if (c != r && A[c][c] != 0 && A[r][c] != 0) {
            E[r][c] -= (A[r][c] / A[c][c]);
            Sum_Elementary_Description(-E[r][c],r,c);
            Mul_Mats_System(E,A,b);
            A = this.A; b = this.b;
            A[r][c] = (A[r][c] >= -0.0001 && A[r][c] <= 0.0001) ? 0 : A[r][c];
            if (r < n) {
                A[r][r] = (A[r][r] >= -0.0001 && A[r][r] <= 0.0001) ? 0 : A[r][r];
            }
            Write_Status_System(A,b);
        }
    }

    private void Mul_Elementary_Action(float[][] A, float[][] b, int r) {
        int m = A.length;
        float[][] E = Unit_Matrix(m);
        if (Is_Unit_Vector(A,r)) {
            int d = Index_for_Unit_Vector(Row_from_Matrix(A,r));
            if (d != -1 && A[r][d] != 0 && A[r][d] != 1) {
                E[r][r] = 1 / A[r][d];
                Mul_Elementary_Description(E[r][r],r);
                Mul_Mats_System(E,A,b);
                A = this.A; b = this.b;
                A[r][d] = 1;
                Write_Status_System(A,b);
            }
        }
    }

    // solve system of linear equations Ax = b by parallel elementary matrices
    public float[][] Elementary_Matrices_Action(float[][] A, float[][] b) {
        fr.println("transform A matrix to I by an elementary matrices:");
        int i = 0, j = 0;
        while (!Is_Unit_Matrix(A)) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            Define_Free_Variable(this.A,this.b,i);
            Retreat_Elementary_Action(this.A,this.b,i);
            Sum_Elementary_Action(this.A,this.b,j,i);
            Mul_Elementary_Action(this.A,this.b,j);
            boolean changed = Is_Reduced_Rows(this.A,this.b,j);
            A = this.A; b = this.b;
            int m = A.length, n = A[0].length;
            if (!changed && Is_Zero_Row(A,j) && !Is_Zero_Row(b,j)) {
                fr.println("does not an exists solutions");
                return null;
            }
            if (j == m - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % m;
        }
        return b;
    }
}
