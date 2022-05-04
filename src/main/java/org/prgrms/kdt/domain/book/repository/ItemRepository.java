package org.prgrms.kdt.domain.book.repository;

import org.prgrms.kdt.domain.book.entity.Item;

import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(long itemId);

    long insert(Item item);

    long update(Item item);

    void deleteById(long itemId);

    void deleteAll();
}
