package action.in.blog.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import static java.time.Month.*;

public class StaticMethodService {

    public int getEventPrice(int price) {
        Month month = LocalDate.now().getMonth();
        if (APRIL.equals(month) || AUGUST.equals(month)) {
            return (int) (price * 0.8);
        }
        return price;
    }

    public int getEventPrice(LocalDate birthDate, int price) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if(10 <= age && age < 20) {
            return (int) (price * 0.8);
        }
        return price;
    }
}
