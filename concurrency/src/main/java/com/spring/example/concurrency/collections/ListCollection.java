package com.spring.example.concurrency.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListCollection {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        //Init list in one thread
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        new Thread(() -> {
            for (String entry : list) {
/*                Console console = System.console();
                console.writer().println();*/
                //TODO:: Элементы теже но информация о листе изменяется
                System.out.println("List: " + list);
                System.out.println("List entry: " + entry);
                System.out.println("List size: " + list.size());
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                if (i % 2 == 0)
                    list.remove(i);
                else
                    list.set(i, String.valueOf(i + 500));
            }
        }).start();
    }

    public static void getConcurrentModificationException(){
        List<String> list = new ArrayList<>();
        //Init list in one thread
        for (int i = 0; i < 200; i++) {
            list.add(String.valueOf(i));
        }
        Iterator<String> it = list.iterator();

        //manipulate list while iterating
        while(it.hasNext()){
            String str = it.next();
            System.out.println("Element Number: " + str);
            if(str.equals("2"))list.remove("5"); //throw ConcurrentModificationException
            if(str.equals("3"))list.add("3 found");
            //below code don't throw ConcurrentModificationException
            //because it doesn't change modCount variable of list
            if(str.equals("4")) list.set(1, "4");
        }
    }
}
