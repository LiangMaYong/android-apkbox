/**
 The MIT License (MIT)

 Copyright (c) 2017 LiangMaYong ( ibeam@qq.com )

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 **/
package com.liangmayong.apkbox.core.resources;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by liangmayong on 2016/9/21.
 */
public class ApkLayoutInflater extends LayoutInflater {

    private LayoutInflater target;

    public ApkLayoutInflater(LayoutInflater target) {
        super(target.getContext());
        this.target = target;
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return target.cloneInContext(newContext);
    }

    @Override
    public Context getContext() {
        return target.getContext();
    }

    @Override
    public void setFactory(Factory factory) {
        target.setFactory(factory);
    }

    @Override
    public Filter getFilter() {
        return target.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        target.setFilter(filter);
    }

    @Override
    public View inflate(int resource, ViewGroup root) {
        return target.inflate(resource, root);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        return target.inflate(parser, root);
    }

    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return target.inflate(resource, root, attachToRoot);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return target.inflate(parser, root, attachToRoot);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        try {
            return (View) LayoutInflater.class.getDeclaredMethod("onCreateView", String.class, AttributeSet.class)
                    .invoke(target, name, attrs);
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateView(name, attrs);
        }
    }

    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        try {
            return (View) LayoutInflater.class
                    .getDeclaredMethod("onCreateView", View.class, String.class, AttributeSet.class)
                    .invoke(target, parent, name, attrs);
        } catch (Exception e) {
            return super.onCreateView(name, attrs);
        }
    }
}
