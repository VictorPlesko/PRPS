package SaveDateClass;

import java.util.ArrayList;

public class TakeTestSave {
    public static int counter = 0;
    public static int correct = 0;
    public static int incorrect = 0;
    private static int idTest;
    private static String time;
    private static int numQuestion;
    public static ArrayList<ArrayList<String>> wording = new ArrayList<>();
    public static ArrayList<ArrayList<String>> trueAns = new ArrayList<>();
    public static ArrayList<ArrayList<String>> falseAns = new ArrayList<>();

    public static int getIdTest() {
        return idTest;
    }

    public static String getTime() {
        return time;
    }

    public static int getNumQuestion() {
        return numQuestion;
    }

    public TakeTestSave(int idTest, String time, int numQuestion) {
        this.idTest = idTest;
        this.time = time;
        this.numQuestion = numQuestion;
    }
}
