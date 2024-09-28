package domrbeeson.gamma.utils;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

    public static InputStream getResource(String path) throws IOException {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
