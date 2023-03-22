package org.example.dao;

import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.example.util.ConnectionManager;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.sql.Connection;

@Log
public final class UserDao {
    public void createTable() {

        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE users( id SERIAL PRIMARY KEY, first_name  VARCHAR (30) , last_name VARCHAR (30), age INTEGER )")) {
            statement.executeUpdate();
            log.info("---- Таблица создана----");
        } catch (SQLException e) {
            log.info("---- Error!----");
        }
    }

    public void deleteTable() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(" DROP TABLE users")) {
            preparedStatement.executeUpdate();
            log.info("---- Таблица удалена----");
        } catch (SQLException e) {
            log.info("---- Error!----");
        }
    }

    public void addUser(User user) {
        try (Connection connection = ConnectionManager.open();

             PreparedStatement preparedStatement = connection.prepareStatement(" INSERT INTO users ( first_name, last_name, age ) " +
                     "VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();
            log.info("----Пользователь создан----");
        } catch (SQLException e) {
            log.info("----Error!----");
        }
    }

    public void deleteUserById(int id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(" DELETE FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            log.info("----Пользователь удален----");
        } catch (SQLException e) {
            log.info("----Error!----");
        }
    }

    public List<User> getUsers() {
        ArrayList<User> listUsers = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User()
                .setId(resultSet.getInt(1))
                .setFirstName(resultSet.getString(2))
                .setLastName(resultSet.getString(3))
                .setAge(resultSet.getInt(4));
                listUsers.add(user);
            }
            log.info("----Список всех пользователей передан---- ");
        } catch (SQLException e) {
            log.info("----Error!----");
            return listUsers;
        }
        return listUsers;
    }

    public User getUserById(int id) {
        User user = new User();
        try (Connection connection = ConnectionManager.open();
             Statement preparedStatement = connection.createStatement()) {
            ResultSet resultSet = preparedStatement.executeQuery(" SELECT * FROM users");
            while (resultSet.next()) {
                if (resultSet.getRow() == id) {
                   user.setId(resultSet.getInt(1))
                    .setFirstName(resultSet.getString(2))
                    .setLastName(resultSet.getString(3))
                    .setAge(resultSet.getInt(4));
                }
            }
            log.info("----Пользователь получен----");
            return user;
        } catch (SQLException e) {
            log.info("----Error!----");
            return user;
        }
    }

    public void updateUser(int id, String firstName, String lastName, int age) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, age = ? WHERE id = ?")) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            log.info("Пользователь обновлен:" + "\n" + "id = " + id + "\n"
                    + "firstName = " + firstName + "\n"
                    + "lastName = " + lastName + "\n"
                    + "age = " + age);
        } catch (SQLException e) {
            log.info("----Error!----");
        }
    }
}

