package org.jwork.ahdb.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class U {

    public static String fixedLenStr(String string, int fixedLen) {
        return fixedLenStr(string, fixedLen, false);
    }

    public static String fixedLenStr(String string, int fixedLen, boolean leftPad) {
        String pad;
        if (leftPad) {
            pad = "%1$-";
        } else {
            pad = "%1$";
        }

//        System.out.println(string +" "+ length(string) +" "+ fixedLen);

        int padLen;
        int cnStrAmend = length(string) - string.length();
        if (cnStrAmend > 0) {
            // string contains CN
            padLen = string.length() + (fixedLen - length(string)) + 1;
            System.out.println(padLen);
        } else {
            // string pure EN
            padLen = fixedLen;
        }

        return String.format(pad + padLen + "s", string);
    }

    public static int length(Object o) {
        if (o == null) {
            return 4; // length of literal null
        }
        if (o instanceof Number) {
            return o.toString().length();
        }
        if (o instanceof String) {
            String str = (String) o;
            int valueLength = 0;
            String chinese = "[\u0391-\uFFE5]";
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    valueLength += 2;
                } else {
                    valueLength += 1;
                }
            }
            return valueLength;
        }
        return ((String) o).length();
    }

    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size())
                .boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> map(K key, V vaule) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, vaule);
        return map;
    }

    public static <E> List<E> list() {
        return new ArrayList<E>();
    }

    public static <E> List<E> list(E e) {
        return Collections.singletonList(e);
    }

    public static <E> List<E> list(E... elements) {
        List<E> list = list();
        Collections.addAll(list, elements);
        return list;
    }

    public static boolean and(boolean left, boolean right) {
        return left && right;
    }

    public static boolean and(boolean... conds) {
        for (boolean cond : conds) {
            if (!cond) {
                return false;
            }
        }
        return true;
    }

    public static boolean or(boolean left, boolean right) {
        return left || right;
    }

    public static boolean or(boolean... conds) {
        for (boolean cond : conds) {
            if (cond) {
                return true;
            }
        }
        return false;
    }

    public static boolean match(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static void printl(Object o) {
        System.out.println(o);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static String readAll(Path path) throws IOException {
        return Files.readString(path);
    }

    public static void main(String[] args) {
        System.out.println(fixedLenStr("asd", 5));
        System.out.println(fixedLenStr("asd", 5, true));
    }

}
