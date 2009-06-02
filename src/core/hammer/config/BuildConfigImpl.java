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

package hammer.config;

import au.net.netstorm.boost.bullet.log.LogLevel;
import hammer.core.Constants;

public final class BuildConfigImpl implements Constants, BuildConfig {
    private final boolean publish;
    private int enabledLevel = getCode(LogLevel.INFO);

    public BuildConfigImpl() {
        this(false, false);
    }

    public BuildConfigImpl(boolean publish, boolean trace) {
        this.publish = publish;
        if (trace) enabledLevel = getCode(LogLevel.TRACE);
    }

    public boolean doPublish() {
        return publish;
    }

    public boolean levelEnabled(LogLevel level) {
        int code = getCode(level);
        return code <= enabledLevel;
    }

    public boolean trace() {
        return levelEnabled(LogLevel.TRACE);
    }

    private int getCode(LogLevel level) {
        if (level == LogLevel.ERROR) return 1;
        if (level == LogLevel.WARN) return 2;
        if (level == LogLevel.INFO) return 3;
        return 4; // TRACE
    }
}
