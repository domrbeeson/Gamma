package domrbeeson.gamma.event;

import domrbeeson.gamma.Stoppable;

public record RegisteredEventListener<T extends Event>(
        Class<T> event,
        EventListener<T> listener,
        int priority,
        EventGroup group
) implements Comparable<RegisteredEventListener<T>>, Stoppable {

    @Override
    public int compareTo(RegisteredEventListener listener) {
        return Integer.compare(priority, listener.priority);
    }

    @Override
    public void stop() {
        group.unlisten(this);
    }

}
