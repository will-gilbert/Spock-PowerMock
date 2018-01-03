package com.gitshah.powermock;

/**
 * This class is responsible to handle the CRUD
 * operations on the Employee objects.
 * @author Deep Shah
 */
public class EmployeeService {

    /**
     * This method is responsible to return
     * the count of employees in the system.
     * Currently this method does nothing but
     * simply throws an exception.
     * @return Total number of employees in the system.
     */
    public int getEmployeeCount() {
        throw new UnsupportedOperationException();
    }

    /**
     * The method that will save
     * the employee instance to the DB.
     * @param employee instance to save.
     */
    public void saveEmployee(Employee employee) {
        throw new UnsupportedOperationException();
    }
}
