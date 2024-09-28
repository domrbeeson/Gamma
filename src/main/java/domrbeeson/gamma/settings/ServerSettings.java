package domrbeeson.gamma.settings;

import domrbeeson.gamma.version.MinecraftVersion;
import domrbeeson.gamma.world.Difficulty;

public final class ServerSettings {
    
    private final MinecraftVersion version;
    private final String ip;
    private final int port;
    private final int chunkPacketCompressionLevel;
    private final String defaultWorld;

    private int maxPlayers;
    private boolean pvp;
    private Difficulty defaultDifficulty;
    private boolean spawnAnimals;
    private boolean spawnMonsters;
    private int viewDistance;
    private boolean betacraftAuth;

    public ServerSettings(
        MinecraftVersion version,
        String ip,
        int port,
        int maxPlayers,
        boolean pvp,
        Difficulty defaultDifficulty,
        boolean spawnAnimals,
        boolean spawnMonsters,
        int viewDistance,
        boolean betacraftAuth,
        int chunkPacketCompressionLevel,
        String defaultWorld
    ) {
        this.version = version;
        this.ip = ip;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.pvp = pvp;
        this.defaultDifficulty = defaultDifficulty;
        this.spawnAnimals = spawnAnimals;
        this.spawnMonsters = spawnMonsters;
        this.viewDistance = viewDistance > 15 ? 15 : viewDistance < 3 ? 3 : viewDistance;
        this.betacraftAuth = betacraftAuth;
        this.chunkPacketCompressionLevel = chunkPacketCompressionLevel;
        this.defaultWorld = defaultWorld;
    }

    public MinecraftVersion getMinecraftVersion() {
        return version;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isPvPEnabled() {
        return pvp;
    }

    public void setPvpEnabled(boolean pvp) {
        this.pvp = pvp;
    }

    public Difficulty getDefaultDifficulty() {
        return defaultDifficulty;
    }

    public void setDefaultDifficulty(Difficulty defaultDifficulty) {
        this.defaultDifficulty = defaultDifficulty;
    }

    public boolean canSpawnAnimals() {
        return spawnAnimals;
    }

    public void setSpawnAnimals(boolean spawnAnimals) {
        this.spawnAnimals = spawnAnimals;
    }

    public boolean canSpawnMonsters() {
        return spawnMonsters;
    }

    public void setSpawnMonsters(boolean spawnMonsters) {
        this.spawnMonsters = spawnMonsters;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public int setViewDistance(int viewDistance) {
        if (viewDistance > 15) viewDistance = 15;
        else if (viewDistance < 3) viewDistance = 3;
        this.viewDistance = viewDistance;
        return viewDistance;
    }

    public boolean useBetacraftAuthentication() {
        return betacraftAuth;
    }

    public void setBetacraftAuthentication(boolean betacraftAuth) {
        this.betacraftAuth = betacraftAuth;
    }

    public int getChunkPacketCompressionLevel() {
        return chunkPacketCompressionLevel;
    }

    public String getDefaultWorldName() {
        return defaultWorld;
    }

}
