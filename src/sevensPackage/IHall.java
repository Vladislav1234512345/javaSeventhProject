package sevensPackage;

import java.util.Optional;

public interface IHall extends Cloneable {
    public int getBooksQty();

    public String getBooksNames();

    public double getGeneralCost();

    public Optional<IBook> getBook(int index);

    public IBook[] getBooksArray();

    public void changeBook(int index, IBook book);

    public void addBook(int index, IBook book);

    public void removeBook(int index);

    public IBook getBestBook();

    public String getHallName();

    public Object clone() throws CloneNotSupportedException;
}
