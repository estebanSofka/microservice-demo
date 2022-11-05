package org.example.application.queries.adapter.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountModeView {
    private String id;
    private String name;
    private String userId;
    private Double balance;
    private Boolean active;
    private Map<String, TransactionModelView> transactionModelViews;

    public void setTransactionModelViews(Map<String, TransactionModelView> transactionModelViews) {
        this.transactionModelViews = transactionModelViews;
    }

    public Map<String, TransactionModelView> getTransactionModelViews() {
        if (Objects.isNull(transactionModelViews))
            return new HashMap<>();
        return transactionModelViews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        if (Objects.isNull(balance))
            return 0.0;
        return balance;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountModeView{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                ", transactionModelViews=" + transactionModelViews +
                '}';
    }
}
