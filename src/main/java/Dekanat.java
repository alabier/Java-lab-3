import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.*;

class Dekanat{
    private List<Student> allStudents = new ArrayList();
    private List<Group> groups = new ArrayList();

    private Document getXmlDoc(InputStream inputXml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputXml);
        return doc;
    }

    void studentsCreate() throws IOException, ParserConfigurationException, SAXException {
        Document doc = getXmlDoc(getClass().getResourceAsStream("Students.xml"));
        NodeList nodeListFio = doc.getElementsByTagName("Fio");
        NodeList nodeListId = doc.getElementsByTagName("Id");
        String fio;
        Integer id;
        allStudents.clear();
        for (int i=0; i<nodeListFio.getLength(); i++){
            id = Integer.parseInt(nodeListId.item(i).getTextContent());
            fio = String.valueOf(nodeListFio.item(i).getTextContent());
            allStudents.add(new Student(id,fio));
        }
    }
    void groupsCreate() throws IOException, ParserConfigurationException, SAXException {
        Document doc = getXmlDoc(getClass().getResourceAsStream("Groups.xml"));
        NodeList nodeListName = doc.getElementsByTagName("Name");
        groups.clear();
        String name;
        for (int i=0; i<nodeListName.getLength(); i++){
            name = String.valueOf(nodeListName.item(i).getTextContent());
            groups.add(new Group(name));
        }
    }
    void fillGroups(){
        Iterator<Student> it = allStudents.iterator();
        for (Group group:groups){
            group.students.clear();
            for(int i=0; i<allStudents.size()/groups.size(); i++) {
                if (!it.hasNext())
                    return;
                group.addStudent(it.next());
            }
        }
    }
    void addRandomMarks(int count){
        Random rand = new Random();
        for (Student student:allStudents){
            for (int i=0; i<count; i++)
                student.addMark(rand.nextInt(5)+1);
        }
    }
    private boolean searchGroup(String title){
        boolean b=false;
        for (Group group:groups){
            if (group.getTitle().equals(title))
                b=true;
        }
        return b;
    }
    private boolean searchStudentInAllStudents(int id){
        boolean b=false;
        for (Student student:allStudents){
            if (student.getId()==id)
                b=true;
        }
        return b;
    }
    private void moveStudent(int id, String title){
        for (Group group:groups) {
            if (group.searchStudentById(id)) {
                group.removeStudent(id);
            }
        }
        if (searchGroup(title)){
            for (Group group:groups) {
                if (group.getTitle().equals(title)) {
                    if (searchStudentInAllStudents(id)) {
                        for (Student student : allStudents) {
                            if (student.getId() == id)
                                group.addStudent(student);
                        }
                    } else throw new IllegalInputName("Нет студента с таким Id");
                }
            }
        }
        else throw new IllegalInputName("Нет такой группы");
    }
    private void removeStudentFromXmlById(int id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document doc = getXmlDoc(getClass().getResourceAsStream("Students.xml"));
        Node node = doc.getDocumentElement();
        NodeList nodeListStudents = doc.getElementsByTagName("Student");
        for (int i = 0; i < nodeListStudents.getLength(); i++) {
            Node nextNode = nodeListStudents.item(i);
            NodeList nl = nextNode.getChildNodes();
            for (int j=0; j<nl.getLength(); j++){
                Node nextRelativeNode = nl.item(j);
                if ((nextRelativeNode.getNodeType()==Node.ELEMENT_NODE)&&(nextRelativeNode.getTextContent()).equals(String.valueOf(id))){
                    node.removeChild(nextNode);
                }
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream("Students.xml"));
        transformer.transform(source,result);
        fillGroups();
    }

    void dismissStudent(double bound) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Iterator<Student> iterator = allStudents.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.averageMark() <= bound) {
                removeStudentFromXmlById(student.getId());
                iterator.remove();
                for (Group group:groups){
                    Iterator iterator1 = group.students.iterator();
                    while (iterator1.hasNext()){
                        Student st = (Student)iterator1.next();
                        if (student.equals(st))
                            iterator1.remove();
                    }
                }
            }
        }
    }
    private void HeadChoiceInit(String title){
        for (Group group:groups){
            if (title.equals(group.getTitle()))
                group.choiceHead();
        }
    }
    void printData(){
        class Format{
            private Formatter f = new Formatter(System.out);
            private void printGroupTitle(String title){
                f.format("\n%22s\n", title);
                f.format("%22s\n","----");
            }
            private void printHead(){
                f.format("%-5s %15s %26s\n","ID", "ФИО", "Средняя оценка");
                f.format("%-5s %15s %20s\n","---","---","---");
            }
            private void printStudent(int id, String fio, double averageMark){
                f.format("%-5d %-30s %5.1f\n", id, fio, averageMark);
            }
            private void printTotal(double meanScore){
                f.format("%-5s %15s %20s\n","---","---","---");
                f.format("%-30s %11s\n","Средняя оценка в группе:", meanScore);
            }
        }
        Format format = new Format();
        for (Group group:groups){
            format.printGroupTitle(group.getTitle());
            format.printHead();
            for (Student student:group.students)
                format.printStudent(student.getId(),student.getFio(),student.averageMark());
            format.printTotal(group.calculateMeanScore());
        }
    }
}
