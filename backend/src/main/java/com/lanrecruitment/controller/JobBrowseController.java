package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.service.JobBrowseService;
import com.lanrecruitment.domain.vo.JobCardVO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobBrowseController {

    private final JobBrowseService jobBrowseService;

    public JobBrowseController(JobBrowseService jobBrowseService) {
        this.jobBrowseService = jobBrowseService;
    }

    @GetMapping("/api/public/job/list")
    public Response<List<JobCardVO>> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "jobType", required = false) String jobType,
            @RequestParam(value = "tagIds", required = false) String tagIds,
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return Response.ok(jobBrowseService.listPublic(keyword, city, jobType, tagIds, cursorId, pageSize));
    }

    @GetMapping("/api/public/job/detail")
    public Response<JobCardVO> detail(@RequestParam("id") Long id) {
        return Response.ok(jobBrowseService.detail(id));
    }

    @GetMapping("/api/user/job/recommend")
    public Response<List<JobCardVO>> recommend(
            @RequestParam(value = "resumeId", required = false) Long resumeId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "jobType", required = false) String jobType,
            @RequestParam(value = "tagIds", required = false) String tagIds,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return Response.ok(jobBrowseService.recommend(resumeId, keyword, city, jobType, tagIds, offset, pageSize));
    }
}
