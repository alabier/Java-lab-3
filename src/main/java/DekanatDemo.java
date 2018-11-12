import java.io.File;

public class DekanatDemo {
    public static void main(String[] args){
        File students=new File("src/main/resources/students.csv").getAbsoluteFile();
        File groups=new File("src/main/resources/groups.csv").getAbsoluteFile();
        Dekanat d1=new Dekanat();
        d1.getStudentsFromFile(students);
        d1.getGroupsFromFile(groups);
        d1.setGroupAllStudentsRandom();
        for(int i=0; i<7; i++)
            d1.generateMarks();
        d1.setHeads();
        d1.printStudents();
        d1.setMarkToStudent("Valaria Coutouhas",5);
        d1.printStudents();
        d1.sendDownTheStudent("Bern Inada");
        d1.changeGroup("Hilly Moreno-hornsleth","B");
        d1.writeToFile();
    }
} 