package ru.abramon;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assist {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return obj instanceof String && (((String) obj).trim()).length() == 0;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static List<String> extractIpList(String value) {
        List<String> result = new ArrayList<>();
        if (value != null) {
            Matcher matcher = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})").matcher(value);
            while (matcher.find()) {
                result.add(matcher.group());
            }
        }
        return result;
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) return true;
        return isEmpty(obj);
    }

    public static String dateToString(Date date) {
        return date != null ? formatter.format(date) : null;
    }

    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static <T> boolean isListNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }

    public static String replaceAllByRegex(String st, String regex){
        return st.replaceAll(regex, " ");
    }

    public static String removeNoDigOrLet(String string) {
        return string.replaceAll("[^\\p{L}\\p{N}]+","");
    }

    public static String[] splitStringIntoWords(String string){
        if (string == null) {
            return new String[0];
        }
        if (string.isEmpty()) {
            return new String[0];
        }
        return string.replaceAll("\\p{P}"," ").replaceAll("\\s+(?!euro)", " ").trim().split(" ");
    }

    public static String convertUuidToString(UUID uuid){
        if(uuid!=null){
            return uuid.toString();
        }else {
            return null;
        }
    }

}
