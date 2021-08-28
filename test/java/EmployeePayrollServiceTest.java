import com.bridgelabz.EmployeePayrollData;
import com.bridgelabz.EmployeePayrollService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bridgelabz.EmployeePayrollService.IOService.DB_IO;
import static com.bridgelabz.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {
    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmployees = new EmployeePayrollData[]{
                new EmployeePayrollData(1, "Sujoy Roy", 100000.0),
                new EmployeePayrollData(2, "Soyam Roy", 150000.0),
                new EmployeePayrollData(3, "Paritosh Roy", 200000.0)
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
        employeePayrollService.writeEmployeePayrollData(FILE_IO);
        employeePayrollService.printData(FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        Assert.assertEquals(1,entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        Assert.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Bill",200000.00);
        boolean rs = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
        Assert.assertTrue(rs);
    }
}
