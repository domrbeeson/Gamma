package domrbeeson.gamma.world;

import java.util.concurrent.CompletableFuture;

public class ChunkSaver implements Runnable {

    private final CompletableFuture<Void> future;
    private final Chunk chunk;

    public ChunkSaver(CompletableFuture<Void> future, Chunk chunk) {
        this.future = future;
        this.chunk = chunk;
    }

    @Override
    public void run() {
        try {
            chunk.getWorld().getFormat().writeChunk(chunk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        future.complete(null);
    }

}
