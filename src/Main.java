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
                    M = cr.Read_Exercise("Receive_Matrices/" + args[0]);
                    if (M != null) {
                        Receive_Matrices re = new Receive_Matrices(M,"decimal");
                        re.Progress_Run();
                    }
                    break;
                case 2:
                    M = cr.Read_Exercise("Decompose_Matrices/" + args[0]);
                    if (M != null) {
                        Decompose_Matrices de = new Decompose_Matrices(M,"decimal");
                        de.Progress_Run();
                    }
                    break;
                case 3:
                    M = cr.Read_Exercise("Invertible_Matrices/" + args[0]);
                    if (M != null) {
                        Invertible_Matrices inv = new Invertible_Matrices(M,"decimal");
                        inv.Progress_Run();
                    }
                    break;
                case 4:
                    M = cr.Read_Exercise("System_Linear_Equations/" + args[0]);
                    if (M != null) {
                        float[][] A = cr.Matrix_Values(M);
                        float[] b = cr.Vector_Values(M);
                        System_Linear_Equations sle = new System_Linear_Equations(A,b,"decimal");
                        sle.Progress_Run();
                    }
                    break;
                case 5:
                    M = cr.Read_Exercise("System_Linear_Equations/" + args[0]);
                    if (M != null) {
                        float[][] A = cr.Matrix_Values(M);
                        float[] vals = cr.Vector_Values(M);
                        float[][] b = new float[vals.length][1];
                        for (int i = 0; i < vals.length; i++) {
                            b[i][0] = vals[i];
                        }
                        System_Linear_Equations_Extended sle = new System_Linear_Equations_Extended(A,b,"decimal");
                        sle.Progress_Run();
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
