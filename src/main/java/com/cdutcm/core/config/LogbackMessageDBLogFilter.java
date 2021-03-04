package com.cdutcm.core.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author mxq
 * @date: 2018/12/17 09:39
 * @desc:
 */
public class LogbackMessageDBLogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {

        if(event.getMessage()!=null&& (event.getMessage().contains("】"))&&(event.getMessage().contains("【"))){
            return FilterReply.ACCEPT;
        }
        /**
         * shrio錯誤日誌不打印
         */
        if(event.getMessage()!=null && (event.getMessage().contains("SecurityManager"))){
            return FilterReply.DENY;
        }
        return FilterReply.DENY;
    }

}
