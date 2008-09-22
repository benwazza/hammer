/*
 * Copyright 2008 Ben Warren
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

import au.net.netstorm.boost.sniper.reflect.util.MethodTestUtil;
import hammer.ioc.Ioc;
import hammer.publish.Publish;

import java.lang.reflect.Method;

public final class TaskImpl implements Task {
    private static final Object[] NO_PARAMS = new Object[0];
    private final BuildComponent proxy;
    private final BuildComponent impl;
    private final Method method;
    // TODO get rid of this
    MethodTestUtil methoder;
    Ioc ioc;

    public TaskImpl(Method method, BuildComponent proxy, BuildComponent impl) {
        validateMethod(method);
        this.method = method;
        this.proxy = proxy;
        this.impl = impl;
    }

    public void init() {
        ioc.inject(impl);
        if (impl instanceof Initialisable) ((Initialisable) impl).init();
    }

    public boolean hasName(String name) {
        return longName().equals(name) || shortName().equals(name);
    }

    public String longName() {
        Class<?> declaringClass = method.getDeclaringClass();
        String className = declaringClass.getName();
        return className + "." + method.getName();
    }

    public String shortName() {
        return method.getName();
    }

    // TODO put annotations on the Impls 
    public boolean isPublished() {
        return (method.isAnnotationPresent(Publish.class));
    }

    public String getDescription() {
        if (!isPublished()) return "";
        Publish publish = method.getAnnotation(Publish.class);
        return publish.value();
    }

    public void invoke() {
        methoder.invoke(proxy, shortName(), NO_PARAMS);
    }

    private void validateMethod(Method method) {
        if (method.getParameterTypes().length != 0)
            throw new IllegalTaskMethodException(method);
    }

    public String toString() {
        return "Task [" + method.getDeclaringClass().getName() + " : " + method.getName() + "]";
    }
}