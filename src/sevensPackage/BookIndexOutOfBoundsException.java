package sevensPackage;

public class BookIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public BookIndexOutOfBoundsException(int index) {
        super("Index out of range: " + index);
    }
}
