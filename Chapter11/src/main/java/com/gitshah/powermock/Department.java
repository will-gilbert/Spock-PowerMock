package com.gitshah.powermock;

import java.util.ArrayList;
import java.util.List;

/**
 * The Department class that will
 * have a relationship with Employee class.
 * One Employee will be
 * associated with at max one Department,
 * but one Department will be associated with
 * one or more Employees
 *
 * @author Deep Shah
 */
public class Department {

    /**
     * The internal list of employee associated with department.
     */
    private List<Employee> employees = new ArrayList<Employee>();

    /**
     * The max salary offered by this department.
     */
    private long maxSalaryOffered;

    /**
     * The method to add a new employee to this department.
     * @param employee the instance to add to this departmnet.
     */
    public void addEmployee(final Employee employee) {
        employees.add(employee);
        updateMaxSalaryOffered();
    }

    /**
     * The private method that keeps track of
     * max salary offered by this department.
     */
    private void updateMaxSalaryOffered() {
        maxSalaryOffered = 0;
        for (Employee employee : employees) {
            if(employee.getSalary() > maxSalaryOffered) {
                maxSalaryOffered = employee.getSalary();
            }
        }
    }
}
