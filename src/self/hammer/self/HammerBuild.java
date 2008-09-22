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

package hammer.self;

import hammer.core.Build;
import hammer.core.BuildInfo;
import hammer.core.Builder;

@BuildInfo(buildName = "Hammer", defaultTask = "all")
public final class HammerBuild implements Build {

    public void addTasks(Builder builder) {
        builder.add(Main.class, new MainImpl());
        builder.add(Prepare.class, new PrepareImpl());
        builder.add(Quality.class, new QualityImpl());
        builder.add(Style.class, new StyleImpl());
        builder.add(Dupe.class, new DupeImpl());
        builder.add(Compile.class, new CompileImpl());
        builder.add(Tests.class, new TestsImpl());
        builder.add(Artifacts.class, new ArtifactsImpl());
        builder.add(Package.class, new PackageImpl());
    }
}
