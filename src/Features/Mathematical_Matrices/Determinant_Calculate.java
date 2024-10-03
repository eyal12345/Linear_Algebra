package Features.Mathematical_Matrices;

import Features.MenuActions;
import Features.ShareTools;
import java.io.PrintWriter;

public class Determinant_Calculate extends ShareTools implements MenuActions {
    public float[][] M;

    public Determinant_Calculate(float[][] nM, String method, String format, PrintWriter writer) {
        super(method, format, writer);
        this.M = nM;
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    private float Determinant_Up_To_3x3(float[][] M) {
        int n = M.length;
        float det = (n > 1) ? 0 : M[0][0];
        for (int i = 0 ;i < n ;i++) {
            float m_d = 1 ,s_d = 1;
            for (int j = 0 ;j < n ;j++) {
                int dl = j - i ,dr = j + i;
                if (dl < 0 && dr >= n) {
                    m_d *= M[dr - n][n + dl];
                    s_d *= M[2*n - 1 - dr][j];
                } else if (dl < 0) {
                    m_d *= M[dr][n + dl];
                    s_d *= M[n - 1 - dr][j];
                } else if (dr >= n) {
                    m_d *= M[dr - n][dl];
                    s_d *= M[2*n - 1 - dr][j];
                } else {
                    m_d *= M[dr][dl];
                    s_d *= M[n - 1 - dr][j];
                }
            }
            det += (m_d - s_d);
        }
        return det;
    }

    private float Determinant_Up_To_3x3_V2(float[][] M) {
        int n = M.length;
        float det = (n > 1) ? 0 : M[0][0];
        for (int i = 0 ;i < n ;i++) {
            float m_d = 1 ,s_d = 1;
            for (int j = 0 ;j < n ;j++) {
                int dl = n + j - i ,dr = n + j + i;
                m_d *= M[dr % n][dl % n];
                s_d *= M[(3*n - 1 - dr) % n][j];
            }
            det += (m_d - s_d);
        }
        return det;
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // choose option in order to correctness check for M matrix
    public void User_Interface(float[][] M) throws Exception {
        int n = M.length;
        float det;
        switch (method) {
            case "Regular_Method" -> {
                Display_Status_Matrix(M,format);
                writer.println("implement the solution by regular method");
                det = Determinant(M);
                writer.println("the determinant of this matrix is:");
                if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                    writer.print((int) (Math.round(det * 1000.0) / 1000.0));
                } else if (format.equals("decimal")) {
                    writer.print(Math.round(det * 1000.0) / 1000.0);
                } else if (format.equals("rational")) {
                    writer.print(convertDecimalToFraction(det));
                }
            }
            case "Special_Method" -> {
                Display_Status_Matrix(M,format);
                writer.println("implement the solution by first special method:");
                if (n <= 3) {
                    det = Determinant_Up_To_3x3(M);
                    writer.println("the determinant of this matrix is:");
                    if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                        writer.print((int) (Math.round(det * 1000.0) / 1000.0));
                    } else if (format.equals("decimal")) {
                        writer.print(Math.round(det * 1000.0) / 1000.0);
                    } else if (format.equals("rational")) {
                        writer.print(convertDecimalToFraction(det));
                    }
                } else {
                    writer.println("you entered matrix with size greater than 3");
                }
            }
            case "Special_Method_2" -> {
                Display_Status_Matrix(M,format);
                writer.println("implement the solution by second special method:");
                if (n <= 3) {
                    det = Determinant_Up_To_3x3_V2(M);
                    writer.println("the determinant of this matrix is:");
                    if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                        writer.print((int) (Math.round(det * 1000.0) / 1000.0));
                    } else if (format.equals("decimal")) {
                        writer.print(Math.round(det * 1000.0) / 1000.0);
                    } else if (format.equals("rational")) {
                        writer.print(convertDecimalToFraction(det));
                    }
                } else {
                    writer.println("you entered matrix with size greater than 3");
                }
            }
            default -> throw new Exception("you entered invalid number");
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (format.equals("decimal") || format.equals("rational")) {
            int m = M.length, n = M[0].length;
            writer.println("calculate determinant of the next matrix (" + m + "*" + n + " size):");
            writer.println(Display_Status_Matrix(M,format));
            if (m != n) {
                writer.println("this is a matrix which is not a square matrix");
            } else {
                User_Interface(M);
            }
            writer.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
