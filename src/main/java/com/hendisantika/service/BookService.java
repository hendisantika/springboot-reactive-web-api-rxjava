package com.hendisantika.service;

import com.hendisantika.dto.request.AddBookRequest;
import com.hendisantika.entity.Author;
import com.hendisantika.repository.AuthorRepository;
import com.hendisantika.repository.BookRepository;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
}
