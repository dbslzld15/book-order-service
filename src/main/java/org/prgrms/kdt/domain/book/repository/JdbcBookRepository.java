package org.prgrms.kdt.domain.book.repository;

import org.prgrms.kdt.domain.book.entity.Book;
import org.prgrms.kdt.domain.book.exception.BookException;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.kdt.domain.book.exception.BookExceptionType.BOOK_NOT_SAVED;
import static org.prgrms.kdt.global.utils.Utils.toLocalDateTime;

@Repository
public class JdbcBookRepository implements BookRepository{

    private static final Logger log = LoggerFactory.getLogger(JdbcBookRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcBookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Book book) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = toParameterSource(book);
        int savedRows = jdbcTemplate.update(
                "INSERT INTO book(title, author_name, item_id, created_at, modified_at) " +
                        "VALUES (:title, :authorName, :itemId, :createdAt, :modifiedAt)",
                source, keyHolder, new String[]{"book_id"}
        );
        if(savedRows != 1) {
            throw new BookException(BOOK_NOT_SAVED);
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book b JOIN item i on b.item_id = i.item_id " +
                "WHERE b.is_deleted = 'N'", Collections.emptyMap(), bookRowMapper);
    }

    @Override
    public Optional<Book> findById(long bookId) {
        try {
            Book book = jdbcTemplate.queryForObject("SELECT * FROM book b JOIN item i on b.item_id = i.item_id " +
                    "WHERE b.book_id = :bookId AND b.is_deleted = 'N'",
                    Collections.singletonMap("bookId", bookId), bookRowMapper);
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 아이디에 해당하는 도서 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public int update(Book book) {
        return jdbcTemplate.update("UPDATE book " +
                "SET title = :title, author_name = :authorName, item_id = :itemId, created_at = :createdAt, modified_at = :modifiedAt " +
                "WHERE book_id = :bookId", toParamMap(book));
    }

    @Override
    public void deleteById(long bookId) {
        jdbcTemplate.update("UPDATE book SET is_deleted = 'Y' WHERE book_id = :bookId",
                Collections.singletonMap("bookId", bookId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM book", Collections.emptyMap());
    }

    private static final RowMapper<Book> bookRowMapper = (rs, i) -> {
        long bookId = rs.getLong("book_id");
        String title = rs.getString("title");
        String authorName = rs.getString("author_name");
        long itemId = rs.getLong("item_id");
        long price = rs.getLong("price");
        int stockQuantity = rs.getInt("stock_quantity");

        return Book.builder()
                .bookId(bookId)
                .title(new Title(title))
                .authorName(new Name(authorName))
                .itemId(itemId)
                .price(new Price(price))
                .stockQuantity(stockQuantity)
                .build();
    };

    private MapSqlParameterSource toParameterSource(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle().getTitle())
                .addValue("authorName", book.getAuthorName().getName())
                .addValue("itemId", book.getItemId())
                .addValue("createdAt", book.getCreatedDateTime())
                .addValue("modifiedAt", book.getModifiedDateTime());
        return parameters;
    }

    private Map<String, Object> toParamMap(Book book) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bookId", book.getBookId());
        paramMap.put("title", book.getTitle().getTitle());
        paramMap.put("authorName", book.getAuthorName().getName());
        paramMap.put("itemId", book.getItemId());
        paramMap.put("createdAt", book.getCreatedDateTime());
        paramMap.put("modifiedAt", book.getModifiedDateTime());
        return paramMap;
    }
}
