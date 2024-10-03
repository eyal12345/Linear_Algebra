package Features;

import java.io.PrintWriter;
import java.util.Vector;

public class ShareTools {

    public final String method;
    public final String format;
    public PrintWriter writer;

    public ShareTools(String method, String format, PrintWriter writer) {
        this.method = method;
        this.format = format;
        this.writer = writer;
    }

    /////////////////////////////////////////////// Display Matrix ///////////////////////////////////////////////
    // display a matrix each current status
    public String Display_Status_Matrix(float[][] M, String fn) {
        int n = M.length;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    s.append((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    s.append(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    s.append(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    s.append(" ,");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if all the values in the main diagonal which are equals to 1
    public boolean Is_One_Slant(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] != 1) {
                return false;
            }
        }
        return true;
    }

    // check if the matrix is a symmetrical matrix
    public boolean Is_Symmetrical_Matrix(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] != M[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if all the values in the main diagonal which are positive
    public boolean Is_Values_Positives(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] < 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the matrix zeros row
    public boolean Is_Zero_Row(float[][] A, int r) {
        int n = A[0].length;
        for (int j = 0; j < n; j++) {
            if (A[r][j] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the vector zeros column
    public boolean Is_Zero_Col(float[][] v, int c) {
        int m = v.length, n = v[0].length;
        for (int i = 0; i < m && c < n; i++) {
            if (v[i][c] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if matrix is unit matrix
    public boolean Is_Unit_Matrix(float[][] M) {
        int m = M.length, n = M[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int r = Math.min(i,j);
                if (M[r][r] != 1 || (i != j && M[i][j] != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the specific row in the matrix is unit vector
    public boolean Is_Unit_Vector(float[][] M, int i) {
        int n = M[0].length, c = 0;
        for (int j = 0; j < n; j++) {
            if (M[i][j] != 0) {
                c++;
            }
        }
        return (c <= 1);
    }

    // check if the matrix is upper triangular
    public boolean Is_Upper_Triangular(float[][] M) {
        int m = M.length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                if (M[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the matrix is lower triangular
    public boolean Is_Lower_Triangular(float[][] M) {
        int m = M.length, n = M[0].length;
        for (int i = 0; i < m - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if exist invalid row in the system which is not allow to reach solution
    public boolean Is_Invalid_System(float[][] A, float[][] b) {
        int m = A.length;
        for (int i = 0; i < m; i++) {
            if (Is_Zero_Row(A,i) && !Is_Zero_Row(b,i)) {
                return true;
            }
        }
        return false;
    }

    // check if the vector is a zero vector
    public boolean Is_Zero_Vector(float[] v) {
        int n = v.length;
        for (int i = 0; i < n; i++) {
            if (v[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if all vector values is equal to each other
    public boolean Is_Equals_Values(Vector<Float> R) {
        int n = R.size();
        for (int i = 0; i < n - 1; i++) {
            if (!R.get(i).equals(R.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    // check if the specific row in the matrix is a unit vector
    public boolean Is_Exist_Vector(float[][] A, int r) {
        int m = A.length;
        float[] v = Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Index_for_Unit_Vector(v);
        for (int i = 0; i < m; i++) {
            if (i != r && Is_Unit_Vector(A,r) && Is_Unit_Vector(A,i)) {
                int c2 = Index_for_Unit_Vector(Row_from_Matrix(A,i));
                if (c1 == c2 && c1 != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if exists two vectors in the matrix which are linearly dependent
    public boolean Is_Linear_Dependent_Rows(float[][] A) {
        int m = A.length, n = A[0].length;
        for (int r1 = 0; r1 < m - 1; r1++) {
            for (int r2 = r1 + 1; r2 < m; r2++) {
                if (!Is_Unit_Vector(A,r1) && !Is_Unit_Vector(A,r2)) {
                    Vector<Float> R  = new Vector<Float>();
                    for (int j = 0; j < n; j++) {
                        if (A[r1][j] != 0 || A[r2][j] != 0) {
                            R.add(A[r1][j] / A[r2][j]);
                            if (r2 == r1 + 1 && Is_Equals_Values(R) && R.size() >= 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index from the vector that is indicating a unit vector
    public int Index_for_Unit_Vector(float[] v) {
        int n = v.length;
        for (int c = 0; c < n; c++) {
            if (v[c] != 0) {
                return c;
            }
        }
        return -1;
    }

    // get the index starting from the specific column in the matrix which are him value not equal to 0 with or in the negative direction
    public int Index_UnZero_Value(float[][] M, int k, boolean flag) {
        int n = M.length;
        if (flag) {
            for (int i = k + 1; i < n + k; i++) {
                if (M[i % n][k] != 0) {
                    return i % n;
                }
            }
        } else {
            for (int i = n + k - 1; i > k - 1; i--) {
                if (M[i % n][k] != 0) {
                    return i % n;
                }
            }
        }
        return -1;
    }

    // find two columns in the matrix which are linearly dependent
    public int Linear_Dependent_Columns(float[][] A) {
        int m = A.length, n = A[0].length;
        for (int c1 = 0; c1 < n - 1; c1++) {
            for (int c2 = c1 + 1; c2 < n; c2++) {
                Vector<Float> C  = new Vector<Float>();
                for (int r = 0; r < m; r++) {
                    if (A[r][c1] != 0 || A[r][c2] != 0) {
                        C.add(A[r][c1] / A[r][c2]);
                    }
                }
                if (Is_Equals_Values(C) && C.size() > 1) {
                    return c1;
                }
            }
        }
        return -1;
    }

    // get the index column from the specific row in the matrix which are indicating intersection between zero rows and zero columns
    public int Intersection_Zero_Row_Col(float[][] A, int r) {
        int n = A[0].length;
        for (int c = 0; c < n; c++) {
            if (Is_Zero_Col(A,c) && Is_Zero_Row(A,r)) {
                return c;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrices Creation /////////////////////////////////////////////
    // create a unit matrix with "n*n" size
    public float[][] Unit_Matrix(int n) {
        float[][] I = new float[n][n];
        for (int i = 0; i < n; i++) {
            I[i][i] = 1;
        }
        return I;
    }

    // duplicate the matrix values into a new matrix
    public float[][] Copy_Matrix(float[][] M) {
        int n = M.length;
        float[][] cM = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cM[i][j] = M[i][j];
            }
        }
        return cM;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // calculate determinant of a matrix
    public float Determinant(float[][] M) {
        int m = M.length, n = M[0].length;
        if (m != n) {
            return 0;
        } else {
            if (m == 1) {
                return M[0][0];
            }
            float sum = 0;
            for (int i = 0; i < m; i++) {
                sum += M[0][i] * Determinant(Sub_Matrix(M,0,i)) * Math.pow(-1,i);
            }
            return sum;
        }
    }

    // calculate of sub-matrix from a matrix by cutting row "x" and column "y"
    public float[][] Sub_Matrix(float[][] M, int x, int y) {
        int n = M.length ,p = 0 ,q = 0;
        float[][] subM = new float[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != x && j != y) {
                    subM[p][q] = M[i][j];
                    q++;
                    if (q == n - 1) {
                        p++;
                        q = 0;
                    }
                }
            }
        }
        return subM;
    }

    // calculate transpose matrix of a matrix
    public float[][] Transpose(float[][] M) {
        int n = M.length;
        float[][] MT = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                MT[j][i] = M[i][j];
            }
        }
        return MT;
    }

    // calculate adjoint matrix of a matrix
    public float[][] Adjoint(float[][] M) {
        int n = M.length;
        if (n == 1) {
            return Unit_Matrix(1);
        } else {
            float[][] Adj = new float[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Adj[j][i] = (float) (Math.pow(-1,i + j) * Determinant(Sub_Matrix(M,i,j)));
                }
            }
            return Adj;
        }
    }

    // calculate invertible matrix of a matrix
    public float[][] Invertible(float[][] M) {
        int n = M.length;
        float det = Determinant(M);
        float[][] invM = new float[n][n];
        float[][] adj = Adjoint(M);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invM[i][j] = (1 / det) * adj[i][j];
            }
        }
        return invM;
    }

    // calculate multiplication between two matrices provided that M1's length column is equal to M2's length row
    public float[][] Mul_Mats(float[][] M1, float[][] M2) {
        int m = M1.length, n = M2[0].length;
        float[][] M = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    M[i][j] += M1[i][k] * M2[k][j];
                }
                M[i][j] = (M[i][j] >= -0.0001 && M[i][j] <= 0.0001) ? 0 : M[i][j];
            }
        }
        return M;
    }

    // calculate ranking of a matrix by upper triangular
    public float[][] Ranking_Matrix(float[][] M) {
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

    ///////////////////////////////////////////////// Matrix Rows ////////////////////////////////////////////////
    // import the specific row from the matrix
    public float[] Row_from_Matrix(float[][] A, int r) {
        int n = A[0].length;
        float[] v = new float[n];
        for (int j = 0; j < n; j++) {
            v[j] = A[r][j];
        }
        return v;
    }

    // replace between two rows in a matrix
    public void Retreat_Rows_Matrix(float[][] M, int r1, int r2) {
        int n = M[0].length;
        for (int j = 0; j < n; j++) {
            float k = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = k;
        }
    }

    ////////////////////////////////////////////////// Convertor /////////////////////////////////////////////////
    // convert a value to a format of rational number
    public String convertDecimalToFraction(float x) {
        if (x < 0) {
            return "-" + convertDecimalToFraction(-x);
        } else {
            float tolerance = (float) 1.0E-5, h1 = 1, h2 = 0, k1 = 0, k2 = 1, b = x;
            do {
                float a = (float) Math.floor(b);
                float aux = h1;
                h1 = a*h1 + h2;
                h2 = aux;
                aux = k1;
                k1 = a*k1 + k2;
                k2 = aux;
                b = 1 / (b - a);
            } while (Math.abs(x - h1/k1) > x*tolerance);
            if (k1 != 1) {
                return String.valueOf((int) h1 + "/" + (int) k1);
            } else {
                return String.valueOf((int) h1);
            }
        }
    }

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for replace between rows in the system
    public void Retreat_Elementary_Description(int i, int j) {
        int r1 = i + 1, r2 = j + 1;
        if (r1 <= r2) {
            writer.println("R" + r1 + " <--> R" + r2);
        } else {
            writer.println("R" + r2 + " <--> R" + r1);
        }
        writer.println();
    }

    // show elementary actions for sum between rows in the system
    public void Sum_Elementary_Description(float k, int j, int i) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        writer.println("R" + r + " --> R" + r + " - R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    }
                } else if (format.equals("decimal")) {
                    if (k % 1 == 0) {
                        writer.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " - " + k + "*R" + c);
                    }
                } else if (format.equals("rational")) {
                    String v = convertDecimalToFraction(k);
                    if (!v.equals("1")) {
                        writer.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " - R" + c);
                    }
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        writer.println("R" + r + " --> R" + r + " + R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    }
                } else if (format.equals("decimal")) {
                    if (k % 1 == 0) {
                        writer.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c);
                    }
                } else if (format.equals("rational")) {
                    String v = convertDecimalToFraction(-k);
                    if (!v.equals("-1")) {
                        writer.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c);
                    } else {
                        writer.println("R" + r + " --> R" + r + " + R" + c);
                    }
                }
            }
            writer.println();
        }
    }

    // show elementary actions for multiplication of a row in the system
    public void Mul_Elementary_Description(float k, int j) {
        if (k != 1) {
            int r = j + 1;
            if (k % 1 == 0) {
                if (k == -1) {
                    writer.println("R" + r + " --> - R" + r);
                } else {
                    writer.println("R" + r + " --> " + (int) k + "*R" + r);
                }
            } else if (format.equals("decimal")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k % 1 == 0) {
                    writer.println("R" + r + " --> " + (int) k + "*R" + r);
                } else {
                    writer.println("R" + r + " --> " + k + "*R" + r);
                }
            } else if (format.equals("rational")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k == -1) {
                    writer.println("R" + r + " --> - R" + r);
                } else {
                    writer.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r);
                }
            }
            writer.println();
        }
    }
}
