package com.hadlink.lay_s.datamanager.net.response;

import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * @author Created by lyao on 2016/1/4.
 * @update
 * @description
 */
public class ImageListResponse<T> implements CommonResponse<List<T>> {

    /**
     * col : 动漫
     * tag : 全部
     * tag3 :
     * sort : 0
     * totalNum : 30000
     * startIndex : 2
     * returnNumber : 20
     */

    public String col;
    public String tag;
    public String tag3;
    public String sort;
    public int totalNum;
    public int startIndex;
    public int returnNumber;
    /**
     * id : 9392140722
     * desc : 【转】十二星座那些有名的动漫人物
     * tags : ["死神"]
     * fromPageTitle : 【转】十二星座那些有名的动漫人物
     * column : 动漫
     * parentTag :
     * date : 2015-11-18
     * downloadUrl : http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b50fd73054d8735e5dde7116e25.jpg
     * imageUrl : http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b50fd73054d8735e5dde7116e25.jpg
     * imageWidth : 1400
     * imageHeight : 990
     * thumbnailUrl : http://imgt7.bdstatic.com/it/u=2,802206130&fm=25&gp=0.jpg
     * thumbnailWidth : 230
     * thumbnailHeight : 162
     * thumbLargeWidth : 310
     * thumbLargeHeight : 219
     * thumbLargeUrl : http://c.hiphotos.baidu.com/image/w%3D310/sign=7b5bc5e134d3d539c13d09c20a86e927/37d12f2eb9389b50fd73054d8735e5dde7116e25.jpg
     * thumbLargeTnWidth : 400
     * thumbLargeTnHeight : 282
     * thumbLargeTnUrl : http://c.hiphotos.baidu.com/image/w%3D400/sign=a77fe6fcd739b6004dce0eb7d9513526/37d12f2eb9389b50fd73054d8735e5dde7116e25.jpg
     * siteName :
     * siteLogo :
     * siteUrl :
     * fromUrl :
     * isBook : 0
     * bookId : 0
     * objUrl : http://f.hiphotos.baidu.com/album/w=2048;q=75/sign=802dbb30b2fb43161a1f7d7a149c7d52/d058ccbf6c81800aa49852f5b13533fa838b47c8.jpg
     * setId : -1
     * albumId : 318726696
     * isAlbum : 0
     * albumName :
     * albumNum : 1
     * userId : 355362267
     * isVip : 0
     * isDapei : 0
     * dressId :
     * dressBuyLink :
     * dressPrice : 0
     * dressDiscount : 0
     * dressExtInfo :
     * dressTag :
     * dressNum : 0
     * objTag : 死神
     * dressImgNum : 0
     * hostName :
     * pictureId : 9392140722
     * pictureSign : 6b5fae7de8d929abd5b1b2146937942457f2f577
     * dataSrc :
     * contentSign : 2814932046,2567695882
     * albumDi :
     * canAlbumId :
     * albumObjNum :
     * appId :
     * photoId :
     * fromName : 0
     * fashion : null
     * title : 【转】十二星座那些有名的动漫人物
     */

    public List<T> imgs;

    @Override public List<T> getResult() {
        return imgs;
    }

    @Override public void setResult(List<T> ts) {
        this.imgs = ts;
    }

    @Override public boolean isValid() {
        return imgs != null && imgs.size() > 0;
    }


}
