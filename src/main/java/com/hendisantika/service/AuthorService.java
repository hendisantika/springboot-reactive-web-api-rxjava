package com.hendisantika.service;

import com.hendisantika.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

}
