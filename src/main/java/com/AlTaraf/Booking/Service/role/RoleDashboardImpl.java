package com.AlTaraf.Booking.Service.role;

import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import com.AlTaraf.Booking.Repository.role.RoleDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDashboardImpl implements RoleDashboardService {


    @Autowired
    private RoleDashboardRepository roleDashboardRepository;

    @Override
    public List<RoleDashboard> getAllRoles() {
        return roleDashboardRepository.findAll();
    }

}
