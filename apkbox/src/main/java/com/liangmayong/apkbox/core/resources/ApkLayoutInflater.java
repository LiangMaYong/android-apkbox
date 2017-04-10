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
