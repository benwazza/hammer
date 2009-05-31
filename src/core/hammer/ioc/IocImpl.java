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

import au.net.netstorm.boost.spider.api.builder.SpiderEgg;
import au.net.netstorm.boost.spider.api.config.wire.Wire;
import au.net.netstorm.boost.spider.api.runtime.Spider;
import au.net.netstorm.boost.spider.ioc.BoostWeb;
import hammer.config.BuildConfig;

public final class IocImpl implements Ioc {
    private final Spider spider = new SpiderEgg(BoostWeb.class, HammerWeb.class).hatch();
    private final Wire wire = resolve(Wire.class);

    public IocImpl(BuildConfig config) {
        instance(Ioc.class, this);
        instance(BuildConfig.class, config);
    }

    public void inject(Object ref) {
        spider.inject(ref);
    }

    public <T> T nu(Class<T> impl, Object... params) {
        return spider.nu(impl, params);
    }

    public <T> T resolve(Class<T> type) {
        return spider.resolve(type);
    }

    public <T, U extends T> void instance(Class<T> iface, U impl) {
        wire.ref(impl).to(iface);
    }

    public <T> void single(Class<T> iface, Class<? extends T> impl) {
        wire.cls(impl).one().to(iface);
    }

    public <T> void multiple(Class<T> iface, Class<? extends T> impl) {
        wire.cls(impl).to(iface);
    }
}
