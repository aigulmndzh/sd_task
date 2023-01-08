import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface CheckFiles {
    default boolean checkPathsIsEmpty(List<Path> paths) {
        if (paths.isEmpty()) {
            System.out.println("Файлов нет");
            return true;
        }
        return false;
    }

    default boolean checkTxtFilesNumberIsEmpty(HashMap<Integer, Integer> txtFileNumbers) {
        if (txtFileNumbers.isEmpty()) {
            System.out.println("Зависимостей между файлами нет");
            return true;
        }
        return false;
    }

    default boolean checkIsSingleCycle(Map.Entry<Integer, Integer> pair, List<Path> paths) {
        if (Objects.equals(pair.getKey(), pair.getValue())) {
            System.out.println("Цикл с одним файлом: " + paths.get(pair.getKey()).toString());
            return true;
        }
        return false;
    }

    default boolean checkIsCorrectPhrase(Map.Entry<Integer, Integer> pair, List<Path> paths) {
        if (pair.getValue() == -1 || pair.getKey() == -1) {
            System.out.println("Некорректный require в файле: " + paths.get(pair.getKey()).toString());
            return false;
        }
        return true;
    }

    default boolean checkIsCycle(Map.Entry<Integer, Integer> pair, Map.Entry<Integer, Integer> secondPair, List<Path> paths) {
        if (Objects.equals(pair.getKey(), secondPair.getValue()) && Objects.equals(pair.getValue(), secondPair.getKey())) {
            System.out.println("Циклическая зависимость у файлов: ");
            System.out.println(paths.get(pair.getKey()).toString());
            System.out.println(paths.get(pair.getValue()).toString());
            return true;
        }
        return false;
    }
}
