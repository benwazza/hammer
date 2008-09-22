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

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import static hammer.ant.core.AntXml.fileSet;
import static hammer.ant.core.AntXml.filter;
import static hammer.ant.core.AntXml.filterSet;
import static hammer.ant.core.AntXml.include;
import hammer.ant.helper.Ant;

import java.io.File;

public final class ArtifactsImpl implements Artifacts, BuildConstants {
    private static final File ARTIFACTS_TEMPLATE_DIR = new File(CONFIG_DIR, "artifacts");
    Ant ant;

    public void publish() {
        ant.run(
            e("copy", a("todir", ARTIFACTS_DIR),
                fileSet(ARTIFACTS_TEMPLATE_DIR,
                    include("**/*")
                ),
                filterSet(
                    filter("build.date", BUILD_DATE),
                    filter("build.label", BUILD_LABEL),
                    filter("project.name", PROJECT_NAME)
                )
            )
        );
    }
}
