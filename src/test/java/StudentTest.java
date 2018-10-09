import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class StudentTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void averageMark_Calculate() throws Exception {
        Student student = new Student(1,"Трушков Николай Викторович");
        for (int i=1; i<=5; i++)
            student.addMark(i);
        assertEquals(3.0,student.averageMark(),1e-6);
    }
    @org.junit.Test
    public void averageMark_ByOneMark() throws Exception {
        Student student = new Student(1,"Трушков Николай Викторович");
        student.addMark(1);
        assertEquals(1,student.averageMark(),1e-6);
    }
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @org.junit.Test
    public void averageMark_NullMarkExceptionCheck() throws Exception {
        Student student = new Student(1,"Трушков Николай Викторович");
        thrown.expect(NumberFormatException.class);
        student.averageMark();
    }
}