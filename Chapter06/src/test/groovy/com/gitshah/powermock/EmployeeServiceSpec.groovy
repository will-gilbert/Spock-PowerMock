package com.gitshah.powermock

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject

/**
 * The class that holds the Spock tests for the EmployeeService class.
 * @author Will Gilbert
 *
 * EmployeeService is the SUT but it needs to be prepared by Powermock
 *   because the mocked constuctor is involked from it.
 */

@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest([EmployeeIdGenerator, WelcomeEmail, EmployeeService])

@Narrative("""
From 'Mock Testing with PowerMock' pp37-41; Mocking "Mocking Constructors (Medium)"

Mocking a constructor

""")

@Subject(EmployeeService)
class EmployeeServiceSpec extends Specification {


    def "Should send welcome email to new employees"()  {

        given: "A stubbed employee Spock stub; is OK to use"
        def employee = Stub(Employee) {
            isNew() >> true
        }

        and: "Stub the EmployeeIdGenerator to return the default integer, 0"
        PowerMockito.mockStatic(EmployeeIdGenerator)

        and: "Creating the mock for the WelcomeEmail class"
        def welcomeEmail = Mock(WelcomeEmail)

        /**
         *  Notice the 'whenNew' syntax
         *
         *    PowerMockito.whenNew().withArguments().thenReturn() 
         *
         *  informs PowerMock that,
         *
         *  when a new instance of WelcomeEmail is created,
         *    with the stubbed employee instance and "Welcome to Mocking with PowerMock How-to!" text,
         *    then return a mock of 'EmployeeEmailService' class.
         */

         and: "Stub the 'WelcomeEmail' constructor"
        PowerMockito.whenNew(WelcomeEmail)
            .withArguments(employee, "Welcome to Mocking with PowerMock How-to!")
            .thenReturn(welcomeEmail)

        and: "Instance the SUT, EmployeeService (Prepared by PowerMock)"
        def employeeService = new EmployeeService()

        when: "Attempt to save the new employee"
        employeeService.saveEmployee(employee)

        then: "Verifying that the constructor for the WelcomeEmail class is invoked"
        PowerMockito
            .verifyNew(WelcomeEmail)
            .withArguments(employee, "Welcome to Mocking with PowerMock How-to!")

        and: "Verifying that the send method was called on the mocked instance"
        1 * welcomeEmail.send()
    }

}
