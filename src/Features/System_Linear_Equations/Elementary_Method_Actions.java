package Features.System_Linear_Equations;

public interface Elementary_Method_Actions {

    void Retreat_Elementary_Action(float[][] A, float[][] b, int c);

    void Sum_Elementary_Action(float[][] A, float[][] b, int r, int c);

    void Mul_Elementary_Action(float[][] A, float[][] b, int r);

    float[][] Elementary_Method_Action(float[][] A, float[][] b);
}
