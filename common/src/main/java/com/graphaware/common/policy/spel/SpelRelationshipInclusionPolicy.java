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

package com.graphaware.common.policy.spel;

import com.graphaware.common.policy.RelationshipInclusionPolicy;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.helpers.Predicate;
import org.neo4j.helpers.collection.FilteringIterable;
import org.neo4j.tooling.GlobalGraphOperations;

/**
 * {@link RelationshipInclusionPolicy} based on a SPEL expression. The expression can use methods defined in
 * {@link RelationshipExpressions}.
 * <p/>
 * Note that there are certain methods (like {@link RelationshipExpressions#getOtherNode()}
 * or {@link RelationshipExpressions#isOutgoing()}) that rely on providing
 * a node whose point of view the call is being made. These methods only work when calling {@link #include(org.neo4j.graphdb.Relationship, org.neo4j.graphdb.Node)}.
 * {@link IllegalArgumentException} is thrown when an incompatible method is invoked.
 */
public class SpelRelationshipInclusionPolicy extends SpelInclusionPolicy implements RelationshipInclusionPolicy {

    public SpelRelationshipInclusionPolicy(String expression) {
        super(expression);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean include(Relationship relationship) {
        return (Boolean) exp.getValue(new RelationshipExpressions(relationship));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean include(Relationship relationship, Node pointOfView) {
        return (Boolean) exp.getValue(new RelationshipExpressions(relationship, pointOfView));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Relationship> getAll(GraphDatabaseService database) {
        return new FilteringIterable<>(GlobalGraphOperations.at(database).getAllRelationships(), new Predicate<Relationship>() {
            @Override
            public boolean accept(Relationship item) {
                return include(item);
            }
        });
    }
}
