package com.aws.repo.service.impl;

import com.aws.repo.exception.FileServiceException;
import com.aws.repo.service.FileService;
import com.aws.repo.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public List<String> getListOfFiles(String directory) {
        LOG.info("Processing get list of files in {}", directory);
        if(!StringUtils.isStringEmpty(directory)) {
            Path start = Paths.get(directory);
            try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
                List<String> collect = stream
                        .filter(t -> !Files.isDirectory(t))
                        .map(t -> String.valueOf(t.getFileName()))
                        .sorted()
                        .collect(Collectors.toList());
                LOG.info("List of files in {} being collected", directory);
                return collect;
            } catch (IOException e) {
                LOG.error("Error occurred when get list of files in directory {} with error message : ", directory, e);
                throw new FileServiceException(e.getMessage());
            }
        } else {
            throw new FileServiceException("Directory can't be null or empty spaces");
        }
    }
}
