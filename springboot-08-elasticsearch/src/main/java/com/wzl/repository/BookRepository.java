package com.wzl.repository;

import com.wzl.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by thinkpad on 2018/10/3.
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {


}
