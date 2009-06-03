Hammer Developer Readme
=======================

Building
--------
Hammer builds itself...

JDK 6 or greater required.

Unix: ./build.sh
 Win: Somebody write one please!
 IDE: IntelliJ, Somebody make an Eclipse config please!

The default task is 'all' which runs and builds everything. The build can
also be run from IntelliJ by running the 'all' run configuration. In fact,
when making API changes this is often the only way it will run! To update the
bootstrap jars that build Hammer itself run the 'updateBootstrap' task or
run configuration. See below for setting up Intellij config files.

IntelliJ
--------
Intellij project files can be found in ./config/intellij. Copy them to the
root directory and your're ready to go.

Documentation
-------------
* See the Hammer wiki at http://wiki.github.com/benwazza/hammer
* Simple examples of use can be found in ./src/demo
* The Hammer build in ./src/self is the best place to go for more complex
  examples

Layout
------
* All source files are split up into their components under the 'src' dir
* All generated files including class files go into the 'gen' directory
  This directory is deleted by the 'clean' target
* Open ./gen/artifacts/index.html for an overview of the main project
  reports and generated files
* All build reports can be found in the ./gen/artifacts/reports/ directory.
* The self contained zip and tar.gz distributions of hammer are built into
  the ./gen/artifacts/installs/ directory
* The individual jars including source can be found in the
  ./gen/artifacts/jars/ directory
* The Hammer runtime dependant jars are the boost and ant jars in the ./lib
  directory. All build tool jars (Simian/JUnit/JMock etc) are in the
  ./lib/tools/ directory
