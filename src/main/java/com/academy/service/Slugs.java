package com.academy.service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

/** Turns titles in Azerbaijani, Russian or English into URL slugs. */
public final class Slugs {

    private static final Map<Character, String> TRANSLIT = Map.ofEntries(
            Map.entry('ə', "e"), Map.entry('ı', "i"), Map.entry('ş', "s"), Map.entry('ç', "c"),
            Map.entry('ğ', "g"), Map.entry('ö', "o"), Map.entry('ü', "u"),
            Map.entry('а', "a"), Map.entry('б', "b"), Map.entry('в', "v"), Map.entry('г', "g"),
            Map.entry('д', "d"), Map.entry('е', "e"), Map.entry('ё', "e"), Map.entry('ж', "zh"),
            Map.entry('з', "z"), Map.entry('и', "i"), Map.entry('й', "y"), Map.entry('к', "k"),
            Map.entry('л', "l"), Map.entry('м', "m"), Map.entry('н', "n"), Map.entry('о', "o"),
            Map.entry('п', "p"), Map.entry('р', "r"), Map.entry('с', "s"), Map.entry('т', "t"),
            Map.entry('у', "u"), Map.entry('ф', "f"), Map.entry('х', "h"), Map.entry('ц', "ts"),
            Map.entry('ч', "ch"), Map.entry('ш', "sh"), Map.entry('щ', "sch"), Map.entry('ъ', ""),
            Map.entry('ы', "y"), Map.entry('ь', ""), Map.entry('э', "e"), Map.entry('ю', "yu"),
            Map.entry('я', "ya"));

    private Slugs() {
    }

    public static String of(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toLowerCase(Locale.ROOT).toCharArray()) {
            sb.append(TRANSLIT.getOrDefault(c, String.valueOf(c)));
        }
        String ascii = Normalizer.normalize(sb, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        String slug = ascii.replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        if (slug.length() > 80) {
            slug = slug.substring(0, 80).replaceAll("-$", "");
        }
        return slug.isEmpty() ? "post" : slug;
    }
}
