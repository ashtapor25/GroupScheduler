package Observer;

import Data.Event;
import Data.Person;

import java.util.List;

public class Observable {
    protected List<Observer> observers;

    public void notifyObservers() {
        for (Observer obsv: observers) {
            obsv.update(this);
        }
    }

}
