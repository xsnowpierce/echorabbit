package snow.pierce.EventSystem.Events;

public class Event {

    public EventType eventType;

    public Event(EventType type ){
        this.eventType = type;
    }

    public Event(){
        this.eventType = EventType.GameEvent;
    }
}
