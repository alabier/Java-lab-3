import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void choiceHead() throws Exception {
        Student student1 = new Student(1,"1");
        student1.addMark(1);
        Student student2 = new Student(2,"2");
        student2.addMark(2);
        Student student3 = new Student(3,"3");
        student3.addMark(3);
        Group group = new Group("T197");
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        group.choiceHead();
        assertEquals(student3,group.getHead());
    }
   @Rule
    public final ExpectedException thrown = ExpectedException.none();
    @Test
    public void choiceHead_NullStudents() throws Exception {
        Group group = new Group("T297");
        //thrown.expect(IndexOutOfBoundsException.class);
        group.choiceHead();

    }
    @Test
    public void meanScore_calculate() throws Exception {
        Student student1 = new Student(1,"1");
        student1.addMark(1);
        Student student2 = new Student(2,"2");
        student2.addMark(2);
        Student student3 = new Student(3,"3");
        student3.addMark(3);
        Group group = new Group("T397");
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        assertEquals(2,group.calculateMeanScore(),1e-6);
    }
    /*@Rule
    public final ExpectedException thrown1 = ExpectedException.none();
    @Test
    public void meanScore_NoMarks() throws Exception {
        Student student1 = new Student(1,"1");
        Student student2 = new Student(2,"2");
        Student student3 = new Student(3,"3");
        Group group = new Group("T397");
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        group.meanScore();
       thrown1.expect(NumberFormatException.class);


    }*/

}