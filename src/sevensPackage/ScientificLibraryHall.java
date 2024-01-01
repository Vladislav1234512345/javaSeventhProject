package sevensPackage;

import java.io.Serializable;
import java.util.Optional;

public class ScientificLibraryHall implements IHall, Serializable {
    private static final long serialVersionUID = 1L;
    protected LinkedList list;
    protected String hallName;

    public ScientificLibraryHall(String hallName, int booksQty) {
        this.hallName = hallName;
        this.list = new LinkedList();
        for (int i = 0; i < booksQty; ++i) {
            this.list.addLast(new Item());
        }
    }

    public ScientificLibraryHall(String hallName, IBook[] books) {
        this.hallName = hallName;
        this.list = new LinkedList();
        for (int i = 0; i < books.length; ++i) {
            list.addLast(new Item(books[i]));
        }
    }

    public String getHallName() {
        return this.hallName;
    }

    public int getBooksQty() {
        if (list.length() < 0) {
            throw new InvalidBookCountException();
        }
        return list.length();
    }

    public String getBooksNames() {
        return "\nСписок названий всех книг в зале:\n" + list.toString();
    }

    public double getGeneralCost()  {
        double generalCost = 0;
        for (int i = 0; i < list.length(); ++i) {
            generalCost += getBook(i).get().getCost();
        }
        if (generalCost < 0) {
            throw new InvalidBookPriceException();
        }
        return generalCost;
    }

    public Optional<IBook> getBook(int index) {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        return Optional.of(list.getItem(index).get().data);
    }

    public IBook[] getBooksArray() {
        IBook[] booksArray = new IBook[getBooksQty()];
        for (int i = 0; i < getBooksQty(); ++i) {
            booksArray[i] = list.getItem(i).get().data;
        }
        return booksArray;
    }

    public void changeBook(int index, IBook book) {
        if (!list.removeItem(index)) {
            throw new BookIndexOutOfBoundsException(index);
        }
        list.removeItem(index);
        list.addItem(index, new Item(book));
    }

    public void addBook(int index, IBook book)  {
        if (!list.addItem(index, new Item(book))) {
            throw new BookIndexOutOfBoundsException(index);
        }
    }

    public void removeBook(int index)  {
        if (!list.removeItem(index)) {
            throw new BookIndexOutOfBoundsException(index);
        }
    }

    public IBook getBestBook() {
        if (list.isEmpty()) return null;
        IBook bestBook = getBook(0).get();
        for (int i = 0; i < list.length(); ++i) {
            if (bestBook.getCost() < getBook(i).get().getCost()) bestBook = getBook(i).get();
        }
        return bestBook;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        ScientificLibraryHall hall = (ScientificLibraryHall) object;
        if (this.getBooksQty() != hall.getBooksQty()) return false;
        for (int i = 0; i < this.getBooksQty(); ++i) {
            if (this.getBook(i).equals(hall.getBook(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + getBooksQty();
        IBook[] books = getBooksArray();
        for (int i = 0; i < getBooksQty(); ++i) {
            hash = hash * 31 + (books[i] != null ? books[i].hashCode() : 0);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ScientificLibraryHall clonedHall = (ScientificLibraryHall) super.clone();
        IBook book = (IBook) list.getItem(0).get().data.clone();
        for (int i = 0; i < getBooksQty(); ++i) {
            clonedHall.changeBook(i, (IBook) this.getBook(i).get().clone());
        }
        return clonedHall;
    }

    @Override
    public String toString() {
        return "\nТип зала: " + getHallName()
                + "\nКоличество книг: " + getBooksQty()
                + this.list.toString();
    }
}
