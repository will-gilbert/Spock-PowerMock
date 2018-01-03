package com.gitshah.powermock

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for "Saying Hello World (Simple)""
 * @author Will Gilbert
 */


@Narrative("""
From 'Mock Testing with PowerMock' pp 8-14; Mocking "Saying Hello World (Simple)"

Introductory tests to show:
   - Stubbing
   - Mocking
   - Mock invocation verification

""")

@Subject(EmployeeController)
public class EmployeeControllerSpec extends Specification {



    def 'Using a Stub: Should return projected count of employees from the service'() {

        given: "Creating a stubbed 'EmployeeService' class using the Spock 'Stub' \
                  and stub the method 'getEmplyeeCount' to return 6"
        def currentCount = 6
        def stub = Stub(EmployeeService) {
            getEmployeeCount() >> currentCount
        }
        
        and: "Inject the stubbed service into the controller"
        def employeeController = new EmployeeController(stub);

        when: "The controller gets the current employee count from the service in order to determine the projected count"
        def count = employeeController.getProjectedEmployeeCount()

        then: "The projected count should 1.2 times the current count"
        count == Math.ceil(currentCount * 1.2)
    }


    def 'Using a Mock: Should return projected count of employees from the service'() {

        given: "Creating a mocked 'EmployeeService' class using the Spock 'Mock' \
                  and stub the method 'getEmplyeeCount' to return 8"
        def currentCount = 8
        def mock = Mock(EmployeeService){
            getEmployeeCount() >> currentCount
        }

        and: "Inject the stubbed service into the controller"
        def employeeController = new EmployeeController(mock);

        when: "The controller gets the current employee count from the service in order to determine the projected count"
        def count = employeeController.getProjectedEmployeeCount()

        then: "The projected count should 1.2 times the current count"
        count == Math.ceil(currentCount * 1.2)
    }


    def 'Should return count of employees from the service with default answer'() {

        given: "Creating a stubbed 'EmployeeService' class using the Spock 'Stub'"
        def stub = Stub(EmployeeService)

        and: "Stub every method to retun 'currentCount'; Not a best practice! But this is how it's done"
        def currentCount = 10
        stub._ >> currentCount

        and: "Inject the stubbed service into the controller"
        def employeeController = new EmployeeController(stub);

        when: "The controller gets the current employee count from the service in order to determine the projected count"
        def count = employeeController.getProjectedEmployeeCount()

        then:
        count == Math.ceil(currentCount * 1.2)
    }


    def 'Use a mock to verify that a method was indeed invoked'() {

        given: "Creating a mocked 'EmployeeService' class using the Spock 'Mock'"
        def mock = Mock(EmployeeService);

        and: "Inject the mocked service into the controller"
        def employeeController = new EmployeeController(mock);

        and: "Create an employee; Concrete or Stubbed shouldn't matter"
        def employee = new Employee();

        when: "The controller will use the service to save the employee"
        employeeController.saveEmployee(employee);

        then: "Verifying that controller did call the 'saveEmployee' method on the mocked service"
        1 * mock.saveEmployee(employee)

        and: "No other methods were invoked on the service mock"
        _ * mock._
    }

}

