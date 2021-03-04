package com.cdutcm.core.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author mxq
 * @date: 2018/12/17 09:01
 * @desc: 日志过滤
 */
public class LogbackMarkerFilter extends AbstractMatcherFilter<ILoggingEvent> {

    private Marker markerToMatch = null;
    @Override
    public void start() {
        if (null != this.markerToMatch) {
            super.start();
        } else {
            addError(" no MARKER yet !");
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        Marker marker = event.getMarker();
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }
        if (null == marker) {
            return onMismatch;
        }
        if (markerToMatch.contains(marker)) {
            return onMatch;
        }
        return onMismatch;
    }

    public void setMarker(String markerStr) {
        if (null != markerStr) {
            markerToMatch = MarkerFactory.getMarker(markerStr);
        }
    }

}
