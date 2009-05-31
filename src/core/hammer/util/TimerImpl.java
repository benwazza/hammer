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

package hammer.util;

import java.text.DecimalFormat;

public final class TimerImpl implements Timer {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final double DUB_MILLIS = 1000;
    private static final int INT_MILLIS = 1000;
    private static final int HOURS = 60;
    private static final int MINS = HOURS;
    private long duration;
    private long start;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        long stop = System.currentTimeMillis();
        duration = stop - start;
    }

    public long getDuration() {
        return duration;
    }

    public String getHoursMinsSecs() {
        long hours = (duration / (INT_MILLIS * MINS * HOURS));
        long mins = (duration / (INT_MILLIS * MINS) % HOURS);
        double secs = (duration / DUB_MILLIS) % MINS;
        return getValue(hours, "hrs") + getValue(mins, "mins") + getValue(secs, "secs");
    }

    public String getSecs() {
        double secs = duration / DUB_MILLIS;
        return getValue(secs, "secs");
    }

    private String getValue(long val, String fieldName) {
        if (val == 0) return "";
        return addName(val, fieldName);
    }

    private String getValue(double val, String fieldName) {
        return addName(DECIMAL_FORMAT.format(val), fieldName);
    }

    private String addName(Object val, String fieldName) {
        return val + " " + fieldName + " ";
    }
}
