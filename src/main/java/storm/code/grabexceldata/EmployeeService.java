package storm.code.grabexceldata;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class EmployeeService {

    public List<Employee> readExcelFile(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Employee employee = new Employee();

            if (!row.getCell(0).getStringCellValue().equals("Name")) {
                employee.setName(row.getCell(0).getStringCellValue());

                BigDecimal salary = null;
                if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                    salary = BigDecimal.valueOf(row.getCell(1).getNumericCellValue());
                }
                employee.setSalary(salary);

                Date birthday = row.getCell(2).getDateCellValue();
                employee.setBirthday(birthday);

                employees.add(employee);
            }
        }

        workbook.close();

        return employees;
    }

}
