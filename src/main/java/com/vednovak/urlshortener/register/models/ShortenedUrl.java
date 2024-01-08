package com.vednovak.urlshortener.register.models;

import com.vednovak.urlshortener.account.models.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: maybe use true/false and do logic regarding that as int takes 4 bytes while 0/1 takes 1
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ShortenedUrl {

    @Id
    @GeneratedValue
    private long id;

    private String url;

    @Column(unique = true)
    private String urlShortened;

    private int redirectType;

    private long visitCount;

    @ManyToOne
    private Account account;
}
