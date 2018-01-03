package com.gitshah.powermock;

/**
 * This is a model class that will hold
 * properties specific to an employee in the system.
 * @author Deep Shah
 */
public class Employee {

    /**
     * The employee Id field.
     */
    private int employeeId;

    /**
     * The employee email field.
     */
    private String email;

    /**
     * The default constructor.
     */
    public Employee() {
    }

    /**
     * The constructor that takes in the email as argument.
     * @param email the employee email.
     */
    public Employee(String email) {
        this.email = email;
    }

    /**
     * The method that is responsible to return the
     * count of employees in the system.
     * @return The total number of employees in the system.
     * Currently this method throws UnsupportedOperationException.
     */
    public static int count() {
        throw new UnsupportedOperationException();
    }

    /**
     * The method that is responsible to increment
     * salaries of all employees by the given percentage.
     * @param percentage the percentage value by which
     *                   salaries would be increased
     * Currently this method throws UnsupportedOperationException.
     */
    public static void giveIncrementOf(int percentage) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is responsible to save the employee
     * details to the DB.  Currently this method
     * throws UnsupportedOperationException.
     */
    public void save() {
        throw new UnsupportedOperationException();
    }

    /**
     * The method that identifies if the employee
     * is not yet persisted in the DB.
     * @return true if employee is not yet
     * persisted in the DB, false otherwise.
     * Currently this method throws
     * UnsupportedOperationException
     */
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is responsible to update
     * an existing employee's information into the DB.
     * Currently this method throws
     * UnsupportedOperationException
     */
    public void update() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is responsible to create
     * a new employee into the DB.
     * Currently this method throws
     * UnsupportedOperationException
     */
    public void create() {
        throw new UnsupportedOperationException();
    }

    /**
     * The setter for the employeeId field.
     * @param employeeId value to set.
     */
    public void setEmployeeId(final int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * The Getter for the employee email.
     * @return the email of the employee.
     */
    public String getEmail() {
        return email;
    }
}
