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

import au.net.netstorm.boost.spider.api.config.web.Web;
import au.net.netstorm.boost.spider.api.config.mapping.Mapper;
import au.net.netstorm.boost.spider.api.config.wire.Wire;
import au.net.netstorm.boost.spider.api.runtime.Resolver;
import hammer.compile.ClasspathMaster;
import hammer.compile.HammerClassLoader;

public final class HammerWeb implements Web {
    Mapper mapper;
    Resolver resolver;
    Wire binder;
    // FIX Need to make this configurable
    String scope = "hammer";

    public void web() {
        mapper.suffix("Impl");
        bootstrap();
    }

    private void bootstrap() {
        // FIX Find a better pattern to handle inheritance cases.
        ClasspathMaster master = resolver.resolve(ClasspathMaster.class);
        binder.nu(HammerClassLoader.class, master).to(HammerClassLoader.class);
    }
}
