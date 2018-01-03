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
public class Department extends BaseEntity {

    /**
     * The internal list of employee associated with department.
     */
    private List<Employee> employees = new ArrayList<Employee>();

    /**
     * The max salary offered by this department.
     */
    private long maxSalaryOffered;

    /**
     * The department id field.
     */
    private int departmentId;

    /** The name of the department. */
    private String name;

    /**
     * The default constructor.
     */
    public Department() {
    }

    /**
     * The constructor that takes in
     * departmentId as argument.
     * @param departmentId department id to set.
     */
    public Department(int departmentId) {
        super();
        this.departmentId = departmentId;
    }

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

    /**
     * Getter for the departmentId.
     * @return the value of departmentId.
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Getter for the name.
     * @return value of name;
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the departmentId.
     * @return the value of departmentId.
     */
    public void setName(String name) {
        this.name = name;
        performAudit(this.name);
    }

    /**
     * This method calculate the total net worth
     * of a given department.
     * This method is supposed to do some complex
     * calculation based on the number of employees
     * and their salaries to find the total net worth of this department.
     * Currently this method throws UnsupportedOperationException.
     * @return the total net worth of department.
     */
    public int calculateTotalNetWorth() {
        throw new UnsupportedOperationException();
    }
}
