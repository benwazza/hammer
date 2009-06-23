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

import static hammer.ant.core.AntXml.classPath;
import static hammer.ant.core.AntXml.fileSet;
import static hammer.ant.core.AntXml.include;
import hammer.ant.helper.Ant;
import hammer.ant.helper.Checkstyle;
import hammer.core.Initialisable;
import hammer.xml.Element;

import java.io.File;

public final class StyleImpl implements BuildConstants, Style, Initialisable {
    private static final File CHECKSYLE_CONFIG_DIR = new File(CONFIG_DIR, "checkstyle");
    private static final File CHECKSTYLE_STYLESHEET =
        new File(CHECKSYLE_CONFIG_DIR, "checkstyle-noframes-sorted.xsl");
    private static final String CORE = "core";
    private static final String TEST = "test";
    private static final String SELF_AND_DEMO = "self-and-demo";
    private static final File CORE_CONFIG = new File(CHECKSYLE_CONFIG_DIR, CORE + ".xml");
    private static final File TEST_CONFIG = new File(CHECKSYLE_CONFIG_DIR, TEST + ".xml");
    private static final File SELF_AND_DEMO_CONFIG = new File(CHECKSYLE_CONFIG_DIR, SELF_AND_DEMO + ".xml");
    private static final File STYLE_REPORTS_DIR = new File(REPORTS_DIR, "style");
    private static final Element CHECKSTYLE_CLASSPATH = classPath(
        fileSet(LIB_DIR, include("tools/checkstyle/*.jar"))
    );

    Checkstyle checkstyle;
    Ant ant;
    Style me;

    // Called by framework once task is registered
    public void init() {
        checkstyle.taskDef(CHECKSTYLE_CLASSPATH);
    }

    public void run() {
        ant.mkDir(STYLE_REPORTS_DIR);
        run(CORE, CORE_SRC_FILESET, CORE_CONFIG);
        run(TEST, TEST_SRC_FILESET, TEST_CONFIG);
        // TODO How to add demo src here??
        run(SELF_AND_DEMO, SELF_SRC_FILESET, SELF_AND_DEMO_CONFIG);
    }

    public void reportStyle() {
        me.run();
        report(CORE);
        report(TEST);
        report(SELF_AND_DEMO);
    }

    public boolean checkStyle() {
        me.run();
        me.reportStyle();
        return
            checkstyle.check(failureProp(CORE)) &&
                checkstyle.check(failureProp(TEST)) &&
                checkstyle.check(failureProp(SELF_AND_DEMO));
    }

    private void run(String srcType, Element srcFileset, File config) {
        File result = resultFile(srcType);
        String failProp = failureProp(srcType);
        checkstyle.run(config, result, failProp, srcFileset);
    }

    private void report(String srcType) {
        File result = resultFile(srcType);
        File report = reportFile(srcType);
        checkstyle.report(result, report, CHECKSTYLE_STYLESHEET);
    }

    private String failureProp(String srcType) {
        return srcType + ".checkstyle.failed";
    }

    private File reportFile(String srcType) {
        return new File(STYLE_REPORTS_DIR, srcType + "-style.html");
    }

    private File resultFile(String srcType) {
        return new File(STYLE_REPORTS_DIR, srcType + "-style.xml");
    }
}