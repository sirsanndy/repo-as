package com.aws.repo.service.impl;

import com.aws.repo.dto.DownloadResponse;
import com.aws.repo.exception.DownloadServiceException;
import com.aws.repo.service.DownloadService;
import com.aws.repo.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class DownloadServiceImpl implements DownloadService {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Override
    public DownloadResponse<StreamingResponseBody> downloadFileByFilename(String directory, String filename) {
        LOG.info("Downloading file with name {} processed", filename);
        if (!StringUtils.isStringEmpty(directory) && !StringUtils.isStringEmpty(filename)) {
            Path start = Paths.get(directory);
            try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
                DownloadResponse<StreamingResponseBody> response = new DownloadResponse<>();
                LOG.info("Find filePath with filename {} in directory {}", filename, directory);
                String filePath = stream
                        .filter(t -> !Files.isDirectory(t))
                        .map(String::valueOf)
                        .filter(t -> t.contains(filename))
                        .findFirst()
                        .orElse("");
                LOG.info("Find {} filePath of {} in directory {} done", filePath, filename, directory);
                File file = new File(filePath);
                    FileInputStream inputStream = new FileInputStream(file);
                    response.setFileLength(file.length());
                    response.setData(outputStream -> {
                        int numberOfBytesToWrite;
                        byte[] data = new byte[64 * 1024];
                        while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
                            outputStream.write(data, 0, numberOfBytesToWrite);
                        }

                        inputStream.close();
                    });
                    return response;
            } catch (IOException e) {
                LOG.error("Error occurred when get filePath by filename {} in directory {} with error message : ", filename, directory, e);
                throw new DownloadServiceException(String.format("Error occurred when get %s to download", filename));
            }
        } else {
            LOG.error("Directory and filename can't be null or empty");
            throw new DownloadServiceException("Directory and filename can't be null or empty");
        }
    }
}
