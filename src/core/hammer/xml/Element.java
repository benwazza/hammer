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

package hammer.xml;

import au.net.netstorm.boost.bullet.primordial.Primordial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Element extends Primordial {
    public static final String NO_TEXT = "";
    private final String name;
    private final List<Attribute> attrs;
    private final List<Element> children;
    private final String text;

    public Element(String name, List<Attribute> attrs, List<Element> children, String text) {
        this.name = name;
        this.attrs = Collections.unmodifiableList(attrs);
        this.children = Collections.unmodifiableList(children);
        this.text = text;
    }

    public String name() {
        return name;
    }

    public List<Attribute> attributes() {
        return attrs;
    }

    public List<Element> children() {
        return children;
    }

    public boolean hasText() {
        return !text.equals(NO_TEXT);
    }

    public String text() {
        return text;
    }

    /**
     * Add attributes to an element.
     * NOTE: This method returns a new element and does not modify the original object.
     *
     * @param attrs The attributes to add.
     * @return A new element with the attributes added.
     */
    public Element withAttrs(Attribute... attrs) {
        return with(attrs, new Element[0]);
    }

    /**
     * Add child elements to an element.
     * NOTE: This method returns a new element and does not modify the original object.
     *
     * @param elems The child elements to add.
     * @return A new element with the children added.
     */
    public Element withElems(Element... elems) {
        return with(new Attribute[0], elems);
    }

    /**
     * Add attributes and child elements to an element.
     * NOTE: This method returns a new element and does not modify the original object.
     *
     * @param attrs The attributes to add.
     * @param elems The child elements to add.
     * @return A new element with the attributes and children added.
     */
    public Element with(Attribute[] attrs, Element[] elems) {
        List<Attribute> atrrsCopy = new ArrayList<Attribute>(this.attrs);
        List<Element> elemsCopy = new ArrayList<Element>(this.children);
        atrrsCopy.addAll(Arrays.asList(attrs));
        elemsCopy.addAll(Arrays.asList(elems));
        return new Element(name, atrrsCopy, elemsCopy, text);
    }
}