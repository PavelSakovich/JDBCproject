package org.example.dao;

import org.example.models.User;
import org.example.models.UsersAddress;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;
@Log
public class UserAddressDao {
    public void createTableUsersAddress() {
        String sql = """
                CREATE TABLE user_address (
                 id int PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
                 city VARCHAR (20),
                 street VARCHAR (20),
                 house INT);
                 """;
        try (Connection connection = ConnectionManager.open(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            log.info("---- Таблица users_address создана-----");
        } catch (SQLException e) {
            log.info("----Error----");
            e.getStackTrace();
        }
    }


    public void addAddressToUser(UsersAddress userAddress) {
        try (Connection connection = ConnectionManager.open();

            PreparedStatement preparedStatement = connection.prepareStatement(" INSERT INTO user_address (id, city, street, house) " + "VALUES (?, ?, ?, ?)");) {
            preparedStatement.setInt(1, userAddress.getId());
            preparedStatement.setString(2, userAddress.getCity());
            preparedStatement.setString(3, userAddress.getStreet());
            preparedStatement.setInt(4, userAddress.getHouse());
            preparedStatement.executeUpdate();
            log.info("----Адрес добавлен----");
        } catch (SQLException e) {
            log.info("----Error!----");
            e.getStackTrace();
        }
    }


    public List<User> getUsersByNumberHouse(int numberHouse) {
        List<User> listUsers = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT users.id, users.first_name, users.last_name, users.age FROM user_address LEFT JOIN  users  ON user_address.id = users.id WHERE user_address.house = ?")
        ) {
            preparedStatement.setInt(1, numberHouse);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user1 = new User();
                user1.setId(resultSet.getInt(1));
                user1.setFirstName(resultSet.getString(2));
                user1.setLastName(resultSet.getString(3));
                user1.setAge(resultSet.getInt(4));
                listUsers.add(user1);
            }
            log.info("----Список пользователей передан----");
            return listUsers;
        } catch (SQLException t) {
            log.info("----Error!----");
            t.getStackTrace();
            return new ArrayList<>();
        }
    }
}
