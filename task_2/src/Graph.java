import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Graph {
    private LinkedList[] adj;
    private int V;
    private final Stack<Integer> stack = new Stack<>();

    public void setV(int value) {
        V = value;
        initializeAdj();
    }

    private void initializeAdj() {
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public Graph() {}

    protected void addEdge(int v, int w) {
        adj[v].add(w);
    }

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
