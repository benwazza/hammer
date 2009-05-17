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

import au.net.netstorm.boost.spider.api.builder.Egg;
import au.net.netstorm.boost.spider.api.builder.DefaultEgg;
import au.net.netstorm.boost.spider.api.runtime.Spider;
import au.net.netstorm.boost.spider.api.legacy.Registry;
import au.net.netstorm.boost.spider.ioc.BoostWeb;

public final class IocImpl implements Ioc {
    private final Egg egg = new DefaultEgg();
    private final Spider spider = egg.hatch(BoostWeb.class, HammerWeb.class);
    private final Registry registry = spider.resolve(Registry.class);

    public IocImpl() {
        instance(Ioc.class, this);
    }

    public void inject(Object ref) {
        spider.inject(ref);
    }

    public <T> T nu(Class<T> impl, Object... params) {
        return spider.nu(impl, params);
    }

    public <T, U extends T> void instance(Class<T> iface, U ref) {
        registry.instance(iface, ref);
    }

    public <T, U extends T> void instance(Class<?> host, Class<T> iface, U ref) {
        registry.instance(host, iface, ref);
    }

    public <T> void single(Class<T> iface, Class<? extends T> impl) {
        registry.single(iface, impl);
    }

    public <T> void multiple(Class<T> iface, Class<? extends T> impl) {
        registry.multiple(iface, impl);
    }

    public <T> T resolve(Class<T> type) {
        return spider.resolve(type);
    }
}