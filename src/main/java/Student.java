import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Student implements Cloneable{
    private int id;
    private String fio;
    private Group group;
    private List<Integer> marks = new ArrayList();
    private int num;

    public Student(int id, String fio){
        this.id=id;
        this.fio=fio;
    }
    public void addMark(int mark){
        marks.add(mark);
        num++;
    }
    public double averageMark() {
        int sumMarks = 0;
        double averageMark;
        double roundAverageMark = 0.0;
        for (int mark : marks)
            sumMarks += mark;

        try {
            averageMark = sumMarks/(double)num;
            BigDecimal bigDecimal = new BigDecimal(averageMark);
            roundAverageMark = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        } catch (ArithmeticException e) {
            System.err.println("У студента нет оценок");
        }
        return roundAverageMark;

    }
    public void addStudent(Group group){

        group.addStudent(this);
    }
    @Override
    public String toString() {
        return "id "+id+": студент "+fio;
    }
    public int getId(){
        return id;
    }
    public String getFio(){
        fio=this.fio;
        return fio;
    }
    public List<Integer> getMarks(){
        return marks;
    }
    public int getNum(){return num;
    }
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}