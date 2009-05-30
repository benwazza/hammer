/*
 *  Copyright 2008 Ben Warren
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package hammer.core;

import au.net.netstorm.boost.bullet.log.Log;
import au.net.netstorm.boost.gunge.layer.Method;
import hammer.log.LogIndenter;
import hammer.util.Reflection;
import hammer.util.Timer;

import java.util.HashMap;
import java.util.Map;

public final class TaskHandlerImpl implements TaskHandler {
    // FIX 2130 Injection framework with new spider will do the assignment.
    private final Map<String, Object> results = new HashMap<String, Object>();
    private final Object real;
    Reflection reflection;
    LogIndenter indenter;
    Timer timer;
    Log log;

    public TaskHandlerImpl(Object real) {
        this.real = real;
    }

    // FIX 2130 Evil.  If you're going to use "synchronized" at all ... do it in an aspect.
    // This is an aspect??
    public synchronized Object invoke(Method method, Object[] args) {
        String name = method.getName();
        if (!results.containsKey(name)) {
            Object result = invokeTask(args, name);
            results.put(name, result);
        }
        return results.get(name);
    }

    // FIX 2130 Split these out?
    private Object invokeTask(Object[] args, String name) {
        // TODO do this logging as a build listener
        String pad = indenter.getIndent();
        indenter.increaseIndent();
        log.info(pad + name + " {");
        Object result = invokeAndRecord(args, name);
        String secs = timer.getSecs();
        log.info(pad + "} " + name + " " + secs);
        indenter.decreaseIndent();
        return result;
    }

    private Object invokeAndRecord(Object[] args, String name) {
        timer.start();
        Object result = reflection.invoke(real, name, args);
        timer.stop();
        return result;
    }

    public void resetInvocationState() {
        results.clear();
    }
}