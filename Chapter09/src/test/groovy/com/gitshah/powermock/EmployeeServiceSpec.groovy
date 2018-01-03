package com.gitshah.powermock;

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds Spock tests for the EmployeeService class.
 * @author Will Gilbert
 */
@Narrative("""
From 'Mock Testing with PowerMock' pp52-55; Mocking "Partial mocking with spies (Advanced)"

- Using a Spy to verify that SUT methods are involked

""")

@Subject(EmployeeService)
public class EmployeeServiceSpec extends Specification {


    def "SUT should invoke the 'createEmployee' method when saving a new employee"() {

        given: "Create an 'EmployeeService', the SUT, instance as a Spy"
        def spy = Spy(EmployeeService)

        and: "A stubbed new employee"
        def employee = Stub(Employee) {
            isNew() >> true
        }

        when: "The SUT saves a new employee"
        spy.saveEmployee(employee);

        then: "'createEmployee' is involked once but not other SUT methods are"
        1 * spy.createEmployee(employee) >> {}

        and: "No other methods were invoked"
        _ * spy._
    }
}
