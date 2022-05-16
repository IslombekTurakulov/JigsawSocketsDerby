package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.animation.Animation;
import javafx.animation.Timeline;

import java.time.LocalTime;

/**
 * The type Timeline counter.
 */
public class TimelineCounter {
    private Timeline timeline;
    private LocalTime time;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TimelineCounter getInstance() {
        return TimelineCounter.SingletonHolder.TIMELINE_COUNTER;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Increment time.
     */
    public void incrementTime() {
        time = time.plusSeconds(1);
    }

    /**
     * Gets timeline.
     *
     * @return the timeline
     */
    public Timeline getTimeline() {
        return timeline;
    }

    /**
     * Sets timeline.
     *
     * @param timeline the timeline
     */
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

    /**
     * The type Singleton holder.
     */
    public static class SingletonHolder {
        /**
         * The constant TIMELINE_COUNTER.
         */
        public static final TimelineCounter TIMELINE_COUNTER = new TimelineCounter();
    }

}
