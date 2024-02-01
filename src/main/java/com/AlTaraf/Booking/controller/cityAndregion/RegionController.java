package com.AlTaraf.Booking.controller.cityAndregion;

import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.entity.cityAndregion.Region;
import com.AlTaraf.Booking.service.cityAndRegion.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/byCity/{cityId}")
    public ResponseEntity<List<RegionDto>> getRegionsByCityId(@PathVariable Long cityId) {
        List<RegionDto> RegionDtos = regionService.getRegionsByCityId(cityId);
        return new ResponseEntity<>(RegionDtos, HttpStatus.OK);
    }
}