package tech.donau;

import tech.donau.data.Book;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @Inject
    BookService bookService;

    @GET
    public List<Book> hello() {
        return bookService.findAll();
    }

    @GET
    @Path("{title}")
    public Book getBook(@PathParam("title") String title) {
        return bookService.get(title);
    }

    @POST
    public Book addBook(Book book) {
        bookService.add(book);
        return book;
    }

}