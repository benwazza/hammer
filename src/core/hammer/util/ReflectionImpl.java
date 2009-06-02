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

import au.net.netstorm.boost.gunge.sledge.java.lang.EdgeClass;
import au.net.netstorm.boost.gunge.sledge.java.lang.reflect.EdgeMethod;

import java.lang.reflect.Method;

public final class ReflectionImpl implements Reflection {
    EdgeMethod edgeMethod;
    EdgeClass edgeClass;

    public Object invoke(Object subject, String method, Object... params) {
        Method m = method(subject, method, params);
        return invoke(subject, m, params);
    }

    public Object invokeDeclared(Class definer, Object subject, String method, Object... params) {
        Method m = declaredMethod(definer, method,  params);
        return invoke(subject, m, params);
    }

    public Method method(Object subject, String method, Object... params) {
        Class[] classes = convert(params);
        return edgeClass.getMethod(subject.getClass(), method, classes);
    }

    public Method method(Class subject, String method, Object... params) {
        Class[] classes = convert(params);
        return edgeClass.getMethod(subject, method, classes);
    }

    public Method declaredMethod(Class subject, String method, Object... params) {
        Class[] classes = convert(params);
        return edgeClass.getDeclaredMethod(subject, method, classes);
    }

    public Object invoke(Object subject, Method method, Object... params) {
        method.setAccessible(true);
        return edgeMethod.invoke(method, subject, params);
    }

    private Class[] convert(Object... params) {
        if (params == null) return new Class[0];
        Class[] paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        return paramTypes;
    }
}
