/*
 *  Copyright 2008-2009 Ben Warren
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

package hammer.xml;

import java.util.ArrayList;
import java.util.List;

public final class Xml {

    public static Element n(String name, Object... content) {
        List<Attribute> attrs = extract(content, Attribute.class);
        List<Element> children = extract(content, Element.class);
        String text = extractText(content);
        return new Element(name, attrs, children, text);
    }

    public static Attribute a(String name, Object value) {
        return new Attribute(name, value.toString());
    }

    private static <T> List<T> extract(Object[] content, Class<T> t) {
        List<T> result = new ArrayList<T>();
        for (Object elem : content) {
            if (t.isAssignableFrom(elem.getClass())) result.add(t.cast(elem));
        }
        return result;
    }

    private static String extractText(Object[] content) {
        List<String> text = extract(content, String.class);
        if (text.isEmpty()) return Element.NO_TEXT;
        return text.get(0);
    }
}
