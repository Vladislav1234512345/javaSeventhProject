package sevensPackage;

import java.io.Serializable;

public class ChildrenBook extends Book implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int age;

    ChildrenBook() {
        super();
        this.age = 0;
    }

    ChildrenBook(String author, String name, double cost, int year, int age) {
        super(author, name, cost, age);
        this.age = age;
    }

    ChildrenBook(String author, int year) {
        super(author, year);
        this.age = 0;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        ChildrenBook book = (ChildrenBook) object;
        if (this.getAge() != book.getYear()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + this.getAge();
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nВозраст: " + getAge();
    }
}
