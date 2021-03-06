/*
* Copyright 2008-2009 Ben Warren, Mark Hibberd
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

public final class HammerClassLoaderImpl extends ClassLoader implements HammerClassLoader {
    private MemoryFileManager manager;

    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (manager == null || !manager.can(name)) return super.findClass(name);
        byte[] bytes = manager.grab(name);
        return defineClass(name, bytes);
    }

    public void setManager(MemoryFileManager manager) {
        this.manager = manager;
    }

    private Class<?> defineClass(String name, byte[] bytes) {
        definePackage(name);
        return super.defineClass(name, bytes, 0, bytes.length);
    }

    private void definePackage(String name) {
        String pkgname = packageName(name);
        if (!pkgname.isEmpty() && !packageDefined(pkgname))
            definePackage(pkgname, null, null, null, null, null, null, null);
    }

    private String packageName(String className) {
        String pkgName = "";
        int i = className.lastIndexOf('.');
        if (i != -1) pkgName = className.substring(0, i);
        return pkgName;
    }

    private boolean packageDefined(String pkgname) {
        return getPackage(pkgname) != null;
    }
}