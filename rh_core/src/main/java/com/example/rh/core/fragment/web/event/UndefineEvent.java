package com.example.rh.core.fragment.web.event;

import com.example.rh.core.utils.log.MyLogger;

/**
 * @author RH
 * @date 2018/11/2
 */
public class UndefineEvent extends Event{
    @Override
    public String execute(String params) {
        MyLogger.e("UndefineEvent",params);
        return null;
    }
}
