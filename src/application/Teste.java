package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Teste {
    public static void main(String[] args) {

        //String formataData = "20200601";
        //DateTimeFormatter inputDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        YearMonth data = YearMonth.of(2020, 06);

        /*String formataData = "202006";
        DateTimeFormatter inputDate = DateTimeFormatter.ofPattern("yyyyMM");*/

        //LocalDate yearMonth = LocalDate.parse(formataData, inputDate);
        //YearMonth yearMonth = YearMonth.parse(formataData, inputDate);
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("pt", "BR"));

        System.out.println(data.format(outFormat));
        //System.out.println(yearMonth.format(outFormat));

       /* YearMonth yearMonth = YearMonth.parse("yyyyMM", inputDate);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("pt", "BR"));
        System.out.println(yearMonth.format(outputFormat));*/

    }


}
