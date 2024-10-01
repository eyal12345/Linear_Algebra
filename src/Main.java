import Features.ContentReader;

public class Main {

    public static void main(String[] args) {
        try {
            ContentReader.Run_Progress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
