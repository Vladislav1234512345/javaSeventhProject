package sevensPackage;

import java.util.Optional;
import java.io.Serializable;

public class ChildrenLibrary implements ILibrary, Serializable {
    private static final long serialVersionUID = 1L;
    private IHall[] hallsArray;

    public ChildrenLibrary(int hallQty, int[] bookHallsQty) {
        if (bookHallsQty.length != hallQty) hallQty = bookHallsQty.length;
        this.hallsArray = new IHall[hallQty];
        for (int i = 0; i < hallQty; i++) {
            hallsArray[i] = new ChildrenLibraryHall("Не определено", bookHallsQty[i]);
        }
    }

    public ChildrenLibrary(IHall[] hallsArray) {
        this.hallsArray = hallsArray;
    }

    public int getHallsQty() {
        if (this.hallsArray.length < 0) {
            throw new InvalidBookCountException();
        }
        return hallsArray.length;
    }

    public int getBooksQty()  {
        int booksQty = 0;
        for (int i = 0; i < hallsArray.length; i++) {
            booksQty += hallsArray[i].getBooksQty();
        }
        if (booksQty < 0) {
            throw new InvalidBookCountException();
        }
        return booksQty;
    }

    public double getGeneralCost()  {
        double generalCost = 0;
        for (int i = 0; i < getHallsQty(); ++i) {
            generalCost += hallsArray[i].getGeneralCost();
        }
        if (generalCost < 0) {
            throw new InvalidBookPriceException();
        }
        return generalCost;
    }

    public IHall[] getHallsArray() {
        return hallsArray;
    }

    public Optional<IHall> getHall(int index)  {
        if (index < 0 || index >= getHallsQty()) {
            throw new HallIndexOutOfBoundsException(index);
        }
        return Optional.of(hallsArray[index]);
    }

    public Optional<IBook> getBook(int index)  {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < hallsArray.length; i++) {
            for (int j = 0; j < hallsArray[i].getBooksQty(); j++) {
                if (index == 0) return hallsArray[i].getBook(j);
                index--;
            }
        }
        return Optional.empty();
    }

    public IBook[] getSortedBooks() {
        IBook[] booksArray = new IBook[getBooksQty()];
        int n = -1;
        for (int i = 0; i < getBooksQty(); i++) {
            for (int j = 0; j < hallsArray[i].getBooksQty(); j++) {
                addBook(n, getBook(n++).get());
            }
        }
        boolean isSort;
        do {
            isSort = true;
            for (int i = 0; i < getBooksQty() - 1; i++) {
                if (booksArray[i].getCost() > booksArray[i + 1].getCost()) {
                    isSort = false;
                    IBook tmpBook = booksArray[i];
                    booksArray[i] = booksArray[i + 1];
                    booksArray[i + 1] = tmpBook;
                }
            }
        }
        while (isSort == false);
        return booksArray;
    }

    public String getHallsNames() {
        String namesAndBooksQty = "";
        for (int i = 0; i < hallsArray.length; i++) {
            namesAndBooksQty += "\nЗал №" + i + ": " + '"' + hallsArray[i].getHallName() + '"' + ", количество книг -  " + hallsArray[i].getBooksQty();
        }
        return namesAndBooksQty;
    }

    public void changeHall(int index, IHall hall)  {
        if (index < 0 || index >= getHallsQty()) {
            throw new HallIndexOutOfBoundsException(index);
        }
        hallsArray[index] = hall;
    }

    public void changeBook(int index, IBook book)  {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        removeBook(index);
        addBook(index, book);
    }

    public void addBook(int index, IBook book)  {
        if (index < 0 || index > getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getHallsQty(); i++) {
            if (index > hallsArray[i].getBooksQty()) {
                index -= hallsArray[i].getBooksQty();
            } else {
                hallsArray[i].addBook(index, book);
                return;
            }
        }
    }

    public void removeBook(int index)  {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getHallsQty(); ++i) {
            if (index < hallsArray[i].getBooksQty()) {
                hallsArray[i].removeBook(index);
                return;
            }
            index -= hallsArray[i].getBooksQty();
        }
    }

    public IBook getBestBook() {
        IBook book = hallsArray[0].getBestBook();
        for (int i = 0; i < getHallsQty(); i++) {
            if (book.getCost() < hallsArray[i].getBestBook().getCost()) {
                book = hallsArray[i].getBestBook();
            }
        }
        return book;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        ChildrenLibrary library = (ChildrenLibrary) object;
        if (this.getHallsQty() != library.getHallsQty()) return false;
        for (int i = 0; i < this.getHallsQty(); ++i) {
            if (!this.getHall(i).equals(library.getHall(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + getHallsQty();
        for (int i = 0; i < getHallsQty(); ++i) {
            hash = hash * 31 + (hallsArray[i] != null ? hallsArray[i].hashCode() : 0);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChildrenLibrary clonedLibrary = (ChildrenLibrary) super.clone();
        for (int i = 0; i < getHallsQty(); ++i) {
            clonedLibrary.changeHall(i, (IHall) this.getHall(i).get().clone());
        }
        return clonedLibrary;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nКоличество залов:" + getHallsQty() + '\n');
        for (int i = 0; i < getHallsQty(); ++i) {
            result.append("\nЗал №" + i + ":" + getHall(i).get().toString() + '\n');
        }
        return result.toString();
    }
}
