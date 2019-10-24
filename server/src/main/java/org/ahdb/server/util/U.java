package org.ahdb.server.util;

import com.google.gson.Gson;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class U {

    public static Gson gson = new Gson();

    public static boolean empty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).isEmpty();
        }
        return false;
    }

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

    public static Timestamp timestampNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }

    public static String stackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    public static String fstr(String string, Object...objects) {
        return MessageFormatter.arrayFormat(string, objects).getMessage();
    }

    public static Timer newTimer() {
        return new Timer();
    }

    public static class Timer {
        long start;
        long last;
        public Timer() {
            start = System.currentTimeMillis();
        }

        public double getTime() {
            if (last == 0) {
                last = start;
            }
            long now = System.currentTimeMillis();
            long dur = now - last;
            last = now;
            double sec = Duration.ofMillis(dur).getNano();
            return sec/100000000;
        }
    }

    public static Counter newCounter(int total) {
        return new Counter(total);
    }

    public static class Counter {
        int total;
        AtomicInteger count = new AtomicInteger();
        int progress;
        boolean isBigJob;

        public Counter(int total) {
            this.total = total;
        }

        public Counter bigJob(boolean isBigJob) {
            this.isBigJob = isBigJob;
            return this;
        }

        public void addOne() {
            count.addAndGet(1);
        }

        public Optional<String> check() {
            double p = (count.doubleValue() / total);
            int intp = (int) (p * 100);
            if (intp % 10 == 0 && intp > progress) {
                progress = intp;
                return Optional.of(intp + "%");
            }
            return Optional.empty();
        }

    }

    public static void main(String[] args) {
        Counter c = U.newCounter(17233);
        for (int i = 0; i < 17233; i++) {
            c.addOne();
            U.sleep(1);
        }

    }
}
