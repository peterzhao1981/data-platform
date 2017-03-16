package com.mode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mode.dao.GaGeoConfigDao;
import com.mode.dao.GaUserAgentConfigDao;
import com.mode.entity.Page;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
@Component
public class CommonParaGenerator {

    private String[] mainStateCities = {"1023191", "1013962", "1014221"};

    private String[] otherStateCities = {"1016367", "1018127", "1015254", "1026481", "1025197",
            "1019250", "1026339", "1013462", "1026759"};


    private List<String> mainState = new ArrayList<>();

    private List<String> otherState = new ArrayList<>();

    private List<String> userAgents = new ArrayList<>();

    @Autowired
    private GaGeoConfigDao gaGeoConfigDao;

    @Autowired
    private GaUserAgentConfigDao gaUserAgentConfigDao;

    private int[] depths = {2, 3};
    private Random random = new Random();

    Page r1 = new Page("http://www.whatsmode.com", "MODE - Home", null, 1);

    Page r1_p1 = new Page("http://www.whatsmode.com/believe-print-leggings.html",
            "Believe Print Leggings", r1, 2);
    Page r1_p2 = new Page("http://www.whatsmode.com/black-a-line-mini-dress.html",
            "Black A-Line Mini Dress", r1, 2);
    Page r1_p3 = new Page("http://www.whatsmode.com/blue-off-the-shoulder-shirt.html",
            "Blue Off-The-Shoulder Shirt", r1, 2);
    Page r1_p4 = new Page("http://www.whatsmode.com/a-line-backless-bodycon-dress.html",
            "A-Line Backless Bodycon Dress", r1, 2);
    Page r1_p5 = new Page("http://www.whatsmode.com/black-open-shoulder-a-line-dress.html",
            "Black Open-Shoulder A-Line Dress", r1, 2);
    Page r1_p6 = new Page("http://www.whatsmode.com/plaid-side-slit-shirtdress.html",
            "Plaid Side-Slit Shirtdress", r1, 2);
    Page r1_p7 = new Page("http://www.whatsmode.com/purple-v-line-lace-dress.html",
            "Purple V-Line Lace Dress", r1, 2);
    Page r1_p8 = new Page("http://www.whatsmode.com/blue-embroidered-bomber-jacket.html",
            "Blue Embroidered Bomber Jacket", r1, 2);
    Page r1_p9 = new Page("http://www.whatsmode.com/pug-dog-print-sweatshirt.html",
            "Pug Dog Print Sweatshirt", r1, 2);
    Page r1_p10 = new Page("http://www.whatsmode.com/elephant-print-mini-dress.html",
            "Elephant Print Mini Dress", r1, 2);
    Page r1_p11 = new Page("http://www.whatsmode.com/black-high-low-maxi-skirt.html",
            "Black High-Low Maxi Skirt", r1, 2);
    Page r1_p12 = new Page("http://www.whatsmode.com/black-high-low-maxi-skirt.html",
            "Pink Sequined Mini Dress", r1, 2);
    Page r1_p13 = new Page("http://www.whatsmode.com/apricot-belted-skirt.html",
            "Apricot Belted Skirt", r1, 2);

