package sevensPackage;

import java.io.Serializable;
import java.util.Optional;

public class LinkedList implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Item head;
    protected int size;

    public LinkedList() {
        this.head = null;
    }

    public LinkedList(Item[] data) {
        for (int i = 0; i < data.length; ++i) {
            this.addLast(data[i]);
        }
    }

    public boolean isEmpty() {
        return (this.head == null);
    }

    public int length() {
        return this.size;
    }

    public Optional<Item> getItem(int index) {
        if (index >= this.length()) return Optional.empty();
        if (index < 0) return Optional.empty();
        Item tmp = this.head;
        for (int i = 0; i < index; ++i) {
            tmp = tmp.next;
        }
        return Optional.of(tmp);
    }

    public void addLast(Item addItem) {
        if (isEmpty()) {
            this.head = addItem;
            this.head.next = this.head;
            ++size;
            return;
        }
        Item tmp = this.head;
        for (int i = 0; i < this.length() - 1; i++) {
            tmp = tmp.next;
        }
        tmp.next = addItem;
        ++size;
    }

    private void addFirst(Item addItem) {
        Item tmp = this.head;
        this.head = addItem;
        this.head.next = tmp;
        ++size;
    }

    private void add(int index, Item addItem) {
        Item tmp = this.head;
        for (int i = 0; i < index - 1; ++i) {
            tmp = tmp.next;
        }
        addItem.next = tmp.next;
        tmp.next = addItem;
        ++size;
    }

    private void removeFirst() {
        if (this.length() == 1) {
            this.head = null;
        } else {
            Item tmp = this.head;
            this.head = this.head.next;
            tmp = null;
        }
        --size;
    }

    private void removeLast() {
        Item tmp = this.head;
        for (int i = 0; i < this.length() - 1; ++i) {
            tmp = tmp.next;
        }
        tmp = this.head;
        --size;
    }

    private void remove(int index) {
        Item tmp = this.head;
        for (int i = 0; i < index - 1; ++i) {
            tmp = tmp.next;
        }
        Item newTmp = tmp.next;
        tmp.next = newTmp.next;
        --size;
    }

    public boolean addItem(int index, Item addItem) {
        if (index > this.length()) return false;
        if (index < 0) return false;
        if (index == this.length()) {
            this.addLast(addItem);
        } else if (index == 0) {
            this.addFirst(addItem);
        } else {
            this.add(index, addItem);
        }
        return true;
    }

    public boolean removeItem(int index) {
        if (index >= this.length()) return false;
        if (index < 0) return false;
        if (index == 0) {
            this.removeFirst();
        } else if (index == this.length() - 1) {
            this.removeLast();
        } else {
            this.remove(index);
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        Item tmp = this.head;
        for (int i = 0; i < this.length(); ++i) {
            result += '\n' + tmp.toString();
            tmp = tmp.next;
        }
        return result;
    }

}
