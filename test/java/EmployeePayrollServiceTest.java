import com.bridgelabz.EmployeePayrollData;
import com.bridgelabz.EmployeePayrollService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.bridgelabz.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {
    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        EmployeePayrollData[] arrayOfEmployees ={
                new EmployeePayrollData(1,"Sujoy Roy",100000.0),
                new EmployeePayrollData(2,"Soyam Roy",150000.0),
                new EmployeePayrollData(3,"Paritosh Roy",200000.0)
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
    }
}
