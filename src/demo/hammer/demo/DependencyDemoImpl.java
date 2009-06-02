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

import hammer.ant.helper.Ant;
import hammer.core.Build;
import hammer.core.BuildTasks;
import hammer.core.Builder;
import hammer.core.LittleHammer;

/**
 * A demo of Hammer dependencies.
 */
public final class DependencyDemoImpl implements DependencyDemo {
    private int numCalls = 0;

    // Package private fields are assigned by the hammer framework.
    // Call all methods through framework assigned objects to use
    // dependency management.
    DependencyDemo me;
    Speaker speaker;
    Ant ant;

    public void demo() {
        speaker.say("Hello World!");
        me.once();
        me.once(); // <- Won't do anything
        ant.echo("Task methods are called " + me.once() + " time per invocation.");
    }

    public int once() {
        ant.echo("I'm only going to say this once...");
        return ++numCalls;
    }
}

class SpeakerImpl implements Speaker {

    Ant ant;

    public void say(String msg) {
        ant.echo(msg);
    }
}

interface DependencyDemo extends BuildTasks {
    int once();
    void demo();
}

interface Speaker extends BuildTasks {
    void say(String msg);
}

class DependencyDemoRunner {
    public static void main(String... args) {
        Build build = new Build() {
            public void addTasks(Builder builder) {
                builder.addTasks(DependencyDemo.class, new DependencyDemoImpl());
                builder.addTasks(Speaker.class, new SpeakerImpl());
            }
        };
        new LittleHammer().hit(build, "demo");
    }
}