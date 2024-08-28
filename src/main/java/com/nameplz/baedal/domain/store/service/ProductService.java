package com.nameplz.baedal.domain.store.service;

import com.nameplz.baedal.domain.store.domain.Product;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.dto.request.ProductCreateRequestDto;
import com.nameplz.baedal.domain.store.dto.response.ProductIdResponseDto;
import com.nameplz.baedal.domain.store.dto.response.ProductResponseDto;
import com.nameplz.baedal.domain.store.mapper.ProductMapper;
import com.nameplz.baedal.domain.store.repository.ProductRepository;
import com.nameplz.baedal.domain.store.repository.StoreRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    private final ProductMapper productMapper;

    /**
     * Product 생성
     */
    @Transactional
    public String createProduct(String username,
        String name, String description, Integer price, String image, String storeId) {

        Store store = storeRepository.findById(UUID.fromString(storeId))
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        //TODO: Store에서 User가 같은지 확인한다.

        Product product = Product.createProduct(name, description, price, image, store);

        productRepository.save(product);

        return product.getId().toString();
    }

    /**
     * Product 일괄 생성
     */
    @Transactional
    public List<ProductIdResponseDto> createProductBatch(
        String username,
        List<ProductCreateRequestDto> productDtoList,
        String storeId) {

        Store store = storeRepository.findById(UUID.fromString(storeId))
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        //TODO: Store에서 User가 같은지 확인한다.

        List<Product> productList = new ArrayList<>();
        for (ProductCreateRequestDto requestDto : productDtoList) {
            if (!requestDto.storeId().equals(storeId)) {
                log.error("잘못된 Store의 상품이 입력되었습니다. 원래 상품 : {}  잘못입력 상품 : {}", storeId,
                    requestDto.storeId());
                throw new GlobalException(ResultCase.INVALID_INPUT);
            }
            productList.add(Product.createProduct(requestDto.name(), requestDto.description(),
                requestDto.price(), requestDto.image(), store));
        }

        List<Product> products = productRepository.saveAll(productList);
        return products.stream()
            .map(product -> new ProductIdResponseDto(product.getId().toString())).toList();
    }

    /**
     * Product 업데이트
     */
    @Transactional
    public ProductResponseDto updateProduct(
        String username,
        UUID productId,
        String name,
        String description,
        String image,
        boolean isPublic
    ) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        //TODO: Store에서 User가 같은지 확인한다.

        product.updateProduct(name, description, image);
        product.updateProductPublic(isPublic);

        return productMapper.productToDto(product);
    }

    /**
     * Product 공개 여부 변경
     */
    @Transactional
    public ProductResponseDto updateProductStatus(UUID productId, boolean isPublic) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        //TODO: Store에서 User가 같은지 확인한다.

        product.updateProductPublic(isPublic);

        return productMapper.productToDto(product);
    }

    @Transactional
    public String deleteProduct(String productId, String username) {
        Product product = productRepository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        //TODO: Store에서 User가 같은지 확인한다.

        product.deleteEntity(username);
        return product.getId().toString();
    }


    /**
     * Product Id로 개별 조회
     */
    public ProductResponseDto findProductById(UUID productId) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        return productMapper.productToDto(product);
    }

    /**
     * Product 목록 조회
     */
    public List<ProductResponseDto> findProductList(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
            .map(product -> new ProductResponseDto(product.getStore().getId().toString(),
                product.getName(),
                product.getDescription(), product.getImage(), product.isPublic(),
                product.getPrice(), product.getCreatedAt())).toList();
    }

    /**
     * 가게에 속한 상품 조회
     */
    public List<ProductResponseDto> findProductListByStore(UUID storeId, boolean hideNotPublic,
        Pageable pageable) {

        // store가 존재하는 지 확인
        storeRepository.findById(storeId)
            .orElseThrow(() -> new GlobalException(
                ResultCase.NOT_FOUND));

        List<Product> productList = productRepository.findProductListByStoreId(
            storeId, hideNotPublic, pageable);

        return productList.stream().map(
            product -> new ProductResponseDto(product.getStore().getId().toString(),
                product.getName(),
                product.getDescription(), product.getImage(), product.isPublic(),
                product.getPrice(), product.getCreatedAt())).toList();
    }

}
