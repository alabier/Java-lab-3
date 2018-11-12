import java.util.ArrayList;

class Student {

    private int id;
    private String name;
    private ArrayList<Integer> marks;
    private Group group;

    Student (int id,String name){
        this.id = id;
        this.name = name;
        marks = new ArrayList<>();
    }

    Student (String id,String name){
        this(Integer.parseInt(id),name);
    }

    String getName() { return name; }
    int getId() {
        return id;
    }

    String getGroup(){
        if(group == null)
            return "";
        return group.getTitle();
    }

    void setStudentToGroup(Group group){
        if(group.findStudentById(id) != null && group.findStudentByName(name) != null){
            System.out.println(this.name + " is in group " + group.getTitle());
        }
        this.group = group;
    }

    void addMark(int mark) throws Exception {
        marks.add(mark);
    }

    double getAverageMark(){
        double avg = 0;
        for(int mark : marks)
            avg += mark;
        try {
            avg = Math.round(avg / marks.size()*100) / 100.0;
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }
        return avg;
    }
} 