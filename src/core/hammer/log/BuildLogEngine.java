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

package hammer.log;

import au.net.netstorm.boost.bullet.log.LogEngine;
import au.net.netstorm.boost.bullet.log.LogLevel;
import au.net.netstorm.boost.gunge.exception.ThrowableMaster;
import hammer.config.BuildConfig;

import java.io.PrintStream;

public final class BuildLogEngine implements LogEngine {
    ThrowableMaster tosser;
    BuildConfig config;

    public void log(LogLevel level, Object o) {
        // SUGGEST Use commons logging
        if (!config.levelEnabled(level)) return;
        // OK GenericIllegalRegexp {
        if (level == LogLevel.ERROR) {
            println(o, System.err);
        } else {
            println(o, System.out);
        }
        // } OK GenericIllegalRegexp
    }

    public void log(LogLevel level, Throwable t) {
        log(level, "", t);
    }

    public void log(LogLevel level, Object o, Throwable t) {
        log(level, o + tosser.trace(t));
    }

    private void println(Object o, PrintStream stream) {
        stream.println(o);
        stream.flush();
    }
}
