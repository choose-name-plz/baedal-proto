package com.nameplz.baedal.domain.web.admin.controller;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.repository.CategoryRepository;
import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.order.repository.OrderRepository;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.repository.StoreRepository;
import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.repository.TerritoryRepository;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole.Authority;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.domain.web.admin.dto.response.CategoryAdminResponseDto;
import com.nameplz.baedal.domain.web.admin.dto.response.OrderAdminResponseDto;
import com.nameplz.baedal.domain.web.admin.dto.response.StoreAdminResponseDto;
import com.nameplz.baedal.domain.web.admin.dto.response.UserAdminRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Secured({Authority.MASTER})
@RequestMapping("admin")
@Controller
public class AdminController {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final TerritoryRepository territoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /*
     *  TODO: 관리자 페이지 접근 제어
     */

    @GetMapping
    public String mainPage() {

        return "home";
    }

    // 로그인 페이지는 누구나 접근이 가능하게 한다.
    @Secured({})
    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("user")
    public String userPage(Model model) {
        PageRequest pageable = getPageRequestInfo();

        Page<User> userList = userRepository.findAll(pageable);

        List<UserAdminRepository> list = userList.stream().map(
            user -> new UserAdminRepository(user.getUsername(), user.getRole(),
                user.getDeletedAt(),
                user.getCreatedAt())).toList();

        model.addAttribute("list", list);
        return "page/user";
    }

    @GetMapping("store")
    public String storePage(Model model) {

        PageRequest pageable = getPageRequestInfo();

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
        PageRequest pageable = getPageRequestInfo();

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
        PageRequest pageable = getPageRequestInfo();

        Page<Territory> territoryList = territoryRepository.findAll(pageable);
        List<CategoryAdminResponseDto> list = territoryList.stream().map(category ->
            new CategoryAdminResponseDto(category.getId().toString(), category.getName(),
                category.getDeletedAt(), category.getCreatedAt())).toList();

        model.addAttribute("list", list);
        return "page/territory";
    }

    @GetMapping("order")
    public String orderListPage(Model model) {
        PageRequest pageable = getPageRequestInfo();

        Page<Order> orderList = orderRepository.findAll(pageable);
        List<OrderAdminResponseDto> list = orderList.stream().map(order ->
            new OrderAdminResponseDto(order.getId().toString(), order.getOrderStatus(),
                order.getAddress(),
                order.getDeletedAt(), order.getCreatedAt())).toList();

        model.addAttribute("list", list);
        return "page/order";
    }

    private PageRequest getPageRequestInfo() {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        return PageRequest.of(0, 100, sort);
    }

}
