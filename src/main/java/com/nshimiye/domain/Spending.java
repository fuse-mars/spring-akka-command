package com.nshimiye.domain;

import java.io.Serializable;

//money spend on lunch or dinner
public class Spending implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6114821633753011551L;
	
	private final long id;
    private final String name; // name of the food
    private final double amount; //in dollars

    public Spending(long id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}