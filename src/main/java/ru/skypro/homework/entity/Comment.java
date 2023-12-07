package ru.skypro.homework.entity;
import lombok.*;
import javax.persistence.*;
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "Comments")
public class Comment {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
    private long createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    private String text;

}