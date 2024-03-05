package com.veyit.waterstoragesystem.service;

import com.github.pagehelper.PageInfo;
import com.veyit.waterstoragesystem.entity.po.StatusReason;
import com.veyit.waterstoragesystem.entity.po.WsInfo;
import com.veyit.waterstoragesystem.entity.vo.WsInfoVO;
import com.veyit.waterstoragesystem.entity.vo.WsPositionVO;
import com.veyit.waterstoragesystem.result.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface WaterStorageService {
    Result<PageInfo<WsInfoVO>> showInfos(Integer page,Integer limit);

    Result<PageInfo<WsInfoVO>> showXsInfos(Integer page, Integer limit);

    Result<List<WsPositionVO>> getPosition();

    Result<WsInfoVO> getInfoByNum(Integer number);

    Result<PageInfo<WsInfoVO>> getInfoByStatus(Integer status,Integer page,Integer limit);

    Result<Integer> addInfo(WsInfoVO wsInfoVO);

    Result<Integer> updateInfo(WsInfoVO wsInfoVO);

    Result<Integer> deleteInfoByXsId(Integer xsId);

    Result<WsInfo> getInfoByXsId(Integer xsId);

}
