package com.aws.repo.web;

import com.aws.repo.service.FileService;
import com.aws.repo.util.DefaultController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/files")
public class FileController extends DefaultController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;
    @Value("${dir.home.path}")
    private String homeDir;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    String getFile(Model model, HttpServletRequest request) {
        getClientIp(request);
        LOG.info("------------- Get list of files in directory {} request from {} started -------------", homeDir, clientIp);
        model.addAttribute("files", fileService.getListOfFiles(homeDir));
        LOG.info("------------- Get list of files in directory {} request from {} finished -------------", homeDir, clientIp);
        return "file";
    }
}
