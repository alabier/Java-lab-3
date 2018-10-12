import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Group{
    private String title;
    List<Student> students = new ArrayList();
    private int num;
    private Student head;

    public Group(String title){
        this.title=title;
    }
    @Override
    public String toString() {
        return title;
    }

    public void addStudent(Student student){
        students.add(student);
        num++;
    }
    void choiceHead ()throws IndexOutOfBoundsException{
        try {
            Student head=students.get(0);
            for (Student student:students) {
                head = (student.averageMark() >=head.averageMark() ? student: head);
            }
            this.head=head;
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            System.err.println("В группе нет студентов");
        }
    }
    void printNum(){
        System.out.println(num);
    }

    public boolean searchStudentById(int id) {
        boolean b=false;
        for (Student student : students) {
            if (id == student.getId())
                b=true;
        }
        return b;
    }
    public Student searchStudentByFio(String fio) {
        Student s = null;
        for (Student student : students) {
            if (fio.equals(student.getFio()))
                s = student;
        }
        if (s==null){
            NoSuchElementException myException = new NoSuchElementException("Студента с таким ФИО нет в группе");
            throw myException;
        }
        return s;
    }
    public double calculateMeanScore() {
        double summAverageMarks = 0.0;
        double roundMeanScore=0.0;
        for (Student student:students)
            summAverageMarks += student.averageMark();
        try {
            double meanScore=summAverageMarks/(double) students.size();

            BigDecimal bigDecimal = new BigDecimal(meanScore);
            roundMeanScore = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            System.err.println("Отсутствуют оценки у студентов");
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            System.err.println("В группе нет студентов");
        }
        return roundMeanScore;
    }
    void removeStudent(int id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
            }
        }
    }
    public String getTitle() {
        return title;
    }

    public Student getHead() {
        return head;
    }
}
