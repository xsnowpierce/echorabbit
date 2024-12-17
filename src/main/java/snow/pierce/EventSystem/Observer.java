package snow.pierce.EventSystem;

import snow.pierce.EventSystem.Events.Event;

public interface Observer {

    void onNotify(Event event);

}