    Page r1_n1 = new Page("http://www.whatsmode.com/new-arrivals.html?mode=grid",
            "New Arrivals", r1, 2);
    Page r1_n2 = new Page("http://www.whatsmode.com/popular-items.html?mode=grid",
            "Popular Items", r1, 2);
    Page r1_n3 = new Page("http://www.whatsmode.com/all-clothing/tops.html?mode=grid",
            "Tops - All Clothing", r1, 2);
    Page r1_n4 = new Page("http://www.whatsmode.com/all-clothing/bottoms.html?mode=grid",
            "Bottoms - All Clothing", r1, 2);
    Page r1_n5 = new Page("http://www.whatsmode.com/all-clothing/dresses.html?mode=grid",
            "Dresses - All Clothing", r1, 2);
    Page r1_n6 = new Page("http://www.whatsmode.com/sale.html?mode=grid",
            "Sale", r1, 2);
    //        Page r1_n7 = new Page("http://www.whatsmode.com/giveaway.html?mode=list&no_cache=1", "Influencer " +
//                "Garments Free Trial", r1, 2);
    Page r1_n1_p1 = new Page("http://www.whatsmode.com/new-arrivals/black-a-line-dress.html",
            "Black A-Line Dress - New Arrivals", r1_n1, 3);
    Page r1_n1_p2 = new Page("http://www.whatsmode.com/new-arrivals/blue-off-the-shoulder-shirt.html",
            "Blue Off-The-Shoulder Shirt - New Arrivals", r1_n1, 3);
    Page r1_n1_p3 = new Page("http://www.whatsmode.com/new-arrivals/58-graphic-sweatpants.html",
            "58 Graphic Sweatpants - New Arrivals", r1_n1, 3);
    Page r1_n1_p4 = new Page("http://www.whatsmode.com/new-arrivals/believe-print-leggings.html",
            "Believe Print Leggings - New Arrivals", r1_n1, 3);
    Page r1_n1_p5 = new Page("http://www.whatsmode.com/new-arrivals/active-yoga-leggings.html",
            "Active Yoga Leggings - New Arrivals", r1_n1, 3);
    Page r1_n1_p6 = new Page("http://www.whatsmode.com/new-arrivals/a-line-velvet-skirt.html",
            "A-Line Velvet Skirt - New Arrivals", r1_n1, 3);
    Page r1_n1_p7 = new Page("http://www.whatsmode.com/new-arrivals/blue-distressed-skinny-jeans.html",
            "Blue Distressed Skinny Jeans - New Arrivals", r1_n1, 3);
    Page r1_n1_p8 = new Page("http://www.whatsmode.com/new-arrivals/blue-wide-leg-pants.html",
            "Blue Wide-Leg Pants - New Arrivals", r1_n1, 3);
    Page r1_n2_p1 = new Page("http://www.whatsmode.com/popular-items/apricot-belted-skirt.html",
            "Apricot Belted Skirt - Popular Items", r1_n2, 3);
    Page r1_n2_p2 = new Page("http://www.whatsmode.com/popular-items/lace-backless-top.html",
            "Lace Backless Top - Popular Items", r1_n2, 3);
    Page r1_n2_p3 = new Page("http://www.whatsmode.com/popular-items/belted-plaid-woolen-skirt.html",
            "Belted Plaid Woolen Skirt - Popular Items", r1_n2, 3);
    Page r1_n2_p4 = new Page("http://www.whatsmode.com/popular-items/ribbed-high-neck-fleece-sweater.html",
            "Ribbed High-Neck Fleece Sweater - Popular Items", r1_n2, 3);
    Page r1_n2_p5 = new Page("http://www.whatsmode.com/popular-items/waved-neck-off-the-shoulder-skinny-skirt.html",
            "Waved Neck Off-The-Shoulder Skinny Skirt - Popular Items", r1_n2, 3);
    Page r1_n2_p6 = new Page("http://www.whatsmode.com/popular-items/polyester-long-sleeve-street-paneled-letter-biker-jacket.html",
            "Polyester Long Sleeve Street Paneled Letter Biker Jacket - Popular Items", r1_n2, 3);
    Page r1_n2_p7 = new Page("http://www.whatsmode.com/popular-items/pug-dog-print-sweatshirt.html",
            "Pug Dog Print Sweatshirt - Popular Items", r1_n2, 3);
    Page r1_n2_p8 = new Page("http://www.whatsmode.com/popular-items/black-high-low-maxi-skirt.html",
            "Black High-Low Maxi Skirt - Popular Items", r1_n2, 3);
    Page r1_n3_p1 = new Page("http://www.whatsmode.com/all-clothing/tops/blue-boyfriend-print-bomber-jacket.html",
            "Blue Boyfriend Print Bomber Jacket - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p2 = new Page("http://www.whatsmode.com/all-clothing/tops/pug-dog-print-sweatshirt.html",
            "Pug Dog Print Sweatshirt - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p3 = new Page("http://www.whatsmode.com/all-clothing/tops/polyester-long-sleeve-street-paneled-letter-biker-jacket.html",
            "Polyester Long Sleeve Street Paneled Letter Biker Jacket - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p4 = new Page("http://www.whatsmode.com/all-clothing/tops/ribbed-high-neck-fleece-sweater.html",
            "Ribbed High-Neck Fleece Sweater - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p5 = new Page("http://www.whatsmode.com/all-clothing/tops/lace-backless-top.html",
            "Lace Backless Top - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p6 = new Page("http://www.whatsmode.com/all-clothing/tops/coffee-h-line-velvet-slit-casual-long-sleeved-top.html",
            "Coffee H-line Velvet Slit Casual Long Sleeved Top - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p7 = new Page("http://www.whatsmode.com/all-clothing/tops/chill-sweatshirt.html",
            "Chill Sweatshirt - Tops - All Clothing", r1_n3, 3);
    Page r1_n3_p8 = new Page("http://www.whatsmode.com/all-clothing/tops/camel-turtleneck-sweatshirt.html",
            "Camel Turtleneck Sweatshirt - Tops - All Clothing", r1_n3, 3);
    Page r1_n4_p1 = new Page("http://www.whatsmode.com/all-clothing/bottoms/believe-print-leggings.html",
            "Believe Print Leggings - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p2 = new Page("http://www.whatsmode.com/all-clothing/bottoms/black-pu-leather-macrame-dress.html",
            "Black PU Leather Macrame Dress - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p3 = new Page("http://www.whatsmode.com/all-clothing/bottoms/blue-distressed-boyfriend-jeans.html",
            "Blue Distressed Boyfriend Jeans - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p4 = new Page("http://www.whatsmode.com/all-clothing/bottoms/blue-pockets-casual-plain-skinny-leg-jeans.html", "Blue " +
            "Pockets Casual Plain Skinny Leg Jeans - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p5 = new Page("http://www.whatsmode.com/all-clothing/bottoms/distressed-flared-jeans.html",
            "Distressed Flared Jeans - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p6 = new Page("http://www.whatsmode.com/all-clothing/bottoms/dusty-black-mesh-paneled-leggings.html",
            "Dusty Black Mesh-Paneled Leggings - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p7 = new Page("http://www.whatsmode.com/all-clothing/bottoms/cotton-denim-split-skirt.html",
            "Cotton Denim Split Skirt - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n4_p8 = new Page("http://www.whatsmode.com/all-clothing/bottoms/black-contemporary-mesh-leggings.html",
            "Black Contemporary Mesh Leggings - Bottoms - All Clothing", r1_n4, 3);
    Page r1_n5_p1 = new Page("http://www.whatsmode.com/all-clothing/dresses/champagne-open-shoulder-mini-dress.html",
            "Champagne Open-Shoulder Mini Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p2 = new Page("http://www.whatsmode.com/all-clothing/dresses/black-mesh-v-neck-dress.html",
            "Black Mesh V-Neck Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p3 = new Page("http://www.whatsmode.com/all-clothing/dresses/wine-red-high-neck-sleeveless-mini-dress.html",
            "Wine Red High-Neck Sleeveless Mini Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p4 = new Page("http://www.whatsmode.com/all-clothing/dresses/v-neck-folded-mini-dress.html",
            "V-Neck Folded Mini Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p5 = new Page("http://www.whatsmode.com/all-clothing/dresses/striped-off-shoulder-culotte.html",
            "Striped Off Shoulder Culotte - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p6 = new Page("http://www.whatsmode.com/all-clothing/dresses/selfie-leslie-lace-up-mini-dress.html",
            "Selfie Leslie Lace-Up Mini Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p7 = new Page("http://www.whatsmode.com/all-clothing/dresses/pook-woolen-dress.html",
            "Pook Woolen Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n5_p8 = new Page("http://www.whatsmode.com/all-clothing/dresses/purple-v-line-lace-dress.html",
            "Purple V-Line Lace Dress - Dresses - All Clothing", r1_n5, 3);
    Page r1_n6_p1 = new Page("http://www.whatsmode.com/sale/blue-pocket-gored-denim-shirt.html",
            "Blue Pocket Gored Denim Shirt - Sale", r1_n6, 3);
    Page r1_n6_p2 = new Page("http://www.whatsmode.com/sale/blue-pocket-moto-jacket.html",
            "Blue Pocket Moto Jacket - Sale", r1_n6, 3);
    Page r1_n6_p3 = new Page("http://www.whatsmode.com/sale/boyfriend-print-sweatshirt.html",
            "Boyfriend Print Sweatshirt - Sale", r1_n6, 3);
    Page r1_n6_p4 = new Page("http://www.whatsmode.com/sale/camel-print-tie-neck-tunic.html",
            "Camel Print Tie-Neck Tunic - Sale", r1_n6, 3);
    Page r1_n6_p5 = new Page("http://www.whatsmode.com/sale/camel-turtleneck-sweatshirt.html",
            "Camel Turtleneck Sweatshirt - Sale", r1_n6, 3);
    Page r1_n6_p6 = new Page("http://www.whatsmode.com/sale/chill-sweatshirt.html",
            "Chill Sweatshirt - Sale", r1_n6, 3);
    Page r1_n6_p7 = new Page("http://www.whatsmode.com/sale/coffee-h-line-velvet-slit-casual-long-sleeved-top.html",
            "Coffee H-line Velvet Slit Casual Long Sleeved Top - Sale", r1_n6, 3);
    Page r1_n6_p8 = new Page("http://www.whatsmode.com/sale/distressed-flared-jeans.html",
            "Distressed Flared Jeans - Sale", r1_n6, 3);
    Page[] r1_2 = {r1_n1, r1_n2, r1_n3, r1_n4, r1_n5, r1_n6, r1_p1, r1_p2,r1_p3,
            r1_p4, r1_p5, r1_p6, r1_p7, r1_p8, r1_p9, r1_p10, r1_p11, r1_p12, r1_p13};
    Page[] r1_3 = {r1_n1_p1, r1_n1_p2, r1_n1_p3, r1_n1_p4,
            r1_n1_p5, r1_n1_p6, r1_n1_p7, r1_n1_p8,
            r1_n2_p1, r1_n2_p2, r1_n2_p3, r1_n2_p4,
            r1_n2_p5, r1_n2_p6, r1_n2_p7, r1_n2_p8,
            r1_n3_p1, r1_n3_p2, r1_n3_p3, r1_n3_p4,
            r1_n3_p5, r1_n3_p6, r1_n3_p7, r1_n3_p8,
            r1_n4_p1, r1_n4_p2, r1_n4_p3, r1_n4_p4,
            r1_n4_p5, r1_n4_p6, r1_n4_p7, r1_n4_p8,
            r1_n5_p1, r1_n5_p2, r1_n5_p3, r1_n5_p4,
            r1_n5_p5, r1_n5_p6, r1_n5_p7, r1_n5_p8,
            r1_n6_p1, r1_n6_p2, r1_n6_p3, r1_n6_p4,
            r1_n6_p5, r1_n6_p6, r1_n6_p7, r1_n6_p8};

