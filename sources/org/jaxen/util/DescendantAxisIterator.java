package org.jaxen.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jaxen.JaxenRuntimeException;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;

public class DescendantAxisIterator implements Iterator {
    private Iterator children;
    private Navigator navigator;
    private ArrayList stack;

    public DescendantAxisIterator(Object contextNode, Navigator navigator2) throws UnsupportedAxisException {
        this(navigator2, navigator2.getChildAxisIterator(contextNode));
    }

    public DescendantAxisIterator(Navigator navigator2, Iterator iterator) {
        this.stack = new ArrayList();
        this.navigator = navigator2;
        this.children = iterator;
    }

    public boolean hasNext() {
        while (!this.children.hasNext()) {
            if (this.stack.isEmpty()) {
                return false;
            }
            this.children = (Iterator) this.stack.remove(this.stack.size() - 1);
        }
        return true;
    }

    public Object next() {
        try {
            if (hasNext()) {
                Object node = this.children.next();
                this.stack.add(this.children);
                this.children = this.navigator.getChildAxisIterator(node);
                return node;
            }
            throw new NoSuchElementException();
        } catch (UnsupportedAxisException e) {
            throw new JaxenRuntimeException((Throwable) e);
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
