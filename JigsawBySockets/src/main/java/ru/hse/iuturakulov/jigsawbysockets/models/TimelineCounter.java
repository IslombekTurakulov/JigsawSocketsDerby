package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.DATE_TIME_FORMATTER;

public class TimelineCounter {
    private Timeline timeline;
    private LocalTime time;

    public TimelineCounter() {
        initializeTimer();
    }

    public LocalTime getTime() {
        return time;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Timer initializer
     */
    private void initializeTimer() {
        time = LocalTime.parse("00:00:00");
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
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

}
