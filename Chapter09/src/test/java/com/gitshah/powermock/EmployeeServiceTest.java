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
@PrepareForTest({Employee.class, EmployeeIdGenerator.class, EmployeeService.class})
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
    public void shouldCreateNewEmployeeIfEmployeeIsNew() throws Exception {
        Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        PowerMockito.mockStatic(EmployeeIdGenerator.class);

        WelcomeEmail welcomeEmailMock = PowerMockito.mock(WelcomeEmail.class);
        PowerMockito.whenNew(WelcomeEmail.class)
                .withArguments(employeeMock, "Welcome to Mocking with PowerMock How-to!")
                .thenReturn(welcomeEmailMock);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(employeeMock);

        //Verifying that the create method was indeed invoked
        //on the employee instance.
        Mockito.verify(employeeMock).create();

        //Verifying that while creating a new employee
        //update was never invoked.
        Mockito.verify(employeeMock, Mockito.never()).update();
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
    public void shouldGenerateEmployeeIdIfEmployeeIsNew() throws Exception {
        Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        PowerMockito.mockStatic(EmployeeIdGenerator.class);
        PowerMockito.when(EmployeeIdGenerator.getNextId()).thenReturn(90);


        WelcomeEmail welcomeEmailMock = PowerMockito.mock(WelcomeEmail.class);
        PowerMockito.whenNew(WelcomeEmail.class)
                .withArguments(employeeMock, "Welcome to Mocking with PowerMock How-to!")
                .thenReturn(welcomeEmailMock);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(employeeMock);

        PowerMockito.verifyStatic();
        EmployeeIdGenerator.getNextId();
        Mockito.verify(employeeMock).setEmployeeId(90);
        Mockito.verify(employeeMock).create();
    }

    @Test
    public void shouldSendWelcomeEmailToNewEmployees() throws Exception {
        Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        PowerMockito.mockStatic(EmployeeIdGenerator.class);

        //Creating the mock for WelcomeEmail.
        WelcomeEmail welcomeEmailMock = PowerMockito.mock(WelcomeEmail.class);

        /**
         *  Notice the whenNew syntax.
         *  PowerMockito.whenNew().withArguments().thenReturn()
         *  informs PowerMock that,
         *  1. When New instance of WelcomeEmail is created,
         *  2. With employee instance and "Welcome to Mocking with PowerMock How-to!" text,
         *  3. Then return a mock of EmployeeEmailService class.
         */
        PowerMockito.whenNew(WelcomeEmail.class)
                .withArguments(employeeMock, "Welcome to Mocking with PowerMock How-to!")
                .thenReturn(welcomeEmailMock);

        EmployeeService employeeService = new EmployeeService();
        employeeService.saveEmployee(employeeMock);

        /**
         * Verifying that the constructor for the WelcomeEmail class is invoked
         * with arguments as the mocked employee instance and text "Welcome to Mocking with PowerMock How-to!".
         */
        PowerMockito
                .verifyNew(WelcomeEmail.class)
                .withArguments(employeeMock, "Welcome to Mocking with PowerMock How-to!");

        //Verifying that the send method was called on the mocked instance.
        Mockito.verify(welcomeEmailMock).send();
    }

    @Test
    public void shouldInvokeTheCreateEmployeeMethodWhileSavingANewEmployee() {
        //Following is the syntax to create a spy using the PowerMockito.spy method.
        //Notice that we have to pass an actual instance of the EmployeeService class.
        //This is necessary since a spy will only mock few methods of a class and
        //invoke the real methods for all methods that are not mocked.
        final EmployeeService spy = PowerMockito.spy(new EmployeeService());

        final Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        //Notice that we have to use the PowerMockito.doNothing().when(spy).createEmployee()
        //syntax to create the spy. This is required because if we use the
        //PowerMockito.when(spy.createEmployee())
        //syntax will result in calling the actual method on the spy.
        //Hence, remember when we are using spies,
        //always use the doNothing(), doReturn() or the doThrow() syntax only.
        PowerMockito.doNothing().when(spy).createEmployee(employeeMock);

        spy.saveEmployee(employeeMock);

        //Verification is simple enough and
        //we have to use the standard syntax for it.
        Mockito.verify(spy).createEmployee(employeeMock);
    }
}
