/*
 *  Copyright 2008 Ben Warren
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

package hammer.self;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import static hammer.ant.core.AntXml.include;
import static hammer.ant.core.AntXml.manifestAttr;
import static hammer.ant.core.AntXml.zipFileSet;
import hammer.ant.helper.Ant;
import hammer.xml.Element;

import java.io.File;

public final class PackageImpl implements Package, BuildConstants {
    private static final File JARS_DIR = new File(ARTIFACTS_DIR, "jars");
    private static final File CORE_JAR = new File(JARS_DIR, artifact(".jar"));
    private static final File SRC_JAR = new File(JARS_DIR, artifact("-src.jar"));
    private static final File DEMO_JAR = new File(JARS_DIR, artifact("-demo.jar"));
    private static final File SELF_JAR = new File(JARS_DIR, artifact("-self.jar"));
    private static final File INSTALLS_DIR = new File(ARTIFACTS_DIR, "installs");
    private static final File ZIP = new File(INSTALLS_DIR, artifact(".zip"));
    private static final File TGZ = new File(INSTALLS_DIR, artifact(".tar.gz"));
    private static final File BOOTSTRAP_DIR = new File(BASE_DIR, "bootstrap");
    private static final File BOOTSTRAP_BIN_DIR = new File(BOOTSTRAP_DIR, "bin");
    private static final File BOOTSTRAP_LIB_DIR = new File(BOOTSTRAP_DIR, "lib");
    private static final File BOOST_DIR = new File(LIB_DIR, "boost");
    private static final File BOOST_JAR = new File(BOOST_DIR, "boost.jar");
    private static final Element MANIFEST =
        e("manifest",
            manifestAttr("Built-By", "leapstream.com.au"),
            e("section", a("name", "common"),
                manifestAttr("Implementation-Title", PROJECT_NAME),
                manifestAttr("Implementation-Version", VERSION),
                manifestAttr("Implementation-Vendor", "leapstream.com.au")
            )
        );

    Compile compile;
    Ant ant;
    Package self;

    public void jars() {
        compile.compile();
        ant.mkDir(JARS_DIR);
        makeJar(CORE_JAR, CORE_CLASS_DIR);
        makeJar(SRC_JAR, CORE_SRC_DIR);
        makeJar(DEMO_JAR, DEMO_SRC_DIR);
        makeJar(SELF_JAR, SELF_CLASS_DIR);
    }

    public void dist() {
        self.jars();
        ant.mkDir(INSTALLS_DIR);
        ant.zip(ZIP,
            binScript(BOOTSTRAP_BIN_DIR, "hammer"),
            distLibJar(JARS_DIR, CORE_JAR.getName()),
            distLibJar(JARS_DIR, SRC_JAR.getName()),
            distLibJar(BOOST_DIR, BOOST_JAR.getName())
        );
        ant.zipToTgz(ZIP, TGZ);
    }

    public void updateBootstrap() {
        self.jars();
        ant.copyToFile(CORE_JAR, new File(BOOTSTRAP_LIB_DIR, ARTIFACTS_NAME + ".jar"));
        ant.copyToDir(BOOST_JAR, BOOTSTRAP_LIB_DIR);
    }

    private Element binScript(File dir, String script) {
        return zipFileSet(dir, "755", ARTIFACTS_NAME + "/bin", include(script));
    }

    private Element distLibJar(File dir, String jarName) {
        return zipFileSet(dir, "644", ARTIFACTS_NAME + "/lib", include(jarName));
    }

    private void makeJar(File jarFile, File classDir) {
        ant.jar(jarFile, classDir, MANIFEST);
    }

    private static String artifact(String suffix) {
        return ARTIFACTS_NAME + "-" + VERSION + suffix;
    }
}