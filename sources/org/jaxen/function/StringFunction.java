package org.jaxen.function;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.JaxenRuntimeException;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;

public class StringFunction implements Function {
    private static DecimalFormat format = ((DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH));

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setNaN("NaN");
        symbols.setInfinity("Infinity");
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(32);
        format.setDecimalFormatSymbols(symbols);
    }

    public Object call(Context context, List args) throws FunctionCallException {
        int size = args.size();
        if (size == 0) {
            return evaluate(context.getNodeSet(), context.getNavigator());
        }
        if (size == 1) {
            return evaluate(args.get(0), context.getNavigator());
        }
        throw new FunctionCallException("string() takes at most argument.");
    }

    public static String evaluate(Object obj, Navigator nav) {
        if (nav != null) {
            try {
                if (nav.isText(obj)) {
                    return nav.getTextStringValue(obj);
                }
            } catch (UnsupportedAxisException e) {
                throw new JaxenRuntimeException((Throwable) e);
            }
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (list.isEmpty()) {
                return "";
            }
            obj = list.get(0);
        }
        if (nav != null) {
            if (nav.isElement(obj)) {
                return nav.getElementStringValue(obj);
            }
            if (nav.isAttribute(obj)) {
                return nav.getAttributeStringValue(obj);
            }
            if (nav.isDocument(obj)) {
                Iterator childAxisIterator = nav.getChildAxisIterator(obj);
                while (childAxisIterator.hasNext()) {
                    Object descendant = childAxisIterator.next();
                    if (nav.isElement(descendant)) {
                        return nav.getElementStringValue(descendant);
                    }
                }
            } else if (nav.isProcessingInstruction(obj)) {
                return nav.getProcessingInstructionData(obj);
            } else {
                if (nav.isComment(obj)) {
                    return nav.getCommentStringValue(obj);
                }
                if (nav.isText(obj)) {
                    return nav.getTextStringValue(obj);
                }
                if (nav.isNamespace(obj)) {
                    return nav.getNamespaceStringValue(obj);
                }
            }
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Boolean) {
            return stringValue(((Boolean) obj).booleanValue());
        }
        if (obj instanceof Number) {
            return stringValue(((Number) obj).doubleValue());
        }
        return "";
    }

    private static String stringValue(double value) {
        String result;
        if (value == 0.0d) {
            return "0";
        }
        synchronized (format) {
            result = format.format(value);
        }
        return result;
    }

    private static String stringValue(boolean value) {
        return value ? "true" : "false";
    }
}
