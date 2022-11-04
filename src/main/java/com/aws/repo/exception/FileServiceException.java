package com.aws.repo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class FileServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6607387625329276885L;

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceException.class);

    public FileServiceException(String str){
        super(str);
        LOG.error(str);
    }
}
