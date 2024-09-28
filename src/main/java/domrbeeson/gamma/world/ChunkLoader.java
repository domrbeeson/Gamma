package domrbeeson.gamma.world;

import java.util.concurrent.CompletableFuture;

public final class ChunkLoader implements Runnable {

    private final CompletableFuture<Chunk> future;
    private final Chunk.Builder builder;

    public ChunkLoader(CompletableFuture<Chunk> future, Chunk.Builder builder) {
        this.future = future;
        this.builder = builder;
    }

    @Override
    public void run() {
        try {
            if (!builder.world.getFormat().readChunk(builder)) {
                builder.world.getGenerator().generate(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        future.complete(new Chunk(builder));
    }

}
