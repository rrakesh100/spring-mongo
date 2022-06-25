package com.binimise.admin.repositories;


import com.binimise.admin.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
//    List<Book> findByAuthor(String name);

//    @Query("{'active':true}")
//    List<Book> findAll();
//
//    @Query("{'author' : ?0, 'category' : ?1}")
//    List<Book> findPositionalParameters(String author, String category);
//
//    @Query("{'author' : :#{#author}, 'category' : :#{#category}}")
//    List<Book> findNamedParameters(@Param("author") String author, @Param("category") String category);

}


