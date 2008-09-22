/*
 * Copyright 2008 Ben Warren
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

package hammer.ant.helper;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.xml.Element;

import java.io.File;

public final class JUnitImpl implements JUnit {
    Ant ant;

    public void run(Element classpath, Element fileSet, File xmlReportDir, String failureProperty) {
        ant.mkDir(xmlReportDir);
        ant.run(
            e("junit", a("fork", "true"),
                a("forkMode", "perBatch"),
                a("showoutput", "true"),
                a("printSummary", "true"),
                a("haltonfailure", "false"),
                a("failureproperty", failureProperty),
                e("formatter", a("type", "xml")),
                e("formatter", a("type", "plain")),
                e("batchtest", a("todir", xmlReportDir),
                    fileSet
                ),
                classpath
            )
        );
    }

    public void report(File reportDir, File xmlReportDir) {
        ant.run(
            e("junitreport", a("todir", reportDir), a("tofile", "TestReport.xml"),
                e("fileset", a("dir", xmlReportDir),
                    e("include", a("name", "TEST-*.xml"))
                ),
                e("report", a("format", "frames"), a("todir", reportDir))
            )
        );
    }

    public boolean check(String failureProperty) {
        return !ant.isPropertySet(failureProperty);
    }
}
