package org.example.test;

import org.example.model.User;
import org.example.dao.UserDao;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserDao methodsTable = new UserDao();
        List<User> user = methodsTable.getUsers();
        user.forEach(System.out::println);
    }
}
