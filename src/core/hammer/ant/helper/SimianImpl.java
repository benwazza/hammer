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

public final class SimianImpl implements Simian {
    Ant ant;

    public void taskDef(Element taskDefClasspath) {
        ant.taskDef(
            "simiantask.properties",
            taskDefClasspath
        );
    }

    public void run(Element[] srcFilesets, File resultFile, String failureProperty, Integer threshold) {
        ant.mkDir(resultFile.getParentFile());
        ant.run(
            e("simian",
                a("threshold", threshold),
                a("language", "java"),
                a("balanceParentheses", "true"),
                a("balanceSquareBrackets", "true"),
                a("reportDuplicateText", "true"),
                a("failOnDuplication", "true"),
                a("failureProperty", failureProperty),
                e("formatter", a("type", "xml"), a("tofile", resultFile)),
                e("formatter", a("type", "plain"))
            ).withElems(srcFilesets)
        );
    }

    public void report(File resultFile, File xslFile, File reportFile) {
        ant.run("xslt",
            a("in", resultFile),
            a("out", reportFile),
            a("style", xslFile)
        );
    }

    public boolean check(String failureProperty) {
        return !ant.isPropertySet(failureProperty);
    }
}
