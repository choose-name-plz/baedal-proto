package com.nameplz.baedal.domain.store.service;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.repository.CategoryRepository;
import com.nameplz.baedal.domain.review.repository.ReviewRepository;
import com.nameplz.baedal.domain.review.repository.dto.ReviewScoreWithStoreDto;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.domain.StoreStatus;
import com.nameplz.baedal.domain.store.dto.response.StoreResponseDto;
import com.nameplz.baedal.domain.store.mapper.StoreMapper;
import com.nameplz.baedal.domain.store.repository.StoreRepository;
import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.repository.TerritoryRepository;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StoreService {

    // repository
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final TerritoryRepository territoryRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    // mapper
    private final StoreMapper storeMapper;

    // 조회 이외에는 점수를 넘겨주지 않는다.
    private final Double DEFAULT_SCORE = 0.0;

    /**
     * Store 생성
     */
    @Transactional
    public String createStore(String title, String description, String image, UUID territoryId,
        UUID categoryId, User user) {

        User userInfo = findUserByIdAndCheck(user.getUsername());
        Territory territory = findTerritoryByIdAndCheck(territoryId);
        Category category = findCategoryByIdAndCheck(categoryId);

        // 유저를 가게 주인으로 변경
        userInfo.customerToOwner();

        Store store = Store.createStore(title, description, image, userInfo, territory, category);
        storeRepository.save(store);
        return store.getId().toString();
    }

    /**
     * Store 업데이트
     */
    @Transactional
    public StoreResponseDto updateStore(User user, UUID storeId, UUID territoryId,
        UUID categoryId,
        String title, String description, String image, StoreStatus status) {

        Territory territory = findTerritoryByIdAndCheck(territoryId);
        Category category = findCategoryByIdAndCheck(categoryId);
        Store store = findStoreByIdAndCheck(storeId);

        // 가게 주인인지 확인
        checkStoreByUsername(user, store);

        store.updateStore(title, description, image, status, territory, category);
        return storeMapper.storeToDto(store, store.getCategory().getName(), DEFAULT_SCORE);
    }

    /*
     * Store 상태 변경
     * CLOSE, OPEN, PREPARING
     */
    @Transactional
    public StoreResponseDto updateStoreStatus(User user, UUID storeId, StoreStatus status) {
        Store store = findStoreByIdAndCheck(storeId);

        // 가게 주인인지 확인
        checkStoreByUsername(user, store);

        store.updateStoreStatus(status);
        return storeMapper.storeToDto(store, store.getCategory().getName(), DEFAULT_SCORE);
    }

    /**
     * Store 카테고리 변경
     */
    @Transactional
    public StoreResponseDto updateStoreCategory(User user, UUID storeId, UUID categoryId) {
        Category category = findCategoryByIdAndCheck(categoryId);
        Store store = findStoreByIdAndCheck(storeId);

        // 가게 주인인지 확인
        checkStoreByUsername(user, store);
        store.updateStoreCategory(category);

        return storeMapper.storeToDto(store, store.getCategory().getName(), DEFAULT_SCORE);
    }

    /**
     * Store 지역 변경
     */
    @Transactional
    public StoreResponseDto updateStoreTerritory(User user, UUID storeId, UUID territoryId) {
        Territory territory = findTerritoryByIdAndCheck(territoryId);
        Store store = findStoreByIdAndCheck(storeId);

        // 가게 주인인지 확인
        checkStoreByUsername(user, store);

        store.updateStoreTerritory(territory);

        return storeMapper.storeToDto(store, store.getCategory().getName(), DEFAULT_SCORE);
    }

    /**
     * Store 삭제
     */
    @Transactional
    public String deleteStore(User user, UUID storeId) {
        Store store = findStoreByIdAndCheck(storeId);

        // 가게 주인인지 확인
        checkStoreByUsername(user, store);

        store.deleteEntity(user.getUsername());
        return store.getId().toString();
    }

    /**
     * Store 단일 검색
     */
    public StoreResponseDto findStoreById(UUID storeId) {
        Store store = findStoreByIdAndCheck(storeId);

        List<Store> storeList = new ArrayList<>();
        storeList.add(store);
        Map<UUID, Double> storeIdAndScoreMapping = findScoreFromStoreId(storeList);

        return storeMapper.storeToDto(store, store.getCategory().getName(),
            storeIdAndScoreMapping.getOrDefault(store.getId(), DEFAULT_SCORE
            ));
    }

    /**
     * Store 목록 검색
     */
    public List<StoreResponseDto> findStoreList(String title, UUID categoryId, StoreStatus status,
        Pageable pageable) {

        // 잘못된 카테고리 입력 여부 확인
        if (categoryId != null) {
            findCategoryByIdAndCheck(categoryId);
        }

        // 지역은 확장 시 추후 구현
        List<Store> storeList = storeRepository.findStoreList(title, categoryId, null, status,
            pageable);

        Map<UUID, Double> storeIdAndScoreMapping = findScoreFromStoreId(storeList);

        List<StoreResponseDto> output = new ArrayList<>();
        for (Store store : storeList) {
            output.add(
                new StoreResponseDto(store.getId().toString(), store.getTitle(),
                    store.getDescription(), store.getImage(), store.getStatus(),
                    store.getCategory().getName(), store.getCreatedAt(),
                    storeIdAndScoreMapping.getOrDefault(store.getId(), DEFAULT_SCORE)));
        }

        return output;
    }

    /**
     * 가게 주인이 맞는지 확인한다.
     */
    private void checkStoreByUsername(User user, Store store) {
        // 마스터면 허용
        if (user.getRole().equals(UserRole.MASTER)) {
            return;
        }

        if (!store.getUser().getUsername().equals(user.getUsername())) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }
    }

    /**
     * 평점을 갖고 오기 위한 함수
     */
    private Map<UUID, Double> findScoreFromStoreId(List<Store> storeList) {

        List<UUID> storeIdList = storeList.stream().map(Store::getId).toList();
        List<ReviewScoreWithStoreDto> reviewRateInStoreIds = reviewRepository.findScoreByStoreIds(
            storeIdList);

        Map<UUID, Double> output = new HashMap<>();
        for (ReviewScoreWithStoreDto reviewRateInStoreId : reviewRateInStoreIds) {
            output.put(reviewRateInStoreId.storeId(),
                ((double) Math.round(reviewRateInStoreId.score() * 100)) / 100);
        }
        return output;
    }

    /**
     * Id 별로 있는지 확인 후 불러오는 함수 모음
     */
    private User findUserByIdAndCheck(String username) {
        return userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.INVALID_INPUT));
    }

    private Store findStoreByIdAndCheck(UUID storeId) {
        return storeRepository.findByIdAndDeletedAtIsNull(storeId)
            .orElseThrow(() -> new GlobalException(ResultCase.STORE_NOT_FOUND));
    }

    private Territory findTerritoryByIdAndCheck(UUID territoryId) {
        return territoryRepository.findByIdAndDeletedAtIsNull(territoryId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.TERRITORY_NOT_FOUND));
    }

    private Category findCategoryByIdAndCheck(UUID categoryId) {
        return categoryRepository.findByIdAndDeletedAtIsNull(categoryId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.CATEGORY_NOT_FOUND));
    }


}
