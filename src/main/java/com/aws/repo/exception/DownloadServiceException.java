package com.aws.repo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class DownloadServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4050771028243943042L;

    private static final Logger LOG = LoggerFactory.getLogger(DownloadServiceException.class);

    public DownloadServiceException(String str) {
        super(str);
        LOG.error(str);
    }
}
