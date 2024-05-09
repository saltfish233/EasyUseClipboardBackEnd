package com.tnxts.easyuseclipboardbackend.controller;


import com.tnxts.easyuseclipboardbackend.domain.ClipboardItem;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItemInfo;
import com.tnxts.easyuseclipboardbackend.domain.CustomUserDetails;
import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.service.ClipboardService;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clipboard")
public class ClipboardController {
    @Autowired
    private ClipboardService clipboardService;

    @PostMapping("add")
    @PreAuthorize("hasAuthority('ADD_CLIPBOARD_ITEM')")
    public ServerResponse addClipboardItem(@RequestBody ClipboardItemInfo info,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        ClipboardItem item = clipboardService.addClipboardItem(info,userDetails.getUser());
        return new ServerResponse(ResultEnum.OK, item);
    }

    @GetMapping("collect/{clipboardItemId}")
    @PreAuthorize("hasAuthority('COLLECT_CLIPBOARD_ITEM')")
    public ServerResponse collectClipboardItem(@PathVariable Long clipboardItemId,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Boolean result = clipboardService.collectClipboardItem(clipboardItemId,userDetails.getUser());
        if(result)
        {
            return new ServerResponse(ResultEnum.OK);
        }
        else
        {
            return new ServerResponse(ResultEnum.NOT_FOUND);
        }
    }

    @GetMapping("uncollect/{clipboardItemId}")
    @PreAuthorize("hasAuthority('UNCOLLECT_CLIPBOARD_ITEM')")
    public ServerResponse unCollectClipboardItem(@PathVariable Long clipboardItemId,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Boolean result = clipboardService.unCollectClipboardItem(clipboardItemId,userDetails.getUser());
        if(result)
        {
            return new ServerResponse(ResultEnum.OK);
        }
        else
        {
            return new ServerResponse(ResultEnum.NOT_FOUND);
        }
    }

    @GetMapping("delete/{clipboardItemId}")
    @PreAuthorize("hasAuthority('DELETE_CLIPBOARD_ITEM')")
    public ServerResponse deleteClipboardItem(@PathVariable Long clipboardItemId,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Boolean result = clipboardService.deleteClipboardItem(clipboardItemId,userDetails.getUser());
        if(result)
        {
            return new ServerResponse(ResultEnum.OK);
        }
        else
        {
            return new ServerResponse(ResultEnum.NOT_FOUND);
        }
    }



    @GetMapping("all")
    @PreAuthorize("hasAuthority('GET_ALL_CLIPBOARD_ITEMS')")
    public ServerResponse getAllUserClipboardItems(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        ServerResponse serverResponse = new ServerResponse(ResultEnum.OK);
        serverResponse.setData(clipboardService.getAllClipboardItems(userDetails.getUser()));
        return serverResponse;
    }


}
