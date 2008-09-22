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

package hammer.core;

import hammer.config.BuildConfig;
import hammer.config.BuildConfigImpl;
import hammer.ioc.Ioc;
import hammer.ioc.IocBooter;
import hammer.ioc.IocImpl;

// TODO delete
public final class HammerBuilderImpl implements HammerBuilder {

    public Hammer build(Build def) {
        return build(new BuildConfigImpl(), def);
    }

    public Hammer build(BuildConfig config, Build def) {
        Ioc ioc = wire(config);
        return ioc.nu(Hammer.class, def);
    }

    private Ioc wire(BuildConfig config) {
        Ioc ioc = new IocImpl();
        ioc.instance(BuildConfig.class, config);
        IocBooter booter = ioc.resolve(IocBooter.class);
        booter.wire(config);
        return ioc;
    }
}