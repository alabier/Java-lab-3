import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;

public class Dekanat {

    private ArrayList<Student> students;
    private ArrayList<Group> groups;

    public Dekanat(){
        students = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public void getStudentsFromFile(File f ){
        String studentsRaw;
        String[] strArr;
        try {
            FileInputStream fileStream = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            while((studentsRaw = reader.readLine()) != null){
                strArr=studentsRaw.trim().split(",");
                students.add(new Student(strArr[0],strArr[1]));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found " + e);
        } catch (IOException e){
            System.out.println("Error of IO " + e);
        }
    }

    public void getGroupsFromFile(File f){
        String groupsRaw;
        String[] strArr;
        try {
            FileInputStream fileStream = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            while((groupsRaw = reader.readLine()) != null){
                strArr=groupsRaw.trim().split(",");
                groups.add(new Group(strArr[0],strArr[1]));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found " + e);
        } catch (IOException e){
            System.out.println("Error of IO " + e);
        }
    }


    public void setMarkToStudent(String s,int mark){
        Student foundStudent = findStudent(s);
        if(foundStudent == null){
            System.out.println("No such student");
        }
        try {
            foundStudent.addMark(mark);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Student findStudent(String s){
        for(Student student : students){
            if(student.getName().equals(s))
                return student;
        }
        return null;
    }

    public Group findGroup(String g){
        for(Group group : groups){
            if(group.getTitle().equals(g)) {
                return group;
            }
        }
        return null;
    }

    public void changeGroup(String s,String g){
        Student student = this.findStudent(s);
        Group group = this.findGroup(g);
        try {
            if (null == group) {
                System.out.println("No such group "+g);
            }
            if (null == student) {
                System.out.println("No such student "+s);
            }
            student.setStudentToGroup(group);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void sendDownTheStudent(String s){
        Student studentToSendDown= findStudent(s);
        students.remove(studentToSendDown);
    }

    public void setHeads(){
        Random r = new Random();
        int amountOfStudentsInTheGroup;
        try {
            for (Group g : groups) {
                amountOfStudentsInTheGroup = g.getAmountOfStudents();
                if (amountOfStudentsInTheGroup == 0){
                    System.out.println("No students in this group "+g.getTitle());
                    break;
                }
                g.setHead(g.getStudentByIndexArr(r.nextInt(amountOfStudentsInTheGroup)));
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void printStudents(){
        System.out.println("ID \t Name \t\t\t\t\t Group \t Average mark");
        for(Student s:students){
            System.out.println(s.getId() + "\t" + s.getName() + "\t\t" + s.getGroup() + "\t" + s.getAverageMark());
        }
        System.out.println("Group\t\t Head\t  Average mark of group");
        for(Group g:groups){
            System.out.println(g.getTitle() + "\t\t " + g.getHead().getName() + "\t " + g.getAverageMarkGroup());
        }
    }

    void generateMarks(){
        int numberOfMarks = (int)((random() * (15-3)) + 3);
        for (Student stud : students) {
            for (int i = 1; i < numberOfMarks; i++) {
                try {
                    stud.addMark((int) ((random() * (6 - 2)) + 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setGroupAllStudentsRandom(){
        Random r = new Random();
        int groupSize = groups.size();
        Group randGrp;
        for(Student s : students){
            randGrp=groups.get(r.nextInt(groupSize));
            s.setStudentToGroup(randGrp);
            randGrp.addStudent(s);
        }
    }

    public void writeToFile() {
        Writer studentWriter;
        Writer groupWriter;
        try {
            groupWriter = new FileWriter("src/main/resources/groups.csv");
            for (Group g : groups) {
                groupWriter.write(g.getId() + "," + g.getTitle() + "," + g.getHead().getName() + "\n");
                //groupWriter.write(System.getProperty("line.separator"));
            }
            groupWriter.flush();
            studentWriter = new FileWriter("src/main/resources/students.csv");
            for (Student s : students) {
                studentWriter.write(s.getId() + "," + s.getName() + "," + s.getGroup() + "," + s.getAverageMark() + "\n");
                //studentWriter.write(System.getProperty("line.separator"));
            }
            studentWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}