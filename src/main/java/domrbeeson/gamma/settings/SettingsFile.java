package domrbeeson.gamma.settings;

import domrbeeson.gamma.version.MinecraftVersion;
import domrbeeson.gamma.world.Difficulty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class SettingsFile {
    
    private static final File SETTINGS_FILE = new File("settings.txt");

    private static final String VERSION_KEY = "version";
    private static final String IP_KEY = "ip";
    private static final String PORT_KEY = "port";
    private static final String MAX_PLAYERS_KEY = "max_players";
    private static final String PVP_KEY = "pvp";
    private static final String DIFFICULTY_KEY = "difficulty";
    private static final String SPAWN_ANIMALS_KEY = "spawn_animals";
    private static final String SPAWN_MONSTERS_KEY = "spawn_monsters";
    private static final String VIEW_DISTANCE_KEY = "view_distance";
    private static final String BETACRAFT_AUTH_KEY = "use_betacraft_authentication";
    private static final String CHUNK_PACKET_COMPRESSION_KEY = "chunk_packet_compression_level";
    private static final String DEFAULT_WORLD_KEY = "default_world_name";

    private static final Map<String, String> DEFAULT_VALUES = new HashMap<>() {{
        put(VERSION_KEY, MinecraftVersion.BETA_1_7.name());
        put(IP_KEY, "");
        put(PORT_KEY, "25565");
        put(MAX_PLAYERS_KEY, "20");
        put(PVP_KEY, "true");
        put(DIFFICULTY_KEY, "easy");
        put(SPAWN_ANIMALS_KEY, "true");
        put(SPAWN_MONSTERS_KEY, "true");
        put(VIEW_DISTANCE_KEY, "10");
        put(BETACRAFT_AUTH_KEY, "false");
        put(CHUNK_PACKET_COMPRESSION_KEY, "1");
        put(DEFAULT_WORLD_KEY, "world");
    }};

    private final ServerSettings serverSettings;

    public SettingsFile() {
        // TODO if a key doesn't exist, write it to the file

        Map<String, String> values = new HashMap<>();
        try {
            if (!SETTINGS_FILE.exists()) {

                FileWriter writer = new FileWriter(SETTINGS_FILE);
                for (Entry<String, String> entry : DEFAULT_VALUES.entrySet()) {
                    writer.append(entry.getKey() + "=" + entry.getValue() + "\n");
                }
                writer.close();
            }

            for (String line : Files.readAllLines(SETTINGS_FILE.toPath())) {
                String[] parts = line.split("=");
                values.put(parts[0], parts.length > 1 ? parts[1] : "");
            }
        } catch (IOException e) {
            serverSettings = null;
            e.printStackTrace();
            System.exit(1);
            return;
        }

        MinecraftVersion version = MinecraftVersion.valueOf(values.get(VERSION_KEY).toUpperCase());
        String ip = values.get(IP_KEY);
        int port = Integer.parseInt(values.get(PORT_KEY));
        int maxPlayers = Integer.parseInt(values.get(MAX_PLAYERS_KEY));
        boolean pvp = Boolean.parseBoolean(values.get(PVP_KEY));
        Difficulty difficulty = Difficulty.valueOf(values.get(DIFFICULTY_KEY).toUpperCase());
        boolean spawnAnimals = Boolean.parseBoolean(values.get(SPAWN_ANIMALS_KEY));
        boolean spawnMonsters = Boolean.parseBoolean(values.get(SPAWN_MONSTERS_KEY));
        int viewDistance = Integer.parseInt(values.get(VIEW_DISTANCE_KEY));
        boolean betacraftAuth = Boolean.parseBoolean(values.get(BETACRAFT_AUTH_KEY));
        int chunkPacketCompressionlevel = Integer.parseInt(values.get(CHUNK_PACKET_COMPRESSION_KEY));
        String defaultWorld = values.get(DEFAULT_WORLD_KEY);

        serverSettings = new ServerSettings(version, ip, port, maxPlayers, pvp, difficulty, spawnAnimals, spawnMonsters, viewDistance, betacraftAuth, chunkPacketCompressionlevel, defaultWorld);
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

}
