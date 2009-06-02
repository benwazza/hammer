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

package hammer.demo;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.ant.helper.Ant;
import hammer.core.Build;
import hammer.core.BuildTasks;
import hammer.core.Builder;
import hammer.core.Hammer;
import hammer.core.LittleHammer;

// SUGGEST Need example/ability of executing ant tasks from everyday code
public final class AntDemoImpl implements AntDemo {

    // package private fields are assigned by hammer
    Ant ant;

    public void demo() {

        // <echo>Child text</echo>
        ant.run(e("echo", "Child text"));

        // <echo message="Attribute text and ">child text</echo>
        ant.run(e("echo", a("message", "Attribute text and "), "child text"));

        // element with child elements, sets an ant project property
        ant.run(
            e("tstamp",
                e("format",
                    a("property", "TIME"),
                    a("pattern", "HH:mm")
                )
            )
        );

        // utility shortcut method
        ant.echo("The time is ${TIME}.");
    }
}

interface AntDemo extends BuildTasks {
    public void demo();
}

class AntDemoRunner {
    public static void main(String... args) {
        Build build = new Build() {
            public void addTasks(Builder builder) {
                builder.addTasks(AntDemo.class, new AntDemoImpl());
            }
        };
        Hammer hammer = new LittleHammer();
        hammer.hit(build, "demo");
    }
}