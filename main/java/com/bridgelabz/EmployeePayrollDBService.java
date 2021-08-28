package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private PreparedStatement employeePayrollDataStatement;
    private static  EmployeePayrollDBService employeePayrollDBService;
    private EmployeePayrollDBService() {

    }

    public static EmployeePayrollDBService getInstance() {
        if(employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String username = "root";
        String password = "HEAD5580a";
        Connection connection;
        System.out.println("Connecting To Database: "+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection is successful!!!"+connection);
        return connection;
    }

    public int updateEmployeeSalary(String name, double salary) {
        return this.updateEmployeeDetailsUsingStatement(name,salary);
    }

    private int updateEmployeeDetailsUsingStatement(String name, double salary) {
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';",salary,name);
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1,name);
            ResultSet rs = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("basic_pay");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
            }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll where name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}