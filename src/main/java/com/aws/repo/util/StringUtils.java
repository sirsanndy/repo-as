package com.aws.repo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class StringUtils {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtils.class);
    public static boolean isStringEmpty(String str){
        LOG.info("Check if string of {} is empty", str);
        return Objects.isNull(str) || str.isEmpty() || str.trim().isEmpty();
    }

    private StringUtils() {
        throw new AssertionError();
    }
}
