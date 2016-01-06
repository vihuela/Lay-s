package com.hadlink.lay_s.datamanager.bean;

import java.util.List;

/**
 * @author Created by lyao on 2016/1/6.
 * @update
 * @description
 */
public class ImageDetail {
    public String id;
    public String desc;
    /**
     * userName : 娴子Love火影
     * userId : 355362267
     * userSign : 1694498816 355112415
     * isSelf : 0
     * portrait : db65e5a8b4e5ad904c6f7665e781abe5bdb12e15
     * isVip : 0
     * isLanv : 0
     * isJiaju :
     * isHunjia :
     * orgName :
     * resUrl :
     * cert :
     * budgetNum :
     * lanvName :
     * contactName :
     */

    public OwnerEntity owner;
    public String fromPageTitle;
    public String column;
    public String parentTag;
    public String date;
    public String downloadUrl;
    public String imageUrl;
    public int imageWidth;
    public int imageHeight;
    public String thumbnailUrl;
    public int thumbnailWidth;
    public int thumbnailHeight;
    public int thumbLargeWidth;
    public int thumbLargeHeight;
    public String thumbLargeUrl;
    public int thumbLargeTnWidth;
    public int thumbLargeTnHeight;
    public String thumbLargeTnUrl;
    public String siteName;
    public String siteLogo;
    public String siteUrl;
    public String fromUrl;
    public String isBook;
    public String bookId;
    public String objUrl;
    public String shareUrl;
    public String setId;
    public String albumId;
    public int isAlbum;
    public String albumName;
    public int albumNum;
    public String userId;
    public int isVip;
    public int isDapei;
    public String dressId;
    public String dressBuyLink;
    public int dressPrice;
    public int dressDiscount;
    public String dressExtInfo;
    public String dressTag;
    public int dressNum;
    public String objTag;
    public int dressImgNum;
    public String hostName;
    public String pictureId;
    public String pictureSign;
    public String dataSrc;
    public String contentSign;
    public String albumDi;
    public String canAlbumId;
    public String albumObjNum;
    public String appId;
    public String photoId;
    public int fromName;
    public String fashion;
    public String title;
    public List<String> tags;

    public static class OwnerEntity {
        public String userName;
        public String userId;
        public String userSign;
        public String isSelf;
        public String portrait;
        public String isVip;
        public String isLanv;
        public String isJiaju;
        public String isHunjia;
        public String orgName;
        public String resUrl;
        public String cert;
        public String budgetNum;
        public String lanvName;
        public String contactName;
    }
}
