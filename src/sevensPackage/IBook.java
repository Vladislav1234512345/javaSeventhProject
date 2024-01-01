package sevensPackage;

public interface IBook extends Cloneable{
    public String getAuthor();

    public String getName();

    public double getCost();

    public int getYear();

    public void setAuthor(String author);

    public void setName(String name);

    public void setCost(double cost);

    public void setYear(int year);

    public Object clone() throws CloneNotSupportedException;
}
