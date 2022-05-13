package ru.netology.selenide;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderFormDateTest {

    @BeforeEach
    public void prepareBrowser() {
        open("http://localhost:9999");
    }

    public String dateSetUp(long changeDay) {
        LocalDate today = LocalDate.now();
        today = today.plusDays(changeDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return today.format(formatter);
    }

    @Test                                                           // Дата встечи меньше требуемой на 2 дня
    public void shouldShowMassageIfDateIsTwoDaysLess() {
        String actualDate = dateSetUp(1);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid span.input__sub")
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test                                                           // Дата встечи меньше требуемой на 1 день
    public void shouldShowMassageIfDateIsOneDaysLess() {
        String actualDate = dateSetUp(2);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid span.input__sub")
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test                                                           // Дата встечи введена некорректно
    public void shouldShowMassageIfDateIsInvalid() {
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue("18.05.202");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid span.input__sub")
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Test                                                           // Дата встечи не указана
    public void shouldShowMassageIfDateIsEmpty() {
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid span.input__sub")
                .shouldHave(exactText("Неверно введена дата"));
    }

}