package com.mmight1.points_demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMP_ID")
    private Long idmpId;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "createdtime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    // Constructors
    public IdempotencyKey() {
    }

    public IdempotencyKey(String idempotencyKey, String accountName) {
        this.idempotencyKey = idempotencyKey;
        this.accountName = accountName;
    }

    // Getters and Setters
    public Long getIdmpId() {
        return idmpId;
    }

    public void setIdmpId(Long idmpId) {
        this.idmpId = idmpId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    // No setter for createdTime since it is set automatically

    @Override
    public String toString() {
        return "IdempotencyKey{" +
                "idmpId=" + idmpId +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", accountName='" + accountName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}