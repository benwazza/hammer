/*
 *  Copyright 2008-2009 Ben Warren
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
import hammer.config.BuildConfig;
import hammer.log.BuildStatusLogger;
import hammer.publish.BuildPublisher;
import hammer.util.Timer;

public final class HammerImpl implements Hammer, Builder, Constants {
    BuildStatusLogger status;
    BuildPublisher publisher;
    TaskRegistry registry;
    TaskProxies proxies;
    BuildConfig config;
    Timer timer;
    Log log;

    public int hit(Build build, String... tasks) {
        build.addTasks(this);
        if (config.doPublish()) {
            publish(build);
            return OK;
        }
        return runTasks(build, tasks);
    }

    public <T extends BuildTasks> void addTasks(Class<T> iface, T impl) {
        proxies.proxy(iface, impl);
    }

    public void publish(Build build) {
        publisher.publish(build);
    }

    private int runTasks(Build build, String... tasks) {
        Throwable e = runSafe(build, tasks);
        if (e != null) {
            status.logFail(log, e, timer);
            return FAILURE;
        }
        status.logSuccess(log, timer);
        return OK;
    }

    // FIX Layer here
    private Throwable runSafe(Build build, String... tasks) {
        try {
            timer.start();
            proxies.runTasks(build, tasks);
            return null;
        } catch (Throwable e) {
            return e;
        } finally {
            timer.stop();
        }
    }
}