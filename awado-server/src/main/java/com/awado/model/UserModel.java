package com.awado.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    private int id;
    private String username;
    private String password;
    private String reservedBikeId;
    private boolean isAdmin;

    public String getReservedBikeId() {
        return reservedBikeId;
    }

    public void setReservedBikeId(String reservedBikeId) {
        this.reservedBikeId = reservedBikeId;
    }
}
