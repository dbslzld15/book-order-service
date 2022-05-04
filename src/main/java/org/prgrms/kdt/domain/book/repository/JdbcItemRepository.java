package org.prgrms.kdt.domain.book.repository;

import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.exception.ItemException;
import org.prgrms.kdt.domain.book.vo.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.prgrms.kdt.domain.book.exception.ItemExceptionType.ITEM_NOT_SAVED;
import static org.prgrms.kdt.global.utils.Utils.toLocalDateTime;

@Repository
public class JdbcItemRepository implements ItemRepository {

    public static final Logger log = LoggerFactory.getLogger(JdbcItemRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcItemRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Item item) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = toParameterSource(item);
        int savedRows = jdbcTemplate.update(
                "INSERT INTO item(price, stock_quantity, created_at, modified_at) " +
                        "VALUES (:price, :stockQuantity, :createdAt, :modifiedAt)",
                source, keyHolder, new String[]{"item_id"}
        );
        if (savedRows != 1) {
            throw new ItemException(ITEM_NOT_SAVED);
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Item> findById(long itemId) {
        try {
            Item item = jdbcTemplate.queryForObject("SELECT * FROM item " +
                    "WHERE item_id = :itemId AND is_deleted = 'N'", Collections.singletonMap("itemId", itemId), itemRowMapper);
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 아이디에 해당하는 도서 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public long update(Item item) {
        return jdbcTemplate.update("UPDATE item " +
                "SET price = :price, stock_quantity = :stockQuantity, modified_at = :modifiedAt " +
                "WHERE item_id = :itemId", toParamMap(item));
    }

    @Override
    public void deleteById(long itemId) {
        jdbcTemplate.update("UPDATE item SET is_deleted = 'Y' WHERE item_id = :itemId",
                Collections.singletonMap("itemId", itemId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM item", Collections.emptyMap());
    }

    private static final RowMapper<Item> itemRowMapper = (rs, i) -> {
        long itemId = rs.getLong("item_id");
        long price = rs.getLong("price");
        int stockQuantity = rs.getInt("stock_quantity");
        return new Item(itemId, new Price(price), stockQuantity);
    };

    private MapSqlParameterSource toParameterSource(Item item) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("price", item.getPrice().getPrice())
                .addValue("stockQuantity", item.getStockQuantity())
                .addValue("createdAt", item.getCreatedDateTime())
                .addValue("modifiedAt", item.getModifiedDateTime());
        return parameters;
    }

    private Map<String, Object> toParamMap(Item item) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("itemId", item.getItemId());
        paramMap.put("price", item.getPrice().getPrice());
        paramMap.put("stockQuantity", item.getStockQuantity());
        paramMap.put("createdAt", item.getCreatedDateTime());
        paramMap.put("modifiedAt", item.getModifiedDateTime());
        return paramMap;
    }
}
