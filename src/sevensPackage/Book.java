package sevensPackage;

import java.io.Serializable;

public class Book implements IBook, Serializable {
    private static final long serialVersionUID = 1L;
    private String author;
    private String name;
    private double cost;
    private int year;

    public Book() {
        this.author = "Не определено";
        this.name = "Не определено";
        this.cost = 0.0;
        this.year = 0;
    }

    public Book(String author, String name, double cost, int year) {
        this.author = author;
        this.name = name;
        this.cost = cost;
        this.year = year;
    }

    public Book(String author, int year) {
        this();
        this.author = author;
        this.year = year;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override

    public void setName(String name) {
        this.name = name;
    }

    @Override

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override

    public void setYear(int year) {
        this.year = year;
    }

    @Override

    public String getAuthor() {
        return author;
    }

    @Override

    public String getName() {
        return name;
    }

    @Override

    public double getCost() {
        return cost;
    }

    @Override

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        Book book = (Book) object;
        if (!(this.getAuthor().equals(book.getAuthor()))) return false;
        if (!(this.getName().equals(book.getName()))) return false;
        if (this.getCost() != book.getCost()) return false;
        if (this.getYear() != book.getYear()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 31 * hash + (this.getAuthor() != null ? this.getAuthor().hashCode() : 0);
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "\nАвтор: " + getAuthor()
                + "\nИмя: " + getName()
                + "\nГод: " + getYear();
    }
}