    Page r2 = new Page("http://www.whatsmode.com/tarmar", "MODE X Tara", null, 1);
    Page r2_p1 = new Page("http://www.whatsmode.com/all-influencers/tara/silk-blouse-designed-by-tara.html",
            "Silk Blouse Designed by Tara - Tara - All Influencers", r2, 2);

    Page r3 = new Page("http://www.whatsmode.com/minnahigh",
            "MODE X minnahigh", null, 1);
    Page r3_p1 = new Page("http://www.whatsmode.com/all-influencers/minnahigh/beige-t-shirt-designed-by-minnahigh.html",
            "Beige T-shirt Designed by Minnahigh - Minnahigh - All Influencers", r3, 2);

    Page r4 = new Page("http://www.whatsmode.com/loveyoli", "MODE X Loveyoli", null, 1);
    Page r4_p1 = new Page("http://www.whatsmode.com/all-influencers/loveyoli/t-shirt-designed-by-loveyoli.html",
            "T-shirt Designed by LoveYoli - Loveyoli - All Influencers", r4, 2);

    Page r5 = new Page("http://www.whatsmode.com/camillabrozzo", "MODE X Camillabrozzo", null, 1);
    Page r5_p1 = new Page("http://www.whatsmode.com/all-influencers/camillabrozzo/floral-off-the-shoulder-dress.html",
            "Floral Off-the-Shoulder Dress - Camillabrozzo - All Influencers", r5, 2);

