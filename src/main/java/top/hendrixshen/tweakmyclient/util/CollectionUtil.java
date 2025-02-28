package top.hendrixshen.tweakmyclient.util;

import java.util.List;
import java.util.Random;

public class CollectionUtil {
    private static final Random RANDOM = new Random();

    public static <T> T selectRandom(List<T> list, T defaultValue) {
        return list.isEmpty() ? defaultValue : list.get(CollectionUtil.RANDOM.nextInt(list.size()));
    }

    public static <T> T selectFirst(List<T> list, T defaultValue) {
        return list.isEmpty() ? defaultValue : list.get(0);
    }
}
