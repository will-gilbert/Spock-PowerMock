package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that holds all tests related to the Department class.
 * @author Deep Shah
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Department.class)
@SuppressStaticInitializationFor("com.gitshah.powermock.BaseEntity")
public class DepartmentTest {

    @Test
    public void shouldVerifyThatNewEmployeeIsAddedToTheDepartment() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        final Department department = new Department();
        final Employee employee = new Employee();

        //Adding the employee to the department.
        department.addEmployee(employee);

        //Getting the privately held employees list
        //from the Department instance.
        final List<Employee> employees = Whitebox.getInternalState(department, "employees");

        //Asserting that the employee was added to the list.
        Assert.assertTrue(employees.contains(employee));
    }

    @Test
    public void shouldAddNewEmployeeToTheDepartment() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        final Department department = new Department();
        final Employee employee = new Employee();

        final ArrayList<Employee> employees = new ArrayList<Employee>();
        //Setting the privately held employees list with our test employees list.
        Whitebox.setInternalState(department, "employees", employees);

        //Adding the employee.
        department.addEmployee(employee);

        //Since we substituted the privately held employees
        //within the department instance
        //we can simply assert whether our list has the newly added employee or not.
        Assert.assertTrue(employees.contains(employee));
    }

    @Test
    public void shouldVerifyThatMaxSalaryOfferedForADepartmentIsCalculatedCorrectly() throws Exception {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        final Department department = new Department();
        final Employee employee1 = new Employee();
        final Employee employee2 = new Employee();
        employee1.setSalary(60000);
        employee2.setSalary(65000);

        //Adding two employees to the test employees list.
        final ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);

        //Substituting the privately held employees list with our test list.
        Whitebox.setInternalState(department, "employees", employees);

        //Invoking the private method updateMaxSalaryOffered on the department instance.
        Whitebox.invokeMethod(department, "updateMaxSalaryOffered");

        //Getting the value of maxSalary from the private field.
        final long maxSalary = Whitebox.getInternalState(department, "maxSalaryOffered");

        Assert.assertEquals(65000, maxSalary);
    }

    @Test
    public void shouldSuppressTheBaseConstructorOfDepartment() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        Assert.assertEquals(10, new Department(10).getDepartmentId());
    }

    @Test
    public void shouldSuppressThePerformAuditMethodOfBaseEntity() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        PowerMockito.suppress(PowerMockito.method(BaseEntity.class, "performAudit", String.class));
        final Department department = new Department();
        department.setName("Mocking with PowerMock");
        Assert.assertEquals("Mocking with PowerMock", department.getName());
    }

    @Test
    public void shouldSuppressTheInitializerForBaseEntity() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        Assert.assertNotNull(new Department());
    }

    @Test
    public void shouldSuppressTheFieldIdentifierForBaseEntity() {
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity.class));
        PowerMockito.suppress(PowerMockito.field(BaseEntity.class, "identifier"));
        Assert.assertNotNull(new Department());
    }
}