    Page r6 = new Page("http://www.whatsmode.com/jmayo", "MODE X Jmayo", null, 1);
    Page r6_p1 = new Page("http://www.whatsmode.com/xlix-t-shirt-designed-by-jmayo.html",
            "XLIX T-Shirt Designed By Jmayo", r6, 2);

    Page r7 = new Page("http://www.whatsmode.com/minilucie13", "MODE X minilucie13", null, 1);
    Page r7_p1 = new Page("http://www.whatsmode.com/all-influencers/minilucie/culotte-short-skirt-designed-by-minilucie.html",
            "Culotte Short Skirt Designed by Minilucie - Minilucie13 - All Influencers", r7, 2);

    Page r8 = new Page("http://www.whatsmode.com/alessandra-cara", "MODE X Alessandra Cara", null, 1);
    Page r8_p1 = new Page("http://www.whatsmode.com/all-influencers/alessandra-cara/ripped-denim-jacket.html",
            "Ripped Denim Jacket - Alessandra Cara - All Influencers", r8, 2);

    Page r9 = new Page("http://www.whatsmode.com/linn", "MODE X Linn", null, 1);
    Page r9_p1 = new Page("http://www.whatsmode.com/all-influencers/linn/burgundy-romper-designed-by-linn.html",
            "Burgundy Romper Designed by LINN - LINN - All Influencers", r9, 2);

