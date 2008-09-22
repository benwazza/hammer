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

package hammer.demo;

import static hammer.ant.core.AntXml.a;
import static hammer.ant.core.AntXml.e;
import hammer.ant.helper.Ant;
import hammer.core.BabyHammer;
import hammer.core.Build;
import hammer.core.BuildComponent;
import hammer.core.BuildInfo;
import hammer.core.Builder;
import hammer.core.Hammer;
import hammer.publish.Publish;

/**
 * A demo of Hammer as an embedded process.
 */
@BuildInfo(buildName = "A Demo Project", defaultTask = "conversation")
public final class DemoBuild implements Build {

    public void addTasks(Builder def) {
        def.add(DemoTasks.class, new DemoTasksImpl());
    }

    public static void main(String... args) {
        Hammer hammer = new BabyHammer();
        hammer.hit(new DemoBuild());
    }
}

interface DemoTasks extends BuildComponent {
    @Publish("Greet the world")
    void hello();

    @Publish("Empart great wisdom")
    void chat();

    @Publish("Finish a conversation")
    void goodbye();

    @Publish("A demo task that prints a conversation via Ant")
    void conversation();
}

final class DemoTasksImpl implements DemoTasks {
    // package private fields are assigned by the hammer ioc engine
    Ant ant;
    // call all methods through the framework to utilise dependency management
    DemoTasks me;

    public void hello() {
        echo1("Hello World!");
    }

    public void chat() {
        echo2("The job is so much easier ", "when you have the right Hammer! :-)");
    }

    public void goodbye() {
        me.hello();
        time();
        echo3("Got to go, it's ${TIME} already!");
    }

    public void conversation() {
        me.hello();
        me.chat();
        me.goodbye();
    }

    private void echo1(String text) {
        // element with text content:
        // <echo>some text</echo>
        ant.run(e("echo", text));
    }

    private void echo2(String attr, String text) {
        // element with an attribute and text:
        // <echo message="some text">more text</echo>
        ant.run(e("echo", a("message", attr), text));
    }

    private void echo3(String msg) {
        // utility method
        ant.echo(msg);
    }

    private void time() {
        // element with mulitple attributes, also sets an ant project property
        ant.run(
            e("tstamp",
                e("format",
                    a("property", "TIME"),
                    a("pattern", "HH:mm")
                )
            )
        );

        // We could have done this..
        // DateFormat df = new SimpleDateFormat("HH:mm");
        // return df.format(new Date());
    }
}