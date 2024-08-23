package com.nameplz.baedal.domain.store.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "p_store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String title;

    @Column
    private String description;

    @Column
    private String image;

    @Column(name = "is_opened")
    private boolean isOpened;

    public static Store createStore(String title, String description, String image) {
        Store store = new Store();
        store.title = title;
        store.description = description;
        store.image = image;
        store.isOpened = true;

        return store;
    }

}