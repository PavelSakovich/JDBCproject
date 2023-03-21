package org.example.test;

import org.example.model.User;
import org.example.work.MethodsTable;

import java.util.ArrayList;


public class Try {
    public static void main(String[] args) {
        MethodsTable methodsTable = new MethodsTable();
        ArrayList<User> user = methodsTable.getUsers();
        user.forEach(System.out::println);
    }
}
