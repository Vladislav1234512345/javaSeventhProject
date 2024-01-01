package sevensPackage;

import java.io.*;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ScientificBook book1 = new ScientificBook("Matvei Zroichenko", "Science and sport", 622, 2022, 8.2);
        ScientificBook book2 = new ScientificBook("Artyom Gritskov", "Videogames in the 21-st century", 653, 2023, 9.1);
        ScientificBook book3 = new ScientificBook("Kamil Ashirov", "I am the master", 462, 2008, 6.7);
        ScientificLibraryHall hall1 = new ScientificLibraryHall("Science", new ScientificBook[]{book1, book2, book3});
        ChildrenLibraryHall hall2 = new ChildrenLibraryHall("Tails", 2);
        ScientificLibrary library1 = new ScientificLibrary(new ScientificLibraryHall[]{hall1, hall1});
        library1.changeHall(library1.getHallsQty() - 1, hall2);
        System.out.println("\nБиблиотека №1:\n" + library1.toString());
        FileOutputStream outputStream = new FileOutputStream("serialize.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(library1);
        outputStream.close();
        objectOutputStream.close();
        FileInputStream inputStream = new FileInputStream("serialize.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ILibrary library2 = (ILibrary) objectInputStream.readObject();
        inputStream.close();
        objectInputStream.close();
        System.out.println("\nБиблиотека №2:\n" + library2.toString());

    }
}