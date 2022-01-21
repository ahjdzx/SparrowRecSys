/*
 * Copyright 2022 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.sparrowrecsys.test;

import org.roaringbitmap.RoaringBitmap;

/**
 * @author zhangxin
 * @description
 * @date 2022/1/21 16:48
 */
public class VeryLargeBitmap {

    public static void main(String[] args) {
        RoaringBitmap rb = new RoaringBitmap();
        rb.add(0L, 1L << 32);// the biggest bitmap we can create
        System.out.println("memory usage: " + rb.getSizeInBytes() * 1.0 / (1L << 32) + " byte per value");
        if (rb.getLongCardinality() != (1L << 32))
            throw new RuntimeException("bug!");

    }
}
