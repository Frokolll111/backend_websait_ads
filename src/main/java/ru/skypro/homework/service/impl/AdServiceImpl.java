package ru.skypro.homework.service.impl;


import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.NoRightsException;

import ru.skypro.homework.exception.UserAdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.AdsMapper;

import ru.skypro.homework.mapper.CreateOrUpdateAdMapper;
import ru.skypro.homework.mapper.ExtendedAdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.servlet.UnavailableException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import java.util.UUID;


@RequiredArgsConstructor
@EqualsAndHashCode
@Service
@Slf4j
public class AdServiceImpl implements AdService{

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private String filePath;
    private final CommentRepository commentRepository;

    /**
     * Метод выводит все объявления AdsDto
     *
     * @return AdsDto
     */
    @Override
    public Ads getAllAds() {
        List<Ad> adList = adRepository.findAll();
        Ads ads = new Ads();
        ads.setCount(adList.size());
        ads.setResult(AdsMapper.INSTANCE.toDto(adList));
        logger.warn("Получены все объявления");
        return ads;
     }
    /**
     * Метод создает объявление
     *
     * @param createOrUpdateAdDto title,price,description
     * @return AdDto
     */
    @Override
    public AdDto addAds(CreateOrUpdateAd createOrUpdateAd,
                        MultipartFile image,
                        Authentication authentication,
                        String userName) {
        Ad ad = CreateOrUpdateAdMapper.INSTANCE.toModel(createOrUpdateAd);
        User user = userRepository.findById(ad.getUser().getId());
        String imageName = uploadImageOnSystem(image, userName);
        ad.setUser(user);
        ad.setAdImage(getUrlImage(imageName));
        adRepository.save(ad);
        logger.info("добавлено новое объявление: " + ad);
        return AdMapper.INSTANCE.toDto(ad, user);
    }
    /**
     * Метод выдает информацию по объявлению
     *
     * @param pk id объявления
     * @return ExtendedAdDto
     */
    @Override
    public ExtendedAd getAds(int pk) throws AdNotFoundException {
        Ad ad = adRepository.findByPk(pk).orElseThrow();
        User user = userRepository.findById(ad.getUser().getId());
        logger.info("найдено объявление: " + ad);
        return ExtendedAdMapper.INSTANCE.toDto(ad,user);
    }
    /**
     * Метод удаляет объявление
     *
     * @param pk id объявления
     */
    @Override
    public void removeAd(int pk, Authentication authentication) throws UnavailableException {
       Ad ad = adRepository.findByPk(pk).orElseThrow();
        Ad newAd = adRepository.getReferenceById(pk);
        String currentAuthor = newAd.getUser().getUserName();
        if (ad.getUser().getRole().equals(Role.USER)) {
            if (ad.getAdImage() != null) {
                try {
                    Files.delete(Path.of(System.getProperty("user.dir") + "/" + filePath
                            + ad.getAdImage().replaceAll("/ads/get", "")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            commentRepository.deleteAllCommentByPk(pk);
            adRepository.delete(ad);
            logger.info("удалено объявление " + pk);

        } else {
            throw new NoRightsException("нет прав");
        }
    }
    /**
     * Метод обновляет объявление
     *
     * @param pk                  id объявления
     * @param createOrUpdateAdDto title,price,description
     * @return AdDto
     */
    @Override
    public CreateOrUpdateAd updateAds(CreateOrUpdateAd createOrUpdateAdDto, Authentication authentication, int pk) throws UnavailableException {
        Ad ad = CreateOrUpdateAdMapper.INSTANCE.toModel(createOrUpdateAdDto);
        User user = userRepository.findUserByUserName(authentication);
        Ad newAd = adRepository.getReferenceById(pk);
        String currentAuthor = newAd.getUser().getUserName();
        if (ad.getUser().getRole().equals(Role.USER)) {
            newAd.setTitle(ad.getTitle());
            newAd.setPrice(ad.getPrice());
            newAd.setDescription(ad.getDescription());
            logger.info("внесены изменения в объявление " + ad.getPk(), ad);
            adRepository.save(newAd);
        } else {
            throw new NoRightsException("нет прав");
        }
        return CreateOrUpdateAdMapper.INSTANCE.toDto(newAd, user);
    }

    /**
     * Метод выводит все объявления пользователя AdsDto
     *
     * @return AdsDto
     */
    @Override
    public Ads getAdsMe(Authentication authentication) {
        User user = userRepository.findUserByUserName(authentication);
        List<Ad> adList = adRepository.findAdByUser(user);
        Ads adsDto = new Ads();
        if (adList == null) {
            throw new UserAdNotFoundException(user.getId());
        } else {
            adsDto.setCount(adList.size());
            adsDto.setResult(AdsMapper.INSTANCE.toDto(adList));
            logger.warn("выведены все объявления авторизованного пользователя " + user.getId());
        }
        return adsDto;
    }
    /**
     * Метод обновления картинки по объявлению
     *
     * @param pk    уникальный идентификатор объявления
     * @param image файл изображения для объявления
     */
    @Override
    public void uploadImage(int pk, Authentication authentication, MultipartFile image, String userName) {
        User user = userRepository.findUserByUserName(authentication);
        Ad ad = adRepository.findByPk(pk).orElseThrow();
        if (ad.getAdImage() != null) {
            try {
                Files.delete(Path.of(System.getProperty("user.dir") + "/" + filePath + ad.getAdImage().replaceAll("/ads/get", "")));
            } catch (IOException e) {
                logger.error("произошла ошибка при попытке удаления изображения объявления " + pk, ad);
                throw new RuntimeException(e);
            }
        }
        String imageName = uploadImageOnSystem(image, userName);
        ad.setAdImage(getUrlImage(imageName));
        ad.setUser(user);
        adRepository.save(ad);
        logger.info("объявление " + ad.getPk() + " обновлено", ad);
    }
    /**
     * Метод создает Url файла
     *
     * @param fileName название картинки объявления
     * @return String
     */
    private String getUrlImage(String fileName) {
        return "/ads/get/" + fileName;
    }

    /**
     * Метод указывает расширение файла
     *
     * @return String
     */
    private String getExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    /**
     * Метод создает название файла
     *
     * @param image    картинка объявления
     * @param userName login пользователя
     * @return String
     */
    private String getFileName(String userName, MultipartFile image) {
        return String.format("image%s_%s.%s", userName, UUID.randomUUID(),
                getExtension(Objects.requireNonNull(image.getOriginalFilename())));
    }

    /**
     * Метод загружает файл
     *
     * @param image файл
     * @return String имя загруженного файла
     */
    private String uploadImageOnSystem(MultipartFile image, String userName) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        String imageName = (getFileName(userName, image));
        try {
            Files.createDirectories(Path.of(dir));
            image.transferTo(new File(dir + "/" + imageName));
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке сохранить изображение " + image.getOriginalFilename() + " для объявления на сервер", image);
            throw new RuntimeException(e);
        }
        logger.info("изображение " + imageName + " сохранено на сервере", image);
        return imageName;
    }

}
