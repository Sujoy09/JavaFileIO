package com.bridgelabz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.bridgelabz.EmployeePayrollService.IOService.CONSOLE_IO;
import static com.bridgelabz.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollService {

    private EmployeePayrollDBService employeePayrollDBService;

    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollList = EmployeePayrollDBService.getInstance().readData();
        return this.employeePayrollList;
    }

    public void updateEmployeeSalary(String name, double salary) {
        int rs = EmployeePayrollDBService.getInstance().updateEmployeeSalary(name, salary);
        if (rs == 0) return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null) employeePayrollData.salary = salary;
    }

    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollList.stream()
                   .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                   .findFirst()
                   .orElse(null);
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));

    }

    public enum IOService {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    private List<EmployeePayrollData> employeePayrollList;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this();
        this.employeePayrollList = employeePayrollList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(CONSOLE_IO);
    }

    public void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee Salary: ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    public void writeEmployeePayrollData(IOService ioService) {
        if(ioService.equals(CONSOLE_IO))
        System.out.println("Writing Employee Payroll Roaster to console" +employeePayrollList);
        else if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().writeData(employeePayrollList);
    }

    public void printData(IOService ioService) {
        if(ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().printData();
    }

    public long countEntries(IOService ioService) {
        if(ioService.equals(FILE_IO))
            return new EmployeePayrollFileIOService().countEntries();
        return 0;
    }
}
