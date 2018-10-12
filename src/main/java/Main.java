
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

class IllegalInputName extends RuntimeException{
    String message;
    public IllegalInputName(String message){
        this.message=message;
    }

    @Override
    public String toString() {
        return message;
    }
}



public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Dekanat dekanat = new Dekanat();
        try {
        dekanat.studentsCreate();
        dekanat.groupsCreate();
        dekanat.fillGroups();
        dekanat.addRandomMarks(7);
        //dekanat.dismissStudent(2.5);
       // dekanat.moveStudent(2,"C197");
        dekanat.printData();
        }
        catch (ParserConfigurationException|IOException|SAXException e){
            e.printStackTrace();
        }
    }
}
