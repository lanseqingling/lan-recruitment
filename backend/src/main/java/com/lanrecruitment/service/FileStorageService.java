package com.lanrecruitment.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeAvatar(MultipartFile file);

    String storeResumeAttachment(MultipartFile file);
}
