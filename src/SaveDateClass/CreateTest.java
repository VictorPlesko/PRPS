package SaveDateClass;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateTest {
    public static CreateTest newTest;
    private int counter = 0;
    public ArrayList<ArrayList<String>> wordings = new ArrayList<>();
    public ArrayList<ArrayList<String>> correctAns = new ArrayList<>();
    public ArrayList<ArrayList<String>> incorrextAns = new ArrayList<>();

    private String subject;
    private String time;
    private int numQuestion;

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public CreateTest(String subject, String time, int numQuestion) {
        this.subject = subject;
        this.time = time;
        this.numQuestion = numQuestion;
    }

    public void incrementCounter(){
        ++counter;
    }

    public int getCounter() {
        return counter;
    }
}
