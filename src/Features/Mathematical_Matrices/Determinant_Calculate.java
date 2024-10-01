package Features.Mathematical_Matrices;

import Features.MenuActions;
import Features.ShareTools;
import java.io.PrintWriter;
import java.util.Scanner;

public class Determinant_Calculate extends ShareTools implements MenuActions {
    public float[][] M;

    public Determinant_Calculate(float[][] nM, String fn, String ne, PrintWriter fr) {
        super(fn, ne, fr);
        this.M = nM;
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    private float Determinant_Up_To_3x3_V1(float[][] M) {
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

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    @Override
    // display user interface by selection method for solution
    public void User_Menu() {
        System.out.println("choose number method to solution:");
        System.out.println("1. calculate determinant in the standard method");
        System.out.println("2. calculate determinant in the special method up to 3*3 size (first method)");
        System.out.println("3. calculate determinant in the special method up to 3*3 size (second method)");
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // choose option in order to correctness check for M matrix
    public void User_Interface(float[][] M) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu();
        int op = sc.nextInt();
        int n = M.length;
        float det;
        switch (op) {
            case 1 -> {
                Display_Status_Matrix(M,fn);
                fr.println("implement the solution by regular method");
                det = Determinant(M);
                fr.println("the determinant of this matrix is:");
                if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(det * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(det * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(det));
                }
            }
            case 2 -> {
                Display_Status_Matrix(M,fn);
                fr.println("implement the solution by first special method:");
                if (n <= 3) {
                    det = Determinant_Up_To_3x3_V1(M);
                    fr.println("the determinant of this matrix is:");
                    if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                        fr.print((int) (Math.round(det * 1000.0) / 1000.0));
                    } else if (fn.equals("decimal")) {
                        fr.print(Math.round(det * 1000.0) / 1000.0);
                    } else if (fn.equals("rational")) {
                        fr.print(convertDecimalToFraction(det));
                    }
                } else {
                    fr.println("you entered matrix with size greater than 3");
                }
            }
            case 3 -> {
                Display_Status_Matrix(M,fn);
                fr.println("implement the solution by second special method:");
                if (n <= 3) {
                    det = Determinant_Up_To_3x3_V2(M);
                    fr.println("the determinant of this matrix is:");
                    if ((Math.round(det * 1000.0) / 1000.0) % 1 == 0) {
                        fr.print((int) (Math.round(det * 1000.0) / 1000.0));
                    } else if (fn.equals("decimal")) {
                        fr.print(Math.round(det * 1000.0) / 1000.0);
                    } else if (fn.equals("rational")) {
                        fr.print(convertDecimalToFraction(det));
                    }
                } else {
                    fr.println("you entered matrix with size greater than 3");
                }
            }
            default -> throw new Exception("you entered an invalid number");
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = M.length, n = M[0].length;
            fr.println("calculate determinant of the next matrix (" + m + "*" + n + " size):");
            fr.println(Display_Status_Matrix(M,fn));
            if (m != n) {
                fr.println("this is a matrix which is not a square matrix");
            } else {
                User_Interface(M);
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
