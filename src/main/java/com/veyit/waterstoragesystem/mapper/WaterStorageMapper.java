package com.veyit.waterstoragesystem.mapper;

import com.veyit.waterstoragesystem.entity.po.StatusReason;
import com.veyit.waterstoragesystem.entity.po.WsInfo;
import com.veyit.waterstoragesystem.entity.vo.WsInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WaterStorageMapper {
    List<WsInfoVO> selectInfos();

    List<WsInfoVO> selectXsInfos();

    List<WsInfo> selectWsInfos();

    WsInfoVO selectInfoByNum(Integer number);

    List<WsInfoVO> selectInfosByStatus(Integer status);

    int addWaterInfo(WsInfo wsInfo);

    int addStatusReason(StatusReason statusReason);

    WsInfo selectInfoByXsId(Integer sId);

    int updateInfoByXsId(WsInfo wsInfo);

    int updateStatus(StatusReason statusReason);

    int deleteInfoByXsId(Integer xsId);

}
