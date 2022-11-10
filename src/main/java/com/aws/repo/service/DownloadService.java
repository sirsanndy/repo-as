package com.aws.repo.service;

import com.aws.repo.dto.DownloadResponse;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface DownloadService {
    DownloadResponse<StreamingResponseBody> downloadFileByFilename(String directory, String filename);
}
