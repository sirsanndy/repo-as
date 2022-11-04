package com.aws.repo.service;

import com.aws.repo.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class FileServiceTest {

    private final FileService fileService = new FileServiceImpl();

    @Test
    void givenNull_whenGetListOfFiles_thenShouldThrowERuntimeException() {
        Exception test = assertThrows(RuntimeException.class, () -> fileService.getListOfFiles(null));
        assertNotNull(test);
        assertEquals("Directory can't be null or empty spaces", test.getMessage());
    }

    @Test
    void givenValidDirectory_whenGetListOfFiles_thenShouldReturnFilename() {
        List<String> dummy = new ArrayList<>();
        dummy.add("file1.txt");
        dummy.add("file2.pdf");
        dummy.add("file3.jpg");

        URL url = Thread.currentThread().getContextClassLoader().getResource("files/");

        List<String> test = fileService.getListOfFiles(Objects.nonNull(url) ? url.getPath() : null);

        assertNotNull(test);
        assertEquals(dummy.size(), test.size());
        assertTrue(test.parallelStream().anyMatch("file3.jpg"::equals));
        assertFalse(test.parallelStream().anyMatch("file3.txt"::equals));
    }
}