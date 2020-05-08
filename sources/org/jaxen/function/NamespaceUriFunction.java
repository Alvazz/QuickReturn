package org.jaxen.function;

import java.util.List;
import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;

public class NamespaceUriFunction implements Function {
    public Object call(Context context, List args) throws FunctionCallException {
        if (args.size() == 0) {
            return evaluate(context.getNodeSet(), context.getNavigator());
        }
        if (args.size() == 1) {
            return evaluate(args, context.getNavigator());
        }
        throw new FunctionCallException("namespace-uri() requires zero or one argument.");
    }

    public static String evaluate(List list, Navigator nav) throws FunctionCallException {
        if (list.isEmpty()) {
            return "";
        }
        Object first = list.get(0);
        if (first instanceof List) {
            return evaluate((List) first, nav);
        }
        if (nav.isElement(first)) {
            return nav.getElementNamespaceUri(first);
        }
        if (nav.isAttribute(first)) {
            String uri = nav.getAttributeNamespaceUri(first);
            if (uri == null) {
                return "";
            }
            return uri;
        } else if (nav.isProcessingInstruction(first)) {
            return "";
        } else {
            if (nav.isNamespace(first)) {
                return "";
            }
            if (nav.isDocument(first)) {
                return "";
            }
            if (nav.isComment(first)) {
                return "";
            }
            if (nav.isText(first)) {
                return "";
            }
            throw new FunctionCallException("The argument to the namespace-uri function must be a node-set");
        }
    }
}
