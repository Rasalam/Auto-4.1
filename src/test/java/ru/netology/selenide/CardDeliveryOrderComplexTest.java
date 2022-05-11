package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderComplexTest {

    public String dateSetUp(long changeDay) {
        LocalDate today = LocalDate.now();
        today = today.plusDays(changeDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return today.format(formatter);
    }

    @BeforeEach
    public void prepareBrowser() {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvSource(value = {"Новосибирск", "Петропавловск-Камчатский", "Симферополь", "Ханты-Мансийск", "Южно-Сахалинск"})
    public void shouldReturnSuccessWhenCityIsPresent(String city) {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").sendKeys("Си");
        $(withText(city)).click();
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Встреча успешно забронирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldPopupHiddenWhenListOfCityIsEmpty() {
        $("[data-test-id=city] input").sendKeys("Sa");
        $(".popup__container").shouldHave(hidden);
    }
 }