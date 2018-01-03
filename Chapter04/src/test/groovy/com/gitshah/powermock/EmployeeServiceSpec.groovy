package com.gitshah.powermock

import org.junit.runner.RunWith

import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.powermock.modules.junit4.PowerMockRunner

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for "Verifying method invocation (Simple)""
 * @author Will Gilbert
 */

@Narrative("""
From 'Mock Testing with PowerMock' pp 28-33; Mocking "Verifying method invocation (Simple)"

How to setup Mock method for subbing and verification. 

Hint: Everything goes in the 'then' blocks

""")

@Subject(EmployeeService)
class EmployeeServiceSpec extends Specification {

    def "Should create new employee if employee is new"() {

        given: "Mocked Employee which is new"
        def mock = Mock(Employee) {
            isNew() >> true
        }

        and: "Instance the subject under test (SUT)"
        def employeeService = new EmployeeService()

        when: "A new employee is saved"
        employeeService.saveEmployee(mock)

        then: "Create method was invoked for a new employee"
        1 * mock.create()

        and: "Update method was not invoked for a new employee"
        0 * mock.update()
    }


    def "Should update employee if employee exists"() {

        given: "Mocked Employee which is not new i.e. exiting"
        def mock = Mock(Employee) {
            isNew() >> false
        }

        and: "Instance the subject under test (SUT)"
        def employeeService = new EmployeeService()
        
        when: "An existing employee is saved"
        employeeService.saveEmployee(mock)

        then: "Create method was not invoked for an existing employee"
        0 * mock.create()

        and: "Update method was invoked for an existing employee"
        1 * mock.update()
    }


    def "Should invoke 'isNew' before invoking create; Moved the 'isNew' method to a 'then' block"() {

        given: "Mocked Employee which is new"
        def mock = Mock(Employee)

        and: "Instance the subject under test (SUT)"
        def employeeService = new EmployeeService()
        
        when: "A new employee is saved"
        employeeService.saveEmployee(mock)

        then: "The isNew method is invoked first; NB pattern must match given"
        1 * mock.isNew() >> true

        then: "Followed by the create method"
        1 * mock.create()

        and: "The update method is not invoked"
        0 * mock.update()
    }
}
