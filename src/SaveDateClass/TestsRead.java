package SaveDateClass;

public class TestsRead {
    private String subject;
    private String time;
    private int numQuestion;
    private int idTest;

    public TestsRead(int idTest,String subject, String time, int numQuestion) {
        this.idTest = idTest;
        this.subject = subject;
        this.time = time;
        this.numQuestion = numQuestion;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public int getIdTest() {
        return idTest;
    }
}
