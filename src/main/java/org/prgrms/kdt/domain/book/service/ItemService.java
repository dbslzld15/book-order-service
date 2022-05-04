package org.prgrms.kdt.domain.book.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.exception.ItemException;
import org.prgrms.kdt.domain.book.repository.ItemRepository;
import org.prgrms.kdt.domain.book.vo.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.prgrms.kdt.domain.book.exception.ItemExceptionType.ITEM_NOT_EXIST;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public long save(long price, int stockQuantity) {
        Item item = new Item(new Price(price), stockQuantity);
        return itemRepository.insert(item);
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ITEM_NOT_EXIST));
    }

    @Transactional
    public void update(long itemId, long price, int stockQuantity) {
        itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ITEM_NOT_EXIST));
        Item item = new Item(itemId, new Price(price), stockQuantity);
        itemRepository.update(item);
        log.info("Update Item, item id: {}", itemId);
    }

    @Transactional
    public void remove(Long itemId) {
        itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ITEM_NOT_EXIST));
        itemRepository.deleteById(itemId);
        log.info("Delete Item, item id: {}", itemId);
    }
}
