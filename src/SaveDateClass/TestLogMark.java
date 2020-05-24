package SaveDateClass;

public class TestLogMark {
    private String name;
    private String surname;
    private String date;
    private int mark;

    public TestLogMark(String name, String surname, String date, int mark) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDate() {
        return date;
    }

    public int getMark() {
        return mark;
    }
}
