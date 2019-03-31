package ru.otus.outside.models;

import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class TestConstants
{
    public static final String TEST_FIRST_NAME_0 = "test_first_name_0";
    public static final String TEST_LAST_NAME_0 = "test_last_name_0";
    public static final String TEST_FIRST_NAME_1 = "test_first_name_1";
    public static final String TEST_LAST_NAME_1 = "test_last_name_1";
    public static final String TEST_FIRST_NAME_2 = "test_first_name_2";
    public static final String TEST_LAST_NAME_2 = "test_last_name_2";
    public static final String TEST_GENRE_0 = "test_genre_0";
    public static final String TEST_GENRE_1 = "test_genre_1";
    public static final String TEST_GENRE_2 = "test_genre_2";
    public static final String TEST_ISBN_0 = "test_isbn_0";
    public static final String TEST_TITLE_0 = "test_title_0";
    public static final String TEST_COPYRIGHT_0 = "test_copyright_0";
    public static final String TEST_ISBN_1 = "test_isbn_1";
    public static final String TEST_TITLE_1 = "test_title_1";
    public static final String TEST_COPYRIGHT_1 = "test_copyright_1";
    public static final String TEST_ISBN_2 = "test_isbn_2";
    public static final String TEST_TITLE_2 = "test_title_2";
    public static final String TEST_COPYRIGHT_2 = "test_copyright_2";
    public static final String TEST_REVIEW_0 = "test_review_0";
    public static final String TEST_REVIEW_1 = "test_review_1";
    public static final String TEST_REVIEW_2 = "test_review_2";
    public static String TEST = "test";

    public static long TEST_ID = 13L;

    public static BigInteger TEST_BID_2 = new BigInteger("2");

    public static BigInteger TEST_BID_13 = new BigInteger("13");

    public static Long TEST_LID_13 = 13L;

    public static String TEST_SID_13 = "13";

    public static int TEST_NUM_3 = 3;

    public static String TEST_FIRST_NAME_13 = "test_first_name_13";

    public static String TEST_LAST_NAME_13 = "test_last_name_13";

    public static String TEST_ISBN_13 = "test_isbn_name_13";

    public static String TEST_TITLE_13 = "test_title_name_13";

    public static String TEST_COPYRIGHT_13 = "test_copyright_13";

    public static String TEST_GENRE_NAME_13 = "test_genre_13";

    public static String TEST_COMMENT_NAME_13 = "test_comment_13";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8")
    );
}
