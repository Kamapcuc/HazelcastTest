package ru.combo_breaker;


import java.io.Serializable;

public class IntObj implements Serializable, Comparable {

    private int i;

    @Override
    public int hashCode() {
        return i;
    }

    @Override
    public int compareTo(Object o) {
        return ((IntObj)o).getI() - this.getI();
    }

    public IntObj(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }

    @Override
    public boolean equals(Object obj) {
        return ((IntObj)obj).getI() == i;
    }
}
