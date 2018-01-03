package com.gitshah.powermock;

import org.powermock.core.spi.PowerMockPolicy;
import org.powermock.mockpolicies.MockPolicyClassLoadingSettings;
import org.powermock.mockpolicies.MockPolicyInterceptionSettings;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

/**
 * The mock policy to handle the Department class.
 * This policy will suppress the static initializer,
 * suppress the performAudit
 * and stub the calculateTotalNetWorth methods.
 * The class custom MockPolicy needs to implement the PowerMockPolicy interface.
 * @author Deep Shah
 */
public class DepartmentMockPolicy implements PowerMockPolicy {

    /**
     * In this method we have to inform PowerMock about the classes whose static initializer need to be suppressed.
     * We have to also inform about any other class that needs to be prepared for test i.e. any class that we pass
     * as argument to the @PrepareForTest annotation is a valid candidate.
     * {@inheritDoc}
     */
    @Override
    public void applyClassLoadingPolicy(final MockPolicyClassLoadingSettings settings) {
        //Specifying the static initializer to be suppressed for BaseEntity.
        settings.addStaticInitializersToSuppress(BaseEntity.class.getName());

        //And we need to prepare the Department class for mocking.
        settings.addFullyQualifiedNamesOfClassesToLoadByMockClassloader(Department.class.getName());
    }

    /**
     * In this method we need to actually suppress/stub the methods that we intend to intercept.
     * {@inheritDoc}
     */
    @Override
    public void applyInterceptionPolicy(final MockPolicyInterceptionSettings settings) {
        //Suppressing the performAudit method so that we will not get any exception when it is called.
        final Method performAudit = Whitebox.getMethod(Department.class, "performAudit", String.class);
        settings.addMethodsToSuppress(performAudit);

        //Stub the calculateTotalNetWorth method to return 90000.
        final Method getNetMethod = Whitebox.getMethod(Department.class, "calculateTotalNetWorth");
        settings.stubMethod(getNetMethod, 90000);

    }
}
