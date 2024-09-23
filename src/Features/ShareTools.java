package Features;

import java.io.PrintWriter;

public class ShareTools {

    public final String fn;
    public final String ne;
    public PrintWriter fr;

    public ShareTools(String fn, String ne, PrintWriter fr) {
        this.fn = fn;
        this.ne = ne;
        this.fr = fr;
    }

    /////////////////////////////////////////////// Display Matrix ///////////////////////////////////////////////
    // display a matrix each current status
    public static String Display_Status_Matrix(float[][] M, String fn) {
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
    // check if a matrix is a unit matrix
    public static boolean Is_Unit_Matrix(float[][] M) {
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

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Unit_Vector(float[][] M, int i) {
        int n = M[0].length, c = 0;
        for (int j = 0; j < n; j++) {
            if (M[i][j] != 0) {
                c++;
            }
        }
        return (c <= 1);
    }

    // check if the matrix is a lower triangular
    public static boolean Is_Lower_Triangular(float[][] M) {
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

    ////////////////////////////////////////////// Matrices Creation /////////////////////////////////////////////
    // create a unit matrix with "n*n" size
    public static float[][] Unit_Matrix(int n) {
        float[][] I = new float[n][n];
        for (int i = 0; i < n; i++) {
            I[i][i] = 1;
        }
        return I;
    }

    // duplicate the matrix values into a new matrix
    public static float[][] Copy_Matrix(float[][] M) {
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
    public static float Determinant(float[][] M) {
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
    public static float[][] Sub_Matrix(float[][] M, int x, int y) {
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
    public static float[][] Transpose(float[][] M) {
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
    public static float[][] Adjoint(float[][] M) {
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
    public static float[][] Invertible(float[][] M) {
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
    public static float[][] Mul_Mats(float[][] M1, float[][] M2) {
        int m = M1.length, n = M2[0].length;
        float[][] M = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    M[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return M;
    }

    // calculate ranking of a matrix by upper triangular
    public static float[][] Ranking_Matrix(float[][] M) {
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
    // replace between two rows in a matrix
    public static void Retreat_Rows_Matrix(float[][] M, int r1, int r2) {
        int n = M[0].length;
        for (int j = 0; j < n; j++) {
            float k = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = k;
        }
    }

    ////////////////////////////////////////////////// Convertor /////////////////////////////////////////////////
    // convert a value to a format of rational number
    public static String convertDecimalToFraction(float x) {
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
            fr.println("R" + r1 + " <--> R" + r2);
        } else {
            fr.println("R" + r2 + " <--> R" + r1);
        }
        fr.println();
    }

    // show elementary actions for sum between rows in the system
    public void Sum_Elementary_Description(float k, int j, int i) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        fr.println("R" + r + " --> R" + r + " - R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    if (k % 1 == 0) {
                        fr.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - " + k + "*R" + c);
                    }
                } else if (fn.equals("rational")) {
                    String v = convertDecimalToFraction(k);
                    if (!v.equals("1")) {
                        fr.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - R" + c);
                    }
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    if (k % 1 == 0) {
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c);
                    }
                } else if (fn.equals("rational")) {
                    String v = convertDecimalToFraction(-k);
                    if (!v.equals("-1")) {
                        fr.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    }
                }
            }
            fr.println();
        }
    }

    // show elementary actions for multiplication of a row in the system
    public void Mul_Elementary_Description(float k, int j) {
        if (k != 1) {
            int r = j + 1;
            if (k % 1 == 0) {
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                }
            } else if (fn.equals("decimal")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k % 1 == 0) {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                } else {
                    fr.println("R" + r + " --> " + k + "*R" + r);
                }
            } else if (fn.equals("rational")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r);
                }
            }
            fr.println();
        }
    }
}
