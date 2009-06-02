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

package hammer.self;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.ant.helper.Ant;
import hammer.ant.helper.Javac;
import hammer.xml.Attribute;
import hammer.xml.Element;

import java.io.File;

public final class CompileImpl implements Compile, BuildConstants {
    private static final Attribute[] JAVAC_ATTRS = new Attribute[]{
        a("debug", "on"),
    };

    Prepare prepare;
    Ant ant;
    Compile me;
    Javac javac;

    public void compile() {
        me.compileCore();
        me.compileTest();
        me.compileDemo();
        me.compileSelf();
    }

    public void compileCore() {
        ant.mkDir(CORE_CLASS_DIR);
        javac(CORE_SRC_DIR, CORE_CLASS_DIR, CORE_COMPILE_CLASSPATH);
    }

    public void compileTest() {
        me.compileCore();
        ant.mkDir(TEST_CLASS_DIR);
        javac(TEST_SRC_DIR, TEST_CLASS_DIR, TEST_COMPILE_CLASSPATH);
    }

    public void compileDemo() {
        me.compileCore();
        ant.mkDir(DEMO_CLASS_DIR);
        javac(DEMO_SRC_DIR, DEMO_CLASS_DIR, DEMO_COMPILE_CLASSPATH);
    }

    public void compileSelf() {
        me.compileCore();
        ant.mkDir(SELF_CLASS_DIR);
        javac(SELF_SRC_DIR, SELF_CLASS_DIR, SELF_COMPILE_CLASSPATH);
    }

    private void javac(File srcDir, File classDir, Element classpath) {
        javac.run(srcDir, classDir, JAVAC_ATTRS, javacElems(classpath));
    }

    private Element[] javacElems(Element classpath) {
        return new Element[]{
            classpath,
            e("compilerarg", a("value", "-Xlint:-path")),
            e("compilerarg", a("value", "-Xlint")),
        };
    }
}