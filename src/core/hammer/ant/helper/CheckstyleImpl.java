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

package hammer.ant.helper;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.xml.Element;

import java.io.File;

public final class CheckstyleImpl implements Checkstyle {
    Ant ant;

    public void taskDef(Element classpath) {
        ant.taskDef("checkstyletask.properties", classpath);
    }

    public void run(File config, File results, String failureProp, Element fileset) {
        ant.run("checkstyle",
            a("config", config),
            a("failOnViolation", "false"),
            a("failureProperty", failureProp),
            e("formatter"),
            e("formatter",
                a("type", "xml"),
                a("tofile", results)),
            fileset);
    }

    public void report(File results, File report, File styleSheet) {
        ant.run("xslt", a("in", results), a("out", report), a("style", styleSheet));
    }

    public boolean check(String failureProp) {
        return !ant.isPropertySet(failureProp);
    }
}
