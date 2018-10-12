package com.honeywelltest;

import java.util.List;

public interface ListemItemsListener {

    void onListUpdated(List<Item> itemList);

    void onErrorOccured(String message);

    void onItemUpdated(Item item);
}
