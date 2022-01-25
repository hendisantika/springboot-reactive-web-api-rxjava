package com.hendisantika.controller;

import com.hendisantika.dto.request.AddAuthorRequest;
import com.hendisantika.dto.request.AddAuthorWebRequest;
import com.hendisantika.dto.response.BaseWebResponse;
import com.hendisantika.service.AuthorService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
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
@Tag(name = "Author", description = "Endpoints to manage Author")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Add New Author",
            description = "Handle New Author.",
            tags = {"Author"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            BaseWebResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Single<ResponseEntity<BaseWebResponse>> addAuthor(@RequestBody AddAuthorWebRequest addAuthorWebRequest) {
        return authorService.addAuthor(toAddAuthorRequest(addAuthorWebRequest))
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                        .created(URI.create("/api/authors/" + s))
                        .body(BaseWebResponse.successNoData()));
    }

    private AddAuthorRequest toAddAuthorRequest(AddAuthorWebRequest addAuthorWebRequest) {
        AddAuthorRequest addAuthorRequest = new AddAuthorRequest();
        BeanUtils.copyProperties(addAuthorWebRequest, addAuthorRequest);
        return addAuthorRequest;
    }
}
