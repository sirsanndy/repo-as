package com.aws.repo.service;

import com.aws.repo.service.impl.DownloadServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class DownloadServiceTest {

    private final DownloadService downloadService = new DownloadServiceImpl();

    @Test
    void downloadFileByFilename() {
    }
}