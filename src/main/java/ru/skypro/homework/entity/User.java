package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String userImage;
    private String userName;
    private String password;
    private int countAd;
    @Transient
    @OneToMany(mappedBy = "user")
    private List<Ad> adList;
    @Transient
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;
}