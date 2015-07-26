package com.wakacommerce.common.extensibility.context.merge.handlers;

public abstract class BaseHandler implements MergeHandler, Comparable<Object> {

    protected int priority;
    protected String xpath;
    protected MergeHandler[] children = {};
    protected String name;

    public int getPriority() {
        return priority;
    }

    public String getXPath() {
        return xpath;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setXPath(String xpath) {
        this.xpath = xpath;
    }

    public int compareTo(Object arg0) {
        return new Integer(getPriority()).compareTo(new Integer(((MergeHandler) arg0).getPriority()));
    }

    public MergeHandler[] getChildren() {
        return children;
    }

    public void setChildren(MergeHandler[] children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
