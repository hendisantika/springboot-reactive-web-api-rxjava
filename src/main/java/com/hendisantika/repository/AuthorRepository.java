package com.hendisantika.repository;

import com.hendisantika.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface AuthorRepository extends JpaRepository<Author, String> {
}
