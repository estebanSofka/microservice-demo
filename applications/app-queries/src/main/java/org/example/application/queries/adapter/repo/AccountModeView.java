package org.example.application.queries.adapter.repo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AccountModeView {
    private String id;
    private String name;
    private String userId;
    private Set<TransactionModelView> transactionModelViews;
    private Double balance;
    private Boolean active;

    public void setTransactionModelViews(Set<TransactionModelView> transactionModelViews) {
        this.transactionModelViews = transactionModelViews;
    }

    public Set<TransactionModelView> getTransactionModelViews() {
        if (Objects.isNull(transactionModelViews))
            return new HashSet<>();
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
                ", transactionModelViews=" + transactionModelViews +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }
}
