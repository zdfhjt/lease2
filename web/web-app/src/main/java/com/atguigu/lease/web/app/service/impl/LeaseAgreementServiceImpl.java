package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.login.LoginUser;
import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.ApartmentInfoService;
import com.atguigu.lease.web.app.service.LeaseAgreementService;
import com.atguigu.lease.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.lease.web.app.vo.agreement.AgreementItemVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;


    @Override
    public List<AgreementItemVo> getAgreementItemVoList(String username) {
        return leaseAgreementMapper.getAgreementItemVoList(username);
    }

    @Override
    public AgreementDetailVo getAgreementDetailVoListByID(Long id) {
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        String phone = leaseAgreement.getPhone();
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        System.out.println(loginUser.getUsername()+"---dsddddddddddddddddddddddddddd");
        System.out.println(phone+"---dsddddddddddddddddddddddddddd");
        if(!phone .equalsIgnoreCase(loginUser.getUsername())){
            throw new LeaseException(ResultCodeEnum.GET_LEASE_BY_ID_ERROR);
        }
        if(leaseAgreement == null){
            return null;
        }
        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());

        //3.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());
        //4.查询图片列表
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, apartmentInfo.getId());

        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, roomInfo.getId());
        //5.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());

        //6.查询租期
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());


        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement,agreementDetailVo);
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());
        return agreementDetailVo;
    }

    @Override
    public LeaseAgreement findByApartmentIdAndRoomIdAndStatus(Long apartmentId, Long roomId, LeaseStatus leaseStatus) {
        LeaseAgreement leaseAgreement = leaseAgreementMapper.findByApartmentIdAndRoomIdAndStatus(apartmentId, roomId, leaseStatus);
        return null;
    }
}




