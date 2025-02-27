package com.register.model;

import lombok.*;

@ToString
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String id;
    private String name;
    private String email;
    private String phone;
}
