package com.ks.dbHost.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String userId;
    String name;
    String password;
    String email;
    String contact;
    String address;
    String city;
}
