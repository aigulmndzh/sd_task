import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Reader {
    private final Scanner in = new Scanner(System.in);
    private final ArrayList<Path> txtFiles = new ArrayList<>();
    private final Queue<Path> directories = new LinkedList<>();

    public Reader() {
        getTxtFiles();
    }

    private void addFiles(Path path) {
        if (Files.isDirectory(path)) {
            directories.add(path);
        } else if (Files.isRegularFile(path) && isTxt(path)) {
            txtFiles.add(path);
        }
    }

    private void getTxtFiles() {
        Path root = Path.of(getDirectory());
        directories.add(root);

        while (!directories.isEmpty()) {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(directories.peek())) {
                for (Path path : files) {
                    addFiles(path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            directories.remove();
        }
    }

    private String getDirectory() {
        while (true) {
            System.out.print("Путь: ");
            String line = in.nextLine();
            if (Files.isDirectory(Path.of(line))) {
                return line;
            } else {
                System.out.println("Директории по данному пути нет. Введите путь снова.");
            }
        }
    }

    private boolean isTxt(Path path) {
        String extension = "txt";
        String name = path.getFileName().toString();
        int i = name.lastIndexOf('.');
        if (i > 0) {
            name = name.substring(i + 1);
            return name.equals(extension);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var item : txtFiles) {
            result.append(item.getFileName()).append("\n");
        }
        return result.toString();
    }
}
