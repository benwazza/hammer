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
package hammer.ioc;

import au.net.netstorm.boost.bullet.log.DelegatingLog;
import au.net.netstorm.boost.bullet.log.Log;
import au.net.netstorm.boost.bullet.log.LogEngine;
import au.net.netstorm.boost.spider.api.config.mapping.Mapper;
import au.net.netstorm.boost.spider.api.config.web.Web;
import au.net.netstorm.boost.spider.api.config.wire.Wire;
import au.net.netstorm.boost.spider.api.runtime.Resolver;
import hammer.log.BuildLogEngine;
import hammer.log.LogIndenter;
import hammer.log.LogIndenterImpl;

public final class HammerWeb implements Web {
    Mapper mapper;
    Resolver resolver;
    Wire wire;
    // FIX Need to make this configurable
    String scope = "hammer";

    public void web() {
        mapper.suffix("Impl");
        logging();
    }

    private void logging() {
        wire.cls(BuildLogEngine.class).one().to(LogEngine.class);
        wire.cls(DelegatingLog.class).to(Log.class);
        // Singleton for indentation tracking
        wire.cls(LogIndenterImpl.class).one().to(LogIndenter.class);
    }
}
