package com.gitee.taven.validate;

import javax.validation.Valid;
import java.util.*;

public class ValidateList<T> implements List<T> {

    @Valid
    private List<T> list = new ArrayList<>();

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public Iterator iterator() {
        return this.list.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return this.list.toArray();
    }

    @Override
    public boolean add(T o) {
        return this.list.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return this.list.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return this.list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return this.list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return this.list.retainAll(c);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return this.list.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }
}
