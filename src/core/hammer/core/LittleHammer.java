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

import hammer.config.BuildConfigImpl;
import hammer.ioc.Ioc;
import hammer.ioc.IocImpl;

public final class LittleHammer implements Hammer {
    private final Hammer delegate;

    public LittleHammer() {
        BuildConfigImpl config = new BuildConfigImpl();
        Ioc ioc = new IocImpl(config);
        delegate = ioc.resolve(Hammer.class);
    }

    public int hit(Build build, String... tasks) {
        return delegate.hit(build, tasks);
    }

    public void publish(Build build) {
        delegate.publish(build);
    }
}
