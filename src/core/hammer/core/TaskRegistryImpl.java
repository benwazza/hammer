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

import au.net.netstorm.boost.bullet.log.Log;
import hammer.config.BuildConfig;
import hammer.ioc.Ioc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TaskRegistryImpl implements TaskRegistry {
    private final List<Task> tasks = new ArrayList<Task>();
    BuildConfig config;
    Ioc ioc;
    Log log;

    public <T extends BuildTasks> void register(Class<T> iface, T impl, T proxy) {
        ioc.instance(iface, proxy);
        registerTasks(iface, proxy, impl);
    }

    public void prepareTasks() {
        for (Task task : tasks) {
            task.init();
        }
    }

    public Task getTask(String name) {
        for (Task task : tasks) {
            if (task.hasName(name)) return task;
        }
        throw new UnknownTaskException(name);
    }

    public List<Task> getPublishedTasks() {
        List<Task> result = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.isPublished()) {
                result.add(task);
            }
        }
        return result;
    }

    private <T extends BuildTasks> void registerTasks(Class<T> iface, T proxy, T impl) {
        Method[] methods = iface.getDeclaredMethods();
        for (Method method : methods) {
            Task task = ioc.nu(Task.class, method, proxy, impl);
            if (config.trace()) log.trace("Registering task: " + task);
            tasks.add(task);
        }
    }
}