    Page[] layer_1 = {r1, r2, r3, r4, r5, r6, r7, r8, r9};


    @PostConstruct
    public void init() {
        mainState = gaGeoConfigDao.getMainState();
        otherState = gaGeoConfigDao.getOtherState();
        userAgents = gaUserAgentConfigDao.getUserAgents();
    }


    public List<Page> getPages(Page root) {
        if (root == null) {
            return null;
        }
        Integer depth = null;
//        if (r1 == root) {
            depth = depths[random.nextInt(depths.length)];
//        } else {
//            depth = 2;
//        }

        List<Page> pages = new ArrayList<>();

        if (depth == 2) {
            if (r1 == root) {
                Page page = r1_2[random.nextInt(r1_2.length)];
                pages.add(page);
            } else if (r2 == root) {
                Page page = r2_p1;
                pages.add(page);
            } else if (r3 == root) {
                Page page = r3_p1;
                pages.add(page);
            } else if (r4 == root) {
                Page page = r4_p1;
                pages.add(page);
            } else if (r5 == root) {
                Page page = r5_p1;
                pages.add(page);
            } else if (r6 == root) {
                Page page = r6_p1;
                pages.add(page);
            } else if (r7 == root) {
                Page page = r7_p1;
                pages.add(page);
            } else if (r8 == root) {
                Page page = r8_p1;
                pages.add(page);
            } else if (r9 == root) {
                Page page = r9_p1;
                pages.add(page);
            }

        } else if (depth == 3) {
            if (r1 == root) {
                Page page = r1_3[random.nextInt(r1_3.length)];
                if (page != null && page.getParent() != null) {
                    pages.add(page.getParent());
                    pages.add(page);
                }
            } else {
                int j = random.nextInt(3);
                if (j == 0) {
                    pages.add(r1);
                    Page page = r1_3[random.nextInt(r1_3.length)];
                    if (page != null && page.getParent() != null) {
                        pages.add(page.getParent());
                        pages.add(page);
                    }
                } else {
                    if (r2 == root) {
                        Page page = r2_p1;
                        pages.add(page);
                    } else if (r3 == root) {
                        Page page = r3_p1;
                        pages.add(page);
                    } else if (r4 == root) {
                        Page page = r4_p1;
                        pages.add(page);
                    } else if (r5 == root) {
                        Page page = r5_p1;
                        pages.add(page);
                    } else if (r6 == root) {
                        Page page = r6_p1;
                        pages.add(page);
                    } else if (r7 == root) {
                        Page page = r7_p1;
                        pages.add(page);
                    } else if (r8 == root) {
                        Page page = r8_p1;
                        pages.add(page);
                    } else if (r9 == root) {
                        Page page = r9_p1;
                        pages.add(page);
                    }
                    pages.add(r1);
                    if (j == 1) {
                        Page page = r1_3[random.nextInt(r1_3.length)];
                        if (page != null && page.getParent() != null) {
                            pages.add(page.getParent());
                            pages.add(page);
                        }
                    } else {
                        Page page = r1_2[random.nextInt(r1_2.length)];
                        pages.add(page);
                    }

                }

            }

        }
        return pages;
    }

