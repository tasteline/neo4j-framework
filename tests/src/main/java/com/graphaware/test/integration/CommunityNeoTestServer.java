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

package com.graphaware.test.integration;

import org.neo4j.server.Bootstrapper;
import org.neo4j.server.CommunityBootstrapper;

/**
 * Community {@link NeoTestServer}
 */
public class CommunityNeoTestServer extends NeoTestServer {

    public CommunityNeoTestServer() {
    }

    public CommunityNeoTestServer(String neo4jConfigFile, String neo4jServerConfigFile) {
        super(neo4jConfigFile, neo4jServerConfigFile);
    }

    @Override
    protected Bootstrapper createBootstrapper() {
        return new CommunityBootstrapper();
    }
}
