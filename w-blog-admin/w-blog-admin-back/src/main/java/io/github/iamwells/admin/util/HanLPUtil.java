package io.github.iamwells.admin.util;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.utility.CharacterHelper;
import com.hankcs.hanlp.utility.TextUtility;

import java.util.List;

public class HanLPUtil {

    public static String segmentForIndex(String text) {
        List<Term> segment = NLPTokenizer.segment(text);
        List<String> words = segment.stream()
                .filter(term -> TextUtility.isAllChinese(term.word) || TextUtility.isAllLetterOrNum(term.word))
                .map(term -> regularizeString(term.word)).toList();
        return TextUtility.join(words, " ");
    }

    public static String segmentForSearch(String text) {
        List<String> words = NLPTokenizer.segment(text).stream()
                .filter(term ->  CoreStopWordDictionary.shouldInclude(term) || TextUtility.isAllLetterOrNum(term.word))
                .map(term ->  regularizeString(term.word) ).toList();
        return TextUtility.join(words, " ");
    }

    public static  String regularizeString(String word) {
        if (TextUtility.isAllLetter(word)) {
            char[] charArray = word.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] = CharacterHelper.regularize(charArray[i]);
            }
            word = new String(charArray);
        }
        return word;
    }
}
