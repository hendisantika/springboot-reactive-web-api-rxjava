package com.hendisantika.repository;

import com.hendisantika.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllByAuthorId(String authorId);
}
