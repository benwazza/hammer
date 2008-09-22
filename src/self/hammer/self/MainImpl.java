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

package hammer.self;

public final class MainImpl implements Main {
    Artifacts artifacts;
    Prepare prepare;
    Quality quality;
    Package pkg;
    Main me;

    public void all() {
        me.quality();
        pkg.dist();
    }

    public void quality() {
        prepare.clean();
        artifacts.publish();
        quality.preCompile();
        quality.postCompile();
    }
}