    public Page getRoot() {
        return layer_1[random.nextInt(layer_1.length)];
    }

    public Integer getGap() {
        return 60000 + random.nextInt(180000);
    }

    public String clientId() {
        return Math.round(Math.random() * 0x7FFFFFFF) + "." + Math.round(System
                .currentTimeMillis() /
                1000);
    }

    public String getUserAgent() {
        return userAgents.get(random.nextInt(userAgents.size()));
    }

    public String getGeoId() {
        int notSet = random.nextInt(100);
        String geoId = null;
        if (notSet >= 10) {
            int i = random.nextInt(10);
            geoId = "21137";
            if (i < 2) {
                // Other cities
                i = random.nextInt(10);
                if (i == 0) {
                    geoId = otherState.get(random.nextInt(otherState.size()));
                } else {
                    geoId = otherStateCities[random.nextInt(otherStateCities.length)];
                }

            } else {
                // New York or California
                i = random.nextInt(10);
                if (i < 3) {
                    geoId = mainState.get(random.nextInt(mainState.size()));
                } else {
                    geoId = mainStateCities[random.nextInt(mainStateCities.length)];
                }

            }
        } else {
            geoId = "notSet";
        }

        return geoId;
    }

    private String[] iosMainVersions = {"9", "10", "10", "10", "10", "10"};

    private String[] ios9MainVersions = {"9.0", "9.1", "9.2", "9.2", "9.3", "9.3", "9.3", "9.3"};

    private String[] ios90Versions = {"9.0", "9.0.1", "9.0.1", "9.0.2", "9.0.2", "9.0.2"};

    private String[] ios91Versions = {"9.1"};

    private String[] ios92Versions = {"9.2", "9.2.1", "9.2.1"};

    private String[] ios93Versions = {"9.3", "9.3.1", "9.3.2", "9.3.3", "9.3.3", "9.3.4", "9.3.4",
            "9.3.5", "9.3.5", "9.3.5", "9.3.5"};

