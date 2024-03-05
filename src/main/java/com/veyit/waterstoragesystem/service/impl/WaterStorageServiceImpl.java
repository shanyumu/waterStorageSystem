package com.veyit.waterstoragesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.veyit.waterstoragesystem.entity.po.StatusReason;
import com.veyit.waterstoragesystem.entity.po.WsInfo;
import com.veyit.waterstoragesystem.entity.vo.WsInfoVO;
import com.veyit.waterstoragesystem.entity.vo.WsPositionVO;
import com.veyit.waterstoragesystem.mapper.WaterStorageMapper;
import com.veyit.waterstoragesystem.result.Result;
import com.veyit.waterstoragesystem.service.WaterStorageService;
import com.veyit.waterstoragesystem.tools.FilterInput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WaterStorageServiceImpl implements WaterStorageService {
    @Resource
    private WaterStorageMapper waterStorageMapper;


    @Override
    public Result<PageInfo<WsInfoVO>> showInfos(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        List<WsInfoVO> infoList = waterStorageMapper.selectInfos();
        if (!infoList.isEmpty()){
            return new Result<>(200, "获取蓄水池数据成功！", new PageInfo<>(infoList));
        }
        return new Result<>(2002,"暂无数据！");
    }

    @Override
    public Result<PageInfo<WsInfoVO>> showXsInfos(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<WsInfoVO> infoList = waterStorageMapper.selectXsInfos();
        if (!infoList.isEmpty()){
            return new Result<>(200, "获取问题蓄水池数据成功！", new PageInfo<>(infoList));
        }
        return new Result<>(2002,"暂无数据！");
    }

    @Override
    public Result<List<WsPositionVO>> getPosition() {
        List<WsInfo> wsInfos = waterStorageMapper.selectWsInfos();
        List<WsPositionVO> wsPositionVOS = new ArrayList<>();
        if (!wsInfos.isEmpty()) {
            for (WsInfo wsInfo : wsInfos) {
                WsPositionVO wsPositionVO = new WsPositionVO();
                wsPositionVO.setName(wsInfo.getPosition());
                List<Double> jwd = new ArrayList<>();
                jwd.add(wsInfo.getLongitude());
                jwd.add(wsInfo.getLatitude());
                wsPositionVO.setValue(jwd);
                wsPositionVO.setStatus(wsInfo.getStatus());
                wsPositionVOS.add(wsPositionVO);
            }
            return new Result<>(200,"获取位置成功！",wsPositionVOS);
            }
        return new Result<>(2002,"暂无数据！",wsPositionVOS);
    }

    @Override
    public Result<WsInfoVO> getInfoByNum(Integer number) {
        WsInfoVO wsInfoVO = waterStorageMapper.selectInfoByNum(number);
        if (wsInfoVO!=null){
            return new Result<>(200,"获取蓄水池成功！",wsInfoVO);
        }
        return new Result<>(2002,"不存在编号为"+number+"的蓄水池！");
    }

    @Override
    public Result<PageInfo<WsInfoVO>> getInfoByStatus(Integer status,Integer page,Integer limit) {
        if (status!=0&&status!=1&&status!=2){
            return new Result<>(2002,"不存在该状态");
        }
        PageHelper.startPage(page,limit);
        List<WsInfoVO> wsInfoVOList = waterStorageMapper.selectInfosByStatus(status);
        if (!wsInfoVOList.isEmpty()){
            return new Result<>(200,"获取"+status+"状态的蓄水池成功！",new PageInfo<>(wsInfoVOList));
        }
        List<WsInfoVO> wsInfoVOS = new ArrayList<>();
        return new Result<>(2002,"不存在"+status+"状态的蓄水池！",new PageInfo<>(wsInfoVOS));
    }

    @Override
    public Result<Integer> addInfo(WsInfoVO wsInfoVO) {
        if (wsInfoVO.getSize()==0){
            return new Result<>(2002,"容量不能为空！");
        }
        if (wsInfoVO.getPosition().equals("")){
            return new Result<>(2002,"蓄水池位置不能为空！");
        }
        if (wsInfoVO.getShape().equals("")){
            return new Result<>(2002,"蓄水池形态不能为空！");
        }
        if (wsInfoVO.getIsReview() == null){
            return new Result<>(2002,"请选择蓄水池是否整改！");
        }
        if (wsInfoVO.getStatus() == null){
            return new Result<>(2002,"请选择蓄水池当前状态！");
        }
        if (wsInfoVO.getLongitude() == null){
            return new Result<>(2002,"经度不能为空！");
        }
        if (wsInfoVO.getLatitude() == null){
            return new Result<>(2002,"纬度不能为空！");
        }
        if (wsInfoVO.getStatus()!=0&&wsInfoVO.getStatus()!=1&&wsInfoVO.getStatus()!=2){
            return new Result<>(2002,"不存在该状态");
        }
        if (!FilterInput.verifyMatch(FilterInput.POSITION_RULES,wsInfoVO.getLongitude().toString())) {
            return new Result<>(2002,"请正确输入经度");
        }
        if (!FilterInput.verifyMatch(FilterInput.POSITION_RULES,wsInfoVO.getLatitude().toString())) {
            return new Result<>(2002,"请正确输入纬度");
        }
        //与之前的数据对比

        WsInfo wsInfo = new WsInfo();
        wsInfo.setSize(wsInfoVO.getSize());
        wsInfo.setLongitude(wsInfoVO.getLongitude());
        wsInfo.setLatitude(wsInfoVO.getLatitude());
        wsInfo.setShape(wsInfoVO.getShape());
        wsInfo.setStatus(wsInfoVO.getStatus());
        wsInfo.setIsReview(wsInfoVO.getIsReview());
        wsInfo.setReviewSituation(wsInfoVO.getReviewSituation());
        wsInfo.setPosition(wsInfoVO.getPosition());
        int a = waterStorageMapper.addWaterInfo(wsInfo);

        StatusReason statusReason = new StatusReason();
        statusReason.setWsId(wsInfo.getXsId());
        statusReason.setWsStatus(wsInfoVO.getStatus());
        statusReason.setReason(wsInfoVO.getStatusReason().getReason());
        int b = waterStorageMapper.addStatusReason(statusReason);
        if (a==1&&b==1){
            return new Result<>(200,"蓄水池添加成功！",wsInfo.getXsId());
        }
        return new Result<>(2003,"添加失败！");
    }

    @Override
    public Result<Integer> updateInfo(WsInfoVO wsInfoVO) {
        WsInfo wsInfo1 = waterStorageMapper.selectInfoByXsId(wsInfoVO.getXsId());
        if (wsInfo1==null){
            return new Result<>(2002,"编号为"+wsInfoVO.getXsId()+"的蓄水池不存在！");
        }
        if (wsInfoVO.getSize()==0){
            return new Result<>(2002,"容量不能为空！");
        }
        if (wsInfoVO.getPosition().equals("")){
            return new Result<>(2002,"蓄水池位置不能为空！");
        }
        if (wsInfoVO.getShape().equals("")){
            return new Result<>(2002,"蓄水池形态不能为空！");
        }
        if (wsInfoVO.getIsReview() == null){
            return new Result<>(2002,"请选择蓄水池是否整改！");
        }
        if (wsInfoVO.getStatus() == null){
            return new Result<>(2002,"请选择蓄水池当前状态！");
        }
        if (wsInfoVO.getLongitude() == null){
            return new Result<>(2002,"经度不能为空！");
        }
        if (wsInfoVO.getLatitude() == null){
            return new Result<>(2002,"纬度不能为空！");
        }
        if (wsInfoVO.getStatus()!=0&&wsInfoVO.getStatus()!=1&&wsInfoVO.getStatus()!=2){
            return new Result<>(2002,"不存在该状态");
        }
        if (!FilterInput.verifyMatch(FilterInput.POSITION_RULES,wsInfoVO.getLongitude().toString())) {
            return new Result<>(2002,"请正确输入经度");
        }
        if (!FilterInput.verifyMatch(FilterInput.POSITION_RULES,wsInfoVO.getLatitude().toString())) {
            return new Result<>(2002,"请正确输入纬度");
        }
        WsInfo wsInfo = new WsInfo();
        wsInfo.setXsId(wsInfoVO.getXsId());
        wsInfo.setSize(wsInfoVO.getSize());
        wsInfo.setLongitude(wsInfoVO.getLongitude());
        wsInfo.setLatitude(wsInfoVO.getLatitude());
        wsInfo.setShape(wsInfoVO.getShape());
        wsInfo.setStatus(wsInfoVO.getStatus());
        wsInfo.setIsReview(wsInfoVO.getIsReview());
        wsInfo.setReviewSituation(wsInfoVO.getReviewSituation());
        wsInfo.setPosition(wsInfoVO.getPosition());
        int a = waterStorageMapper.updateInfoByXsId(wsInfo);
        StatusReason statusReason = new StatusReason();
        statusReason.setWsStatus(wsInfoVO.getStatus());
        statusReason.setWsId(wsInfoVO.getXsId());
        statusReason.setReason(wsInfoVO.getStatusReason().getReason());
        int b = waterStorageMapper.updateStatus(statusReason);
        if (a==1&&b==1){
            return new Result<>(200,"更新编号为"+wsInfo.getXsId()+"的蓄水池基本信息成功！",wsInfo.getXsId());
        }
        return new Result<>(2003,"更新失败！");
    }

    @Override
    public Result<Integer> deleteInfoByXsId(Integer xsId) {
        WsInfo wsInfo = waterStorageMapper.selectInfoByXsId(xsId);
        if (wsInfo==null){
            return new Result<>(2002,"编号为"+xsId+"的蓄水池不存在！");
        }
        int a = waterStorageMapper.deleteInfoByXsId(xsId);
        if (a==2){
            return new Result<>(200,"编号为"+xsId+"的蓄水池删除成功！",xsId);
        }
        return new Result<>(2003,"删除失败！");
    }

    @Override
    public Result<WsInfo> getInfoByXsId(Integer xsId) {
        WsInfo wsInfo = waterStorageMapper.selectInfoByXsId(xsId);
        if (wsInfo!=null){
            return new Result<>(200,"获取编号为"+xsId+"的蓄水池信息成功！",wsInfo);
        }
        return new Result<>(2002,"编号为"+xsId+"的蓄水池不存在！");
    }

}
