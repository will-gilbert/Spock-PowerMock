package com.gitshah.powermock;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Narrative

/**
 * The class that holds all Spock tests for "Supressing unwanted behavior (Advanced)"
 * @author Will Gilbert
 */

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Sputnik)
@PrepareForTest(Department.class)
@SuppressStaticInitializationFor("com.gitshah.powermock.BaseEntity")

@Narrative("""
From 'Mock Testing with PowerMock' pp 64-67; Mocking "Supressing unwanted behavior (Advanced)"


""")

class DepartmentSpec extends Specification {

    def "Should verify that max salary offered for a department is calculated correctly"() {

        given: "Supress invoking the super class constructor of 'Department'"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity))

        and: "Instance a department with the no-args constructor; The super class should be supressed"
        def department = new Department()

        and: "Adding two employees to our test employees list"
        def employees = []
        employees.add(new Employee(salary:60000))
        employees.add(new Employee(salary:65000))

        and: "Substituting the privately held employees list with our test list"
        department.employees = employees

        when: "Invoking the private method 'updateMaxSalaryOffered' on the department instance"
        department.updateMaxSalaryOffered()

        then: "The max salary is updated as a private field"
        65000 == department.maxSalaryOffered
    }


    def "Should suppress the base constructor of department"() {

        given: "Supress invoking the super class constructor of 'Department'"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity))

        when: "Instance a department with the int constructor; The super class should be supressed"
        def department = new Department(10)

        then: "The super class constructor is not invoked"
        10 == department.getDepartmentId()
    }

    def "Should suppress the 'performAudit' method of super class"() {

        given: "Supress invoking the super class constructor of 'Department'"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity));

        and: "Supress the invocation of the super class method 'performAudit'"
        PowerMockito.suppress(PowerMockito.method(BaseEntity, 'performAudit', String))

        and: "Instance a department with the no-args constructor; The super class should be supressed"
        def department = new Department();

        when: "Set the department name; which invokes the super class method 'performAudit'"
        department.setName("Mocking with PowerMock");

        then: "No exception is throw but the name is set"
        'Mocking with PowerMock' == department.getName()
    }

    def "Should suppress the default initializer for BaseEntity"() {

        given: "Supress invoking the super class constructor of 'Department'"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity));

        when: "Instance a department with the no-args constructor; The super class should be supressed"
        def department = new Department()

        then: "The department is instanced with defualt values for id and name"
        department != null
        department.departmentId == 0
        department.name == null
    }

    def "Should suppress the field 'identifier' for BaseEntity"() {

        given: "Supress invoking the super class constructor of 'Department'"
        PowerMockito.suppress(PowerMockito.constructor(BaseEntity));

        and: "Prevent the static long 'identifier' field from throwing an exception"
        PowerMockito.suppress(PowerMockito.field(BaseEntity, 'identifier'));

        when: "Instance a department with the no-args constructor; The super class should be supressed"
        def department = new Department()

        then: "The department is instanced with defualt values for id and name"
        department != null
        department.departmentId == 0
        department.name == null
    }
}
