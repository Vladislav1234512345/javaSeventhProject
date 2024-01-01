package sevensPackage;

import java.io.Serializable;

public class ScientificBook extends Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private double index;

    ScientificBook() {
        super();
        this.index = 0.0;
    }

    ScientificBook(String author, String name, double cost, int year, double index) {
        super(author, name, cost, year);
        this.index = index;
    }

    ScientificBook(String author, int year) {
        super(author, year);
        this.index = 0.0;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public double getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() != object.hashCode()) return false;
        if (this.getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        ScientificBook book = (ScientificBook) object;
        if (this.getIndex() != book.getIndex()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        long temp = Double.doubleToLongBits(index);
        hash = 31 * hash + (int) (temp ^ (temp >>> 32));
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nИндекс цитируемости: " + getIndex();
    }
}
