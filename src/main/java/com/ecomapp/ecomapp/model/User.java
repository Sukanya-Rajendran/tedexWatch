package com.ecomapp.ecomapp.model;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    private boolean blocked;
    private String referralCode;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @ManyToOne
    @JoinColumn(name = "referring_user_id")
    private User referringUser;

    public User getReferringUser() {
        return referringUser;
    }

    public void setReferringUser(User referringUser) {
        this.referringUser = referringUser;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Column(name = "reset_password_token")
    private String resetPasswordToken;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<Role> roles;

    public User() {

    }
    //user register


    public User(String firstName, String lastName, String email, String password,  Collection<Role> roles, Boolean blocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.roles = roles;
        this.blocked = blocked;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    @OneToOne
    @JoinColumn(name = "cart_id")
    @ToString.Exclude
    private Cart cart;

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                ", roles=" + roles +
                '}';
    }

    public void setResetPasswordToken(String token) {
        this.resetPasswordToken=token;

    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }


//    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<Order> orders;
}
