package snow.pierce.EventSystem;

import snow.pierce.EventSystem.Events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {

    private static final List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public static void Notify(Event event) {
        for(Observer observer : observers){
            observer.onNotify(event);
        }
    }
}
