package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for "Breaking the encapsulation (Advanced)""
 * @author Will Gilbert
 */

 @Narrative("""
From 'Mock Testing with PowerMock' pp 59-64; Mocking "Breaking the encapsulation (Advanced))"

These tests show how to use PowerMock's 'Whitebox' class to access a private field.

Private field can be access for Spock due to Grooy's abilty to ignore field access.

Note: That if the field is "final private" Spock would be able to get it but not set
      whereas a private field can be accessed by Spock.

""")

@Subject(Department)
public class DepartmentSpec extends Specification {

    def "PowerMock: Should verify that new employee is added to the department"() {

        given: "A department and an employee; Both concrete instances"
        def department = new Department()
        def employee = new Employee()

        when: "Adding the employee to the department"
        department.addEmployee(employee)

        and: "Getting the privately held employees list using PowerMock Whitebox"
        def employees = Whitebox.getInternalState(department, "employees")

        then: "Asserting that the employee was added to the list"
        employees.contains(employee)
    }

    def "Spock: Should verify that new employee is added to the department"() {

        given: "A department and an employee; Both concrete instances"
        def department = new Department()
        def employee = new Employee()

        when: "Adding the employee to the department"
        department.addEmployee(employee)

        and: "Getting the privately held employees list using Groovy access"
        def employees = department.employees

        then: "Asserting that the employee was added to the list"
        employees.contains(employee)
    }


    def "PowerMock: Should add new employee to the department"() {

        given: "A department (SUT) and an employee; Both concrete instances"
        def department = new Department();
        def employee = new Employee();

        and: "Setting the privately held employees list with our test employees list"
        def employees = new ArrayList<Employee>();
        
        and: "Set out 'employees' list into the SUT"
        Whitebox.setInternalState(department, "employees", employees);

        when: "Adding the employee"
        department.addEmployee(employee);

        then: "Simply assert whether our list has the newly added employee or not"
        Assert.assertTrue(employees.contains(employee));
    }

    def "Spock: Should add new employee to the department"() {

        given: "A department and an employee; Both concrete instances"
        def department = new Department()
        def employee = new Employee()

        and: "Setting the privately held employees list with our test employees list"
        def employees = []
        
        and: "Setting the privately held employees list using Groovy access"
        department.employees = employees

        when: "Adding the employee"
        department.addEmployee(employee);

        then:"We can simply assert whether our list has the newly added employee or not"
        employees.contains(employee)
    }

    def "PowerMock: Should verify that 'maxSalaryOffered' for a department is calculated correctly"() {

        given: "The SUT (Department) and two concrete employees"
        final Department department = new Department();
        final Employee employee1 = new Employee();
        final Employee employee2 = new Employee();
        employee1.setSalary(60000);
        employee2.setSalary(65000);

        and: "Adding two employees to the test employees list"
        final ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);

        and: "Substituting the privately held employees list with our test list"
        Whitebox.setInternalState(department, "employees", employees);

        and: "Invoking the private method updateMaxSalaryOffered on the department instance"
        Whitebox.invokeMethod(department, "updateMaxSalaryOffered");

        when: "Getting the value of maxSalary from the private field"
        final long maxSalary = Whitebox.getInternalState(department, "maxSalaryOffered");

        then: "Verify that the maximum salary was returned"

        Assert.assertEquals(65000, maxSalary);
    }


    def "Spock: Should verify that 'maxSalaryOffered' for a department is calculated correctly"() throws Exception {

        given: "The SUT (Department) and two concrete employees"
        def department = new Department()
        def employee1 = new Employee(salary: 60000)
        def employee2 = new Employee(salary: 65000)

        and: "Adding two employees to the test employees list"
        def employees = [] as ArrayList<Employee>
        employees.add(employee1)
        employees.add(employee2)

        and: "Substituting the privately held employees list with our test list"
        department.employees = employees

        and: "Invoking the private method updateMaxSalaryOffered on the department instance"
        department.updateMaxSalaryOffered()

        when: "Getting the value of maxSalary from the private field"
        def maxSalary = department.maxSalaryOffered

        then: "Verify that the maximum salary was returned"
        65000 == maxSalary
    }
}
