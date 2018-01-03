package com.gitshah.powermock

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Narrative

/**
 * The class that holds all Spock tests for "Verifying method invocation (Simple)""
 * @author Will Gilbert
 */


@Narrative("""
From 'Mock Testing with PowerMock' pp 28-33; Mocking "Verifying method invocation (Simple)"

Introductory tests to show:
   - Stubbing
   - Mocking
   - Mock invocation verification

""")


@Subject(EmployeeController)
class EmployeeControllerSpec extends Specification {

    def 'Should invoke save employee on the service while saving the employee'() {

        given: "Create a mock for the 'EmployeeService' class"
        def mock = Mock(EmployeeService)

        and: 'Create a EmployeeController instance with the mocked EmployeeService'
        def employeeController = new EmployeeController(mock)

        and: 'Create an instance of Employee; Concrete or stubbed will work'
        def employee = new Employee()

        when: 'And save it in EmployeeController'
        employeeController.saveEmployee(employee)

        then: "Verify that mocked 'EmployeeService.saveEmployee' was invoked with the 'employee'"
        1 * mock.saveEmployee(employee)

        and: "No other methods were invoked"
        _ * mock._()
    }

}
