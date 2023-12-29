package ru.skypro.homework.entity;
import lombok.*;
import javax.persistence.*;
import java.util.List;
/**
 * Класс Ad представляет информацию о объявление. Эта информация сохраняется в базе данных.
 */
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String adImage;
    private int price;
    private String title;
    private String description;
    private int countComment;
    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad", orphanRemoval = true)
    private List<Comment> commentList;
}