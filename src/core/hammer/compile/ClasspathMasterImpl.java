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

import au.net.netstorm.boost.sledge.java.lang.EdgeClass;
import au.net.netstorm.boost.sledge.java.lang.reflect.EdgeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public final class ClasspathMasterImpl implements ClasspathMaster {
    private final URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    EdgeMethod methods;
    EdgeClass classes;

    public void extend(String classpath) {
        Method addUrl = method("addURL");
        URL[] urls = toUrls(classpath);
        for (URL url : urls) {
            methods.invoke(addUrl, sysLoader, url);
        }
    }

    private URL[] toUrls(String classpath) {
        String[] elems = classpath.split(File.pathSeparator);
        URL[] urls = new URL[elems.length];
        convert(elems, urls);
        return urls;
    }

    private void convert(String[] elems, URL[] urls) {
        for (int i = 0; i < elems.length; i++) {
            File file = new File(elems[i]);
            url(urls, i, file);
        }
    }

    // TODO Edge
    private void url(URL[] urls, int i, File file) {
        try {
            urls[i] = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Method method(String name) {
        Method addUrl = classes.getDeclaredMethod(URLClassLoader.class, name, URL.class);
        addUrl.setAccessible(true);
        return addUrl;
    }
}