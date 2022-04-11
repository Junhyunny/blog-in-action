package action.in.blog.service;

import java.time.LocalDate;
import java.time.Month;

import static java.time.Month.*;

public class StaticMethodService {

    public int getEventPrice(int price) {
        Month month = LocalDate.now().getMonth();
        if (APRIL.equals(month) || AUGUST.equals(month) || DECEMBER.equals(month)) {
            return (int) (price * 0.8);
        }
        return price;
    }
}
