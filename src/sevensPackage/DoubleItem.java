package sevensPackage;

import java.io.Serializable;

public class DoubleItem implements Serializable {
    private static final long serialVersionUID = 1L;

    protected DoubleItem next;
    protected DoubleItem prev;
    protected IHall data;

    public DoubleItem(IHall data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public DoubleItem() {
        this.data = new ScientificLibraryHall("Не определено", 0);
        this.next = null;
        this.prev = null;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
