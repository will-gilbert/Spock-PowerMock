package com.gitshah.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds all Spock tests for the EmployeeController class.
 * @author Will Gilbert
 */

@Narrative("""
From 'Mock Testing with PowerMock' pp48-51; Mocking "Understanding Argument Matchers (Advanced)"

- Spock's answer to PowerMock 'Answer'; Cascade if/else

""")

@Subject(EmployeeController)
class EmployeeControllerSpec extends Specification{


    def "Should find employee by e-mail using an if/else answer cascade"() {

        given: "A stubbed employee "
        def employee = Stub(Employee)

        and: "A stubbed service mehtod which contains some if/else logic"
        def employeeService = Stub(EmployeeService) {
            findEmployeeByEmail(_ as String) >> { args ->
                def email = args[0]
               
                if(email == null)
                    return null
                else if( email.startsWith('deep') || email.endsWith('packtpub.com') ) 
                    return employee
                else
                    return null
            }            
        }


        when: "Instance the SUT and inject the stubbed service"
        def employeeController = new EmployeeController(employeeService)

        then: "Test some valid e-mails"
        employee == employeeController.findEmployeeByEmail('deep@gitshah.com')
        employee == employeeController.findEmployeeByEmail('deep@packtpub.com')
        employee == employeeController.findEmployeeByEmail('noreply@packtpub.com')

        and: "test some invalid e-mails"
        null == employeeController.findEmployeeByEmail('hello@world.com')
    }


}
