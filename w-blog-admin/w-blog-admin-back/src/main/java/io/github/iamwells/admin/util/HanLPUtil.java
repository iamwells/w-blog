package io.github.iamwells.admin.util;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.utility.CharacterHelper;
import com.hankcs.hanlp.utility.TextUtility;

import java.util.List;

public class HanLPUtil {

    public static String segment(SegmentType type, String text, String delimiter) {
        switch (type) {
            case INDEX -> {
                return segmentForIndex(text, delimiter);
            }
            case SEARCH -> {
                return segmentForSearch(text, delimiter);
            }
            default -> {
                return TextUtility.join(NLPTokenizer.segment(text).stream().map(term -> term.word).toList(), delimiter);
            }
        }
    }

    public static String segmentForIndex(String text) {
        return segmentForIndex(text, " ");
    }

    public static String segmentForIndex(String text, String delimiter) {
        List<Term> segment = NLPTokenizer.segment(text);
        List<String> words = segment.stream()
                .filter(term -> TextUtility.isAllChinese(term.word) || TextUtility.isAllLetterOrNum(term.word))
                .map(term -> regularizeString(term.word)).toList();
        if (delimiter == null || delimiter.isEmpty()) {
            delimiter = " ";
        }
        return TextUtility.join(words, delimiter);
    }

    public static String segmentForSearch(String text) {
        return segmentForSearch(text, " ");
    }

    public static String segmentForSearch(String text, String delimiter) {
        List<String> words = NLPTokenizer.segment(text).stream()
                .filter(term -> CoreStopWordDictionary.shouldInclude(term) || TextUtility.isAllLetterOrNum(term.word))
                .map(term -> regularizeString(term.word)).toList();
        if (delimiter == null || delimiter.isEmpty()) {
            delimiter = " ";
        }
        return TextUtility.join(words, delimiter);
    }

    public static String regularizeString(String word) {
        if (TextUtility.isAllLetter(word)) {
            char[] charArray = word.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] = CharacterHelper.regularize(charArray[i]);
            }
            word = new String(charArray);
        }
        return word;
    }

    /**
     * 分词类型
     */
    public enum SegmentType {
        /**
         * 索引分词（细粒度）
         */
        INDEX("index"),
        /**
         * 搜索分词（粗粒度）
         */
        SEARCH("search");


        private final String value;

        SegmentType(String value) {
            this.value = value;
        }

    }
}
