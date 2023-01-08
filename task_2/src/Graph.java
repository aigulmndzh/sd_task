import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Graph implements Check {
    private final int V;
    private final LinkedList<Integer>[] adj;
    private final ArrayList<Path> paths;
    private final HashMap<Integer, Integer> txtFileNumbers = new HashMap<>();
    private final Path root;

    public Graph(ArrayList<Path> paths, Path root) {
        this.root = root;
        this.paths = new ArrayList<>(paths);
        V = paths.size();
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    private void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public void getAnswer() {
        buildGraph();
        if (checkCycle()) {
            topologicalSort();
        }
    }

    private boolean checkCycle() {
        if (paths.isEmpty()) {
            System.out.println("Файлов нет");
            return false;
        }
        if (txtFileNumbers.isEmpty()) {
            System.out.println("Зависимостей между файлами нет");
            return false;
        }
        for (var pair : txtFileNumbers.entrySet()) {
            if (Objects.equals(pair.getKey(), pair.getValue())) {
                System.out.println("Цикл с одним файлом: " + paths.get(pair.getKey()).toString());
                return false;
            }
            if (pair.getValue() == -1 || pair.getKey() == -1) {
                System.out.println("Некорректный require в файле: " + paths.get(pair.getKey()).toString());
                return false;
            }
            for (var secondPair : txtFileNumbers.entrySet()) {
                if (Objects.equals(pair.getKey(), secondPair.getValue()) &&
                        Objects.equals(pair.getValue(), secondPair.getKey())) {
                    System.out.println("Циклическая зависимость у файлов: ");
                    System.out.println(paths.get(pair.getKey()).toString());
                    System.out.println(paths.get(pair.getValue()).toString());
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
            System.out.println("Такого пути не существует: " + path.toString());
            return null;
        }
    }

    private void checkString(Path path, String string) {
        Path line;
        if (string.startsWith("require ")) {
            line = createPath(string.substring(9, string.length() - 1));
            int vertex = findPathNumber(line);
            txtFileNumbers.put(paths.indexOf(path), vertex);
            if (vertex > 0) {
                addEdge(paths.indexOf(path), vertex);
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
                        checkString(path, str);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка");
        }
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack stack) {
        visited[v] = true;
        int i;

        for (Integer integer : adj[v]) {
            i = integer;
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(v);
    }

    private void topologicalSort() {
        Stack<Integer> stack = new Stack<>();

        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i) {
            visited[i] = false;
        }
        for (int i = 0; i < V; ++i) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        while (!stack.empty()) {
            System.out.println(paths.get(stack.pop()));
        }
    }
}
