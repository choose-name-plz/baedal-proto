package com.nameplz.baedal.domain.web.admin.controller;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.repository.CategoryRepository;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.repository.StoreRepository;
import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.repository.TerritoryRepository;
import com.nameplz.baedal.domain.web.admin.dto.response.CategoryAdminResponseDto;
import com.nameplz.baedal.domain.web.admin.dto.response.StoreAdminResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin")
@Controller
public class AdminController {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final TerritoryRepository territoryRepository;

    /*
     *  TODO: 관리자 페이지 접근 제어
     */

    @GetMapping

    public String mainPage() {

        return "home";
    }

    @GetMapping("store")
    public String storePage(Model model) {

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        PageRequest pageable = PageRequest.of(0, 100, sort);

        Page<Store> storeListFromDB = storeRepository.findAll(pageable);

        List<StoreAdminResponseDto> list = storeListFromDB.stream().map(
            store -> new StoreAdminResponseDto(store.getId().toString(), store.getTitle(),
                store.getDescription(), store.getStatus(), store.getDeletedAt(),
                store.getCreatedAt())).toList();

        model.addAttribute("list", list);
        return "page/store";
    }

    @GetMapping("category")
    public String categoryPage(Model model) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        PageRequest pageable = PageRequest.of(0, 100, sort);

        Page<Category> categoryList = categoryRepository.findAll(pageable);
        List<CategoryAdminResponseDto> list = categoryList.stream().map(category ->
            new CategoryAdminResponseDto(category.getId().toString(), category.getName(),
                category.getDeletedAt(), category.getCreatedAt())).toList();

        // jwt
        model.addAttribute("list", list);
        return "page/category";
    }

    @GetMapping("territory")
    public String territoryPage(Model model) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        PageRequest pageable = PageRequest.of(0, 100, sort);

        Page<Territory> territoryList = territoryRepository.findAll(pageable);
        List<CategoryAdminResponseDto> list = territoryList.stream().map(category ->
            new CategoryAdminResponseDto(category.getId().toString(), category.getName(),
                category.getDeletedAt(), category.getCreatedAt())).toList();

        model.addAttribute("list", list);
        return "page/territory";
    }

}
