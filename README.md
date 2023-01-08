# Консольное приложение по сортировке файлов
## ИДЗ по Конструированию программного обеспечения
Манджиева Айгуль БПИ216
### Условие задания
>Имеется корневая папка. В этой папке могут находиться текстовые файлы, а также другие папки. В других папках также могут находиться текстовые файлы и >папки (уровень вложенности может оказаться любым).
>
>В каждом файле может быть ни одной, одна или несколько директив формата: 
>require ‘<путь к другому файлу от корневого каталога>’
>
>Директива означает, что текущий файл зависит от другого указанного файла.
>
>Необходимо выявить все зависимости между файлами, построить сортированный список, для которого выполняется условие: если файл А, зависит от файла В, то >файл А находится ниже файла В в списке.
>
>Осуществить конкатенацию файлов в соответствии со списком. Если такой список построить невозможно (существует циклическая зависимость), программа должна >вывести соответствующее сообщение.
