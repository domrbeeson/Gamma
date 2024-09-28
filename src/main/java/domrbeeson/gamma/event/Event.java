package domrbeeson.gamma.event;

public interface Event {

    default void call(EventGroup group) {
        group.call(this);
    }

    interface WorldEvent extends Event {}
    interface GlobalEvent extends Event {}

}
