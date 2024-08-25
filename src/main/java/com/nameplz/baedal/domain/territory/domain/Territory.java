package com.nameplz.baedal.domain.territory.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.store.domain.Store;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "p_teritory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Territory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "territory")
    private List<Store> storeList = new ArrayList<>();

    public static Territory createTerritory(String name) {
        Territory territory = new Territory();
        territory.name = name;
        return territory;
    }

}
