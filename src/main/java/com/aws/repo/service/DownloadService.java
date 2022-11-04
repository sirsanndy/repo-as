package com.aws.repo.service;

import java.io.File;

public interface DownloadService {
    File downloadFileByFilename(String directory, String filename);
}
