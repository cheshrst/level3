package ru.gb.lesson1;


import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        1 задание
        String[] mass0 = {"123", "qew", "sdfdfs", "fasfasas"};
        Integer[] mass1 = {213, 12321323, 543534, 1221321321};
        swapMass(mass0, 2, 3);
        System.out.println(Arrays.toString(mass0));
        swapMass(mass1, 3, 0);
        System.out.println(Arrays.toString(mass1) + " \n");
//       2 задание
        System.out.println(mass0.getClass());
        List<String> list = convertToList(mass0);
        System.out.println(list.getClass() + " \n");
//        3 задание
        Box<Orange> orangeBox = new Box<>();
        Box<Orange> orangeBox2 = new Box<>();
        Box<Apple> appleBox = new Box<>();
        orangeBox.addFruit(new Orange(10));
        orangeBox2.addFruit(new Orange(10));
        appleBox.addFruit(new Apple(11));
        orangeBox.info();
        double weight = orangeBox.getWeight();
        System.out.println(weight);
        orangeBox.moveAt(orangeBox2);
        double weight2 = orangeBox2.getWeight();
        System.out.println(weight2);  // Если вывести 1 коробку, то выйдет ошибка -> в ней нет ничего, можно в боксе сделать try/catch или if/else для этого случая
        System.out.println(appleBox.compare(orangeBox2));


    }

    static <T> void swapMass(T[] array, int index1, int index2) {

        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static <E> List<E> convertToList(E[] array) {
        return Arrays.asList(array);
    }
}
