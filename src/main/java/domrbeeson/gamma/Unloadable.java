package domrbeeson.gamma;

import java.util.concurrent.CompletableFuture;

public interface Unloadable {

    CompletableFuture<Void> unload();

}
