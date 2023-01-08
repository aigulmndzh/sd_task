public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        FilesWork graph = new FilesWork(reader.txtFiles, reader.root);
        graph.getResult();
    }
}