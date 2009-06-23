/*
 *  Copyright 2008-2009 Ben Warren
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package build;

import static hammer.ant.core.AntXml.classPath;
import static hammer.ant.core.AntXml.fileSet;
import static hammer.ant.core.AntXml.include;
import hammer.ant.helper.Simian;
import hammer.core.Initialisable;
import hammer.xml.Element;

import java.io.File;

public final class DupeImpl implements Dupe, BuildConstants, Initialisable {
    private static final File SIMIAN_CONFIG_DIR = new File(CONFIG_DIR, "simian");
    private static final File SIMIAN_XSL = new File(SIMIAN_CONFIG_DIR, "simian.xsl");
    private static final File DUPE_REPORTS_DIR = new File(REPORTS_DIR, "dupe");
    private static final File DUPE_RESULT = new File(DUPE_REPORTS_DIR, "dupe.xml");
    private static final File DUPE_REPORT = new File(DUPE_REPORTS_DIR, "dupe.html");
    private static final String DUPE_FAILED = "dupe.failed";
    private static final int LINES_THRESHOLD = 4;
    private static final Element[] ALL_SRC_FILES = {
        CORE_SRC_FILESET,
        TEST_SRC_FILESET,
        DEMO_SRC_FILESET,
        SELF_SRC_FILESET
    };
    private static final Element SIMIAN_CLASSPATH = classPath(
        fileSet(LIB_DIR, include("tools/simian/simian*.jar"))
    );

    Simian simian;
    Dupe me;

    public void init() {
        simian.taskDef(SIMIAN_CLASSPATH);
    }

    public void run() {
        simian.run(ALL_SRC_FILES, DUPE_RESULT, DUPE_FAILED, LINES_THRESHOLD);
    }

    public void reportDupe() {
        me.run();
        simian.report(DUPE_RESULT, SIMIAN_XSL, DUPE_REPORT);
    }

    public boolean checkDupe() {
        me.run();
        me.reportDupe();
        return simian.check(DUPE_FAILED);
    }
}