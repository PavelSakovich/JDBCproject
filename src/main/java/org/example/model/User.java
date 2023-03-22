package org.example.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors (chain = true)
@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int age;

}
