package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.dto.ApplyJobDTO;
import com.lanrecruitment.entity.JobApply;
import com.lanrecruitment.service.ApplyService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UserApplyController {

    private final ApplyService applyService;

    public UserApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping("/api/user/job/apply")
    public Response<Void> apply(@Valid @RequestBody ApplyJobDTO dto) {
        applyService.apply(dto);
        return Response.ok(null);
    }

    @GetMapping("/api/user/apply/list")
    public Response<List<JobApply>> listMy() {
        return Response.ok(applyService.listMy());
    }
}

