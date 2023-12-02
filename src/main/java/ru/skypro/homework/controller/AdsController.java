package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @GetMapping
    public ResponseEntity<Ads> getAllAds(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Ad> addAd(@RequestParam MultipartFile image,
                                    @RequestParam String properties){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAd(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/ads/{id}")
    public ResponseEntity<Ad> deleteAds(@PathVariable Integer adId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ads/{id}")
    public ResponseEntity<Ad> updateAd (@PathVariable Integer adsId,
                                        @RequestBody CreateOrUpdateAd createOrUpdateAd){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Ads> updateAdsImage(MultipartFile image, @PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }
}