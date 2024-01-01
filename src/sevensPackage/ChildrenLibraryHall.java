package sevensPackage;

import java.io.Serializable;
import java.util.Optional;

public class ChildrenLibraryHall implements IHall, Serializable {
    private static final long serialVersionUID = 1L;
    public String hallName;
    protected IBook[] booksArray;

    public ChildrenLibraryHall() {
        this.hallName = "Не определено";
        this.booksArray = null;
    }

    public ChildrenLibraryHall(String hallName, int quantity) {
        booksArray = new IBook[quantity];
        this.hallName = hallName;
        for (int i = 0; i < quantity; i++) {
            booksArray[i] = new ScientificBook();
        }
    }

    public ChildrenLibraryHall(String hallName, IBook[] bookArray) {
        this.hallName = hallName;
        this.booksArray = bookArray;
    }

    public String getHallName() {
        return this.hallName;
    }

    public int getBooksQty() throws InvalidBookCountException {
        if (booksArray.length < 0) {
            throw new InvalidBookCountException();
        }
        return booksArray.length;
    }

    public String getBooksNames() {
        String namesOfBooks = "\nСписок названий всех книг в зале:\n";
        for (int i = 0; i < getBooksQty(); i++) {
            namesOfBooks += booksArray[i].getName() + '\n';
        }
        return namesOfBooks;
    }

    public double getGeneralCost()  {
        double generalCost = 0;
        for (int i = 0; i < getBooksQty(); i++) {
            generalCost += booksArray[i].getCost();
        }
        if (generalCost < 0) {
            throw new InvalidBookPriceException();
        }
        return generalCost;
    }

    public Optional<IBook> getBook(int index)  {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getBooksQty(); i++) {
            if (index == i) {
                return Optional.of(booksArray[i]);
            }
        }
        return Optional.empty();
    }

    public IBook[] getBooksArray() {
        return this.booksArray;
    }

    public void changeBook(int index, IBook book)  {
        if (index >= getBooksQty() || index < 0) {
            throw new BookIndexOutOfBoundsException(index);
        }
        removeBook(index);
        addBook(index, book);
    }

    public void removeBook(int index)  {
        if (index >= getBooksQty() || index < 0) {
            throw new BookIndexOutOfBoundsException(index);
        }
        IBook[] tempBooksArray = new IBook[booksArray.length - 1];
        int n = 0;
        for (int i = 0; i < getBooksQty(); i++) {
            if (index != i) {
                tempBooksArray[n] = booksArray[i];
                n++;
            }
        }
        booksArray = tempBooksArray;
    }

    public void addBook(int index, IBook book)  {
        if (index > getBooksQty() || index < 0) {
            throw new BookIndexOutOfBoundsException(index);
        }
        IBook[] newBooksArray = new IBook[booksArray.length + 1];
        if (index == booksArray.length) {
            for (int i = 0; i < getBooksQty(); i++) {
                newBooksArray[i] = booksArray[i];
            }
            newBooksArray[booksArray.length] = book;
            booksArray = newBooksArray;
        } else {
            for (int i = 0, j = 0; i < getBooksQty() + 1; i++) {
                if (index == i) {
                    newBooksArray[i] = book;
                } else {
                    newBooksArray[i] = booksArray[j];
                    j++;
                }
            }
            booksArray = newBooksArray;
        }
    }

    public IBook getBestBook() {
        double max = booksArray[0].getCost();
        IBook bestBook = booksArray[0];
        for (int i = 0; i < getBooksQty(); i++) {
            if (max < booksArray[i].getCost()) {
                max = booksArray[i].getCost();
                bestBook = booksArray[i];
            }
        }
        return bestBook;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        ChildrenLibraryHall hall = (ChildrenLibraryHall) object;
        if (this.getBooksQty() != hall.getBooksQty()) return false;
        for (int i = 0; i < this.getBooksQty(); ++i) {
            if (!this.getBook(i).equals(hall.getBook(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + getBooksQty();
        for (int i = 0; i < getBooksQty(); ++i) {
            hash = hash * 31 + (booksArray[i] != null ? booksArray[i].hashCode() : 0);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChildrenLibraryHall clonedHall = (ChildrenLibraryHall) super.clone();
        for (int i = 0; i < getBooksQty(); ++i) {
            clonedHall.changeBook(i, (IBook) this.getBook(i).get().clone());
        }
        return clonedHall;
    }

    @Override
    public String toString() {
        String result = "\nТип зала: " + getHallName()
                + "\nКоличество книг: " + getBooksQty();
        for (int i = 0; i < getBooksQty(); ++i) {
            result += '\n' + this.booksArray[i].toString();
        }
        return result;
    }
}
