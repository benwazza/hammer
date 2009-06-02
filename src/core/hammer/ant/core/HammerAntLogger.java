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

package hammer.ant.core;

import au.net.netstorm.boost.bullet.log.Log;
import au.net.netstorm.boost.gunge.exception.ThrowableMaster;
import hammer.config.BuildConfig;
import hammer.core.Constants;
import hammer.log.LogIndenter;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

public final class HammerAntLogger implements BuildLogger, Constants {
    LogLevelTranslator translator;
    ThrowableMaster tosser;
    LogIndenter indenter;
    BuildConfig config;
    Log log;

    public void messageLogged(BuildEvent event) {
        if (!somethingToLog(event)) return;
        if (event.getTask() != null) {
            String message = buildTaskMessage(event, event.getTask());
            logEvent(event, message);
        } else {
            logEvent(event, event.getMessage());
        }
    }

    public void buildStarted(BuildEvent event) {
        doNothing();
    }

    public void buildFinished(BuildEvent event) {
        doNothing();
    }

    public void targetStarted(BuildEvent event) {
        doNothing();
    }

    public void targetFinished(BuildEvent event) {
        doNothing();
    }

    public void taskStarted(BuildEvent event) {
        doNothing();
    }

    public void taskFinished(BuildEvent event) {
        doNothing();
    }

    public void setMessageOutputLevel(int level) {
        doNothing();
    }

    public void setOutputPrintStream(PrintStream output) {
        doNothing();
    }

    public void setEmacsMode(boolean emacsMode) {
        doNothing();
    }

    public void setErrorPrintStream(PrintStream err) {
        doNothing();
    }

    private boolean empty(BuildEvent event) {
        return event.getMessage() == null || "".equals(event.getMessage().trim());
    }

    private void logEvent(BuildEvent event, String message) {
        Throwable ex = event.getException();
        if (ex != null) {
            logException(message, ex);
        } else {
            log.info(message);
        }
    }

    private void logException(String message, Throwable ex) {
        Throwable cause = tosser.realCause(ex);
        String trace = tosser.trace(cause);
        log.error(message + trace);
    }

    private String buildTaskMessage(BuildEvent event, Task task) {
        StringBuffer message = new StringBuffer();
        String name = makeTaskName(task);
        // TODO Edge here???
        try {
            addTaskNameToLines(event, message, name);
        } catch (Exception e) {
            throw new IllegalStateException("Holy cow this is impossible!");
        }
        return message.toString();
    }

    private void addTaskNameToLines(BuildEvent event, StringBuffer message, String name) throws IOException {
        BufferedReader r = new BufferedReader(new StringReader(event.getMessage()));
        String line = r.readLine();
        if (line == null) message.append(name).append(LINE_SEPARATOR);
        while (line != null) {
            message.append(name).append(line);
            line = r.readLine();
        }
        r.close();
    }

    private String makeTaskName(Task task) {
        String name = task.getTaskName();
        StringBuffer result = new StringBuffer("[" + name + "]");
        String padding = indenter.getIndent();
        result.insert(0, padding);
        return result.toString();
    }

    private boolean somethingToLog(BuildEvent event) {
        return !empty(event) && config.levelEnabled(translator.translate(event));
    }

    private void doNothing() {
    }
}