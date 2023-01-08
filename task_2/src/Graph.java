import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * реализация сортировки в соответствии с условиями задания
 */
public final class Graph {
    /**
     * список номеров в соттевтствии с зависимостями файлов
     */
    private LinkedList[] adj;

    /**
     * количество вершин графа (путей текстовых файлов)
     */
    private int V;

    /**
     * упорядоченнный список номеров путей
     */
    private final Stack<Integer> stack = new Stack<>();

    /**
     * инициализация V
     *
     * @param value количество путей текстовых файлов
     */
    public void setV(int value) {
        V = value;
        initializeAdj();
    }

    /**
     * инициализация adj
     */
    private void initializeAdj() {
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * конструктор
     */
    public Graph() {
    }

    /**
     * добавление ребра (зависимости между двумя текстовыми файлами)
     *
     * @param v номер первого текстового файла
     * @param w номер второго текстового файла
     */
    protected void addEdge(int v, int w) {
        adj[v].add(w);
    }

    /**
     * Рекурсивная функция, используемая topologicalSort
     *
     * @param v       номер вершины (номер пути в списке paths)
     * @param visited список посещенных вершин (путей)
     */
    private void topologicalSortUtil(int v, boolean[] visited) {
        visited[v] = true;
        int i;

        for (var integer : adj[v]) {
            i = (int) integer;
            if (!visited[i]) {
                topologicalSortUtil(i, visited);
            }
        }
        stack.push(v);
    }

    /**
     * сортировка файлов
     * статья -> https://habr.com/ru/company/otus/blog/499138/
     *
     * @return упорядоченный список файлов
     */
    protected Stack<Integer> topologicalSort() {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i) {
            visited[i] = false;
        }
        for (int i = 0; i < V; ++i) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited);
            }
        }
        return stack;
    }

    /**
     * вывод отсортированного списка путей
     *
     * @param paths неотсортированный список файлов
     */
    protected void printListFiles(List<Path> paths) {
        System.out.println("Список зависимых файлов: ");
        Stack<Integer> copyStack = new Stack<>();
        for (var item : stack) {
            copyStack.push(item);
        }
        while (!copyStack.empty()) {
            System.out.println(paths.get(copyStack.pop()));
        }
        System.out.println("Конкатенация файлов в соответствии с их зависимостью находится в result.txt");
    }
}
