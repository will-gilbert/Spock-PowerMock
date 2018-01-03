package com.gitshah.powermock;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Narrative

/**
 * The class that holds all Spock tests for "Mocking Private Methods (Medium)""
 * @author Will Gilbert
 */

@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest(EmployeeService)

@Narrative("""
From 'Mock Testing with PowerMock' pp 55-59; Mocking "Private Methods (Medium)"

This test differs from Chapter09 in that the access of the EmployeeService 'createEmployee' method has been change from package-private to private.

Private methods are implicitly final.  Spock 'Spy' has no way to implement  final methods.

You could change visibility to protected or package-private or as shown below use PowerMock.

""")

public class EmployeeServiceSpec extends Specification {

    def "SUT should invoke the now PRIVATE 'createEmployee' method when saving a new employee"() {

        given: "A PowerMockito Spy is created from an instance of the SUT"
        def spy = PowerMockito.spy(new EmployeeService())

        and: "A stubbed new employee; Spock stub OK to use"
        def employee = Stub(Employee) {
            isNew() >> true
        }

        and: "Use PowerMock to stub the private method 'createEmployee'"
        PowerMockito.doNothing().when(spy, 'createEmployee', employee)

        when: "Attempt to save the new employee"
        spy.saveEmployee(employee)

        then: "Verify that the SUT stubbed private method was invoked"
        PowerMockito.verifyPrivate(spy).invoke("createEmployee", employee)
    }

}
