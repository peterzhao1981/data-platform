package com.mode.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoweiwei on 17/1/9.
 */
public class ClubFactoryItemListResponse {

//    private String jsonrpc = "2.0";
//    private Long id = 0l;

    private List<ItemSummary> result = new ArrayList<>();

//    public String getJsonrpc() {
//        return jsonrpc;
//    }
//
//    public void setJsonrpc(String jsonrpc) {
//        this.jsonrpc = jsonrpc;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public List<ItemSummary> getResult() {
        return result;
    }

    public void setResult(List<ItemSummary> result) {
        this.result = result;
    }

    public static class ItemSummary {
//        private Float c_platform_price;
//        private String url;
//        private Float price;
//        private Boolean on_sale;
        private String image_url;
//        private String c_platform_name;
        private Long id;

//        public Float getC_platform_price() {
//            return c_platform_price;
//        }
//
//        public void setC_platform_price(Float c_platform_price) {
//            this.c_platform_price = c_platform_price;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public Float getPrice() {
//            return price;
//        }
//
//        public void setPrice(Float price) {
//            this.price = price;
//        }
//
//        public Boolean getOn_sale() {
//            return on_sale;
//        }
//
//        public void setOn_sale(Boolean on_sale) {
//            this.on_sale = on_sale;
//        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

//        public String getC_platform_name() {
//            return c_platform_name;
//        }
//
//        public void setC_platform_name(String c_platform_name) {
//            this.c_platform_name = c_platform_name;
//        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
