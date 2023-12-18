package ru.skypro.homework.service.impl;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import javax.servlet.UnavailableException;
@Service
public interface AdService {

    AdDto addAds(CreateOrUpdateAd createOrUpdateAdDto, MultipartFile image,
                 Authentication authentication, String userName);
    CreateOrUpdateAd updateAds(CreateOrUpdateAd createOrUpdateAdDto, Authentication authentication, int pk) throws UnavailableException;
    Ads getAllAds();
    ExtendedAd getAds(int pk);
    Ads getAdsMe(Authentication authentication);

    void removeAd(int pk, Authentication authentication) throws UnavailableException;

    void uploadImage(int pk, Authentication authentication, MultipartFile image, String userName);
}
