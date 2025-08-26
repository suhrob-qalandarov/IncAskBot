package org.exp.incaskbot.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Message {

    List<String> emojis = new ArrayList<>(List.of(
            "🪂",
            "🦋",
            "💎",
            "🍿",
            "🍓",
            "🏖",
            "✨",
            "🫀",
            "🪙",
            "🌙",
            "☀️",
            "♨️",
            "🚀",
            "🛩️",
            "🚂️",
            "🎯",
            "🪐"
    ));

    static String getRandomEmoji() {
        if (emojis.isEmpty()) {
            return "";
        }
        Random random = new Random();
        int index = random.nextInt(emojis.size());
        return emojis.get(index);
    }
}
