package com.eliamtechnologies.mymango;

public class Topic {
    private String title;
    private Class<?> activityClass;

    public Topic(String title, Class<?> activityClass) {
        this.title = title;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}
