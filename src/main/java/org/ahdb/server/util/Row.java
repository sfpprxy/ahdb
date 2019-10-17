package org.ahdb.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row<E> extends ArrayList<E> {

    @SafeVarargs
    @SuppressWarnings("varargs")
    public Row(E... cells) {
        super(Arrays.asList(cells));
    }

    public Row(List list) {
        super(list);
    }

}
