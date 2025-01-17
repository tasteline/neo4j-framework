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

package com.graphaware.tx.executor.input;

import com.graphaware.tx.executor.single.TransactionCallback;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.helpers.collection.Iterables;

/**
 * {@link TransactionalInput} returning all nodes with a specific label.
 */
public final class AllNodesWithLabel  extends TransactionalInput<Node> {

    /**
     * Create a new input.
     *
     * @param database  to take all nodes from.
     * @param batchSize how many nodes in a batch.
     * @param label which all returned nodes have.
     */
    public AllNodesWithLabel(GraphDatabaseService database, int batchSize, final Label label) {
        super(database, batchSize, new TransactionCallback<Iterable<Node>>() {
            @Override
            public Iterable<Node> doInTransaction(GraphDatabaseService database) throws Exception {
                return Iterables.asResourceIterable(database.findNodes(label));
            }
        });
    }
}
