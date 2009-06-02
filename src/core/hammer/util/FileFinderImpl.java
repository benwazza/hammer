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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FileFinderImpl implements FileFinder {

    public File[] findFiles(File baseDir, final String suffix) {
        List<File> files = find(baseDir, suffix);
        return files.toArray(new File[files.size()]);
    }

    private List<File> find(File baseDir, String suffix) {
        List<File> result = new ArrayList<File>();
        File[] files = baseDir.listFiles();
        for (File file : files) {
            check(suffix, result, file);
        }
        return result;
    }

    private void check(String suffix, List<File> result, File file) {
        if (matchingFile(suffix, file)) {
            result.add(file);
        } else if (file.isDirectory()) {
            List<File> children = find(file, suffix);
            result.addAll(children);
        }
    }

    private boolean matchingFile(String suffix, File file) {
        String name = file.getName();
        return file.isFile() && name.endsWith(suffix);
    }
}