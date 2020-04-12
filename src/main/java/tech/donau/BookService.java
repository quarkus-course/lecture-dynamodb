package tech.donau;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import tech.donau.data.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookService {
    public static final String BOOK_TITLE_COL = "title";
    public static final String BOOK_PAGES_COL = "pages";
    public static final String BOOKS_TABLE = "Books";

    @Inject
    DynamoDbClient client;

    public void add(Book book) {
        client.putItem(putItemRequest(book));
    }

    public List<Book> findAll() {
        return client.scanPaginator(scanRequest()).items()
                .stream()
                .map(Book::from)
                .collect(Collectors.toList());
    }

    public Book get(String title) {
        return Book.from(client.getItem(getItemRequest(title)).item());
    }


    private ScanRequest scanRequest() {
        return ScanRequest.builder()
                .tableName(BOOKS_TABLE)
                .attributesToGet(BOOK_TITLE_COL, BOOK_PAGES_COL)
                .build();
    }

    private GetItemRequest getItemRequest(String name) {
        final HashMap<String, AttributeValue> item = new HashMap<>();
        item.put(BOOK_TITLE_COL, AttributeValue.builder().s(name).build());

        return GetItemRequest.builder()
                .tableName(BOOKS_TABLE)
                .key(item)
                .attributesToGet(BOOK_TITLE_COL, BOOK_PAGES_COL)
                .build();
    }

    private PutItemRequest putItemRequest(Book book) {
        final HashMap<String, AttributeValue> item = new HashMap<>();
        item.put(BOOK_TITLE_COL, AttributeValue.builder().s(book.getTitle()).build());
        item.put(BOOK_PAGES_COL, AttributeValue.builder().n(book.getPages().toString()).build());

        return PutItemRequest.builder()
                .tableName(BOOKS_TABLE)
                .item(item)
                .build();
    }

}
