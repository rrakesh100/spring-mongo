package com.binimise.admin.controllers;

import com.binimise.admin.dtos.request.CreateBookRequest;
import com.binimise.admin.dtos.request.CreateMCRequest;
import com.binimise.admin.dtos.response.MessageResponse;
import com.binimise.admin.service.BookService;
import com.binimise.admin.service.MCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("")
    public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookRequest book) {

        bookService.create(book);
        return ResponseEntity.ok(new MessageResponse("MC successfully created!"));
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(bookService.getAll());
    }

}
