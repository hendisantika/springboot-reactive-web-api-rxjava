package com.hendisantika.controller;

import com.hendisantika.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.41
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/api/authors")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;
}
