package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

//@Service
//public class AdService {
//
//    private final AdRepository adRepository;
//    private final AdMapper adMapper;
//
//    @Autowired
//    public AdService(AdRepository adRepository, AdMapper adMapper) {
//        this.adRepository = adRepository;
//        this.adMapper = adMapper;
//    }
//
//    public AdDto convertToDTO(Ad ad) {
//        return adMapper.adToAdDto(ad);
//    }
//
//    public Ad convertToEntity(AdDto adDto) {
//        return adMapper.adDtoToAd(adDto);
//    }
//
//    public List<AdDto> getAllAds() {
//        List<Ad> ads = adRepository.findAll();
//        return adMapper.adsToAdDtos(ads);
//    }
//
//    public AdDto getAdById(Integer adId) {
//        return adRepository.findById(adId)
//                .map(adMapper::adToAdDto)
//                .orElse(null);
//    }
//
//    public void addAd(AdDto adDto) {
//        Ad ad = convertToEntity(adDto);
//        adRepository.save(ad);
//    }
//
//    public void updateAd(Integer adId, AdDto adDto) {
//        if (adRepository.existsById(adId)) {
//            Ad updatedAd = convertToEntity(adDto);
//            updatedAd.setPk(adId);
//            adRepository.save(updatedAd);
//        }
//    }
//
//    public void deleteAd(Integer adId) {
//        adRepository.deleteById(adId);
//    }
//}
