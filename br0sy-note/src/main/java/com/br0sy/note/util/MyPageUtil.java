package com.br0sy.note.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageUtil {

    // 一页最多放多少篇云记
    private int maxArticleInOnePage = 10;

    // 属性
    private int articleCount;
    private int pageCount;
    private int nextPageNum;
    private int prePageNum;
    private int nowPageNum;
    private int lastArticleNumber;

    // 构造函数
    public MyPageUtil(int articleCount) {
        this.articleCount = articleCount;
        this.pageCount = articleCount / maxArticleInOnePage + 1;
    }

    // 方法

    /**
     * 计算现在页数中前面的页数和后面的页数
     * @param nowPageNum
     */
    public void calPreAndNextPageNum(int nowPageNum) {
        this.nowPageNum = nowPageNum;
        // 默认一开始前面的页数和后面的页数分别为4,5
        prePageNum = 4;
        nextPageNum = 5;
        if (nowPageNum <= 4) {
            prePageNum = nowPageNum - 1;
        }
        if (pageCount - nowPageNum <= 5) {
            nextPageNum = pageCount - nowPageNum;
        }
    }


}
