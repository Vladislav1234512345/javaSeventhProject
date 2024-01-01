package sevensPackage;

import java.io.Serializable;
import java.util.Optional;

public class DoubleLinkedList implements Serializable {
    private static final long serialVersionUID = 1L;

    protected DoubleItem head;
    protected int size;

    public DoubleLinkedList() {
        this.head = null;
    }

    public DoubleLinkedList(DoubleItem[] data) {
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

    public Optional<DoubleItem> getDoubleItem(int index) {
        if (index >= this.length()) return Optional.empty();
        if (index < 0) return Optional.empty();
        DoubleItem tmp = this.head;
        for (int i = 0; i < index; ++i) {
            tmp = tmp.next;
        }
        return Optional.of(tmp);
    }

    public void addLast(DoubleItem addItem) {
        if (isEmpty()) {
            this.head = addItem;
            this.head.prev = this.head;
            this.head.next = this.head;
            ++size;
            return;
        }
        DoubleItem tail = this.head.prev;
        addItem.prev = tail;
        addItem.next = head;
        tail.next = addItem;
        this.head.prev = addItem;
        ++size;
    }

    private void addFirst(DoubleItem addItem) {
        DoubleItem tail = this.head.prev;
        addItem.prev = tail;
        addItem.next = this.head;
        tail.next = addItem;
        this.head.prev = addItem;
        this.head = addItem;
        ++size;
    }

    private void add(int index, DoubleItem addItem) {
        DoubleItem tmp = this.head;
        for (int i = 0; i < index - 1; ++i) {
            tmp = tmp.next;
        }
        DoubleItem newTmp = tmp.next;
        tmp.next = addItem;
        addItem.prev = tmp;
        addItem.next = newTmp;
        newTmp.prev = addItem;
        ++size;
    }

    private void removeFirst() {
        DoubleItem newHead = this.head.next;
        DoubleItem tail = this.head.prev;
        tail.next = newHead;
        newHead.prev = tail;
        this.head = newHead;
        --size;
    }

    private void removeLast() {
        DoubleItem tail = this.head.prev.prev;
        tail.next = head;
        head.prev = tail;
        --size;
    }

    private void remove(int index) {
        DoubleItem tmp = this.head;
        for (int i = 0; i < index - 1; ++i) {
            tmp = tmp.next;
        }
        DoubleItem toDelete = tmp.next;
        DoubleItem nextNode = toDelete.next;
        tmp.next = nextNode;
        nextNode.prev = tmp;
        --size;
    }

    public boolean addDoubleItem(int index, DoubleItem addItem) {
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

    public boolean removeDoubleItem(int index) {
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
        DoubleItem tmp = this.head;
        for (int i = 0; i < this.length(); ++i) {
            result += '\n' + tmp.toString();
            tmp = tmp.next;
        }
        return result;
    }

}
