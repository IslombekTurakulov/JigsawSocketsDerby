package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.animation.Animation;
import javafx.animation.Timeline;

import java.time.LocalTime;

public class TimelineCounter {
    private Timeline timeline;
    private LocalTime time;

    public static TimelineCounter getInstance() {
        return TimelineCounter.SingletonHolder.TIMELINE_COUNTER;
    }

    public LocalTime getTime() {
        return time;
    }

    public void incrementTime() {
        time = time.plusSeconds(1);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * Timer initializer
     */
    public void initializeTimer() {
        time = LocalTime.parse("00:00:00");
    }

    /**
     * Method to pause timer.
     */
    private void pauseTimer() {
        switch (timeline.getStatus()) {
            case PAUSED -> timeline.play();
            case RUNNING -> timeline.pause();
        }
    }

    public static class SingletonHolder {
        public static final TimelineCounter TIMELINE_COUNTER = new TimelineCounter();
    }

}
