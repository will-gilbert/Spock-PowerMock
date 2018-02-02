package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.exceptions.TooManyMethodsFoundException;

import java.lang.reflect.Method;

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
        PowerMockito.verifyStatic(Employee.class);
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

        PowerMockito.verifyStatic(EmployeeIdGenerator.class);
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
    public void shouldInvokeThePrivateCreateEmployeeMethodWhileSavingANewEmployee() throws Exception {
        final EmployeeService spy = PowerMockito.spy(new EmployeeService());

        final Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        //Since we cannot access the private method outside the class,
        //We have to pass the name of the private method along with the arguments passed
        //To the PowerMockito.doNothing().when() method.
        PowerMockito.doNothing().when(spy, "createEmployee", employeeMock);

        spy.saveEmployee(employeeMock);

        //Verification is similar to setting up the mock.
        //We have to inform PowerMock about which private method to verify by invoking the
        //invoke method on PowerMockito.verifyPrivate().
        //The name of the private method along with its arguments are passed to invoke method.
        PowerMockito.verifyPrivate(spy).invoke("createEmployee", employeeMock);
    }

    @Test(expected = TooManyMethodsFoundException.class)
    public void shouldInvokeThePrivateCreateEmployeeMethodWithoutSpecifyingMethodName() throws Exception {
        final EmployeeService spy = PowerMockito.spy(new EmployeeService());

        final Employee employeeMock = PowerMockito.mock(Employee.class);
        PowerMockito.when(employeeMock.isNew()).thenReturn(true);

        //Finding the methods from EmployeeService class that take Employee as their argument.
        final Method createEmployeeMethod = PowerMockito.method(EmployeeService.class, Employee.class);

        //Passing the method instance found in previous step to the when method.
        //This sets up the mock on the private method.
        PowerMockito.doNothing().when(spy, createEmployeeMethod).withArguments(employeeMock);

        spy.saveEmployee(employeeMock);

        //Verifying that the private method was indeed invoked
        //using the same method instance we found earlier.
        PowerMockito.verifyPrivate(spy).invoke(createEmployeeMethod).withArguments(employeeMock);
    }
}
