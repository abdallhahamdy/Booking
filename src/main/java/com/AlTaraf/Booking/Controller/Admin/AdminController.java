package com.AlTaraf.Booking.Controller.Admin;


import com.AlTaraf.Booking.Dto.TechnicalSupport.TechnicalSupportDTO;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Api/Admin")
public class AdminController {

    @Autowired
    TechnicalSupportService technicalSupportService;

    @Autowired
    UserService userService;

    @GetMapping("/Technical-Support-Get-All")
    public Page<TechnicalSupportDTO> getAllTechnicalSupport(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size) {
        Page<TechnicalSupport> technicalSupportPage = technicalSupportService.getAllTechnicalSupport(PageRequest.of(page, size));
        List<TechnicalSupportDTO> technicalSupportDTOList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportDTOList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
    }


}
