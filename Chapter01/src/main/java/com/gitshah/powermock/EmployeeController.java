package com.gitshah.powermock;

/**
 * This is a very simple employee controller
 * which will make use of the EmployeeService to
 * perform Create, Read, Update and Delete (CRUD)
 * of Employee objects.
 * It delegates the heavy lifting to the
 * EmployeeService class
 * @author Deep Shah
 */
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * This method is responsible to return the
     * projected count of employees in the system.
     * Let's say the company is growing by 20% every year,
     * then the project count of employees is 20% more than
     * the actual count of employees in the system.
     * We will also round it off to the ceiling value.
     * @return Total number of projected employees in the system.
     */
    public int getProjectedEmployeeCount() {
        final int actualEmployeeCount = employeeService.getEmployeeCount();

        return (int) Math.ceil(actualEmployeeCount * 1.2);
    }

    /**
     * This method saves the employee instance.
     * It delegates this task to the employee service.
     * @param employee the instance to save.
     */
    public void saveEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
    }
}
