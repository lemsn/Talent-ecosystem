package com.cykj.net.service.admin.impl;

import com.cykj.net.javabean.AdminPositionStation;
import com.cykj.net.mapper.admin.AdminPositionStationMapper;
import com.cykj.net.service.admin.AdminPositionStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminPositionStationService")
public class AdminPositionStationServiceImpl implements AdminPositionStationService {

    @Autowired
    private AdminPositionStationMapper adminPositionStationMapper;

    @Override
    public List<AdminPositionStation> findPositionList() {
        return adminPositionStationMapper.findPositionList();
    }

    @Override
    public int countStation(String poid, int page, int limit) {
        return adminPositionStationMapper.countStation(poid,page,limit);
    }

    @Override
    public List<AdminPositionStation> findStation(String poid, int page, int limit) {
        return adminPositionStationMapper.findStation(poid,page,limit);
    }
}