    private String[] ios10MainVersions = {"10.0", "10.1", "10.2", "10.2", "10.2", "10.2", "10.2",
            "10.3", "10.3", "10.3"};

    private String[] ios100Versions = {"10.0", "10.0.1", "10.0.2", "10.0.2", "10.0.3", "10.0.3",
            "10.0.3"};

    private String[] ios101Versions = {"10.1", "10.1.1", "10.1.1", "10.1.1"};

    private String[] ios102Versions = {"10.2", "10.2.1", "10.2.1"};

    private String[] ios103Versions = {"10.3"};

    private String getIosVersion() {
        String iosMainVersion = iosMainVersions[random.nextInt(iosMainVersions.length)];
        if ("9".equals(iosMainVersion)) {
            String ios9MainVersion = ios9MainVersions[random.nextInt(ios9MainVersions.length)];
            if ("9.0".equals(ios9MainVersion)) {
                return ios90Versions[random.nextInt(ios90Versions.length)];
            } else if ("9.1".equals(ios9MainVersion)) {
                return ios91Versions[random.nextInt(ios91Versions.length)];
            } else if ("9.2".equals(ios9MainVersion)) {
                return ios92Versions[random.nextInt(ios92Versions.length)];
            } else if ("9.3".equals(ios9MainVersion)) {
                return ios93Versions[random.nextInt(ios93Versions.length)];
            }
        } else if ("10".equals(iosMainVersion)) {
            String ios10MainVersion = ios10MainVersions[random.nextInt(ios10MainVersions.length)];
            if ("10.0".equals(ios10MainVersion)) {
                return ios100Versions[random.nextInt(ios100Versions.length)];
            } else if ("10.1".equals(ios10MainVersion)) {
                return ios101Versions[random.nextInt(ios101Versions.length)];
            } else if ("10.2".equals(ios10MainVersion)) {
                return ios102Versions[random.nextInt(ios102Versions.length)];
            } else if ("10.3".equals(ios10MainVersion)) {
                return ios103Versions[random.nextInt(ios103Versions.length)];
            }
        }

        return "10.2";
    }

    private String[] appUserAgents = {"GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac " +
            "OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPhone; U; CPU iOS %s like Mac OS X)",
            "GoogleAnalytics/3.17 (iPad; U; CPU iOS %s like Mac OS X)"};

    public String getAppUserAgent() {
        String ios = getIosVersion();
        return String.format(appUserAgents[random.nextInt(appUserAgents.length)], ios);
    }

    private int[] appDepths = {1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 6, 7, 8};

    private String[] fromHomeTo = {"SHOP_TAB", "SEARCH_TAB", "ME"};

    private String[] fromShopTo = {"HOME", "SEARCH_TAB", "ME"};

    private String[] fromSearchTo = {"HOME", "SHOP_TAB", "ME"};

    private String[] fromMeTo = {"HOME", "SHOP_TAB", "SEARCH_TAB"};

