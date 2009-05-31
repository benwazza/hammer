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

package hammer.compile;

import edge.java.io.File;
import hammer.ioc.Ioc;
import hammer.util.Reflection;

import java.net.URL;
import java.net.URLClassLoader;

public final class ClasspathMasterImpl implements ClasspathMaster {
    private final URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    Reflection reflection;
    Ioc ioc;

    public void extend(String classpath) {
        URL[] urls = toUrls(classpath);
        for (URL url : urls) {
            reflection.invokeDeclared(URLClassLoader.class, sysLoader,"addURL", url);
        }
    }

    // TODO move out to converter
    private URL[] toUrls(String classpath) {
        String[] elems = classpath.split(java.io.File.pathSeparator);
        URL[] urls = new URL[elems.length];
        convert(elems, urls);
        return urls;
    }

    private void convert(String[] elems, URL[] urls) {
        for (int i = 0; i < elems.length; i++) {
            File file = ioc.nu(File.class, elems[i]);
            urls[i] = file.toURI().toURL();
        }
    }
}