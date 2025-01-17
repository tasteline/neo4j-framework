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

package com.graphaware.common.policy.fluent;

import com.graphaware.common.description.property.DetachedPropertiesDescription;
import com.graphaware.common.policy.RelationshipInclusionPolicy;
import com.graphaware.common.util.DirectionUtils;
import org.neo4j.graphdb.*;
import org.parboiled.common.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract base class for {@link RelationshipInclusionPolicy} implementations with fluent interface,
 * intended to be used programmatically.
 */
public abstract class BaseIncludeRelationships<T extends BaseIncludeRelationships<T>> extends IncludePropertyContainers<T, Relationship> implements RelationshipInclusionPolicy {

    private final Direction direction;
    private final RelationshipType[] relationshipTypes;

    /**
     * Create a new policy.
     *
     * @param direction             that matching relationships must have, {@link Direction#BOTH} for both.
     * @param relationshipTypes     one of which the matching relationships must have, empty for all.
     * @param propertiesDescription of the matching relationships.
     */
    protected BaseIncludeRelationships(Direction direction, RelationshipType[] relationshipTypes, DetachedPropertiesDescription propertiesDescription) {
        super(propertiesDescription);

        if (direction == null) {
            throw new IllegalArgumentException("Direction must not be null");
        }

        if (relationshipTypes == null) {
            throw new IllegalArgumentException("RelationshipTypes must not be null");
        }

        this.direction = direction;
        this.relationshipTypes = relationshipTypes;
    }

    /**
     * Create a new policy from the current one, reconfigured to only match relationships with the given relationship types.
     *
     * @param relationshipTypes one of which the matching relationships must have, empty for all.
     * @return reconfigured policy.
     */
    public T with(String... relationshipTypes) {
        return with(stringsToTypes(relationshipTypes));
    }

    /**
     * Create a new policy from the current one, reconfigured to only match relationships with the given relationship types.
     *
     * @param relationshipTypes one of which the matching relationships must have, empty for all.
     * @return reconfigured policy.
     */
    public T with(RelationshipType... relationshipTypes) {
        return with(getDirection(), relationshipTypes);
    }

    /**
     * Create a new policy from the current one, reconfigured to only match relationships with the given direction.
     *
     * @param direction that matching relationships must have, {@link Direction#BOTH} for both.
     * @return reconfigured policy.
     */
    public T with(Direction direction) {
        return with(direction, getRelationshipTypes());
    }

    /**
     * Create a new policy from the current one, reconfigured to only match relationships with the given direction and relationship types.
     *
     * @param direction         that matching relationships must have, {@link Direction#BOTH} for both.
     * @param relationshipTypes one of which the matching relationships must have, empty for all.
     * @return reconfigured policy.
     */
    public T with(Direction direction, String... relationshipTypes) {
        return with(direction, stringsToTypes(relationshipTypes));
    }

    /**
     * Create a new policy from the current one, reconfigured to only match relationships with the given direction and relationship types.
     *
     * @param direction         that matching relationships must have, {@link Direction#BOTH} for both.
     * @param relationshipTypes one of which the matching relationships must have, empty for all.
     * @return reconfigured policy.
     */
    public T with(Direction direction, RelationshipType... relationshipTypes) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction must not be null");
        }

        if (relationshipTypes == null) {
            throw new IllegalArgumentException("RelationshipTypes must not be null");
        }

        return newInstance(direction, relationshipTypes);
    }

    /**
     * Create a new instance of this policy with the given direction and relationship types.
     *
     * @param direction         of the new policy.
     * @param relationshipTypes of the new policy.
     * @return new policy.
     */
    protected abstract T newInstance(Direction direction, RelationshipType... relationshipTypes);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean include(Relationship relationship) {
        if (relationshipTypes == null || relationshipTypes.length == 0) {
            return super.include(relationship);
        }

        for (RelationshipType type : relationshipTypes) {
            if (relationship.isType(type)) {
                return super.include(relationship);
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean include(Relationship relationship, Node pointOfView) {
        return include(relationship)
                && DirectionUtils.matches(this.direction, DirectionUtils.resolveDirection(relationship, pointOfView));
    }

    /**
     * Get the direction with which this policy has been configured.
     *
     * @return direction, never null.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the relationship types with which this policy has been configured.
     *
     * @return types, never null.
     */
    public RelationshipType[] getRelationshipTypes() {
        return relationshipTypes;
    }

    /**
     * Convert relationship types represented as strings to {@link RelationshipType} objects.
     *
     * @param relationshipTypes to convert.
     * @return converted.
     */
    private RelationshipType[] stringsToTypes(String[] relationshipTypes) {
        List<RelationshipType> types = new LinkedList<>();

        for (String type : relationshipTypes) {
            if (type == null || StringUtils.isEmpty(type)) {
                throw new IllegalArgumentException("Empty and null relationships types are not supported");
            }
            types.add(DynamicRelationshipType.withName(type));
        }

        return types.toArray(new RelationshipType[types.size()]);
    }

    /**
     * Convert relationship types represented as  {@link RelationshipType} objects to strings.
     *
     * @param relationshipTypes to convert.
     * @return converted.
     */
    private String[] typesToStrings(RelationshipType[] relationshipTypes) {
        List<String> strings = new LinkedList<>();

        for (RelationshipType type : relationshipTypes) {
            if (type == null) {
                throw new IllegalArgumentException("Null relationships types are not supported");
            }
            strings.add(type.name());
        }

        return strings.toArray(new String[strings.size()]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BaseIncludeRelationships that = (BaseIncludeRelationships) o;

        if (direction != that.direction) return false;
        if (!Arrays.equals(typesToStrings(relationshipTypes), typesToStrings(that.relationshipTypes))) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + Arrays.hashCode(typesToStrings(relationshipTypes));
        return result;
    }
}
