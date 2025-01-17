/*
 * Copyright (c) 2013-2016 GraphAware
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

package com.graphaware.runtime.config.function;

import com.graphaware.common.policy.InclusionPolicy;
import org.neo4j.function.Function;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * A {@link Function} that converts String to {@link com.graphaware.common.policy.InclusionPolicy}.
 * <p/>
 * Converts a fully qualified class name to an instance of the class, or a SPEL expression to {@link com.graphaware.common.policy.InclusionPolicy}.
 */
public abstract class StringToInclusionPolicy<T extends InclusionPolicy> implements Function<String, T> {

    private static final Pattern CLASS_NAME_REGEX = Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");

    /**
     * {@inheritDoc}
     */
    @Override
    public T apply(String s) {
        if (CLASS_NAME_REGEX.matcher(s).matches()) {
            try {
                Class<?> clazz = Class.forName(s);

                //try singleton
                try {
                    Method method = clazz.getMethod("getInstance", new Class[0]);
                    return compositePolicy((T) method.invoke(clazz, new Object[0]));
                } catch (NoSuchMethodException e) {
                    //ok, try constructor
                }

                //try constructor
                return compositePolicy((T) clazz.newInstance());

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return compositePolicy(spelPolicy(s));
    }

    /**
     * Instantiate a composite policy from IncludeAllBusiness* policy and the given policy.
     *
     * @param policy second policy in the composite.
     * @return composite policy.
     */
    protected abstract T compositePolicy(T policy);

    /**
     * Instantiate a new SPEL-bases policy.
     *
     * @param spel expression.
     * @return policy.
     */
    protected abstract T spelPolicy(String spel);
}
