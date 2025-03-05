package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
    @Column(name = "FIRSTNAME", length = 10)
    private String firstName;
    @Column(name = "MIDDLENAME", length = 20)
    private String middleName;
    @Column(name = "LASTNAME", length = 30)
    private String lastName;
    @Column(name = "SEX", length = 1)
    private Character sex;
    @Column(name = "PHONE", length = 10, unique = true)
    private String phone;
    @Column(name = "ADDRSS", length = 200)
    private String address;
    @Column(name = "CITY", length = 200)
    private String city;
    @Column(name = "PROVIDE", length = 200)
    private String provide;
    @Column(name = "EMAIL", length = 30)
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
