package com.utmstack.grpc.util;

import java.util.List;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle useful generic functions related to List
 */
public class ListUtil {
    /**
     * Method to "on demand" clear a specific list.
     * @param list is the list to be cleared.
     * Can be used to clear collectorConfigList or other local list as needed.
     */
    public static void clearList(List<?> list) {
        list.clear();
    }
    /**
     * Method to "on demand" clear a specific list.
     * @param baseList is the list to remove elements.
     * @param elements are the elements you need to remove from baseList.
     * Can be used to clear any configuration list after we read the configurations.
     */
    public static <T> void removeListElements(List<T> baseList, List<T> elements) {
        baseList.removeAll(elements);
    }
}
