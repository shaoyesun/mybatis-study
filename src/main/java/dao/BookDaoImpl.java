package dao;

import domain.Book;
import org.apache.ibatis.session.SqlSession;
import util.SessionFactoryUtil;

import java.util.List;
import java.util.Map;

public class BookDaoImpl implements BookDao {
    @Override
    public List<Book> findAll() {
        SqlSession session = SessionFactoryUtil.getSession("development");
        BookDao mapper = session.getMapper(BookDao.class);
        List<Book> books =  mapper.findAll();
        session.close();
        return books;
    }

    @Override
    public Book findOne(Long id) {
        SqlSession session = SessionFactoryUtil.getSession("development");
        BookDao mapper = session.getMapper(BookDao.class);
        return mapper.findOne(id);
    }

    @Override
    public void addBook(Book book) {
        SqlSession session = SessionFactoryUtil.getSession("development");
        BookDao mapper = session.getMapper(BookDao.class);
        mapper.addBook(book);
        session.commit();
        session.close();
    }

    @Override
    public List<Book> findByIdWithIn(List ids) {
        SqlSession session = SessionFactoryUtil.getSession("development");
        BookDao mapper = session.getMapper(BookDao.class);
        List<Book> books =  mapper.findByIdWithIn(ids);
        session.close();
        return books;
    }

    @Override
    public List<Book> findByIdAndNameWithIn(Map params) {
        SqlSession session = SessionFactoryUtil.getSession("development");
        BookDao mapper = session.getMapper(BookDao.class);
        List<Book> books =  mapper.findByIdAndNameWithIn(params);
        session.close();
        return books;
    }
}
