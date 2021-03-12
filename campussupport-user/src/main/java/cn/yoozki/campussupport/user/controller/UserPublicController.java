package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.user.pojo.CampusDO;
import cn.yoozki.campussupport.user.service.UserPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/public")
public class UserPublicController {

    @Autowired
    private UserPublicService userPublicService;

    @GetMapping("/campus")
    public JSONResult listCampus() {
        return JSONResult.ok(userPublicService.listCampusDO());
    }

}
