package wzd.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class UtilValidate {

    /** Check whether string s is empty. */
    public static boolean isEmpty(String s) {
        return ((s == null) || (s.length() == 0));
    }

    /** Check whether collection c is empty. */
    public static <E> boolean isEmpty(Collection<E> c) {
        return ((c == null) || (c.size() == 0));
    }

    /** Check whether map m is empty. */
    public static <K,E> boolean isEmpty(Map<K,E> m) {
        return ((m == null) || (m.size() == 0));
    }

    /** Check whether charsequence c is empty. */
    public static <E> boolean isEmpty(CharSequence c) {
        return ((c == null) || (c.length() == 0));
    }

    /** Check whether string s is NOT empty. */
    public static boolean isNotEmpty(String s) {
        return ((s != null) && (s.length() > 0));
    }

    /** Check whether collection c is NOT empty. */
    public static <E> boolean isNotEmpty(Collection<E> c) {
        return ((c != null) && (c.size() > 0));
    }

    /** Check whether charsequence c is NOT empty. */
    public static <E> boolean isNotEmpty(CharSequence c) {
        return ((c != null) && (c.length() > 0));
    }

    public static boolean isString(Object obj) {
        return ((obj != null) && (obj instanceof java.lang.String));
    }
    
    public static <T> boolean isNotEmpty(List<T> list){
    	return ((list != null)&&(list.size()>0));
    }
}
