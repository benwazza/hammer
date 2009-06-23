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

package hammer.ant.helper;

import au.net.netstorm.boost.spider.api.lifecycle.Constructable;
import hammer.ant.core.AntBuilder;
import hammer.ant.core.AntBuilderFactory;
import hammer.ant.core.AntXml;
import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.ioc.Ioc;
import hammer.xml.Element;
import hammer.xml.Attribute;

import java.io.File;

public final class AntImpl implements Ant, Constructable {
    private AntBuilder builder;
    Ioc ioc;

    public void constructor() {
        AntBuilderFactory factory = ioc.nu(AntBuilderFactory.class);
        builder = factory.nu();
    }

    public void run(String elementName, Object... contents) {
        builder.execute(e(elementName, contents));
    }

    public void run(Element element) {
        builder.execute(element);
    }

    public boolean isPropertySet(String property) {
        return builder.hasProperty(property);
    }

    public void echo(String message) {
        run("echo", message);
    }

    public void mkDir(File dir) {
        run("mkdir", a("dir", dir));
    }

    public void deleteDir(File dir) {
        delete("dir", dir);
    }

    public void deleteFile(File file) {
        delete("file", file);
    }

    public void copyToFile(File file, File toFile) {
        run("copy", a("file", file), a("tofile", toFile));
    }

    public void copyToDir(File file, File toDir) {
        run(AntXml.copyToDir(toDir).withAttrs(a("file", file)));
    }

    public void copyToDir(File toDir, Element... contents) {
        run(AntXml.copyToDir(toDir).withElems(contents));
    }

    public void taskDef(String resource, Element classPath) {
        run(e("taskdef", a("resource", resource),
            classPath
        ));
    }

    public void jar(File jarFile, File classDir, Element manifest) {
        run(e("jar", a("destfile", jarFile), a("basedir", classDir),
            manifest
        ));
    }

    public void zip(File zip, Element... contents) {
        run(e("zip", a("destfile", zip), a("duplicate", "fail")).withElems(contents));
    }

    public void zipToTgz(File zip, File tgz) {
        run(e("tar", a("destfile", tgz), a("compression", "gzip"),
            e("zipfileset", a("src", zip))
        ));
    }

    public void exec(File dir, String executable, Element... contents) {
        run(e("exec", a("dir", dir), a("executable", executable)).with(new Attribute[0], contents));
    }

    // FIX Move this into AntXml and delete
    public void exec(File dir, String executable, Attribute[] attrs, Element... contents) {
        run(e("exec", a("dir", dir), a("executable", executable)).with(attrs, contents));
    }

    public void replaceInDir(File dir, String token, String value) {
        replace("dir", dir, token, value);
    }

    public void replaceInFile(File file, String token, String value) {
        replace("file", file, token, value);
    }

    public void chmod(File file, String perms) {
        run(e("chmod", a("file", file), a("perm", perms)));
    }

    public void replace(String type, File file, String token, String value) {
        run(e("replace", a(type, file), a("token", token), a("value", value)));
    }

    private void delete(String fileOrDir, File file) {
        run("delete", a(fileOrDir, file), a("includeemptydirs", "true"));
    }
}