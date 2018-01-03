package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

/**
 * The class that holds all unit tests for
 * the EmployeeController class.
 * @author Deep Shah
 */
public class EmployeeControllerTest {

    @Test
    public void shouldReturnCountOfEmployeesFromTheService() {
        //Creating a mock using the PowerMockito.mock method for the EmployeeService class.
        EmployeeService mock = PowerMockito.mock(EmployeeService.class);

        //Next statement essentially says that when getEmployeeCount method
        //is called on the mocked EmployeeService instance, return 10.
        PowerMockito.when(mock.getEmployeeCount()).thenReturn(10);

        EmployeeController employeeController = new EmployeeController(mock);
        Assert.assertEquals(10, employeeController.getEmployeeCount());
    }

    @Test
    public void shouldReturnCountOfEmployeesFromTheServiceWithDefaultAnswer() {
        //Creating a mock using the PowerMockito.mock method for the EmployeeService class.
        EmployeeService mock = PowerMockito.mock(EmployeeService.class,
                /**
                 * Passing in a default answer instance.
                 * This method will be called when no matching mock methods have been setup.
                 */
                new Answer() {
                    /**
                     * We are simply implementing the answer method of the interface
                     * and returning hardcoded 10.
                     * @param invocation The context of the invocation.
                     *                   Holds useful information like what arguments where passed.
                     * @return Object the value to return for this mock.
                     */
                    @Override
                    public Object answer(InvocationOnMock invocation) {
                        return 10;
                    }
                });

        EmployeeController employeeController = new EmployeeController(mock);
        Assert.assertEquals(10, employeeController.getEmployeeCount());
    }

    @Test
    public void shouldInvokeSaveEmployeeOnTheServiceWhileSavingTheEmployee() {
        EmployeeService mock = PowerMockito.mock(EmployeeService.class);

        EmployeeController employeeController = new EmployeeController(mock);

        Employee employee = new Employee();
        employeeController.saveEmployee(employee);

        //Verifying that controller did call the
        //saveEmployee method on the mocked service instance.
        Mockito.verify(mock).saveEmployee(employee);
    }

    @Test
    public void shouldInvokeSaveEmployeeOnTheServiceWhileSavingTheEmployeeWithMockSettings() {
        EmployeeService mock = PowerMockito.mock(EmployeeService.class, Mockito
                .withSettings()
                .name("EmployeeServiceMock")
                .verboseLogging());

        EmployeeController employeeController = new EmployeeController(mock);

        Employee employee = new Employee();
        employeeController.saveEmployee(employee);

        //Verifying that controller did call the
        //saveEmployee method on the mocked service instance.
        Mockito.verify(mock).saveEmployee(employee);
    }

    @Test
    public void shouldFindEmployeeByEmail() {
        final EmployeeService mock = PowerMockito.mock(EmployeeService.class);

        final Employee employee = new Employee();

        //Notice that we are just check if the email address
        //starts with "deep" then we have found the matching employee.
        PowerMockito.when(mock.findEmployeeByEmail(Mockito.startsWith("deep"))).thenReturn(employee);

        final EmployeeController employeeController = new EmployeeController(mock);

        //Following 2 invocations will match return valid employee,
        //since the email address passed does start with "deep"
        Assert.assertSame(employee, employeeController.findEmployeeByEmail("deep@gitshah.com"));
        Assert.assertSame(employee, employeeController.findEmployeeByEmail("deep@packtpub.com"));

        //However, this next invocation would not return a valid employee,
        //since the email address passed does not start with "deep"
        Assert.assertNull(employeeController.findEmployeeByEmail("noreply@packtpub.com"));
    }

    @Test
    public void shouldReturnNullIfNoEmployeeFoundByEmail() {
        final EmployeeService mock = PowerMockito.mock(EmployeeService.class);

        //No matter what email is passed
        //calling the findEmployeeByEmail on the
        //mocked EmployeeService instance is now going to return null.
        PowerMockito.when(mock.findEmployeeByEmail(Mockito.anyString())).thenReturn(null);

        final EmployeeController employeeController = new EmployeeController(mock);

        Assert.assertNull(employeeController.findEmployeeByEmail("deep@gitshah.com"));
        Assert.assertNull(employeeController.findEmployeeByEmail("deep@packtpub.com"));
        Assert.assertNull(employeeController.findEmployeeByEmail("noreply@packtpub.com"));
    }

    @Test
    public void shouldReturnTrueIfEmployeeEmailIsAlreadyTaken() {
        final EmployeeService mock = PowerMockito.mock(EmployeeService.class);

        //A little more complex matcher using the ArgumentMatcher class.
        //By implementing the matches method in this class we can write any kind of complex logic
        //to validate that the correct arguments are being passed.
        final String employeeEmail = "packt@gitshah.com";
        PowerMockito.when(mock.employeeExists(Mockito.argThat(new ArgumentMatcher<Employee>() {
            /**
             * This method currently only check that
             * the email address set in the employee instance
             * matches the email address we passed to the controller.
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object employee) {
                return ((Employee) employee).getEmail().equals(employeeEmail);
            }
        }))).thenReturn(true);

        final EmployeeController employeeController = new EmployeeController(mock);
        Assert.assertTrue(employeeController.isEmployeeEmailAlreadyTaken(employeeEmail));
    }
}
