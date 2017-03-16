package com.mode.common;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zhaoweiwei on 17/1/9.
 */
public class ClubFactoryRequestBody {

    private String jsonrpc = "2.0";
    private String method = "call";
    private Params params;
    private Long id = 0L;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Params {
        private ItemFilter item_filter;

        public ItemFilter getItem_filter() {
            return item_filter;
        }

        public void setItem_filter(ItemFilter item_filter) {
            this.item_filter = item_filter;
        }
    }


    public static class ItemFilter {
        private Integer currentPage = 1;
        private Integer numPages = 0;
        private Integer categoryId = 0;
        private String categoryName = "";
        private Integer limit = 50;
        private Integer totalItems = 0;
        private String q = "";
        private String order = "overall_score desc";
        private String country_code = "us";

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getNumPages() {
            return numPages;
        }

        public void setNumPages(Integer numPages) {
            this.numPages = numPages;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Integer totalItems) {
            this.totalItems = totalItems;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }
    }

    public static void main(String[] args) throws Exception {
//        ClubFactoryRequestBody body = new ClubFactoryRequestBody();
//        ItemFilter itemFilter = new ItemFilter();
//        itemFilter.setCurrentPage(1);
//        Params params = new Params();
//        params.setItem_filter(itemFilter);
//        body.setParams(params);
//        ObjectMapper ob = new ObjectMapper();
//        System.out.println(ob.writeValueAsString(body));

        ClubFactoryRequestBody body = new ClubFactoryRequestBody();
//        ClubFactoryRequestBody.Params params = new ClubFactoryRequestBody.Params();
//        body.setParams(null);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(body));

    }
}
