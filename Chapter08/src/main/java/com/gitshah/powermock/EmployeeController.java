package com.gitshah.powermock;

/**
 * This is a very simple employee controller
 * which will make use of the EmployeeService to
 * perform Create, Read, Update and Delete (CRUD)
 * of Employee objects.
 * It delegates the heavy lifting to the
 * EmployeeService class
 *
 * @author Deep Shah
 */
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * This method is responsible to return the
     * count of employees in the system.
     *
     * @return Total number of employees in the system.
     */
    public int getEmployeeCount() {
        return employeeService.getEmployeeCount();
    }

    /**
     * This method saves the employee instance.
     * It delegates this task to the employee service.
     *
     * @param employee the instance to save.
     */
    public void saveEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
    }

    /**
     * The method that will
     * find an employee by their email.
     * It delegates this task to the employee service.
     *
     * @param email the employee email to search.
     * @return The employee matching by email.
     */
    public Employee findEmployeeByEmail(String email) {
        return employeeService.findEmployeeByEmail(email);
    }

    /**
     * This method is responsible to check if
     * the employee email is already taken or not.
     * It delegates this task to the employee service.
     *
     * @param email The employee email to validate.
     * @return true if the employee email is taken,
     * false otherwise.
     */
    public boolean isEmployeeEmailAlreadyTaken(String email) {
        return employeeService.employeeExists(new Employee(email));
    }
}
