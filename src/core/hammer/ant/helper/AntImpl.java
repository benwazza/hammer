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
import hammer.ant.core.AntBuilderCreator;
import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.ioc.Ioc;
import hammer.xml.Element;

import java.io.File;

public final class AntImpl implements Ant, Constructable {
    private AntBuilder builder;
    Ioc ioc;

    public void constructor() {
        AntBuilderCreator creator = ioc.nu(AntBuilderCreator.class);
        builder = creator.create();
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

    public void copyToDir(File file, File toDir) {
        run("copy", a("file", file), a("todir", toDir));
    }

    public void copyToFile(File file, File toDir) {
        run("copy", a("file", file), a("tofile", toDir));
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

    private void delete(String fileOrDir, File file) {
        run("delete", a(fileOrDir, file));
    }
}