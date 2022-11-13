package wisniautilitypack.wisniautilitypack.utils.events;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener toAdd) {
        listeners.add(toAdd);
    }

    public void call(EventName eventName) {
        // Notify everybody that may be interested.
        for (EventListener eventListener : listeners)
            eventListener.onEvent(eventName);
    }
}
