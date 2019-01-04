package Data;

import java.io.File;

public class Guest extends Person {

    public Guest(String name, int pwhash){
        super(name, pwhash);
    }

    public String role(){
        return "guest";
    }

    public void removePerson(Person self){
        this.getSchedules().clear();
        File file = new File("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\"+name+".csv");
        if (file.exists()) file.delete();
    }
}
