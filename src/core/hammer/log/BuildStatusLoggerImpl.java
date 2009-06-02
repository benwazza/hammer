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

import au.net.netstorm.boost.bullet.log.Log;
import au.net.netstorm.boost.gunge.exception.ThrowableMaster;
import hammer.core.HammerException;
import hammer.util.Timer;

public final class BuildStatusLoggerImpl implements BuildStatusLogger {
    ThrowableMaster tosser;

    public void logSuccess(Log log, Timer time) {
        logStatus(log, time, true);
    }

    public void logFail(Log log, Exception e, Timer time) {
        logException(log, e);
        logStatus(log, time, false);
    }

    public void logException(Log log, Throwable e) {
        log.info("");
        log.info("");
        Throwable real = tosser.realCause(e);
        if (!(real instanceof HammerException)) log.info(tosser.trace(real));
        log.info("Exception: " + real.getMessage());
        log.info("");
    }

    private void logStatus(Log log, Timer timer, boolean success) {
        log.info("");
        String time = timer.getHoursMinsSecs();
        if (success) log.info("BUILD SUCCESSFUL in " + time);
        else log.info("BUILD FAILED after " + time);
        log.info("");
    }
}
