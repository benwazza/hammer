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

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public final class ClassHolderImpl extends SimpleJavaFileObject implements ClassHolder {
    private final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private final String name;

    public ClassHolderImpl(String name) {
        super(URI.create("memory://" + name), JavaFileObject.Kind.CLASS);
        this.name = name;
    }

    public OutputStream openOutputStream() {
        return bytes;
    }

    public String getClassName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes.toByteArray();
    }
}
