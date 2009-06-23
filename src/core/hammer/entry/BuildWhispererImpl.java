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

package hammer.entry;

import au.net.netstorm.boost.gunge.sledge.java.lang.EdgeClass;
import au.net.netstorm.boost.spider.api.config.scope.Scoper;
import hammer.compile.ClasspathMaster;
import hammer.compile.HammerClassLoader;
import hammer.compile.HammerCompiler;
import hammer.compile.MemoryFileManager;
import hammer.core.Build;
import hammer.core.Constants;
import hammer.ioc.Ioc;
import hammer.util.FileFinder;
import hammer.util.PropertiesFile;

import java.io.File;

// DEBT ClassFanOut {
public final class BuildWhispererImpl implements BuildWhisperer, Constants {

    private static final String BUILD_CLASSPATH = "build.classpath";
    private static final String BUILD_SRC_DIR = "build.src.dir";
    private static final String BUILD_CLASS = "build.class";
    private static final String IOC_SCOPE = "ioc.scope";

    ToolsJarLocator toolsLocator;
    ClasspathMaster classpather;
    HammerClassLoader loader;
    HammerCompiler compiler;
    FileFinder fileFinder;
    EdgeClass classer;
    Scoper scoper;
    Ioc ioc;

    public Build createBuild() {
        PropertiesFile props = ioc.nu(PropertiesFile.class);
        props.load(PROPERTIES_FILENAME);
        prepareLoader(props);
        return instantiateBuild(props);
    }

    private Build instantiateBuild(PropertiesFile props) {
        String buildClassName = props.prop(BUILD_CLASS);
        Class<?> buildClass = forName(buildClassName, (ClassLoader) loader);
        scope(buildClass, props);
        return (Build) classer.newInstance(buildClass);
    }

    private void scope(Class<?> buildClass, PropertiesFile props) {
        Package pkg = buildClass.getPackage();
        scoper.scope(pkg.getName());
        if (props.has(IOC_SCOPE)) {
            String scope = props.prop(IOC_SCOPE);
            scoper.scope(scope);
        }
    }

    private void prepareLoader(PropertiesFile props) {
        String classpath = props.prop(BUILD_CLASSPATH);
        classpath = toolsLocator.addToPath(classpath);
        MemoryFileManager fileManager = compileBuild(props, classpath);
        classpather.extend(classpath);
        loader.setManager(fileManager);
    }

    private MemoryFileManager compileBuild(PropertiesFile props, String classpath) {
        String src = props.prop(BUILD_SRC_DIR);
        File srcDir = new File(src);
        File[] javaFiles = fileFinder.findFiles(srcDir, ".java");
        return compiler.compile(javaFiles, classpath, loader);
    }

    // TODO add methods that takes a classloader to EdgeClass
    private Class<?> forName(String name, ClassLoader loader) {
        try {
            return Class.forName(name, true, loader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
// } DEBT ClassFanOut