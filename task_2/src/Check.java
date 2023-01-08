import java.nio.file.Files;
import java.nio.file.Path;

public interface Check {
    default boolean isTxtFile(String path) {
        return path.endsWith(".txt");
    }
}
