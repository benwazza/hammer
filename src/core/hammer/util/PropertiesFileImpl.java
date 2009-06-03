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

package hammer.util;

import edge.java.io.FileReader;
import edge.java.util.Properties;
import hammer.config.BuildConfigException;
import hammer.ioc.Ioc;

import java.io.File;

public final class PropertiesFileImpl implements PropertiesFile {
    private Properties props;
    Ioc ioc;

    public void load(String filename) {
        props = props(filename);
    }

    public String getProperty(String name) {
        String val = props.getProperty(name);
        if (val != null) return val;
        throw new BuildConfigException(name);
    }

    private Properties props(String filename) {
        File file = safeGetFile(filename);
        FileReader fileReader = ioc.nu(FileReader.class, file);
        Properties props = ioc.nu(Properties.class, System.getProperties());
        props.load(fileReader);
        return props;
    }

    private File safeGetFile(String filename) {
        File file = new File(filename);
        if (!(file.exists() && file.isFile() && file.canRead()))
            throw new BuildConfigException(file);
        return file;
    }
}