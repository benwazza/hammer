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

package hammer.publish;

import hammer.core.Build;
import hammer.core.BuildInfo;
import hammer.core.Constants;
import hammer.core.Task;
import hammer.core.TaskRegistry;
import hammer.util.Padder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BuildPublisherImpl implements BuildPublisher, Constants {
    TaskRegistry registry;
    Padder padder;

    public void publish(Build build) {
        List<Task> tasks = registry.getPublishedTasks();
        List<StringBuffer> names = collectNames(tasks);
        String buildName = getBuildName(build);
        logDetails(buildName, tasks, names);
    }

    private String getBuildName(Build build) {
        Class<? extends Build> buildClass = build.getClass();
        if (!buildClass.isAnnotationPresent(BuildInfo.class)) return "Not Specified";
        BuildInfo info = buildClass.getAnnotation(BuildInfo.class);
        return info.buildName();
    }

    private List<StringBuffer> collectNames(List<Task> tasks) {
        List<StringBuffer> names = new ArrayList<StringBuffer>();
        Set<String> shortNames = new HashSet<String>();
        for (Task task : tasks) {
            colectName(names, shortNames, task);
        }
        return names;
    }

    private void colectName(List<StringBuffer> names, Set<String> shortNames, Task task) {
        String name = task.shortName();
        if (!shortNames.contains(name)) names.add(new StringBuffer(name));
        else names.add(new StringBuffer(task.longName()));
        shortNames.add(name);
    }

    // OK GenericIllegalRegexp {
    private void logDetails(String projectName, List<Task> tasks, List<StringBuffer> names) {
        System.out.println("");
        System.out.println("Published tasks for the '" + projectName + "' project:");
        System.out.println("");
        padder.padAfter(names, " ");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            StringBuffer name = names.get(i);
            System.out.println("  " + name + "  " + task.getDescription());
        }
        System.out.println(LINE_SEPARATOR);
    }
    // } OK GenericIllegalRegexp 
}