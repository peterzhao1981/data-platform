package com.mode.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.mode.base.AppConfig;
import com.mode.base.response.Message;
import com.mode.dao.MediaDao;
import com.mode.dao.ProductDao;
import com.mode.dao.ProductMediaDao;
import com.mode.dao.ProductOptionValueDao;
import com.mode.dao.ProductPriceDao;
import com.mode.dao.ProductVariantDao;
import com.mode.dao.ProductVariantOptionValueDao;
import com.mode.entity.Media;
import com.mode.entity.Product;
import com.mode.entity.ProductMedia;
import com.mode.entity.ProductOptionValue;
import com.mode.entity.ProductPrice;
import com.mode.entity.ProductVariant;
import com.mode.entity.ProductVariantOptionValue;
import com.mode.exception.ModeException;

/**
 * Created by zhaoweiwei on 16/7/20.
 */
@Service
public class AmazonProductImportServiceImpl implements AmazonProductImportService {

    @Autowired
    private SignedRequestsHelper helper;

    @Value("${mode.amazon.associateTag}")
    private String associateTag;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private ProductMediaDao productMediaDao;

    @Autowired
    private ProductVariantDao productVariantDao;

    @Autowired
    private ProductOptionValueDao productOptionValueDao;

    @Autowired
    private ProductVariantOptionValueDao productVariantOptionValueDao;

    @Autowired
    private ProductPriceDao productPriceDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public String productImport(String url) throws Exception {
        String childASIN = null;
        Pattern pattern = Pattern.compile("http[s]?://www.amazon.com/([\\w-]+/)?(dp|gp/product)/(\\w+/)?(\\w{10})");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            childASIN = matcher.group(4);
        }

        if (StringUtils.isEmpty(childASIN)) {
            throw new ModeException(Message.GET_ASIN_ERROR);
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConfig.AMAZON_SERVICE_NAME, AppConfig.AMAZON_SERVICE_NAME_VALUE);
        params.put(AppConfig.AMAZON_SERVICE_ACCOCIATE_TAG, associateTag);
        params.put(AppConfig.AMAZON_SERVICE_OPERATION, AppConfig.AMAZON_SERVICE_OPERATION_VALUE);
        params.put(AppConfig.AMAZON_SERVICE_ITEM_ID, childASIN);
        params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, AppConfig.AMAZON_SERVICE_RESPONSE_GROUP_VARIATION);

        String requestUrl = helper.sign(params);
        String parentASIN = getParentASIN(requestUrl);

        if (!StringUtils.isEmpty(parentASIN)) {
            params.put(AppConfig.AMAZON_SERVICE_RESPONSE_GROUP, AppConfig.AMAZON_SERVICE_RESPONSE_GROUP_LARGE);
            params.put(AppConfig.AMAZON_SERVICE_ITEM_ID, parentASIN);
            requestUrl = helper.sign(params);

            System.out.println(requestUrl);
        }
        return parentASIN;
    }

    private String getParentASIN(String requestUrl) throws Exception {
        String parentASIN = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        if (!StringUtils.isEmpty(requestUrl)) {
            Document doc = db.parse(requestUrl);
            Node titleNode = doc.getElementsByTagName("ParentASIN").item(0);
            parentASIN = titleNode.getTextContent();

        }

        return parentASIN;
    }


    private Map<String, String> checkParentASIN(String parentASIN) {
        // Check whether the parentASIN has already processed
        Product product = productDao.getProductByExternalId(parentASIN);
        if (product != null) {
            Map<String, String> result = new HashMap<>();
            Media media = mediaDao.getMedia(product.getCoverMediaId());
            if (media == null) {
                throw new ModeException(Message.PROCESS_ERROR);
            }

            result.put("productId", String.valueOf(product.getId()));
            result.put("imageUrl", media.getUrl());

            return result;
        }
        return null;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED,
            rollbackFor = Throwable.class)
    public Map<String, String> productImport(Product product) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            long now = System.currentTimeMillis();
//            int success = 0;
//            product.setCreatedAt(now);
//            product.setUpdatedAt(now);
//            success = productDao.createProduct(product);
//            if (success == 0) {
//                throw new ModeException(Message.PROCESS_ERROR);
//            }
//            int i = 1/0;
//
//            // Process medias
//            String coverMediaUrl = processMedias(product, now);
//            if (StringUtils.isEmpty(coverMediaUrl)) {
//                throw new ModeException(Message.PROCESS_ERROR);
//            }
//            // Process product variants
//            processProductVariants(product, now);
//
//            Map<String, String> result = new HashMap<>();
//            result.put("productId", String.valueOf(product.getId()));
//            result.put("imageUrl", coverMediaUrl);
//            transactionManager.commit(status);
//            return result;
//        }
//        catch (Exception ex) {
//            System.out.println("starts catch");
//            transactionManager.rollback(status);
//            throw ex;
//        }


