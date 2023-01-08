import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Нахождение всех текстовых файлов
 */
public final class Reader {
    private final Scanner in = new Scanner(System.in);

    /**
     * путь корневой папки
     */
    public Path root;
    public final List<Path> txtFiles = new ArrayList<>();
    private final Queue<Path> directories = new LinkedList<>();

    public Reader() {
        getTxtFiles();
    }

    /**
     * Проверка на тип txt
     *
     * @param path путь файла
     * @return является ли файл txt
     */
    private boolean isTxtFile(String path) {
        return path.endsWith(".txt");
    }

    /**
     * @param path путь файла
     */
    private void addFiles(Path path) {
        if (Files.isDirectory(path)) {
            directories.add(path);
        } else if (isTxtFile(path.toString())) {
            txtFiles.add(path);
        }
    }

    /**
     *
     */
    private void getTxtFiles() {
        this.root = Path.of(getDirectory());
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

    /**
     * ввод пути дериктории с консоли
     *
     * @return путь директории
     */
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


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var item : txtFiles) {
            result.append(item.toString()).append("\n");
        }
        return result.toString();
    }
}
