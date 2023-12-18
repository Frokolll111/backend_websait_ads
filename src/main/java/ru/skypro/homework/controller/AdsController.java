package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.impl.AdService;

import javax.servlet.UnavailableException;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления", description = "контроллер для работы с объявлениями")
@RequestMapping("/ads")
public class AdsController {

    private final AdService adService;

    public AdsController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<Ads> getAllAds(){
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping
    public ResponseEntity<AdDto> addAd(@RequestParam MultipartFile image,
                                       @RequestParam CreateOrUpdateAd properties,
                                        Authentication authentication){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return ResponseEntity.ok(adService.addAds(properties, image, authentication, userName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAd(@PathVariable int id){
        return new ResponseEntity<>(adService.getAds(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdDto> deleteAds(@PathVariable int adId,
                                           Authentication authentication)  throws UnavailableException {
        adService.removeAd(adId, authentication);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateOrUpdateAd> updateAd (@PathVariable int adsId,
                                           @RequestBody CreateOrUpdateAd createOrUpdateAd,
                                           Authentication authentication) throws UnavailableException {
        return ResponseEntity.ok(adService.updateAds(createOrUpdateAd, authentication, adsId ));
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication));
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Ads> updateAdsImage(@RequestParam MultipartFile image,
                                              @PathVariable int id,
                                              Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        adService.uploadImage(id, authentication, image, userName );
        return ResponseEntity.ok().build();
    }
}