    public String[] getAppContent() {
        int depth = depth = appDepths[random.nextInt(appDepths.length)];
        System.out.println(depth);
        if (depth == 1) {
            return null;
        } else if (depth == 2) {
            return new String[]{fromHomeTo[random.nextInt(fromHomeTo.length)]};
        } else if (depth == 3) {
            String[] result = new String[2];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            if ("SHOP_TAB".equals(result[0])) {
                result[1] = fromShopTo[random.nextInt(fromShopTo.length)];
            } else if ("SEARCH_TAB".equals(result[0])) {
                result[1] = fromSearchTo[random.nextInt(fromSearchTo.length)];
            } else if ("ME".equals(result[0])) {
                result[1] = fromMeTo[random.nextInt(fromMeTo.length)];
            }
            return result;
        } else if (depth == 4) {
            String[] result = new String[3];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            for (int i = 1; i < 3; i ++) {
                if ("SHOP_TAB".equals(result[i - 1])) {
                    result[i] = fromShopTo[random.nextInt(fromShopTo.length)];
                } else if ("SEARCH_TAB".equals(result[i - 1])) {
                    result[i] = fromSearchTo[random.nextInt(fromSearchTo.length)];
                } else if ("ME".equals(result[i - 1])) {
                    result[i] = fromMeTo[random.nextInt(fromMeTo.length)];
                } else if ("HOME".equals(result[i - 1])) {
                    result[i] = fromHomeTo[random.nextInt(fromHomeTo.length)];
                }
            }
            return result;
        } else if (depth == 5) {
            String[] result = new String[4];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            for (int i = 1; i < 4; i ++) {
                if ("SHOP_TAB".equals(result[i - 1])) {
                    result[i] = fromShopTo[random.nextInt(fromShopTo.length)];
                } else if ("SEARCH_TAB".equals(result[i - 1])) {
                    result[i] = fromSearchTo[random.nextInt(fromSearchTo.length)];
                } else if ("ME".equals(result[i - 1])) {
                    result[i] = fromMeTo[random.nextInt(fromMeTo.length)];
                } else if ("HOME".equals(result[i - 1])) {
                    result[i] = fromHomeTo[random.nextInt(fromHomeTo.length)];
                }
            }
            return result;
        } else if (depth == 6) {
            String[] result = new String[5];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            for (int i = 1; i < 5; i ++) {
                if ("SHOP_TAB".equals(result[i - 1])) {
                    result[i] = fromShopTo[random.nextInt(fromShopTo.length)];
                } else if ("SEARCH_TAB".equals(result[i - 1])) {
                    result[i] = fromSearchTo[random.nextInt(fromSearchTo.length)];
                } else if ("ME".equals(result[i - 1])) {
                    result[i] = fromMeTo[random.nextInt(fromMeTo.length)];
                } else if ("HOME".equals(result[i - 1])) {
                    result[i] = fromHomeTo[random.nextInt(fromHomeTo.length)];
                }
            }
            return result;
        } else if (depth == 7) {
            String[] result = new String[6];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            for (int i = 1; i < 6; i ++) {
                if ("SHOP_TAB".equals(result[i - 1])) {
                    result[i] = fromShopTo[random.nextInt(fromShopTo.length)];
                } else if ("SEARCH_TAB".equals(result[i - 1])) {
                    result[i] = fromSearchTo[random.nextInt(fromSearchTo.length)];
                } else if ("ME".equals(result[i - 1])) {
                    result[i] = fromMeTo[random.nextInt(fromMeTo.length)];
                } else if ("HOME".equals(result[i - 1])) {
                    result[i] = fromHomeTo[random.nextInt(fromHomeTo.length)];
                }
            }
            return result;
        } else if (depth == 8) {
            String[] result = new String[7];
            result[0] = fromHomeTo[random.nextInt(fromHomeTo.length)];
            for (int i = 1; i < 7; i ++) {
                if ("SHOP_TAB".equals(result[i - 1])) {
                    result[i] = fromShopTo[random.nextInt(fromShopTo.length)];
                } else if ("SEARCH_TAB".equals(result[i - 1])) {
                    result[i] = fromSearchTo[random.nextInt(fromSearchTo.length)];
                } else if ("ME".equals(result[i - 1])) {
                    result[i] = fromMeTo[random.nextInt(fromMeTo.length)];
                } else if ("HOME".equals(result[i - 1])) {
                    result[i] = fromHomeTo[random.nextInt(fromHomeTo.length)];
                }
            }
            return result;
        }
        return null;
    }

    public static void main(String[] args) {
        CommonParaGenerator c = new CommonParaGenerator();
        String[] s = c.getAppContent();
        if (s == null) {
            System.out.println("empty");
        }
        for (String ss : s) {
            System.out.println(ss);
        }
    }
}
