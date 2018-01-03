package com.gitshah.powermock;

import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Narrative

/**
 * The class that holds all Spock tests for "Mocking final classes or methods (Simple)""
 * @author Will Gilbert
 */


@Narrative("""
From 'Mock Testing with PowerMock' pp34-37; Mocking "Mocking final classes or methods (Simple)"

Stubbing a final static method.

Static method verification with PowerMock needs to be in the 'when' block
  and not a 'then' block.

""")

@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest([Employee, EmployeeIdGenerator])

@Subject(EmployeeService)
class EmployeeServiceSpec extends Specification {


    def 'Should get the next employee ID if employee is new'() {

        given: "Test parameters; Next ID will be 90"
        def nextId = 90

        and: "Mocked new Employee"
        def mock = Mock(Employee) {
            isNew() >> true
        }

        and: "Stub the static method 'EmployeeIdGenerator.getNextId' to return the test parameter"
        PowerMockito.mockStatic(EmployeeIdGenerator)
        PowerMockito.when(EmployeeIdGenerator.getNextId()).thenReturn(nextId);

        and: "Instance the subject under test (SUT)"
        def employeeService = new EmployeeService();

        when: "A new employee is saved"
        employeeService.saveEmployee(mock);

        and: "The static method 'EmployeeIdGenerator.getNextId' should have been called once"
        PowerMockito.verifyStatic(VerificationModeFactory.times(1))
        EmployeeIdGenerator.getNextId()

        and: "There were no other methods invoked in 'EmployeeIdGenerator'"
        PowerMockito.verifyNoMoreInteractions(EmployeeIdGenerator);

        then: "The method 'employee.setEmployeeId' was invoked once"
        1 * mock.setEmployeeId(nextId)

        then: "...followed by the method 'employee.create' also invoked once"
        1 * mock.create()
    }
}
