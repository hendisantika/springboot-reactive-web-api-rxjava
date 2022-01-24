package com.hendisantika.controller;

import com.hendisantika.dto.request.AddAuthorWebRequest;
import com.hendisantika.dto.response.BaseWebResponse;
import com.hendisantika.service.AuthorService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> addAuthor(@RequestBody AddAuthorWebRequest addAuthorWebRequest) {
        return authorService.addAuthor(toAddAuthorRequest(addAuthorWebRequest))
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                        .created(URI.create("/api/authors/" + s))
                        .body(BaseWebResponse.successNoData()));
    }
}
