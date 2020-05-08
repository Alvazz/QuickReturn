package org.jaxen.pattern;

import org.jaxen.Context;

public class TextNodeTest extends NodeTest {
    public static final TextNodeTest SINGLETON = new TextNodeTest();

    public boolean matches(Object node, Context context) {
        return context.getNavigator().isText(node);
    }

    public double getPriority() {
        return -0.5d;
    }

    public short getMatchType() {
        return 3;
    }

    public String getText() {
        return "text()";
    }
}