//        try {
            System.out.print(transactionManager);
            long now = System.currentTimeMillis();
            int success = 0;
            product.setCreatedAt(now);
            product.setUpdatedAt(now);
            success = productDao.createProduct(product);
            if (success == 0) {
                throw new ModeException(Message.PROCESS_ERROR);
            }
            int i = 1/0;

            // Process medias
            String coverMediaUrl = processMedias(product, now);
            if (StringUtils.isEmpty(coverMediaUrl)) {
                throw new ModeException(Message.PROCESS_ERROR);
            }
            // Process product variants
            processProductVariants(product, now);

            Map<String, String> result = new HashMap<>();
            result.put("productId", String.valueOf(product.getId()));
            result.put("imageUrl", coverMediaUrl);

            return result;
      //  }
//    catch (Exception e) {
//            System.out.println("starts catch exception");
//            throw new ModeException(Message.BAD_REQUEST);
//        }
    }

    /**
     * Process medias and return cover media url.
     *
     * @param product
     * @param now
     * @return
     */
    private String processMedias(Product product, long now) {
        Long productId = product.getId();
        List<Media> medias = product.getMedias();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        String coverMediaUrl = null;
        for (Media media : medias) {
            media.setCreatedAt(now);
            media.setUpdatedAt(now);
            mediaDao.createMedia(media);
            Long mediaId = media.getId();
            if (media.isCovered()) {
                if (mediaId == null) {
                    throw new ModeException(Message.PROCESS_ERROR);
                }
                // Update product cover media
                productDao.updateProductCoverMedia(productId, mediaId);
                coverMediaUrl = media.getUrl();
            }
            // Insert product media relationship;
            ProductMedia productMedia = new ProductMedia();
            productMedia.setMediaId(mediaId);
            productMedia.setProductId(productId);
            productMediaDao.createProductMedia(productMedia);
        }

        return coverMediaUrl;

    }

    /**
     * Process product variants.
     *
     * @param product
     * @param now
     */
    private void processProductVariants(Product product, long now) {
        List<ProductVariant> productVariants = product.getProductVariants();
        int success = 0;
        for (ProductVariant productVariant : productVariants) {
            productVariant.setProductId(product.getId());
            productVariant.setCreatedAt(now);
            productVariant.setUpdatedAt(now);
            success = productVariantDao.createProductVariant(productVariant);

            if (success == 0) {
                throw new ModeException(Message.PROCESS_ERROR);
            }
            // Process product variant option value
            processProductVariantOptionValue(productVariant);

            // Process product price
            processProductPrice(productVariant, now);

        }
    }


    /**
     * Process product variant option value.
     *
     * @param productVariant
     */
    private void processProductVariantOptionValue(ProductVariant productVariant) {
        List<ProductOptionValue> productOptionValues = productVariant.getProductOptionValues();
        for (ProductOptionValue productOptionValue : productOptionValues) {
            Long productOptionId = productOptionValue.getProductOptionId();
            String value = productOptionValue.getValue();
            Long productOptionValueId = null;
            // Currently just support 1: color and 2: size
            if (1l == productOptionId || 2l == productOptionId) {
                ProductOptionValue productOptionValueNew = productOptionValueDao
                        .getProductOptionValue(value, productOptionId);
                // If doesn't contain this color, then create
                if (productOptionValueNew == null) {
                    Integer displayOrder = null;
                    Integer maxDisplayOrder = productOptionValueDao
                            .getMaxDisplayOrder(productOptionId);
                    if (maxDisplayOrder == null || maxDisplayOrder < 1000) {
                        displayOrder = 1000;
                    } else {
                        displayOrder = maxDisplayOrder + 1;
                    }
                    productOptionValue.setDisplayOrder(displayOrder);
                    productOptionValueDao.createProductOptionValue(productOptionValue);
                    productOptionValueId = productOptionValue.getId();
                } else {
                    productOptionValueId = productOptionValueNew.getId();
                }

                ProductVariantOptionValue productVariantOptionValue = new
                        ProductVariantOptionValue();
                productVariantOptionValue.setVariantId(productVariant.getId());
                productVariantOptionValue.setOptionValueId(productOptionValueId);
                productVariantOptionValueDao
                        .createProductVariantOptionValue(productVariantOptionValue);
            }
        }
    }


    /**
     * Process product price.
     *
     * @param productVariant
     * @param now
     */
    private void processProductPrice(ProductVariant productVariant, long now) {
        ProductPrice productPrice = productVariant.getProductPrice();
        if (productPrice != null) {
            productPrice.setVariantId(productVariant.getId());
            if (productPrice.getCurrency() == null) {
                productPrice.setCurrency(AppConfig.DEFAULT_CURRENCY);
            }
            productPrice.setCreatedAt(now);
            productPrice.setUpdatedAt(now);

            productPriceDao.createProductPrice(productPrice);
        }

    }

}
