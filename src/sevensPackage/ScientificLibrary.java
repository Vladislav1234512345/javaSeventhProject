package sevensPackage;

import java.io.Serializable;
import java.util.Optional;

public class ScientificLibrary implements ILibrary, Serializable {
    private static final long serialVersionUID = 1L;
    private DoubleLinkedList doubleList;

    public ScientificLibrary(int hallsQty, int[] books) {
        this.doubleList = new DoubleLinkedList();
        for (int i = 0; i < hallsQty; ++i) {
            doubleList.addLast(new DoubleItem(new ScientificLibraryHall("Не определено", books[i])));
        }
    }

    public ScientificLibrary(IHall[] halls) {
        this.doubleList = new DoubleLinkedList();
        for (int i = 0; i < halls.length; ++i) {
            this.doubleList.addLast(new DoubleItem(halls[i]));
        }
    }

    public int getHallsQty()  {
        if (this.doubleList.length() < 0) {
            throw new InvalidBookCountException();
        }
        return this.doubleList.length();
    }

    public int getBooksQty()  {
        int booksQty = 0;
        for (int i = 0; i < getHallsQty(); ++i) {
            booksQty += getHall(i).get().getBooksQty();
        }
        if (booksQty < 0) {
            throw new InvalidBookCountException();
        }
        return booksQty;
    }

    public double getGeneralCost()  {
        double generalCost = 0;
        for (int i = 0; i < getHallsQty(); ++i) {
            generalCost += getHall(i).get().getGeneralCost();
        }
        if (generalCost < 0) {
            throw new InvalidBookPriceException();
        }
        return generalCost;
    }

    public IHall[] getHallsArray() {
        IHall[] hallsArray = new IHall[getHallsQty()];
        for (int i = 0; i < getHallsQty(); ++i) {
            hallsArray[i] = getHall(i).get();
        }
        return hallsArray;
    }

    public Optional<IHall> getHall(int index) {
        if (index < 0 || index >= getHallsQty()) {
            throw new HallIndexOutOfBoundsException(index);
        }
        return Optional.of(doubleList.getDoubleItem(index).get().data);
    }

    public Optional<IBook> getBook(int index) {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getHallsQty(); ++i) {
            if (index < doubleList.getDoubleItem(i).get().data.getBooksQty())
                return doubleList.getDoubleItem(i).get().data.getBook(index);
            index -= doubleList.getDoubleItem(i).get().data.getBooksQty();
        }
        return Optional.empty();
    }

    public IBook[] getSortedBooks() {
        IBook[] booksArray = new IBook[getBooksQty()];
        for (int i = 0; i < booksArray.length; ++i) {
            booksArray[i] = getBook(i).get();
        }
        for (int i = 0; i < booksArray.length; ++i) {
            boolean isSorted = false;
            for (int j = 0; j < booksArray.length - 1; ++j) {
                if (booksArray[j].getCost() < booksArray[j + 1].getCost()) {
                    isSorted = true;
                    IBook tmp = booksArray[j];
                    booksArray[j] = booksArray[j + 1];
                    booksArray[j + 1] = tmp;
                }
            }
            if (!isSorted) break;
        }
        return booksArray;
    }

    public String getHallsNames() {
        String result = "";
        for (int i = 0; i < getHallsQty(); ++i) {
            result += "\n\nЗал №" + i + ":\nИмя зала - "
                    + getHall(i).get().getHallName() + ", количество книг - "
                    + getHall(i).get().getBooksQty();
        }
        return result;
    }

    public void changeHall(int index, IHall hall)  {
        if (index < 0 || index >= getHallsQty()) {
            throw new HallIndexOutOfBoundsException(index);
        }
        this.doubleList.removeDoubleItem(index);
        this.doubleList.addDoubleItem(index, new DoubleItem(hall));
    }

    public void changeBook(int index, IBook book)  {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        removeBook(index);
        addBook(index, book);
    }

    public void addBook(int index, IBook book) {
        if (index < 0 || index > getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getHallsQty(); ++i) {
            if (index <= getHall(i).get().getBooksQty()) {
                getHall(i).get().addBook(index, book);
                return;
            }
            index -= getHall(i).get().getBooksQty();
        }
    }

    public void removeBook(int index) {
        if (index < 0 || index >= getBooksQty()) {
            throw new BookIndexOutOfBoundsException(index);
        }
        for (int i = 0; i < getHallsQty(); ++i) {
            if (index < getHall(i).get().getBooksQty()) {
                getHall(i).get().removeBook(index);
                break;
            }
            index -= getHall(i).get().getBooksQty();
        }
    }

    public IBook getBestBook() {
        if (doubleList.isEmpty()) return null;
        IBook bestBook = doubleList.getDoubleItem(0).get().data.getBestBook();
        for (int i = 0; i < getHallsQty(); ++i) {
            if (bestBook.getCost() < doubleList.getDoubleItem(i).get().data.getBestBook().getCost())
                bestBook = doubleList.getDoubleItem(i).get().data.getBestBook();
        }
        return bestBook;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        ScientificLibrary library = (ScientificLibrary) object;
        if (this.getHallsQty() != library.getHallsQty()) return false;
        for (int i = 0; i < this.getHallsQty(); i++) {
            if (!this.getHall(i).equals(library.getHall(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + getHallsQty();
        IHall[] halls = getHallsArray();
        for (int i = 0; i < getHallsQty(); ++i) {
            hash = hash * 31 + (halls[i] != null ? halls[i].hashCode() : 0);
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ScientificLibrary clonedLibrary = (ScientificLibrary) super.clone();
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
