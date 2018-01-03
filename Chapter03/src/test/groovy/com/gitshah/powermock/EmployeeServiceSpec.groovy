package com.gitshah.powermock

import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.spockframework.runtime.Sputnik

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for "Mocking static methods (Simple)""
 * @author Will Gilbert
 */


@Narrative("""
From 'Mock Testing with PowerMock' pp 22-28; Mocking "Mocking static methods (Simple)"

How to use PowerMock with Spock using PowerMock's runner delegate.
  See: https://medium.com/@WZNote/how-to-make-spock-and-powermock-work-together-a1889e9c5692
  And: https://github.com/powermock/powermock/wiki/junit_delegating_runner

- Stub a static method using PowerMock's 'mockStatic' along with 'when' and 'thenReturn'
- Stub all static methods using PowerMock's 'mockStatic' so that all methods do nothing
- Stub a static method using PowerMock's 'mockStatic' to throw an exception


""")

@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest(Employee)

@Subject(EmployeeService)
public class EmployeeServiceSpec extends Specification {

    def 'Should return the count of employees from a static method'() {

        given: "Stub the static method using PowerMock"
        def expectedCount = 900
        PowerMockito.mockStatic(Employee);
        PowerMockito.when(Employee.count()).thenReturn(expectedCount);

        and: "Instance the SUT"
        def employeeService = new EmployeeService();

        when: "The service (aka SUT) gets the count from Employee's static method"
        def result = employeeService.getEmployeeCount()

        then: "Verify the employee count"
        result == expectedCount
    }

    def "Should return true when 'Employee.giveIncrementOf' does not throw an exception"() {

        given: "Stub all Employee methods to do nothing i.e. do not throw an exception "
        PowerMockito.mockStatic(Employee);
        PowerMockito.doNothing().when(Employee);

        and: "Instance the SUT"
        def employeeService = new EmployeeService();

        when: "Indirectly invoke the static via the SUT"
        def result = employeeService.giveIncrementToAllEmployeesOf(10)

        then: "Verify that no exception was thrown i.e the static method behaved as expected"
        result == true
    }

    def "Should return false when 'Employee.giveIncrementOf' throws an exception"() {

        given: "Stub all Employee methods to throw an exception "
        PowerMockito.mockStatic(Employee);
        PowerMockito.doThrow(new IllegalStateException()).when(Employee)

        and: "Use some test data"
        def increment = 10
        
        and: "Create a stub for a static method"
        Employee.giveIncrementOf(increment)

        and: "Instance the SUT"
        def employeeService = new EmployeeService()

        when: "Indirectly invoke the static via the SUT"
        def result = employeeService.giveIncrementToAllEmployeesOf(increment)

        then: "Verify that an exception was thrown i.e the static method behaved as expected"
        result == false
    }
}
