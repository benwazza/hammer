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
import hammer.xml.Attribute;
import hammer.xml.Element;

import java.io.File;

public final class JavacImpl implements Javac {
    Ant ant;

    public void run(File srcDir,
                    File classDir,
                    Attribute[] attrs,
                    Element[] elems) {
        ant.run(
            e("javac",
                a("srcdir", srcDir),
                a("destdir", classDir))
                .with(attrs, elems));
    }
}
