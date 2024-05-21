package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "fileForAds", target = "fileForAdsDTOS")
    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "packageAds", target = "packageAds")
//    @Mapping(source = "statusUnit", target = "statusUnit")
    AdsResponseDto toDto(Ads ads);

//    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
//    @Mapping(source = "packageAdsId", target = "packageAds.id")
//    @Mapping(source = "statusUnitId", target = "statusUnit.id")
    Ads toEntity(AdsRequestDto adsDto);

    default List<String> extractFilePaths(List<FileForUnit> fileForUnits) {
        return fileForUnits.stream()
                .map(FileForUnit::getFileDownloadUri)
                .collect(Collectors.toList());
    }
}
