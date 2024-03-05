package com.veyit.waterstoragesystem.controller;

import com.github.pagehelper.PageInfo;
import com.veyit.waterstoragesystem.entity.po.StatusReason;
import com.veyit.waterstoragesystem.entity.po.WsInfo;
import com.veyit.waterstoragesystem.entity.vo.WsInfoVO;
import com.veyit.waterstoragesystem.entity.vo.WsPositionVO;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.service.WaterStorageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("waterStorage")
public class WaterStorageController {
    @Resource
    private WaterStorageService waterStorageService;

    //获取所有蓄水池的信息
    @GetMapping("getInfos")
    public Result<PageInfo<WsInfoVO>> infoList(@RequestParam(required = false, defaultValue = "1") Integer page,@RequestParam(required = false, defaultValue = "5") Integer limit) {
        return waterStorageService.showInfos(page,limit);
    }

    //获取所有问题蓄水池信息
    @GetMapping("getXsInfos")
    public Result<PageInfo<WsInfoVO>> xsInfoList(@RequestParam(required = false, defaultValue = "1") Integer page,@RequestParam(required = false, defaultValue = "5") Integer limit) {
        return waterStorageService.showXsInfos(page,limit);
    }

    //按照编号获取蓄水池信息
    @GetMapping("getInfoByXsId/{xsId}")
    public Result<WsInfo> getInfoByXsId(@PathVariable Integer xsId){
        return waterStorageService.getInfoByXsId(xsId);
    }

    //获取蓄水池的经纬度和位置
    @GetMapping("getPosition")
    public Result<List<WsPositionVO>> getPosition() {
        return waterStorageService.getPosition();
    }

    //检索
    //按照编号检索
    @GetMapping("getInfoum/{numberByN}")
    public Result<WsInfoVO> getInfoByNum(@PathVariable Integer number){
        return waterStorageService.getInfoByNum(number);
    }

    //按照状态检索
    //在用：0 闲置：1 废弃：2
    @GetMapping("getInfosByStatus/{status}")
    public Result<PageInfo<WsInfoVO>> getInfoByStatus(@PathVariable Integer status, @RequestParam(required = false, defaultValue = "1") Integer page,@RequestParam(required = false, defaultValue = "5") Integer limit){
        return waterStorageService.getInfoByStatus(status,page,limit);
    }

    //添加蓄水池
    @PostMapping("addInfo")
    public Result<Integer> addInfo(@RequestBody WsInfoVO wsInfoVO){
        return waterStorageService.addInfo(wsInfoVO);
    }

    //更新蓄水池
    //更新基本数据
    @PostMapping("updateInfo")
    public Result<Integer> update(@RequestBody WsInfoVO wsInfoVO){
        System.out.println(wsInfoVO);
        return waterStorageService.updateInfo(wsInfoVO);
    }

    //删除蓄水池
    @DeleteMapping("deleteInfo/{xsId}")
    public Result<Integer> deleteInfoByXsId(@PathVariable Integer xsId){
        return waterStorageService.deleteInfoByXsId(xsId);
    }
}
