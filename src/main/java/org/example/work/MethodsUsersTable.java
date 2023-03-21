package org.example.work;

import org.example.util.ConnectionManager;
import org.example.model.User;

import java.sql.*;
import java.util.logging.Logger;
import java.sql.Connection;

public class MethodsUsersTable {

    private static Logger logger = Logger.getLogger("MethodsUsersTable");

    public  void creatTable() {
        String sql = """
    CREATE TABLE users( id SERIAL PRIMARY KEY, first_name  VARCHAR (30) , last_name VARCHAR (30), age INTEGER );
    """;
        try(Connection connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.executeUpdate();
            logger.info ("---- таблица создана-----");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public  void deleteTables(){
        try ( Connection connection = ConnectionManager.open();
              PreparedStatement preparedStatement = connection.prepareStatement(" DROP TABLE users")){
            preparedStatement.executeUpdate();
            logger.info ("---- таблица удалена-----");
        } catch (SQLException e){
            e.getStackTrace();
            logger.info ("---- таблица не удалена-----");
        }
    }
    public void addUsers (User user){
        try (Connection connection  = ConnectionManager.open();

            PreparedStatement preparedStatement = connection.prepareStatement(" INSERT INTO users ( first_name, last_name, age ) " +
                                                                            "VALUES (?, ?, ?)")){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();
            logger.info("------пользователь создан:" + user.toString());
        } catch ( SQLException e){
            logger.info("------пользователь не создан--------");
     e.getStackTrace();
        }
    }
    public void deleteUsersToId(int id){
        try ( Connection connection = ConnectionManager.open();
              PreparedStatement preparedStatement = connection.prepareStatement(" DELETE FROM users WHERE id = ?"))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.getStackTrace();
        }
    }
    public void outputUser() {
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                System.out.println( "id = " + resultSet.getRow() +
                        "; first name = " + firstName +
                        "; " + "last name = " + lastName + "; " +
                        "age = " + age + " years;");
            }
                } catch(SQLException e){
            logger.info("Данные отсутствуют");
                    e.getStackTrace();
                }
            }
    public void outputUserToId (int id) {
        try ( Connection connection = ConnectionManager.open();
              Statement preparedStatement = connection.createStatement())
        {
            ResultSet resultSet = preparedStatement.executeQuery(" SELECT * FROM users");
            while (resultSet.next()){
                if (resultSet.getRow() == id){
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int age = resultSet.getInt(4);
                    System.out.println( "id = " + resultSet.getRow() +
                            "; first name = " + firstName +
                            "; " + "last name = " + lastName + "; " +
                            "age = " + age + " years;");
                }
            }
        } catch (SQLException e){
            e.getStackTrace();
            logger.info("------- пользователя под id = " + id + " не найден-------");
        }
    }
    public void updateUsers (int id, String firstName, String lastName, int age) {
        try ( Connection connection = ConnectionManager.open();
              PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, age = ? WHERE id = ?"))
        {
            new MethodsUsersTable().outputUserToId(id);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            logger.info("Таблица обновлена:" + "\n" + "id = " + id + "\n"
                    + "firstName = " + firstName + "\n"
                    + "lastName = " + lastName + "\n"
                    + "age = " + age);
        } catch (SQLException e){
            e.getStackTrace();
        }
    }
}

