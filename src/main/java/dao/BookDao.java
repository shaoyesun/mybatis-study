package dao;


import domain.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {
    List<Book> findAll();
    Book findOne(Long id);
    void addBook(Book book);
    List<Book> findByIdWithIn(List ids);
    List<Book> findByIdAndNameWithIn(Map params);
}
