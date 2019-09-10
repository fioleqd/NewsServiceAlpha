package com.fiole.newsservicealpha.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Page2ListUtil {
    public static <T> List<T> page2List(Page<T> page){
        List<T> list = new ArrayList<>();
        if (page == null)
            return list;
        Iterator<T> iterator = page.iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    public static <T> List<T> page2List(Page<T> page,List<T> list){
        if (page == null)
            return list;
        Iterator<T> iterator = page.iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}
