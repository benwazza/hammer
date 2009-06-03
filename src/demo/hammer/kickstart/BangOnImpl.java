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

package hammer.kickstart;

import hammer.ant.helper.Ant;

public final class BangOnImpl implements BangOn {
    BangOn me;
    Ant ant;

    public void plug() {
        allSmiles();
        me.say("Be awesome. Get with the Hammer and Bang On!");
        me.say("Blah"); // Won't run because has already run for this target
        allSmiles(); // Will run because calls are direct and are not being tracked
    }

    public void allSmiles() {
        ant.echo(":-) :-) :-) :-) :-) :-) :-) :-) :-) :-) :-) :-)");
    }

    public void say(String message) {
        ant.echo(message);
    }
}
