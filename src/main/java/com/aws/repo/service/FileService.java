package com.aws.repo.service;

import java.util.List;

public interface FileService {
    List<String> getListOfFiles(String directory);
}
