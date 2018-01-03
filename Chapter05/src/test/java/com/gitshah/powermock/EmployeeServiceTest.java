package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * The class that holds all unit tests for
 * the EmployeeService class.
 * @author Deep Shah
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Employee.class, EmployeeIdGenerator.class})
public class EmployeeServiceTest {

    @Test
    public void shouldReturnTheCountOfEmployeesUsingTheDomainClass() {
        PowerMockito.mockStatic(Employee.class);
        PowerMockito.when(Employee.count()).thenReturn(900);

        EmployeeService employeeService = new EmployeeService();
        Assert.assertEquals(900, employeeService.getEmployeeCount());
    }

    @Test
    public void shouldReturnTrueWhenIncrementOf10PercentageIsGivenSuccessfully() {
        PowerMockito.mockStatic(Employee.class);
        PowerMockito.doNothing().when(Employee.class);
        Employee.giveIncrementOf(10);

        EmployeeService employeeService = new EmployeeService();
        Assert.assertTrue(employeeService.giveIncrementToAllEmployeesOf(10));
    }

    @Test
    public void shouldReturnFalseWhenIncrementOf10PercentageIsNotGivenSuccessfully() {
        PowerMockito.mockStatic(Employee.class);
        PowerMockito.doThrow(new IllegalStateException()).when(Employee.class);
        Employee.giveIncrementOf(10);

        EmployeeService employeeService = new EmployeeService();
        Assert.assertFalse(employeeService.giveIncrementToAllEmployeesOf(10));
    }

    @Test
    public void shouldInvoke_giveIncrementOfMethodOnEmployeeWhileGivingIncrement() {
        PowerMockito.mockStatic(Employee.class);
        PowerMockito.doNothing().when(Employee.class);
        Employee.giveIncrementOf(9);

        EmployeeService employeeService = new EmployeeService();
        employeeService.giveIncrementToAllEmployeesOf(9);

        //We first have to inform PowerMock that we will now verify
        //the invocation of a static method by calling verifyStatic.
        PowerMockito.verifyStatic();
        //Then we need to inform PowerMock
        //about the method we want to verify.
        //This is done by actually invoking the static method.
        Employee.giveIncrementOf(9);
    }

    @Test
    public void shouldCreateNewEmployeeIfEmployeeIsNew() {
        Employee mock = PowerMockito.mock(Employee.class);
        PowerMockito.when(mock.isNew()).thenReturn(true);

        PowerMockito.mockStatic(EmployeeIdGenerator.class);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(mock);

        //Verifying that the create method was indeed invoked
        //on the employee instance.
        Mockito.verify(mock).create();

        //Verifying that while creating a new employee
        //update was never invoked.
        Mockito.verify(mock, Mockito.never()).update();
    }

    @Test
    public void shouldUpdateEmployeeIfEmployeeExists() {
        Employee mock = PowerMockito.mock(Employee.class);
        PowerMockito.when(mock.isNew()).thenReturn(false);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(mock);

        //Verifying that the update method was indeed invoked
        //on the employee instance.
        Mockito.verify(mock).update();

        //Verifying that while updating an employee
        //create was never invoked.
        Mockito.verify(mock, Mockito.never()).create();
    }

    @Test
    public void shouldInvokeIsNewBeforeInvokingCreate() {
        Employee mock = PowerMockito.mock(Employee.class);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(mock);

        //First we have to let PowerMock know that
        //the verification order is going to be important
        //This is done by calling Mockito.inOrder and passing
        //it the mocked object.
        InOrder inOrder = Mockito.inOrder(mock);

        //Next, we can continue our verification using
        //the inOrder instance using the same technique
        //as seen earlier.
        inOrder.verify(mock).isNew();
        inOrder.verify(mock).update();
        inOrder.verify(mock, Mockito.never()).create();
    }

    @Test
    public void shouldGenerateEmployeeIdIfEmployeeIsNew() {
        Employee mock = PowerMockito.mock(Employee.class);
        PowerMockito.when(mock.isNew()).thenReturn(true);

        PowerMockito.mockStatic(EmployeeIdGenerator.class);
        PowerMockito.when(EmployeeIdGenerator.getNextId()).thenReturn(90);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(mock);

        PowerMockito.verifyStatic();
        EmployeeIdGenerator.getNextId();
        Mockito.verify(mock).setEmployeeId(90);
        Mockito.verify(mock).create();
    }
}
