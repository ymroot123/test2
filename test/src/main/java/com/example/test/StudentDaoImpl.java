package com.example.test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class StudentDaoImpl implements JdbcOperation<Student, Integer> {
    @Override
    public void insert(Student entity) {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO car VALUES (?,?,?,?,?)")) {
            preparedStatement.setString(1, entity.getSno());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDate());
            preparedStatement.setString(4, entity.getSex());
            preparedStatement.setString(5, entity.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Student entity, String primaryKey) {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE car SET name=?, date=?, sex=?, phone=? WHERE sno=?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDate());
            preparedStatement.setString(3, entity.getSex());
            preparedStatement.setString(4, entity.getPhone());
            preparedStatement.setString(5, primaryKey);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(String primaryKey) {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM car WHERE sno=?")) {
            preparedStatement.setString(1, primaryKey);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> queryAll() {
        List<Student> resultList = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM car")) {
            while (resultSet.next()) {
                Student result = new Student();
                result.setSno(String.valueOf(resultSet.getString("sno")));
                result.setName(resultSet.getString("name"));
                result.setDate(resultSet.getString("date"));
                result.setSex(resultSet.getString("sex"));
                result.setPhone(resultSet.getString("phone"));
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override//分页
    public List<Student> queryWithPagination(int page, int pageSize) {
        List<Student> resultList = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM car LIMIT ? OFFSET ?")) {
            int offset = (page - 1) * pageSize;
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student result = new Student();
                result.setSno(String.valueOf(resultSet.getString("sno")));
                result.setName(resultSet.getString("name"));
                result.setDate(resultSet.getString("date"));
                result.setSex(resultSet.getString("sex"));
                result.setPhone(resultSet.getString("phone"));

                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Student> searchByCondition(String sno, String name, String sex, String date, String phone) {
        List<Student> resultList = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM car where sno like ? and name like ? and sex like ? and date like ? and phone like ?")) {
            preparedStatement.setString(1, sno+"%");
            preparedStatement.setString(2, name+"%");
            preparedStatement.setString(3, sex+"%");
            preparedStatement.setString(4, date+"%");
            preparedStatement.setString(5, phone+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student result = new Student();
                result.setSno(String.valueOf(resultSet.getString("sno")));
                result.setName(resultSet.getString("name"));
                result.setDate(resultSet.getString("date"));
                result.setSex(resultSet.getString("sex"));
                result.setPhone(resultSet.getString("phone"));
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}