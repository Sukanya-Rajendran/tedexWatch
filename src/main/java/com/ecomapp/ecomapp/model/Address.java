package com.ecomapp.ecomapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
    private UUID id;

    private String flat;

    private String area;

    private String town;

    private String city;

    private String state;

    private String pin;

    private String landmark;

    private boolean isDeleted;

    private boolean defaultAddress;
    private boolean enabled = true;

    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




    // Getters and setters for the new defaultAddress field
    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}

//    @OneToMany(mappedBy = "address")
//    @ToString.Exclude
//    private List<Order> orders;
//}
