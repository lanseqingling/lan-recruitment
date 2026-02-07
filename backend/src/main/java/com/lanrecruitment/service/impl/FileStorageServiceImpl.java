package com.lanrecruitment.service.impl;

import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.service.FileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final long AVATAR_MAX_SIZE = 2 * 1024 * 1024L;
    private static final long RESUME_MAX_SIZE = 10 * 1024 * 1024L;

    @Override
    public String storeAvatar(MultipartFile file) {
        return store(file, "avatar", new String[]{"jpg", "jpeg", "png"}, AVATAR_MAX_SIZE);
    }

    @Override
    public String storeResumeAttachment(MultipartFile file) {
        return store(file, "resume", new String[]{"pdf", "jpg", "jpeg", "png"}, RESUME_MAX_SIZE);
    }

    private String store(MultipartFile file, String subDir, String[] allowedExt, long maxSize) {
        if (file == null || file.isEmpty()) {
            throw new BizException(400, "文件不能为空");
        }
        if (file.getSize() > maxSize) {
            throw new BizException(400, "文件过大");
        }

        String original = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String ext = ext(original);
        if (!isAllowed(ext, allowedExt)) {
            throw new BizException(400, "文件类型不支持");
        }

        String filename = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        Path dir = Paths.get("uploads", subDir);
        Path target = dir.resolve(filename);

        try {
            Files.createDirectories(dir);
            file.transferTo(target.toFile());
        } catch (IOException e) {
            throw new BizException(500, "文件保存失败");
        }

        return "/uploads/" + subDir + "/" + filename;
    }

    private boolean isAllowed(String ext, String[] allowed) {
        for (String a : allowed) {
            if (a.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    private String ext(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) {
            return "";
        }
        return filename.substring(idx + 1).toLowerCase(Locale.ROOT);
    }
}
