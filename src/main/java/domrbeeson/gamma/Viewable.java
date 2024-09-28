package domrbeeson.gamma;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.EntityInRange;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public interface Viewable {

    boolean isViewing(Player player);
    CompletableFuture<Void> addViewer(Player player);
    CompletableFuture<Void> removeViewer(Player player);
    default CompletableFuture<Void> removeAllViewers() {
        Collection<Player> viewers = new ArrayList<>(getViewers());
        CompletableFuture<?>[] futures = new CompletableFuture[viewers.size()];
        int i = 0;
        for (Player viewer : viewers) {
            futures[i] = removeViewer(viewer);
            i++;
        }
        return CompletableFuture.allOf(futures);
    }
    List<Player> getViewers();
    boolean hasViewers();

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
