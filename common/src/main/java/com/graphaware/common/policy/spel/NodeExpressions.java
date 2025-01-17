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

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;

import java.util.LinkedList;
import java.util.List;

import static org.neo4j.graphdb.Direction.valueOf;
import static org.neo4j.graphdb.DynamicRelationshipType.withName;

/**
 * {@link PropertyContainerExpressions} for {@link Node}s.
 */
class NodeExpressions extends PropertyContainerExpressions<Node> {

    NodeExpressions(Node node) {
        super(node);
    }

    public int getDegree() {
        return propertyContainer.getDegree();
    }

    public int getDegree(String typeOrDirection) {
        try {
            return propertyContainer.getDegree(valueOf(typeOrDirection.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return propertyContainer.getDegree(withName(typeOrDirection));
        }
    }

    public int getDegree(String type, String direction) {
        return propertyContainer.getDegree(withName(type), valueOf(direction.toUpperCase()));
    }

    public boolean hasLabel(String label) {
        return propertyContainer.hasLabel(DynamicLabel.label(label));
    }

    public String[] getLabels() {
        List<String> labels = new LinkedList<>();
        for (Label label : propertyContainer.getLabels()) {
            labels.add(label.name());
        }
        return labels.toArray(new String[labels.size()]);
    }
}
