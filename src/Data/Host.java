package Data;

import java.io.File;

public class Host extends Person {

    public Host(String name, int pwhash){
        super(name, pwhash);
    }

    public String role(){
        return "host";
    }

    public void removePerson(Person person){
        person.getSchedules().clear();
        File file = new File("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\"+person.getName()+".csv");
        if (file.exists()) file.delete();
    }
}
