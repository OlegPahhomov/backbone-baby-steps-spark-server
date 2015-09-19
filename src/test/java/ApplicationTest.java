import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationTest {


    @Test
    /**
     * Yeah, this is crap.
     */
    public void quickTestingdates(){
        /*long num = 1_209_589_200_000L;
        LocalDate localDate = LocalDate.ofEpochDay(num);*/
        String str = "2008-04-30T21:00:00.000Z";
        LocalDateTime parse1 = LocalDateTime.parse(str, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println(parse1);
        String substring = str.substring(0, 10);
        LocalDate parse = LocalDate.parse(substring);
        System.out.println(parse);
    }
}