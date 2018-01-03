package com.gitshah.powermock

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for the EmployeeController class.
 * @author Will gilbert
 */

@Narrative("""
From 'Mock Testing with PowerMock' pp42-47; Mocking "Understanding Argument Matchers (Medium)"

- Spock's answer to PowerMock 'Answer'

""")

@Subject(EmployeeController)
class EmployeeControllerSpec extends Specification {


    def "Should find an employee by startsWith email"() {

        given: "Stubbed employee and service"
        def employeeService = Stub(EmployeeService)
        def employee = Stub(Employee)

        and: "Looking and employee by email will return the stub if starts with 'deep', null otherwise"
        employeeService.findEmployeeByEmail(_ as String) >> { args ->
            def email = args[0]
            email.startsWith('deep') ? employee : null
        }

        when: "Insnatce the SUT and inject the stubbed service"
        def employeeController = new EmployeeController(employeeService)

        then: "test some valid email addresses"
        employee == employeeController.findEmployeeByEmail('deep@gitshah.com')
        employee == employeeController.findEmployeeByEmail('deep@packtpub.com')

        and: "and some invalid email addresses"
        null == employeeController.findEmployeeByEmail("noreply@packtpub.com")
    }


    def "Should return null for any email"() {

        given: "The service returns 'null' for any e-mail address"
        def employeeService = Stub(EmployeeService) {
            findEmployeeByEmail(_ as String) >> null
        }

        when: "Instance the SUT with the stubbed service"
        def employeeController = new EmployeeController(employeeService);

        then: "Test some email addresses; all return null"
        null == employeeController.findEmployeeByEmail('deep@gitshah.com')
        null == employeeController.findEmployeeByEmail('deep@packtpub.com')
        null == employeeController.findEmployeeByEmail('noreply@packtpub.com')
    }


    def "Should return true if employee email is already taken"() {

        given: "A pre-esisting e-mail address"
        def employeeEmail = 'packt@gitshah.com';

        and: "a mocked 'EmployeeService' which claims the test e-mail exists"
        def employeeService = Stub(EmployeeService) {
                employeeExists(_ as Employee) >> { args ->
                def employee = args[0]
                employee.getEmail() == employeeEmail
            }
        }
            
        when: "Inject the mocked 'EmployeeService' into the SUT"
        def employeeController = new EmployeeController(employeeService)

        then: "The controller test checking the existing e-mail returns true"
        employeeController.isEmployeeEmailAlreadyTaken(employeeEmail)
    }
}
