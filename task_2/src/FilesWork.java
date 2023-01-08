import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FilesWork implements CheckFiles {
    private final Graph graph = new Graph();
    private final List<Path> paths;
    private final HashMap<Integer, Integer> txtFileNumbers = new HashMap<>();
    private final Path root;

    public FilesWork(List<Path> paths, Path root) {
        this.root = root;
        this.paths = new ArrayList<>(paths);
        graph.setV(paths.size());
    }

    public void getResult() {
        buildGraph();
        if (checkCycle()) {
            Stack<Integer> stack = graph.topologicalSort();
            graph.printListFiles(paths);
            createFinalFile(stack);
        }
    }

    private void createFinalFile(Stack<Integer> stack) {
        Path currentPath;
        List<String> list;
        try (FileWriter writer = new FileWriter("result.txt", false)) {
            while (!stack.empty()) {
                currentPath = paths.get(stack.pop());
                list = Files.readAllLines(currentPath, StandardCharsets.UTF_8);
                for (String str : list) {
                    writer.write(str + "\n");
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    private boolean checkCycle() {
        if (checkPathsIsEmpty(paths) || checkTxtFilesNumberIsEmpty(txtFileNumbers)) {
            return false;
        }

        for (var pair : txtFileNumbers.entrySet()) {
            if (checkIsSingleCycle(pair, paths) || !checkIsCorrectPhrase(pair, paths)) {
                return false;
            }

            for (var secondPair : txtFileNumbers.entrySet()) {
                if (checkIsCycle(pair, secondPair, paths)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int findPathNumber(Path path) {
        for (var item : paths) {
            if (item.equals(path)) {
                return paths.indexOf(item);
            }
        }
        return -1;
    }

    private Path createPath(String str) {
        if (!str.endsWith(".txt")) {
            str += ".txt";
        }
        Path path = Path.of(root.toString() + "/" + str);
        if (Files.exists(path)) {
            return path;
        } else {
            System.out.println("Такого пути не существует: " + path);
            return null;
        }
    }

    private void createEdge(Path path, String string) {
        Path line;
        if (string.startsWith("require ")) {
            line = createPath(string.substring(9, string.length() - 1));

            int vertex = findPathNumber(line);
            txtFileNumbers.put(paths.indexOf(path), vertex);
            if (vertex > 0) {
                graph.addEdge(paths.indexOf(path), vertex);
            }
        }
    }

    private void buildGraph() {
        List<String> list;
        try {
            if (!paths.isEmpty()) {
                for (var path : paths) {
                    list = Files.readAllLines(path, StandardCharsets.UTF_8);
                    for (String str : list) {
                        createEdge(path, str);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка");
        }
    }
}
