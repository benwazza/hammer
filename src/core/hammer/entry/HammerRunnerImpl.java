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
package hammer.entry;

import au.net.netstorm.boost.bullet.log.Log;
import hammer.config.BuildConfig;
import hammer.core.Build;
import hammer.core.Constants;
import hammer.core.Hammer;
import hammer.ioc.Ioc;
import hammer.ioc.IocImpl;
import hammer.log.BuildStatusLogger;

public final class HammerRunnerImpl implements HammerRunner, Constants {
    private final Ioc ioc;
    BuildStatusLogger status;
    BuildWhisperer whisperer;
    Log log;

    public HammerRunnerImpl(BuildConfig config) {
        ioc = new IocImpl(config);
        ioc.inject(this);
    }

    public int run(String... tasks) {
        try {
            return runBuild(tasks);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    private int runBuild(String... tasks) {
        Build build = whisperer.createBuild();
        Hammer hammer = ioc.nu(Hammer.class);
        return hammer.hit(build, tasks);
    }

    // TODO Use in HammerImpl as well
    private int handleError(Exception e) {
        Throwable cleaned = e;
        if (e instanceof InstantiationException)
            cleaned = e.getCause();
        status.logException(log, cleaned);
        return FAILURE;
    }
}
