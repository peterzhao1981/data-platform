package com.mode.util;

import java.util.ArrayList;
import java.util.List;

import com.mode.entity.Page;

/**
 * Created by zhaoweiwei on 17/1/22.
 */
public enum PageInfo {

    ROOT1("http://www.whatsmode.com", "MODE - Home"),
    ROOT1_NAV1("http://www.whatsmode.com/new-arrivals.html?mode=grid", "New Arrivals"),
    ROOT1_NAV2("http://www.whatsmode.com/popular-items.html?mode=grid", "Popular Items"),
    ROOT1_NAV3("http://www.whatsmode.com/all-clothing/tops.html?mode=grid", "Tops - All Clothing"),
    ROOT1_NAV4("http://www.whatsmode.com/all-clothing/bottoms.html?mode=grid", "Bottoms - All Clothing"),
    ROOT1_NAV5("http://www.whatsmode.com/all-clothing/dresses.html?mode=grid", "Dresses - All Clothing"),
    ROOT1_NAV6("http://www.whatsmode.com/sale.html?mode=grid", "Sale"),
    ROOT1_NAV7("http://www.whatsmode.com/giveaway.html?mode=list&no_cache=1", "Influencer " +
            "Garments Free Trial"),
    ROOT1_NAV1_P1("http://www.whatsmode.com/new-arrivals/black-a-line-dress.html", "Black A-Line Dress - New Arrivals"),
    ROOT1_NAV1_P2("http://www.whatsmode.com/new-arrivals/blue-off-the-shoulder-shirt.html", "Blue Off-The-Shoulder Shirt - New Arrivals"),
    ROOT1_NAV1_P3("http://www.whatsmode.com/new-arrivals/58-graphic-sweatpants.html", "58 Graphic Sweatpants - New Arrivals"),
    ROOT1_NAV1_P4("http://www.whatsmode.com/new-arrivals/believe-print-leggings.html", "Believe Print Leggings - New Arrivals"),
    ROOT1_NAV2_P1("http://www.whatsmode.com/popular-items/apricot-belted-skirt.html", "Apricot Belted Skirt - Popular Items"),
    ROOT1_NAV2_P2("http://www.whatsmode.com/popular-items/lace-backless-top.html", "Lace Backless Top - Popular Items"),
    ROOT1_NAV2_P3("http://www.whatsmode.com/popular-items/belted-plaid-woolen-skirt.html",
            "Belted Plaid Woolen Skirt - Popular Items"),
    ROOT1_NAV2_P4("http://www.whatsmode.com/popular-items/ribbed-high-neck-fleece-sweater.html", "Ribbed High-Neck Fleece Sweater - Popular Items"),
    ROOT1_NAV3_P1("http://www.whatsmode.com/all-clothing/tops/blue-boyfriend-print-bomber-jacket" +
            ".html", "Blue Boyfriend Print Bomber Jacket - Tops - All Clothing"),
    ROOT1_NAV3_P2("http://www.whatsmode.com/all-clothing/tops/pug-dog-print-sweatshirt.html", "Pug Dog Print Sweatshirt - Tops - All Clothing"),
    ROOT1_NAV3_P3("http://www.whatsmode.com/all-clothing/tops/polyester-long-sleeve-street-paneled-letter-biker-jacket.html", "Polyester Long Sleeve Street Paneled Letter Biker Jacket - Tops - All Clothing"),
    ROOT1_NAV3_P4("http://www.whatsmode.com/all-clothing/tops/ribbed-high-neck-fleece-sweater" +
            ".html", "Ribbed High-Neck Fleece Sweater - Tops - All Clothing"),
    ROOT1_NAV4_P1("http://www.whatsmode.com/all-clothing/bottoms/believe-print-leggings.html", "Believe Print Leggings - Bottoms - All Clothing"),
    ROOT1_NAV4_P2("http://www.whatsmode.com/all-clothing/bottoms/black-pu-leather-macrame-dress" +
            ".html", "Black PU Leather Macrame Dress - Bottoms - All Clothing"),
    ROOT1_NAV4_P3("http://www.whatsmode.com/all-clothing/bottoms/blue-distressed-boyfriend-jeans" +
            ".html", "Blue Distressed Boyfriend Jeans - Bottoms - All Clothing"),
    ROOT1_NAV4_P4("http://www.whatsmode" +
            ".com/all-clothing/bottoms/blue-pockets-casual-plain-skinny-leg-jeans.html", "Blue " +
            "Pockets Casual Plain Skinny Leg Jeans - Bottoms - All Clothing")
    ;
    private String url;
    private String title;

    PageInfo(String url, String title) {
        this.url = url;
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static PageInfo fromString(String url) {
        if (url != null) {
            for (PageInfo pageInfo : PageInfo.values()) {
                if (url.equalsIgnoreCase(pageInfo.getUrl())) {
                    return pageInfo;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "{"
                + "\"url\":\"" + url + "\""
                + ", \"title\":\"" + title + "\""
                + "}";
    }

    public static void main(String[] args) {
        Page r1 = new Page("http://www.whatsmode.com", "MODE - Home", null, 1);
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
        Page[] r1_2 = {r1_n1, r1_n2, r1_n3, r1_n4, r1_n5, r1_n6};
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
                "", r5, 2);

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


    }

}
