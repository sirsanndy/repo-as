package com.aws.repo.controller;

import com.aws.repo.dto.DownloadResponse;
import com.aws.repo.service.DownloadService;
import com.aws.repo.util.DefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


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
    ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable(name = "filename") String filename, HttpServletRequest request) {
        getClientIp(request);
        LOG.info("------------- Download file {} request from {} started -------------", filename, clientIp);
        DownloadResponse<StreamingResponseBody> responseBody = downloadService.downloadFileByFilename(homeDir, filename);
        HttpHeaders respHeaders = new HttpHeaders();
        if (Objects.nonNull(responseBody) && Objects.nonNull(responseBody.getData())) {
            respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            respHeaders.setContentLength(responseBody.getFileLength());
            respHeaders.setContentDispositionFormData("attachment", filename);
            LOG.info("------------- Download file {} request from {} finished -------------", filename, clientIp);
            return ResponseEntity.ok()
                    .headers(respHeaders)
                    .body(responseBody.getData());
        }

        return ResponseEntity.ok()
                .headers(respHeaders)
                .body(null);
    }
}
