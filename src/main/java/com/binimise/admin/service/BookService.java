package com.binimise.admin.service;

import com.binimise.admin.dtos.request.CreateBookRequest;
import com.binimise.admin.dtos.request.CreateMCRequest;
import com.binimise.admin.models.Address;
import com.binimise.admin.models.Book;
import com.binimise.admin.models.MC;
import com.binimise.admin.models.POC;
import com.binimise.admin.repositories.BookRepository;
import com.binimise.admin.repositories.MCRepository;
import com.binimise.admin.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void create(CreateBookRequest createBookRequest) {
        bookRepository.save(Book.builder().name(createBookRequest.getName()).author(createBookRequest.getAuthor()).price(createBookRequest.getPrice()).build());
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

}
