/*
 * Copyright (c) 2013-2015 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.runtime.config;

import com.graphaware.common.policy.InclusionPolicies;
import com.graphaware.runtime.policy.InclusionPoliciesFactory;

/**
 * {@link TxDrivenModuleConfiguration} with fluent interface.
 * Intended for users of Neo4j in embedded mode to programatically configure the runtime.
 */
public final class FluentTxDrivenModuleConfiguration extends BaseTxDrivenModuleConfiguration<FluentTxDrivenModuleConfiguration> {

    /**
     * Creates an instance with default values, i.e., with {@link com.graphaware.runtime.policy.InclusionPoliciesFactory#allBusiness()}.
     *
     * @return The {@link FluentRuntimeConfiguration} instance.
     */
    public static FluentTxDrivenModuleConfiguration defaultConfiguration() {
        return new FluentTxDrivenModuleConfiguration();
    }

    /**
     * Create a new configuration with {@link com.graphaware.runtime.policy.InclusionPoliciesFactory#allBusiness()}.
     */
    private FluentTxDrivenModuleConfiguration() {
        super(InclusionPoliciesFactory.allBusiness());
    }

    /**
     * Create a new configuration.
     *
     * @param inclusionPolicies of the configuration.
     */
    private FluentTxDrivenModuleConfiguration(InclusionPolicies inclusionPolicies) {
        super(inclusionPolicies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FluentTxDrivenModuleConfiguration newInstance(InclusionPolicies inclusionPolicies) {
        return new FluentTxDrivenModuleConfiguration(inclusionPolicies);
    }
}
