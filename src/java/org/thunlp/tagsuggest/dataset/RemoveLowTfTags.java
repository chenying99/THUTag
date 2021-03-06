package org.thunlp.tagsuggest.dataset;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.thunlp.io.JsonUtil;
import org.thunlp.io.RecordReader;
import org.thunlp.tagsuggest.common.DoubanPost;
import org.thunlp.tagsuggest.common.Post;

public class RemoveLowTfTags {
	public static void main(String[] args){
		int counter = 0;
		try{
			JsonUtil J = new JsonUtil();
//			RecordReader reader = new RecordReader("/home/cxx/smt/sample/moviePost.dat");
//			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
//					new FileOutputStream("/home/cxx/smt/sample/moviePostTf5.txt"),
//					"UTF-8"));
//			BufferedWriter outPost = new BufferedWriter(new OutputStreamWriter(
//					new FileOutputStream("/home/cxx/smt/sample/moviepostTf5.txt"),
//					"UTF-8"));
			RecordReader reader = new RecordReader("/home/cxx/smt/sample/moviePost.dat");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("/home/cxx/smt/sample/moviePostTf5.txt"),
					"UTF-8"));
			BufferedWriter outPost = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("/home/cxx/smt/sample/moviepostTf5.txt"),
					"UTF-8"));
			HashMap<String, Integer> doubanTags = new HashMap<String, Integer>();
			HashSet<String> postTags = new HashSet<String>();
			while (reader.next()) {
				DoubanPost p = J.fromJson(reader.value(), DoubanPost.class);
				
				doubanTags.clear();
				postTags.clear();
				for(Entry<String, Integer> e:p.getDoubanTags().entrySet()){
					if(e.getValue() >= 5){
						doubanTags.put(e.getKey(), e.getValue());
						postTags.add(e.getKey());
					}
				}
				if(doubanTags.size() != 0){
					p.setDoubanTags(doubanTags);
					out.write(J.toJson(p));
					out.newLine();
					
					Post post = new Post();
					post.setContent(p.getContent());
					post.setExtras(p.getExtras());
					post.setId(p.getId());
					post.setResourceKey(p.getResourceKey());
					post.setTags(postTags);
					post.setTimestamp(p.getTimestamp());
					post.setTitle(p.getTitle());
					post.setUserId(p.getUserId());
					outPost.write(J.toJson(post));
					outPost.newLine();
				}
				
				counter ++;
				if(counter % 10000 == 0){
					System.out.println("clean tags num:"+counter);
				}
			}
			out.close();
			outPost.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(counter);
			e.printStackTrace();
		}
	}
}
