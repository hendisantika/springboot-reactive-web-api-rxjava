package com.hendisantika.service;

import com.hendisantika.dto.request.AddBookRequest;
import com.hendisantika.dto.request.UpdateBookRequest;
import com.hendisantika.dto.response.BookResponse;
import com.hendisantika.entity.Author;
import com.hendisantika.entity.Book;
import com.hendisantika.repository.AuthorRepository;
import com.hendisantika.repository.BookRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Single<String> addBook(AddBookRequest addBookRequest) {
        return saveBookToRepository(addBookRequest);
    }

    private Single<String> saveBookToRepository(AddBookRequest addBookRequest) {
        return Single.create(singleSubscriber -> {
            Optional<Author> optionalAuthor = authorRepository.findById(addBookRequest.getAuthorId());
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                String addedBookId = bookRepository.save(toBook(addBookRequest)).getId();
                singleSubscriber.onSuccess(addedBookId);
            }
        });
    }

    private Book toBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        BeanUtils.copyProperties(addBookRequest, book);
        book.setId(UUID.randomUUID().toString());
        book.setAuthor(Author.builder()
                .id(addBookRequest.getAuthorId())
                .build());
        return book;
    }

    public Completable updateBook(UpdateBookRequest updateBookRequest) {
        return updateBookToRepository(updateBookRequest);
    }

    private Completable updateBookToRepository(UpdateBookRequest updateBookRequest) {
        return Completable.create(completableSubscriber -> {
            Optional<Book> optionalBook = bookRepository.findById(updateBookRequest.getId());
            if (!optionalBook.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                Book book = optionalBook.get();
                book.setTitle(updateBookRequest.getTitle());
                bookRepository.save(book);
                completableSubscriber.onComplete();
            }
        });
    }

    public Single<List<BookResponse>> getAllBooks(int limit, int page) {
        return findAllBooksInRepository(limit, page)
                .map(this::toBookResponseList);
    }

    private Single<List<Book>> findAllBooksInRepository(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<Book> books = bookRepository.findAll(PageRequest.of(page, limit)).getContent();
            singleSubscriber.onSuccess(books);
        });
    }
}
