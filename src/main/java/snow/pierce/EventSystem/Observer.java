package snow.pierce.EventSystem;

import snow.pierce.Components.GameObject;
import snow.pierce.EventSystem.Events.Event;

public interface Observer {

    void onNotify(GameObject object, Event event);

}
