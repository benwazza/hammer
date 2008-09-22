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

import au.net.netstorm.boost.gunge.proxy.LayerFactory;
import hammer.ioc.Ioc;

import java.util.ArrayList;
import java.util.List;

public final class TaskProxiesImpl implements TaskProxies {
    // FIX 2130 The new spider will inject a list.  Remove assignment.
    private final List<TaskHandler> handlers = new ArrayList<TaskHandler>();
    LayerFactory layers;
    TaskRegistry registry;
    Ioc ioc;

    public <T extends BuildComponent> void proxy(Class<T> iface, T impl) {
        TaskHandler handler = handler(impl);
        BuildComponent proxy = layer(iface, handler);
        registry.register(iface, impl, iface.cast(proxy));
    }

    private <T extends BuildComponent> T layer(Class<T> iface, TaskHandler handler) {
        // TODO generisize layers
        Object proxy = layers.newProxy(iface, handler);
        return iface.cast(proxy);
    }

    private <T extends BuildComponent> TaskHandler handler(T impl) {
        TaskHandler handler = ioc.nu(TaskHandler.class, impl);
        handlers.add(handler);
        return handler;
    }

    public void runTasks(Build build, String... names) {
        registry.prepareTasks();
        if (names.length == 0) {
            String task = defaultTask(build);
            runTask(task);
        } else runTasks(names);
    }

    private void runTasks(String... names) {
        for (String name : names) {
            runTask(name);
        }
    }

    private String defaultTask(Build build) {
        Class<? extends Build> buildClass = build.getClass();
        if (!buildClass.isAnnotationPresent(BuildInfo.class)) throw new UnknownTaskException();
        BuildInfo info = buildClass.getAnnotation(BuildInfo.class);
        String def = info.defaultTask();
        if (def.trim().isEmpty()) throw new UnknownTaskException();
        return def;
    }

    private void runTask(String name) {
        invokeTask(name);
        resetInvocationStates();
    }

    private void invokeTask(String name) {
        // TODO handle circles
        Task task = registry.getTask(name);
        task.invoke();
    }

    private void resetInvocationStates() {
        for (TaskHandler handler : handlers) {
            handler.resetInvocationState();
        }
    }
}