package com.project.market.service.candle;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TransDateToString {
    // 받은 여러 형식의 날짜 값을 업비트 API 에서 필요로 하는 값으로 변환
    // ( yyyy-MM-dd , yyyy-MM-dd HH:mm , yyyy년 M월 d일 HH시 mm분, yyyy년M월d일, yyyyMMdd 다섯 가지 가능)
    private static final String[] filterWordArray = {"-", ":", "년", "월", "일", "시", "분", "초"};
    private static final String[] formalDateForm = {"-", "-", "T", "%3A", "%3A"};

    public String transDate (String date) {
        StringBuilder sb = new StringBuilder();
        String[] dateArray = new String[6]; // 년 월 일 시 분 초
        int index = 0;

        loop:
        for (int i = 0; i < date.length(); i++) {
            char word = date.charAt(i);
            if (word == ' ') { // 띄어쓰기일 경우 무시
                continue;
            }

            if(Arrays.stream(filterWordArray).anyMatch(filterWord -> filterWord.indexOf(word) != -1)) {
                dateArray[index] = sb.toString();
                sb.setLength(0);
                index++;
            } else {
                sb.append(word);
            }

            if (i == date.length() - 1) {
                dateArray[index] = sb.toString();
                sb.setLength(0);
                index++;
            }

            if (index >= 6) { // OutOfLength 방지
                break loop;
            }
        }

        for (int j = 0; j < dateArray.length; j++) {
            if (dateArray[j] != null) {
                sb.append(dateArray[j]);
            } else { // 시 부터는 null 일 수 있음
                sb.append("00");
            }
            if (j < dateArray.length - 1) {
                sb.append(formalDateForm[j]);
            }
        }

        sb.append("%2B09%3A00"); // KST 시간 규정 +09:00
        return sb.toString();
    }
}
