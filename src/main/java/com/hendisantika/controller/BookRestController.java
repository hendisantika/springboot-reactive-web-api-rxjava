package com.hendisantika.controller;

import com.hendisantika.dto.request.AddBookRequest;
import com.hendisantika.dto.request.UpdateBookRequest;
import com.hendisantika.dto.request.UpdateBookWebRequest;
import com.hendisantika.dto.response.BaseWebResponse;
import com.hendisantika.dto.response.BookResponse;
import com.hendisantika.dto.response.BookWebResponse;
import com.hendisantika.service.BookService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/api/books")
@Tag(name = "Book", description = "Endpoints to manage Books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Add New Book",
            description = "Handle New Book.",
            tags = {"Book"})
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
    public Single<ResponseEntity<BaseWebResponse>> addBook(
            @RequestBody AddBookRequest addBookRequest) {
        return bookService.addBook(addBookRequest).subscribeOn(Schedulers.io()).map(
                s -> ResponseEntity.created(URI.create("/api/books/" + s))
                        .body(BaseWebResponse.successNoData()));
    }

    /*private AddBookRequest toAddBookRequest(AddBookWebRequest addBookWebRequest) {
        AddBookRequest addBookRequest = new AddBookRequest();
        BeanUtils.copyProperties(addBookWebRequest, addBookRequest);
        return addBookRequest;
    }*/

    @PutMapping(
            value = "/{bookId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update Book By ID",
            description = "Update Book By ID.",
            tags = {"Book"})
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
    public Single<ResponseEntity<BaseWebResponse>> updateBook(@PathVariable(value = "bookId") String bookId,
                                                              @RequestBody UpdateBookWebRequest updateBookWebRequest) {
        return bookService.updateBook(toUpdateBookRequest(bookId, updateBookWebRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    private UpdateBookRequest toUpdateBookRequest(String bookId, UpdateBookWebRequest updateBookWebRequest) {
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        BeanUtils.copyProperties(updateBookWebRequest, updateBookRequest);
        updateBookRequest.setId(bookId);
        return updateBookRequest;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get All Books",
            description = "Get All Books.",
            tags = {"Book"})
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
    public Single<ResponseEntity<BaseWebResponse<List<BookWebResponse>>>> getAllBooks(@RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                                      @RequestParam(value = "page", defaultValue = "0") int page) {
        return bookService.getAllBooks(limit, page)
                .subscribeOn(Schedulers.io())
                .map(bookResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toBookWebResponseList(bookResponses))));
    }

    private List<BookWebResponse> toBookWebResponseList(List<BookResponse> bookResponseList) {
        return bookResponseList
                .stream()
                .map(this::toBookWebResponse)
                .collect(Collectors.toList());
    }

    private BookWebResponse toBookWebResponse(BookResponse bookResponse) {
        BookWebResponse bookWebResponse = new BookWebResponse();
        BeanUtils.copyProperties(bookResponse, bookWebResponse);
        return bookWebResponse;
    }

    @GetMapping(
            value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Search Book by ID",
            description = "Search Book by ID.",
            tags = {"Book"})
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
    public Single<ResponseEntity<BaseWebResponse<BookWebResponse>>> getBookDetail(@PathVariable(value = "bookId") String bookId) {
        return bookService.getBookDetail(bookId)
                .subscribeOn(Schedulers.io())
                .map(bookResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toBookWebResponse(bookResponse))));
    }


    @DeleteMapping(
            value = "/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Delete Book by ID",
            description = "Delete Book by ID.",
            tags = {"Book"})
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
    public Single<ResponseEntity<BaseWebResponse>> deleteBook(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteBook(bookId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

}
