package org.habv.mnemosyne.util;

import java.text.Normalizer;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 25/10/2016
 */
@UtilityClass
public class StringUtil {

    /**
     * Change text to be used as part of url, replace all special characters
     * with guion: "Lorem ipsum dolor sit amet." to
     * "lorem-ipsum-dolor-sit-amet".
     *
     * @param text text to change
     * @return text without all special characters to be used as part of url.
     */
    public static String toURL(@NonNull String text) {
        return normalize(text)//normalize text
                .replaceAll("[^A-Za-z0-9]+", "-")//replace all special characters with guion
                .replaceAll("(^-|-$)", "")//remove first and last guion
                .toLowerCase();
    }

    /**
     * Normalize a sequence of char values: "Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ" to "This is
     * a funky String".
     *
     * @param text text to normalize
     * @return normalized text
     */
    public static String normalize(@NonNull String text) {
        return Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
