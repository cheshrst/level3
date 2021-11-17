package ru.gb.lesson1;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit> {
    private List<T> list;
    public T create() {
        return null;
    }
    public Box() {
        list = new ArrayList<T>();
    }
    public Box merge(Box<? extends Fruit> source) {
        return new Box();
    }

    public void addFruit(T obj){
        list.add(obj);
    }
    public void info(){
        System.out.println(list.size());
    }
    double getWeight(){
        return list.size() * list.get(0).getWeight();
    }

    boolean compare(Box<? extends Fruit> box) {
        return this.getWeight() == box.getWeight();
    }

    void moveAt(Box<T> box) {
        box.list.addAll(list);
        list.clear();
    }

}
