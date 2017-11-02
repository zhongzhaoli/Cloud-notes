package com.notes.Util;

public class ShareSplit {
	public String[] split_qg(String a){
		//切割
		String qg_sha = a.split("\\[")[1];
		String qg_sha_2 = qg_sha.split("\\]")[0];
		//获取到一个数组吧
		String[] qg_sha_3 = qg_sha_2.split(",");
		return qg_sha_3;
	}
}
