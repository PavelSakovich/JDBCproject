package org.example.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int age;

}
