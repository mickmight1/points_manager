package com.mmight1.points_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String account;
    private Long balance = 0L;
    
    public Accounts() {
    }

    public Accounts(String account) {
        this.account = account;
    }

    public Accounts(String account, Long initialBalance) {
        this.account = account;
        this.balance = initialBalance;
    }
    
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public Long getBalance() {
    	return balance;
    }
    
    // setter is private to this class
    
    private void setBalance(Long balance) {
        this.balance = balance;
    }

	public void deductPoints(Long amount) {
		this.setBalance(this.balance - amount);
	}
}
