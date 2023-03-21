package org.example.work;

import org.example.model.User;
import org.example.model.UsersAddress;
import org.example.util.ConnectionManager;
import org.example.work.MethodsUsersTable;

import java.sql.*;
import java.util.logging.Logger;

public class MethodsUserAddressTable {
    private static Logger logger = Logger.getLogger("MethodsUserAddressTable");

    public void creatUser_addressTable() {
        String sql = """
    CREATE TABLE user_address (
	    id int PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
	    city VARCHAR (20),
	    street VARCHAR (20),
	    house INT);
	    """;
        try(Connection connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.executeUpdate();
            logger.info ("---- таблица user_address создана-----");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void deleteUsers_addressToId(int id){
        try ( Connection connection = ConnectionManager.open();
              PreparedStatement preparedStatement = connection.prepareStatement(" DELETE FROM users WHERE id = ?"))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.getStackTrace();
        }
    }
    public void addTableAddress(UsersAddress usersAddress){
        try (Connection connection  = ConnectionManager.open();

             PreparedStatement preparedStatement = connection.prepareStatement(" INSERT INTO user_address (id, city, street, house) " +
                     "VALUES (?, ?, ?, ?)" ); ){
            preparedStatement.setInt(1, usersAddress.getId());
            preparedStatement.setString(2, usersAddress.getCity());
            preparedStatement.setString(3, usersAddress.getStreet());
            preparedStatement.setInt(4, usersAddress.getHouse());
            preparedStatement.executeUpdate();
            logger.info("------адрес пользователя добавлен--------" );
        } catch ( SQLException e){
            logger.info("------адрес не добавлен--------");
           e.getStackTrace();
        }
    }
    public void selectUserHouse (int numberHouse){
        try (Connection connection  = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT users.id, users.first_name, users.last_name, users.age FROM user_address LEFT JOIN  users  ON user_address.id = users.id WHERE user_address.house = ?")
             ){
           preparedStatement.setInt(1, numberHouse);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                System.out.println("id = " + id + "; firstName = " + firstName + "; lastName = "+ lastName + "; age = " + age);
            }
        } catch (SQLException t){
            throw new RuntimeException(t);
        }
}
}
