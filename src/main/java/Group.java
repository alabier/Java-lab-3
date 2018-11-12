import java.util.ArrayList;
public class Group {

    private int id;
    private String title;
    private  ArrayList<Student> students;
    private Student head;

    Group(int id, String name) {
        this.id = id;
        this.title = name;
        students= new ArrayList<>();
    }

    Group(String id, String name) {
        this(Integer.parseInt(id),name);
    }

    void addStudent(Student std){
        if(findStudentByName(std.getName()) != null || findStudentById(std.getId()) != null){
            System.out.println("The student " + std.getName() +  " is already in this group");
        }
        students.add(std);
    }

    void setHead(Student std){
        head=std;
    }

    String getTitle() {
        return title;
    }

    Student getHead() { return head; }

    int getId() {
        return id;
    }

    Student findStudentByName(String stud){
        for(Student s:students){
            if(stud.equals(s.getName())){
                return s;
            }
        }
        return null;
    }

    int getAmountOfStudents() {
        return students.size();
    }

    Student getStudentByIndexArr(int i){
        return students.get(i);
    }

    Student findStudentById(int id){
        for(Student s : students){
            if(id==s.getId()) return s;
        }
        return null;
    }

    double getAverageMarkGroup(){
        double average = 0;
        for(Student s : students){
            average += s.getAverageMark();
        }
        return Math.round(average / students.size() * 100) / 100.0;
    }

    void removeStudent(Student s){
        if(students.contains(s)){
            students.remove(s);
        }
        else{System.out.println("No such student");}
    }
} 