import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Проверка файлов на цикличность
 */
public interface CheckFiles {
    /**
     * Проверка на наличие файлов по заданному пути
     *
     * @param paths список путей текстовых файлов
     * @return наличие файлов по заданному пути
     */
    default boolean checkPathsIsEmpty(List<Path> paths) {
        if (paths.isEmpty()) {
            System.out.println("Файлов нет");
            return true;
        }
        return false;
    }

    /**
     * проверка на наличие зависимостей между файлами в общем
     *
     * @param txtFileNumbers хранятся пары (порядковый номер пути 1, порядковый номер пути 2) у которых есть зависимость
     * @return наличие зависимостей между файлами в общем
     */
    default boolean checkTxtFilesNumberIsEmpty(HashMap<Integer, Integer> txtFileNumbers) {
        if (txtFileNumbers.isEmpty()) {
            System.out.println("Зависимостей между файлами нет");
            return true;
        }
        return false;
    }

    /**
     * проверка на цикличность одного файла
     *
     * @param pair  пара путей
     * @param paths список путей текстовых файлов
     * @return является ли цикличностью
     */
    default boolean checkIsSingleCycle(Map.Entry<Integer, Integer> pair, List<Path> paths) {
        if (Objects.equals(pair.getKey(), pair.getValue())) {
            System.out.println("Цикл с одним файлом: " + paths.get(pair.getKey()).toString());
            return true;
        }
        return false;
    }

    /**
     * проверка корректности написания require
     *
     * @param pair  пара путей
     * @param paths список путей текстовых файлов
     * @return корректность написания require
     */
    default boolean checkIsCorrectPhrase(Map.Entry<Integer, Integer> pair, List<Path> paths) {
        if (pair.getValue() == -1 || pair.getKey() == -1) {
            System.out.println("Некорректный require в файле: " + paths.get(pair.getKey()).toString());
            return false;
        }
        return true;
    }

    /**
     * проверка цикличности двух пар путей
     *
     * @param pair       пара путей
     * @param secondPair другая пара путей
     * @param paths      список путей текстовых файлов
     * @return наличие цикличности
     */
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
