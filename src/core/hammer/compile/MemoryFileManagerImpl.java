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

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MemoryFileManagerImpl
    extends ForwardingJavaFileManager<JavaFileManager> implements MemoryFileManager {

    private final Map<String, ClassHolder> classes = new ConcurrentHashMap<String, ClassHolder>();

    protected MemoryFileManagerImpl(JavaFileManager delegate) {
        super(delegate);
    }

    public JavaFileObject getJavaFileForOutput(
        JavaFileManager.Location location,
        String name,
        JavaFileObject.Kind kind,
        FileObject sibling) {
        if (kind == JavaFileObject.Kind.CLASS) return inMemory(name);
        else return delegate(location, name, kind, sibling);
    }

    public boolean can(String name) {
        return classes.containsKey(name);
    }

    public byte[] grab(String name) throws ClassNotFoundException {
        if (!can(name)) throw new ClassNotFoundException("Can't find " + name);
        ClassHolder classHolder = classes.get(name);
        return classHolder.getBytes();
    }

    private ClassHolder inMemory(String name) {
        ClassHolder cls = new ClassHolderImpl(name);
        classes.put(name, cls);
        return cls;
    }

    private JavaFileObject delegate(
        JavaFileManager.Location location,
        String name,
        JavaFileObject.Kind kind,
        FileObject sibling) {
        try {
            return super.getJavaFileForOutput(location, name, kind, sibling);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

