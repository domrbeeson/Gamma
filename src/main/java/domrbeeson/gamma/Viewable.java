package domrbeeson.gamma;

import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.EntityInRange;
import domrbeeson.gamma.player.Player;

import java.util.*;

public interface Viewable {

    boolean isViewing(Player player);
    void addViewer(Player player);
    void removeViewer(Player player);
    default boolean canView(Player player) {
        return true;
    }
    default void removeAllViewers() {
        Collection<Player> viewers = new ArrayList<>(getViewers());
        for (Player viewer : viewers) {
            removeViewer(viewer);
        }
    }
    List<Player> getViewers();
    boolean hasViewers();

    default void broadcast(ChatMessage message) {
        broadcast(message.toString());
    }

    default void broadcast(String message) {
        getViewers().forEach(player -> {
            player.sendMessage(message);
        });
    }

    default SortedSet<EntityInRange> getViewersInRange(Pos centre, double radius) {
        SortedSet<EntityInRange> players = new TreeSet<>();
        getViewers().forEach(viewer -> {
            double distance = centre.distance(viewer.getPos());
            if (distance > radius) {
                return;
            }
            players.add(new EntityInRange(viewer, distance));
        });
        return players;
    }

}
