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

package hammer.core;

import java.io.File;

// OK InterfaceIsType {
public interface Constants {
    String FILE_SEPARATOR = File.separator;
    String PATH_SEPARATOR = File.pathSeparator;
    String LINE_SEPARATOR = System.getProperty("line.separator");
    String JAVA_HOME_PATH = System.getProperty("java.home");
    String JAVA_TMPDIR_PATH = System.getProperty("java.io.tmpdir");
    File JAVA_TMPDIR = new File(JAVA_TMPDIR_PATH);
    File JAVA_HOME = new File(JAVA_HOME_PATH);
    String PROPERTIES_FILENAME = "hammer.properties";
    int FAILURE = 1;
    int OK = 0;
}
// } OK InterfaceIsType
