/*
 * Copyright 2008-2009 Ben Warren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package build;

import static hammer.ant.core.AntXml.fileSet;
import static hammer.ant.core.AntXml.include;
import static hammer.ant.core.AntXml.pathElement;
import hammer.ant.helper.JUnit;
import hammer.xml.Element;

import java.io.File;

public final class TestsImpl implements Tests {
    private static final File TEST_REPORTS_DIR = new File(REPORTS_DIR, "tests");
    private static final File INTERNAL_REPORTS_DIR = new File(TEST_REPORTS_DIR, "internal");
    private static final File INTERNAL_XML_DIR = new File(INTERNAL_REPORTS_DIR, "xml");
    private static final String INTERNAL_FAIL_PROP = "internal.tests.failed";
    private static final Element INTERNAL_TEST_CLASSPATH = TEST_COMPILE_CLASSPATH
        .withElems(pathElement(TEST_CLASS_DIR));
    private static final Element INTERNAL_TEST_FILESET =
        fileSet(TEST_SRC_DIR, include("**/*Test.java"));

    JUnit jUnit;
    Tests me;
    Compile compile;

    public void runTests() {
        compile.compileTest();
        jUnit.run(INTERNAL_TEST_CLASSPATH, INTERNAL_TEST_FILESET, INTERNAL_XML_DIR, INTERNAL_FAIL_PROP);
    }

    public void reportTests() {
        me.runTests();
        jUnit.report(INTERNAL_REPORTS_DIR, INTERNAL_XML_DIR);
    }

    public boolean checkTests() {
        me.runTests();
        me.reportTests();
        return jUnit.check(INTERNAL_FAIL_PROP);
    }
}
