package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientAdress.
 */
@Entity
@Table(name = "client_adress")
public class ClientAdress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adress")
    private String adress;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public ClientAdress adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Client getClient() {
        return client;
    }

    public ClientAdress client(Client client) {
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
        ClientAdress clientAdress = (ClientAdress) o;
        if (clientAdress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientAdress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientAdress{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
