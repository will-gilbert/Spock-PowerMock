package com.gitshah.powermock;

import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;

import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate

import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Narrative
import spock.lang.Subject


/**
 * The class that holds all Spock tests for "Supressing unwanted behavior (Advanced)"
 * @author Will Gilbert
 */

@Narrative("""
From 'Mock Testing with PowerMock' pp 67-68; Mocking "Supressing unwanted behavior (Advanced)"

- Suppress a static initializer

""")

@RunWith(PowerMockRunner)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest(Department)

@SuppressStaticInitializationFor("com.gitshah.powermock.BaseEntity")

@Subject(Department.class)
public class DepartmentSpec extends Specification {

    def "PowerMock: Should return net worth of the department"() {

        given: "Create the expected result"
        def expectedNetWorth = 90000

        and: "Suppress the super class constructor"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity));

        and: "Get the 'calculateTotalNetWorth' method and stub it"
        def method = Whitebox.getMethod(Department, "calculateTotalNetWorth")

        and: "Stub a method from a conrete class; Similar to using a Spy"
        PowerMockito.stub(method).toReturn(expectedNetWorth)

        and: "Instance the SUT"
        def department = new Department()

        when: "Invoke the stubbed method 'calculateTotalNetWorth'"
        def netWorth = department.calculateTotalNetWorth()

        then: "Verify the expected results"
        netWorth == expectedNetWorth
    }


    def "Spock: Should return net worth of the department"() {

        given: "Create the expected result"
        def expectedNetWorth = 90000

        and: "Suppress the super class constructor"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity));

        and: "Use a Spock Spy to stub the method 'calculateTotalNetWorth'"
        def department = Spy(Department) {
            calculateTotalNetWorth() >> expectedNetWorth
        }

        when: "Invoke the stubbed method 'calculateTotalNetWorth'"
        def netWorth = department.calculateTotalNetWorth()

        then: "Verify the expected results"
        netWorth == expectedNetWorth
    }
}