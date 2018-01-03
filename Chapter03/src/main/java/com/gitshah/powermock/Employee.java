package com.gitshah.powermock;

/**
 * This is a model class that will hold
 * properties specific to an employee in the system.
 * @author Deep Shah
 */
public class Employee {

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
}
