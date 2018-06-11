package com.yan.netty.examples.simhash;

import java.math.BigInteger;

public class Test {
	public static void main(String[] args) {

		 String s = "This is a test string for testing";
		 SimHash hash1 = new SimHash(s, 64);
		// System.out.println("intSimHash " + hash1.intSimHash + " " + "bitLength " +
		 hash1.simHash().bitLength();
		 hash1.subByDistance(hash1, 3);
		
		 s = "This is a test string for testing, This is a test string for testing abcdef";
		 SimHash hash2 = new SimHash(s, 64);
		 System.out.println(hash2.simHash() + " " + hash2.simHash().bitCount());
		 hash1.subByDistance(hash2, 3);
		
		 System.out.println("============================");
		 //计算汉明距离，距离越大，相似度越低
		 System.out.println("hammingDistance "+hash1.hammingDistance(hash2));

		/*
		 * 测试数学之美中判断数组内容完全相同的算法
		 *
		 *
		 */
		String[] list1 = { "《数学之美》读后感| 忆桐之家的博客", "数学之美读书笔记（四） - 程序园", "《数学之美》的笔记-全书 - 豆瓣读书" };
		String[] list2 = { "《数学之美》的笔记-全书 - 豆瓣读书", "数学之美读书笔记（四） - 程序园", "《数学之美》读后感| 忆桐之家的博客" };
		BigInteger res1 = new BigInteger("0");
		BigInteger res2 = new BigInteger("0");
		for (int i = 0; i < 3; i++) {
			BigInteger n1 = new SimHash(list1[i], 64).simHash();
			System.out.println(n1);
			BigInteger n2 = new SimHash(list2[i], 64).simHash();
			System.out.println(n2);
			res1 = res1.add(n1);
			res2 = res2.add(n2);
		}
		System.out.println(res1.subtract(res2));

	}
}
