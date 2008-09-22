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

package hammer.ioc;

import au.net.netstorm.boost.bullet.log.DelegatingLog;
import au.net.netstorm.boost.bullet.log.Log;
import au.net.netstorm.boost.bullet.log.LogEngine;
import hammer.config.BuildConfig;
import hammer.log.BuildLogEngine;
import hammer.util.Timer;
import hammer.util.TimerImpl;

public final class IocBooterImpl implements IocBooter {
    private final Ioc ioc = new IocImpl();

    public void wire(BuildConfig config) {
        ioc.instance(BuildConfig.class, config);
        setupLogging(ioc);
        setupMultiples(ioc);
    }

    public Ioc getIoc() {
        return ioc;
    }

    private void setupLogging(Ioc ioc) {
        ioc.single(LogEngine.class, BuildLogEngine.class);
        LogEngine engine = ioc.resolve(LogEngine.class);
        ioc.instance(Log.class, new DelegatingLog(engine));
    }

    private void setupMultiples(Ioc ioc) {
        ioc.multiple(Timer.class, TimerImpl.class);
        // TODO Make the TaskRegistry immutable.
//        ioc.multiple(TaskRegistry.class, TaskRegistryImpl.class);
//        TaskRegistry registry = ioc.resolve(TaskRegistry.class);
//        ioc.instance(TaskProxiesImpl.class, TaskRegistry.class, registry);
//        ioc.instance(Hammer.class, TaskRegistry.class, registry);

//        ioc.multiple(TaskProxies.class, TaskProxiesImpl.class);
//        TaskProxies proxies = ioc.resolve(TaskProxies.class);
//        ioc.instance(Hammer.class, TaskProxies.class, proxies);
    }
}
