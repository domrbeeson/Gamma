package domrbeeson.gamma.event;

import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.network.packet.in.PacketIn;
import domrbeeson.gamma.player.PlayerConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class EventGroup<E extends Event> implements Tickable {

    private final Map<Class<? extends E>, SortedSet<RegisteredEventListener<E>>> registeredListeners = new HashMap<>();
    private final Map<PlayerConnection, Map<Class<? extends PacketIn>, PacketIn>> playerPackets = new ConcurrentHashMap<>();

    public <T extends E> RegisteredEventListener<T> listen(Class<T> event, EventListener<T> listener) {
        return listen(event, listener, 0);
    }

    public <T extends E> RegisteredEventListener<T> listen(Class<T> event, EventListener<T> listener, int priority) {
        var listeners = registeredListeners.getOrDefault(event, new TreeSet<>());
        RegisteredEventListener<T> comparableListener = new RegisteredEventListener<>(event, listener, priority, this);
        listeners.add((RegisteredEventListener<E>) comparableListener);
        registeredListeners.put(event, listeners);
        return comparableListener;
    }

    public <T extends E> void unlisten(RegisteredEventListener<T> listener) {
        var listeners = registeredListeners.get(listener.event());
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        registeredListeners.put(listener.event(), listeners);
    }

    public void call(E event) {
        SortedSet<RegisteredEventListener<E>> listeners;
        synchronized (registeredListeners) {
            listeners = registeredListeners.get(event.getClass());
        }

        if (listeners == null) {
            return;
        }

        Cancellable eventCancellable = null;
        if (event instanceof Cancellable) {
            eventCancellable = (Cancellable) event;
        }

        for (RegisteredEventListener<E> listener : listeners) {
            if (eventCancellable != null && eventCancellable.isCancelled()) {
                break;
            }
            try {
                listener.listener().onEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void queuePacket(PacketIn packet, PlayerConnection connection) {
        Map<Class<? extends PacketIn>, PacketIn> packets = playerPackets.getOrDefault(connection, new ConcurrentHashMap<>());
        if (packets.containsKey(packet.getClass())) {
//            System.out.println("dropped packet " + packet.getClass().getSimpleName());
            return;
        }
        packets.put(packet.getClass(), packet);
        playerPackets.put(connection, packets);
    }

    @Override
    public void tick(long ticks) {
        if (!this.playerPackets.isEmpty()) {
            Map<PlayerConnection, Map<Class<? extends PacketIn>, PacketIn>> playerPackets;
            synchronized (this.playerPackets) {
                playerPackets = new HashMap<>(this.playerPackets);
                this.playerPackets.clear();
            }
            playerPackets.forEach((connection, packets) -> {
                packets.forEach((clazz, packet) -> {
                    packet.handle();
                });
            });
        }
    }

}
