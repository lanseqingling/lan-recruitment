package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.dto.IdDTO;
import com.lanrecruitment.dto.NoticeSaveDTO;
import com.lanrecruitment.entity.Notice;
import com.lanrecruitment.service.NoticeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/api/public/notice/list")
    public Response<List<Notice>> listPublic() {
        return Response.ok(noticeService.listPublic());
    }

    @GetMapping("/api/admin/notice/list")
    public Response<List<Notice>> listAdmin(@RequestParam(value = "status", required = false) Integer status) {
        return Response.ok(noticeService.listAdmin(status));
    }

    @PostMapping("/api/admin/notice/save")
    public Response<Void> save(@Valid @RequestBody NoticeSaveDTO dto) {
        noticeService.save(dto);
        return Response.ok(null);
    }

    @PostMapping("/api/admin/notice/delete")
    public Response<Void> delete(@Valid @RequestBody IdDTO dto) {
        noticeService.delete(dto);
        return Response.ok(null);
    }
}

