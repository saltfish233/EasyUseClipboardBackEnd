package com.tnxts.easyuseclipboardbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class ExceptionController {

    @RequestMapping("/exception/exThrow")
    @ResponseBody
    public void rethrow(HttpServletRequest request) throws Exception {
        log.info("进入异常controller");
        throw ((Exception)request.getAttribute("filter.error"));
    }
}
