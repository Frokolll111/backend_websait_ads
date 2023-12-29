package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

import javax.servlet.UnavailableException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdsController.class)
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdService adService;

    @Test
    @WithUserDetails
    void getAllAdsTest() throws Exception {
        Ads adDtoTest = new Ads();
        when(adService.getAllAds()).thenReturn(adDtoTest);
        mockMvc.perform(MockMvcRequestBuilders.get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        verify(adService, times(1)).getAllAds();
    }
    @Test
    @WithUserDetails
    void addAdTest() throws Exception {

        MultipartFile image = new MockMultipartFile("test.jpg", new byte[10]);

        CreateOrUpdateAd properties = new CreateOrUpdateAd();
        properties.setTitle("Test Ad");

        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "password");

        AdDto adDto = new AdDto();
        adDto.setPk(1);
        adDto.setTitle("Test Ad");
        when(adService.addAd(properties, image, authentication, "Test")).thenReturn(adDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails
    void getAdTest() throws Exception {
        int adId = 123;
        ExtendedAd expectedAd = new ExtendedAd(1, "Test Ad", "This is a test ad", "testuser",
                "testuser","testuser","testuser",3,"testuser");
        AdService adService = Mockito.mock(AdService.class);
        Mockito.when(adService.getAd(adId)).thenReturn(expectedAd);

        AdsController adController = new AdsController(adService);

        ResponseEntity<ExtendedAd> response = adController.getAd(adId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAd, response.getBody());
        Mockito.verify(adService, Mockito.times(1)).getAd(adId);
    }

    @Test
    @WithUserDetails
    void deleteAdsTest() throws UnavailableException {
        int adId = 1;
        Authentication authentication = Mockito.mock(Authentication.class);
        AdService adService = Mockito.mock(AdService.class);

        Mockito.doNothing().when(adService).removeAd(adId, authentication);
        AdsController adsController = new AdsController(adService);
        ResponseEntity<AdDto> response = adsController.deleteAds(adId, authentication);

        Mockito.verify(adService, Mockito.times(1)).removeAd(adId, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @WithUserDetails
    void updateAdTest() throws UnavailableException {
        int adId = 1;
        CreateOrUpdateAd updateAd = new CreateOrUpdateAd();
        Authentication authentication = Mockito.mock(Authentication.class);
        AdsController adsController = new AdsController(adService);
        Mockito.when(adService.updateAd(updateAd, authentication, adId)).thenReturn(updateAd);
        ResponseEntity<CreateOrUpdateAd> response = adsController.updateAd(adId, updateAd, authentication);

        Mockito.verify(adService, Mockito.times(1)).updateAd(updateAd, authentication, adId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateAd, response.getBody());
    }

    @Test
    @WithUserDetails
    void getAdsMeTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Ads ads = new Ads();
        AdsController adsController = new AdsController(adService);
        Mockito.when(adService.getAdsMe(authentication)).thenReturn(ads);
        ResponseEntity<Ads> response = adsController.getAdsMe(authentication);

        Mockito.verify(adService, Mockito.times(1)).getAdsMe(authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ads, response.getBody());
    }
    @Test
    @WithUserDetails
    void uploadImageTest()  {
        int id = 1;
        MultipartFile image = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "test image content".getBytes());
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "testPassword");
        AdsController adsController = new AdsController(adService);
        Mockito.doNothing().when(adService).uploadImage(id,authentication,image,"testUser");
        ResponseEntity<String> response = adsController.updateAdsImage(image, id, authentication);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @WithUserDetails
    void serveFileTest() {
        String filename = "test-image.jpg";
        byte[] testImageData = "test image content".getBytes();
        AdsController adsController = new AdsController(adService);
        when(adService.getAdImage(filename)).thenReturn(testImageData);

        ResponseEntity<byte[]> response = adsController.serveFile(filename);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(testImageData, response.getBody());
    }
}
