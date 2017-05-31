package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientProfile.
 */
@Entity
@Table(name = "client_profile")
public class ClientProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "pass_hash")
    private Long passHash;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ClientProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPassHash() {
        return passHash;
    }

    public ClientProfile passHash(Long passHash) {
        this.passHash = passHash;
        return this;
    }

    public void setPassHash(Long passHash) {
        this.passHash = passHash;
    }

    public Client getClient() {
        return client;
    }

    public ClientProfile client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientProfile clientProfile = (ClientProfile) o;
        if (clientProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientProfile{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", passHash='" + getPassHash() + "'" +
            "}";
    }
}
