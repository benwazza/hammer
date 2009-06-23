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

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import static hammer.ant.core.AntXml.fileSet;
import static hammer.ant.core.AntXml.include;
import static hammer.ant.core.AntXml.manifestAttr;
import static hammer.ant.core.AntXml.zipFileSet;
import hammer.ant.helper.Ant;
import hammer.core.Constants;
import hammer.xml.Element;

import java.io.File;

public final class PackageImpl implements Package, BuildConstants, Constants {
    private static final File JARS_DIR = new File(ARTIFACTS_DIR, "jars");
    private static final File CORE_JAR = new File(JARS_DIR, versionName("-core.jar"));
    private static final File SRC_JAR = new File(JARS_DIR, versionName("-src.jar"));
    private static final File DEMO_JAR = new File(JARS_DIR, versionName("-demo.jar"));
    private static final File SELF_JAR = new File(JARS_DIR, versionName("-self.jar"));
    private static final File INSTALLS_DIR = new File(ARTIFACTS_DIR, "installs");
    private static final File SRC_EXPORT = new File(JAVA_TMPDIR, versionName("-src"));
    private static final File ZIP = new File(INSTALLS_DIR, versionName(".zip"));
    private static final File SRC_ZIP = new File(INSTALLS_DIR, versionName("-src.zip"));
    private static final File TGZ = new File(INSTALLS_DIR, versionName(".tar.gz"));
    private static final File SRC_TGZ = new File(INSTALLS_DIR, versionName("-src.tar.gz"));
    private static final File BOOTSTRAP_DIR = new File(BASE_DIR, "bootstrap");
    private static final File BOOTSTRAP_BIN_DIR = new File(BOOTSTRAP_DIR, "bin");
    private static final File BOOTSTRAP_MISC_DIR = new File(BOOTSTRAP_DIR, "misc");
    private static final File BOOTSTRAP_LIB_DIR = new File(BOOTSTRAP_DIR, "lib");
    private static final File BOOST_DIR = new File(LIB_DIR, "boost");
    private static final File BOOST_JAR = new File(BOOST_DIR, "boost.jar");
    private static final File KICKSTART_DIR = new File(DEMO_SRC_DIR, "hammer/kickstart");

    private static final Element MANIFEST =
        e("manifest",
            manifestAttr("Built-By", "agilemethods.com.au"),
            e("section", a("name", "common"),
                manifestAttr("Implementation-Title", PROJECT_NAME),
                manifestAttr("Implementation-Version", VERSION),
                manifestAttr("Implementation-Vendor", "agilemethods.com.au")
            )
        );
    Compile compile;
    Ant ant;
    Package self;

    public void jars() {
        compile.compile();
        ant.mkDir(JARS_DIR);
        ant.copyToDir(CORE_CLASS_DIR, fileSet(CORE_SRC_DIR, include("**/xml2hammer.xsl")));
        makeJar(CORE_JAR, CORE_CLASS_DIR);
        makeJar(SRC_JAR, CORE_SRC_DIR);
        makeJar(DEMO_JAR, DEMO_SRC_DIR);
        makeJar(SELF_JAR, SELF_CLASS_DIR);
    }

    public void dist() {
        self.jars();
        clean(INSTALLS_DIR);
        binDists();
        ant.zipToTgz(ZIP, TGZ);
        srcDists();
    }

    private void binDists() {
        ant.zip(ZIP,
            addToZip(BOOTSTRAP_BIN_DIR, "/bin", "744", "hammer"),
            addToZip(BOOTSTRAP_BIN_DIR, "/bin", "744", "xml2hammer"),
            addToZip(BOOTSTRAP_MISC_DIR, "/misc", "644", "xml2hammer.xsl"),
            addToZip(JARS_DIR, "/lib", "644", CORE_JAR.getName()),
            addToZip(JARS_DIR, "/lib", "644", SRC_JAR.getName()),
            addToZip(BOOST_DIR, "/lib", "644", BOOST_JAR.getName()),
            addToZip(KICKSTART_DIR, "/kickstart", "644", "*"),
            addToZip(BASE_DIR, "/", "INSTALL.txt"),
            addToZip(BASE_DIR, "/", "README.txt")
        );
    }

    private void srcDists() {
        clean(SRC_EXPORT);
        gitExport(BASE_DIR, SRC_EXPORT);
        ant.zip(SRC_ZIP, zipFileSet(JAVA_TMPDIR, include(SRC_EXPORT.getName() + "/**")));
        ant.zipToTgz(SRC_ZIP, SRC_TGZ);
        ant.deleteDir(SRC_EXPORT);
    }

    public void updateBootstrap() {
        self.jars();
        ant.copyToFile(CORE_JAR, new File(BOOTSTRAP_LIB_DIR, ARTIFACTS_NAME + ".jar"));
        ant.copyToDir(BOOST_JAR, BOOTSTRAP_LIB_DIR);
    }

    private Element addToZip(File dir, String zipPath, String perms, String pattern) {
        Element files = addToZip(dir, zipPath, pattern);
        return files.withAttrs(a("filemode", perms));
    }

    private Element addToZip(File dir, String zipPath, String pattern) {
        Element files = zipFileSet(dir, include(pattern));
        return files.withAttrs(a("prefix", versionName(zipPath)));
    }

    private void makeJar(File jarFile, File classDir) {
        ant.jar(jarFile, classDir, MANIFEST);
    }

    private static String versionName(String suffix) {
        return ARTIFACTS_NAME + "-" + VERSION + suffix;
    }

    // TODO Split out
    private void gitExport(File repoDir, File toDir) {
        String gitArgs = "checkout-index '--prefix=" + toDir.getPath() + "/' -a";
        ant.exec(repoDir, "git", e("arg", a("line", gitArgs)));
    }

    // TODO Split out or put in Ant
    private void clean(File dir) {
        if (dir.exists()) ant.deleteDir(dir);
        ant.mkDir(dir);
    }
}