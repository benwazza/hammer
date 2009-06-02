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

import hammer.xml.Element;

import java.io.File;

public interface Ant {
    void run(String elementName, Object... contents);

    void run(Element element);

    boolean isPropertySet(String property);

    void echo(String message);

    void mkDir(File dir);

    void deleteDir(File dir);

    void deleteFile(File file);

    void taskDef(String resourceName, Element classPath);

    void copyToDir(File file, File toDir);

    void jar(File jarFile, File classDir, Element manifest);

    void zip(File zip, Element... contents);

    void zipToTgz(File zip, File tgz);

    void copyToFile(File file, File toDir);
}
