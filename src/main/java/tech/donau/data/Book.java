package tech.donau.data;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import tech.donau.BookService;

import java.util.Map;

public class Book {
    private String title;
    private Integer pages;

    public Book() {
    }

    public Book(String title, Integer pages) {
        this.title = title;
        this.pages = pages;
    }

    public static Book from(Map<String, AttributeValue> attributes) {
        final Book book = new Book();
        book.setTitle(attributes.get(BookService.BOOK_TITLE_COL).s());
        book.setPages(Integer.parseInt(attributes.get(BookService.BOOK_PAGES_COL).n()));
        return book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
