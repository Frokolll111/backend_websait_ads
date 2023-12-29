package ru.skypro.homework.service;


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

    AdDto addAd(CreateOrUpdateAd createOrUpdateAd, MultipartFile image,
                 Authentication authentication, String userName);
    CreateOrUpdateAd updateAd(CreateOrUpdateAd createOrUpdateAdDto, Authentication authentication, int pk) throws UnavailableException;
    Ads getAllAds();
    ExtendedAd getAd(int pk);
    Ads getAdsMe(Authentication authentication);

    void removeAd(int pk, Authentication authentication) throws UnavailableException;

    void uploadImage(int pk, Authentication authentication, MultipartFile image, String userName);

    byte[] getAdImage(String filename);
}
