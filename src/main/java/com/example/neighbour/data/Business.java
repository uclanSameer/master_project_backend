package com.example.neighbour.data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table(name = "business")
@EqualsAndHashCode
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetail businessInfo;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "is_featured")
    private boolean isFeatured;

    public Business() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public UserDetail getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(UserDetail businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public Business(User user, UserDetail businessInfo) {
        this.user = user;
        this.businessInfo = businessInfo;
    }
}