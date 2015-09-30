package com.nshimiye.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


public class Database{

    private static Map<Long, Spending> db = new ConcurrentHashMap<>();

    public static Spending write(Spending spending) {
        db.put(spending.getId(), spending);
        return Database.read(spending.getId());
    }

    public static Spending read(Long key) {
        return db.get(key);
    }
}