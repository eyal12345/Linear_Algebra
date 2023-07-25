import Tools.ContentReader;
import Features.Receive_Matrices;
import Features.Decompose_Matrices;
import Features.Invertible_Matrices;
import Features.System_Linear_Equations;
import Features.System_Linear_Equations_Extended;
import java.util.Scanner;

public class Main {

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
            ContentReader cr = new ContentReader();
            float[][] M;
            Scanner sc = new Scanner(System.in);
            int op = sc.nextInt();
            switch (op) {
                case 1:
                    M = cr.Read_Exercise("Receive_Matrices/Ex5.txt");
                    if (M != null) {
                        Receive_Matrices re = new Receive_Matrices("decimal");
                        re.Check_User_Input(M);
                    }
                    break;
                case 2:
                    M = cr.Read_Exercise("Decompose_Matrices/Ex41.txt");
                    if (M != null) {
                        Decompose_Matrices de = new Decompose_Matrices("decimal");
                        de.Check_User_Input(M);
                    }
                    break;
                case 3:
                    M = cr.Read_Exercise("Invertible_Matrices/Ex41.txt");
                    if (M != null) {
                        Invertible_Matrices inv = new Invertible_Matrices("decimal");
                        inv.Check_User_Input(M);
                    }
                    break;
                case 4:
                    M = cr.Read_Exercise("System_Linear_Equations/Ex45.txt");
                    if (M != null) {
                        float[][] A = cr.Matrix_Values(M);
                        float[] b = cr.Vector_Values(M);
                        System_Linear_Equations sle = new System_Linear_Equations("decimal");
                        sle.Check_User_Input(A,b);
                    }
                    break;
                case 5:
                    M = cr.Read_Exercise("System_Linear_Equations/Ex38.txt");
                    if (M != null) {
                        float[][] A = cr.Matrix_Values(M);
                        float[] b = cr.Vector_Values(M);
                        System_Linear_Equations_Extended sle = new System_Linear_Equations_Extended("decimal");
                        sle.Check_User_Input(A,b);
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
