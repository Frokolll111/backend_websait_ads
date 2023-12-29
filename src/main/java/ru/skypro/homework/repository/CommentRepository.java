package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;
import java.util.List;
import java.util.Optional;
/**
 * Интерфейс UserRepository представляет репозиторий для управления информацией о комментарии.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByAdPk(Integer adId);

    Optional<Comment> findByAd_PkAndPk(int adId, int commentId);
}

