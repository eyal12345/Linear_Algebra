package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class System_Linear_Equations_Extended extends System_Linear_Equations {

    public System_Linear_Equations_Extended(float[][] nA, float[][] nb, String fn, PrintWriter fr) {
        super(nA, nb, fn, fr);
    }

    //////////////////////////////////////// Actions for Ranking Methods ////////////////////////////////////////
    // add zero row from the system by needed
    public void Add_Zero_Row(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length, t = b[0].length;
        float[][] nA = new float[m + 1][n];
        float[][] nb = new float[m + 1][t];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                nA[i][j] = A[i][j];
            }
            for (int k = 0; k < t ; k++) {
                nb[i][k] = b[i][k];
            }
        }
        this.A = nA; this.b = nb;
    }

    // remove zero row from the system by needed
    public void Delete_Zero_Row(float[][] A, float[][] b, int r) {
        int m = A.length, n = A[0].length, t = b[0].length;
        float[][] nA = new float[m - 1][n];
        float[][] nb = new float[m - 1][t];
        int k = 0;
        for (int i = 0; i < m; i++) {
            if (i != r) {
                for (int j = 0; j < n ; j++) {
                    nA[k][j] = A[i][j];
                }
                for (int j = 0; j < t ; j++) {
                    nb[k][j] = b[i][j];
                }
                k++;
            }
        }
        this.A = nA; this.b = nb;
    }

    // add a new column to the vector
    public float[][] Increase_Col_in_Vector(float[][] b) {
        int m = b.length, n = b[0].length;
        float[][] nb = new float[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
    }

    // remove the last column from the vector
    public float[][] Decrease_Col_in_Vector(float[][] b, float c) {
        int m = b.length;
        float[][] nb = new float[m][1];
        for (int i = 0; i < m; i++) {
            nb[i][0] = b[i][0] + c * b[i][1];
        }
        return nb;
    }

    // counter free variables in the system
    public int Count_Free_Variables(float[][] A, float[][] b, int r) {
        int t = b[0].length - 1;
        if (t == 1 && !Is_Zero_Row(b,r)) {
            float c = - b[r][0] / b[r][1];
            if (c % 1 == 0) {
                fr.println("exist certainly const for scalar (λ = " + (int) c + ") so we simplify the vector b");
            } else {
                fr.println("exist certainly const for scalar (λ = " + c + ") so we simplify the vector b");
            }
            b = Decrease_Col_in_Vector(b,c);
            t--;
            Write_Status_System(A,b);
            this.b = b;
        }
        return t;
    }

    // define free variable in row "r" where exist zero rows in the matrix
    public void Define_Free_Variable(float[][] A, float[][] b, int r) {
        int m = A.length, n = A[0].length;
        if (m < n && (Is_Unit_Vector(A,r) || m == 1)) {
            fr.println("adding new row to the system");
            Add_Zero_Row(A,b);
            A = this.A; b = this.b;
            m = A.length;
            Write_Status_System(A,b);
        }
        if (m <= n && Is_Zero_Row(A,r) && !Is_Linear_Dependent_Rows(A)) {
            int t = Count_Free_Variables(A,b,r); b = this.b;
            int d = m, d1 = Intersection_Zero_Row_Col(A,r), d2 = Linear_Dependent_Columns(A);
            if (d1 != -1) {
                d = d1;
            } else if (d2 != -1) {
                d = d2;
            } else if (!Is_Exist_Vector(A,r)) {
                d = r;
            } if (d < m && Is_Zero_Row(b,r)) {
                A[r][d] = 1;
                fr.println("define new column in the vector b when x" + (d + 1) + " is free variable in R" + m + " space:");
                b = Increase_Col_in_Vector(b);
                b[r][++t] = 1;
                Write_Status_System(A,b);
            }
        }
        this.A = A; this.b = b;
    }

    // check if number of rows in the matrix has reduced
    public boolean Is_Reduced_Rows(float[][] A, float[][] b, int r) {
        boolean changed = false;
        int m = A.length, n = A[0].length;
        if (m > n && Is_Zero_Row(A,r) && Is_Zero_Row(b,r)) {
            fr.println("delete the zero row from the system:");
            Delete_Zero_Row(A,b,r);
            A = this.A; b = this.b;
            changed = true;
            Write_Status_System(A,b);
        }
        return changed;
    }
}
