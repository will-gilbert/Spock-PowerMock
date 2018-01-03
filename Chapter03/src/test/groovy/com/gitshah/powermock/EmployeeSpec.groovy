package com.gitshah.powermock

import spock.lang.Specification
import spock.lang.Narrative

/**
 * The class that holds all Spock tests for "Mocking static methods (Simple)""
 * @author Will Gilbert
 */

@Narrative("""
From 'Mock Testing with PowerMock' pp 27-28; Mocking "Mocking static methods (Simple)"

Stubbing non static methods of a class.

""")

class EmployeeSpec extends Specification {

    def 'Concrete class throws an exception when saved'() {

        given: "Create a concrete class"
        def employee = new Employee()

        when: "Invoke the concrete 'save' method"
        employee.save()

        then: "The expect exception is thrown"
        thrown(UnsupportedOperationException)
    }


    def 'Stubbed method does not throw an exception when saved'() {

        given: "Create a stubbed class with the 'save' method stubbed to do nothing"
        def employee = Stub(Employee) {
            save() >> {}
        }

        when: "Invoke the stubbed 'save' method"
        employee.save()

        then: "Nothing happens; We supressed the throwing of the exception"
        notThrown(UnsupportedOperationException)
    }


    def 'Stubbed method throws an alternate exception when saved'() {

        given: "Create a stubbed class with the 'save' method stubbed to throw an alternate exception"
        def employee = Stub(Employee) {
            save() >> { throw new IllegalStateException() }
        }

        when: "Invoke the stubbed 'save' method"
        employee.save()

        then: "The alternate exception was thrown"
        thrown(IllegalStateException)
    }
}
