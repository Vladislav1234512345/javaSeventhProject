package sevensPackage;

import java.util.Optional;

public interface ILibrary extends Cloneable {
    public int getHallsQty();

    public int getBooksQty();

    public double getGeneralCost();

    public IHall[] getHallsArray();

    public Optional<IHall> getHall(int index);

    public Optional<IBook> getBook(int index);

    public IBook[] getSortedBooks();

    public String getHallsNames();

    public void changeHall(int index, IHall hall);

    public void changeBook(int index, IBook book);

    public void addBook(int index, IBook book);

    public void removeBook(int index);

    public IBook getBestBook();

    public Object clone() throws CloneNotSupportedException;
}
