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

package hammer.ant.core;

import au.net.netstorm.boost.bullet.log.LogLevel;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;

public final class LogLevelTranslatorImpl implements LogLevelTranslator {

    public LogLevel translate(BuildEvent event) {
        int priority = event.getPriority();

        if (priority == Project.MSG_ERR) {
            return LogLevel.ERROR;
        }

        if (priority == Project.MSG_WARN) {
            return LogLevel.WARN;
        }

        if (priority == Project.MSG_INFO) {
            return LogLevel.INFO;
        }

        return LogLevel.TRACE;
    }
}
