<a name="top"/>
GraphAware Neo4j Framework
==========================

[![Build Status](https://travis-ci.org/graphaware/neo4j-framework.png)](https://travis-ci.org/graphaware/neo4j-framework) | <a href="http://graphaware.com/downloads/" target="_blank">Downloads</a> | <a href="http://graphaware.com/site/framework/latest/apidocs/" target="_blank">Javadoc</a> | Latest Release: 2.3.1.37

GraphAware Framework speeds up development with <a href="http://neo4j.org" target="_blank">Neo4j</a> by providing a
platform for building useful generic as well as domain-specific functionality, analytical capabilities, (iterative) graph algorithms,
etc.

See the <a href="http://graphaware.com/neo4j/2014/05/28/graph-aware-neo4j-framework.html" target="_blank">announcement on our blog</a>.

Upgrading to Neo4j 2.3
----------------------

Please note that a number of things have changed in Neo4j 2.3 and consequently in the GraphAware Framework. Notably:
* There is now only one .jar file for both enterprise and community deployments.
* You need to add `org.neo4j.server.thirdparty_jaxrs_classes=com.graphaware.server=/graphaware` to `neo4j-server.properties` to make the Framework work.

Features Overview
-----------------

On a high level, there are two key pieces of functionality:
* [GraphAware Server](server) is a Neo4j server extension that allows developers to rapidly build (REST) APIs
on top of Neo4j using Spring MVC, rather than JAX-RS
* [GraphAware Runtime](runtime) is a runtime environment for both embedded and server deployments, which
allows the use of pre-built as well as custom modules called [GraphAware Runtime Modules](runtime). These
modules typically extend the core functionality of the database by
    * transparently enriching/modifying/preventing ongoing transactions in real-time
    * performing continuous computations on the graph in the background

Whether or not you use the code in this repository as a framework or runtime as described above, you can always use it
 as a software library by adding it as a dependency to your project, and take advantage of its useful features.
 For Java developers only(1), the following functionality is provided:

* [GraphAware Test](tests)
    * [GraphUnit](tests#graphunit) - simple graph unit-testing
    * [Integration Testing](tests#inttest) - support for integration testing
    * [Performance Testing](tests#perftest) - support for performance testing
* [Improved Neo4j Transaction API](tx-api)
* [Transaction Executor](tx-executor) and [Batch Transaction Executor](tx-executor#batch-tx)
* [Database Writer](writer)
* [Miscellaneous Utilities](common)

(1) i.e., for embedded mode users, managed/unmanaged extensions developers, [GraphAware Runtime Module](#runtime)
 developers and framework-powered Spring MVC controller developers

Please take a look at the provided [examples](examples).

Framework Usage
---------------

<a name="servermode"/>
### Server Mode

When using Neo4j in the <a href="http://docs.neo4j.org/chunked/stable/server-installation.html" target="_blank">standalone server</a> mode,
deploying the GraphAware Framework (and any code using it) is a matter of :
* [downloading](#download) the appropriate .jar files
* copying them into the _plugins_ directory in your Neo4j installation
* adding `org.neo4j.server.thirdparty_jaxrs_classes=com.graphaware.server=/graphaware` to `neo4j-server.properties`
* restarting the server

The framework and modules are then used via calls to their REST APIs, if they provide any.

### Embedded Mode / Java Development

Java developers that use Neo4j in <a href="http://docs.neo4j.org/chunked/stable/tutorials-java-embedded.html" target="_blank">embedded mode</a>
and those developing Neo4j <a href="http://docs.neo4j.org/chunked/stable/server-plugins.html" target="_blank">server plugins</a>,
<a href="http://docs.neo4j.org/chunked/stable/server-unmanaged-extensions.html" target="_blank">unmanaged extensions</a>,
[GraphAware Runtime Modules](runtime), or Spring MVC controllers can include use the framework as a dependency
for their Java project and use it as a library of useful tested code, in addition to the functionality provided for
[server mode](#servermode).

<a name="download"/>
Getting GraphAware Framework
----------------------------

### Releases

To use the latest release, download the appropriate version and put it
the _plugins_ directory in your Neo4j server installation and restart the server (server mode), or on the classpath (embedded mode).

The following downloads are available:
* [GraphAware Framework for Embedded Mode, version 2.3.1.37](http://graphaware.com/downloads/graphaware-embedded-all-2.3.1.37.jar)
* [GraphAware Framework for Server Mode, version 2.3.1.37](http://graphaware.com/downloads/graphaware-server-all-2.3.1.37.jar)

Releases are synced to <a href="http://search.maven.org/#search%7Cga%7C1%7Ccom.graphaware.neo4j" target="_blank">Maven Central
repository</a>. When using Maven for dependency management, include one or more dependencies in your pom.xml. To find out
 which ones, read further by clicking on one of the sub-modules of this project.

### Snapshots

To use the latest development version, just clone this repository and run `mvn clean install`. This will produce 2.3.1.38-SNAPSHOT
jar files. If you need standalone .jar files with all dependencies, look into the `target` folders in the `build` directory.

### Note on Versioning Scheme

The version number has two parts. The first three numbers indicate compatibility with a Neo4j version.
 The last number is the version of the framework. For example, version 2.2.0.28 is version 28 of the framework
 compatible with Neo4j 2.2.0


Data Collection
---------------

Note that framework sends a POST request to Google Analytics every 5 minutes after start just so we know how many people
actually use it. The only data transmitted are module class names, version and edition of the framework,
and an anonymous UUID that changes with restart.


License
-------

Copyright (c) 2013-16 GraphAware

GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.
