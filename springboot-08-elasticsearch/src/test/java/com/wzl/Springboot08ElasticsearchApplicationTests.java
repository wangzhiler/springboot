package com.wzl;

import com.wzl.bean.Article;
import com.wzl.bean.Book;
import com.wzl.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot08ElasticsearchApplicationTests {

	@Autowired
	JestClient jestClient;

	@Autowired
	BookRepository bookRepository;

	@Test
	public void test02() {
		Book book = new Book();

		bookRepository.index(book);
	}


	@Test
	public void contextLoads() {
		//1. 给es中索引(保存)一个文档
		Article article = new Article();
		article.setId(1);
		article.setAuthor("autho222d");
		article.setTitle("titlehainf");
		article.setContent("Helejfiaof helloooooo");


		//构建一个索引
		Index index = new Index.Builder(article).index("wzl").type("news").build();

		//
		try {
			//执行
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void search() {
		String json = "{\n" +
				"\t\"query\":{\n" +
				"\t\t\"match\":{\n" +
				"\t\t\t\"content\":\"hel\"\n" +
				"\t\t}\n" +
				"\t}\n" +
				"}";
		Search search = new Search.Builder(json).addIndex("wzl").addType("news").build();
		try {
			SearchResult result = jestClient.execute(search);
			System.out.println(result.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
