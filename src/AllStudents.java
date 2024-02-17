import java.util.ArrayList;

public class AllStudents {
    private ArrayList<Student> studentList = new ArrayList<>();
    private static ArrayList<String> nameTags = new ArrayList<>();

    public AllStudents(String filename){
        ArrayList<String> rawdata = FileHandling.wholeFileRead(filename);

        String classCode = "null";
        //skip header for file
        //loop until end of file
        for (int i =1;i < rawdata.size();i++){
            //if line begins with class list report, grab class id
            if (rawdata.get(i).startsWith("Class List Report")){
                int startIndex = 20;
                int endIndex = rawdata.get(i).indexOf(" ", startIndex);
                classCode = rawdata.get(i).substring(startIndex,endIndex);
                //System.out.println(classCode);
                // if line begins with full name or male: ignore it
            } else if (rawdata.get(i).startsWith("Full Name") || rawdata.get(i).startsWith("Males: ")){
                continue;
            } else {
                //create new student objects and add to array
                Student newStudent = new Student(rawdata.get(i));
                studentList.add(newStudent);
                nameTags.add(newStudent.getNameTag());
            }
        }
    }
    public static ArrayList<String> getNameTags() {
        return nameTags;
    }

}
