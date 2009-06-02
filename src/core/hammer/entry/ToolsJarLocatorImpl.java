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

import hammer.ant.core.MissingToolsJarException;
import hammer.core.Constants;

import java.io.File;

public final class ToolsJarLocatorImpl implements ToolsJarLocator, Constants {
    private static final String LIB_TOOLS_JAR = "lib" + FILE_SEPARATOR + "tools.jar";

    public String addToPath(String classpath) {
        if (loaded()) return classpath;
        File path = locate();
        return path.getAbsolutePath() + PATH_SEPARATOR + classpath;
    }

    private boolean loaded() {
        if (loaded("com.sun.tools.javac.Main")) return true;
        return loaded("sun.tools.javac.Main");
    }

    private File locate() {
        File toolsJar = toolsJar(JAVA_HOME);
        if (toolsJar.exists()) return toolsJar;
        if (usingJre()) toolsJar = toolsJar(JAVA_HOME.getParentFile());
        if (toolsJar.exists()) return toolsJar;
        throw new MissingToolsJarException();
    }

    // TODO Move to classloader util?
    private boolean loaded(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private File toolsJar(File libParent) {
        return new File(libParent, LIB_TOOLS_JAR);
    }

    private boolean usingJre() {
        return JAVA_HOME.getName().equalsIgnoreCase("jre");
    }
}
