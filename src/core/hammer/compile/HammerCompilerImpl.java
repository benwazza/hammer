/*
* Copyright 2008 Ben Warren, Mark Hibberd
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

import au.net.netstorm.boost.bullet.log.Log;
import hammer.config.BuildConfig;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// DEBT ClassFanOut {
public final class HammerCompilerImpl implements HammerCompiler {
    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final StandardJavaFileManager filer = compiler.getStandardFileManager(null, null, null);
    BuildConfig config;
    Log log;

    public MemoryFileManager compile(File[] files, String classpath, HammerClassLoader loader) {
        if (config.trace()) log.trace("Compiling files: " + Arrays.toString(files));
        MemoryFileManager manager = new MemoryFileManagerImpl(filer);
        Iterable<? extends JavaFileObject> units = filer.getJavaFileObjects(files);
        List<String> options = compileOptions(classpath);
        return compile(manager, units, options);
    }

    private MemoryFileManager compile(
        MemoryFileManager manager,
        Iterable<? extends JavaFileObject> units,
        List<String> options) {
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, listener(), options, null, units);
        if (!task.call()) throw new CompilationException();
        if (config.trace()) log.trace("Compilation Successful!");
        return manager;
    }

    private List<String> compileOptions(String classpath) {
        List<String> options = new ArrayList<String>();
        classpath(classpath, options);
        return options;
    }

    private void classpath(String classpath, List<String> options) {
        String loaderClasspath = System.getProperty("java.class.path");
        if (!classpath.isEmpty()) loaderClasspath += ":" + classpath;
        options.add("-classpath");
        options.add(loaderClasspath);
        log.trace("Compiling with classpath: " + loaderClasspath);
    }

    private DiagnosticListener<JavaFileObject> listener() {
        return new DiagnosticListener<JavaFileObject>() {
            public void report(Diagnostic diagnostic) {
                log.info(diagnostic);
            }
        };
    }
}
// } DEBT ClassFanOut