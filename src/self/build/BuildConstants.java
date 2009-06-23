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
import static hammer.ant.core.AntXml.pathElement;
import hammer.xml.Element;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface BuildConstants {

    // project specifics
    String PROJECT_NAME = "Hammer";
    String ARTIFACTS_NAME = "hammer";
    String VERSION = "1.0-pre-beta";
    String BUILD_DATE = new SimpleDateFormat("dd MMM, yyyy 'at' HH:mm").format(new Date());

    // directories and files
    File BASE_DIR = new File(".");
    File GEN_DIR = new File(BASE_DIR, "gen");
    File SRC_DIR = new File(BASE_DIR, "src");
    File LIB_DIR = new File(BASE_DIR, "lib");
    File CONFIG_DIR = new File(BASE_DIR, "config");
    File CORE_SRC_DIR = new File(SRC_DIR, "core");
    File TEST_SRC_DIR = new File(SRC_DIR, "test");
    File DEMO_SRC_DIR = new File(SRC_DIR, "demo");
    File SELF_SRC_DIR = new File(SRC_DIR, "self");
    File CLASS_DIR = new File(GEN_DIR, "classes");
    File CORE_CLASS_DIR = new File(CLASS_DIR, "core");
    File TEST_CLASS_DIR = new File(CLASS_DIR, "test");
    File DEMO_CLASS_DIR = new File(CLASS_DIR, "demo");
    File SELF_CLASS_DIR = new File(CLASS_DIR, "self");
    File ARTIFACTS_DIR = new File(GEN_DIR, "artifacts");
    File REPORTS_DIR = new File(ARTIFACTS_DIR, "reports");

    // filesets
    Element CORE_SRC_FILESET = fileSet(CORE_SRC_DIR);
    Element TEST_SRC_FILESET = fileSet(TEST_SRC_DIR);
    Element DEMO_SRC_FILESET = fileSet(DEMO_SRC_DIR);
    Element SELF_SRC_FILESET = fileSet(SELF_SRC_DIR);

    // classpaths
    Element CORE_COMPILE_CLASSPATH =
        classPath(
            fileSet(LIB_DIR, include("boost/boost.jar")),
            fileSet(LIB_DIR, include("ant/ant*.jar"))
        );
    Element TEST_COMPILE_CLASSPATH = CORE_COMPILE_CLASSPATH
        .withElems(pathElement(CORE_CLASS_DIR),
            fileSet(LIB_DIR, include("tools/junit/junit*.jar")),
            fileSet(LIB_DIR, include("tools/jmock/jmock*.jar")));
    Element DEMO_COMPILE_CLASSPATH = CORE_COMPILE_CLASSPATH;
    Element SELF_COMPILE_CLASSPATH = CORE_COMPILE_CLASSPATH;
}
