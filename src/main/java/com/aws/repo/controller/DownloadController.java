package com.aws.repo.controller;

import com.aws.repo.service.DownloadService;
import com.aws.repo.util.DefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@RestController
@RequestMapping("/download")
public class DownloadController extends DefaultController {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

    private final DownloadService downloadService;

    @Value("${dir.home.path}")
    private String homeDir;

    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/{filename}")
    ResponseEntity<FileSystemResource> downloadFile(@PathVariable(name = "filename") String filename, HttpServletRequest request) {
        getClientIp(request);
        try {
            LOG.info("------------- Download file {} request from {} started -------------", filename, clientIp);
            File file = downloadService.downloadFileByFilename(homeDir, filename);
            long fileLength = file.length();
            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            respHeaders.setContentLength(fileLength);
            respHeaders.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error when processing download file {} request with error message : {}", filename, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } finally {
            LOG.info("------------- Download file {} request from {} finished -------------", filename, clientIp);
        }
    }
}
