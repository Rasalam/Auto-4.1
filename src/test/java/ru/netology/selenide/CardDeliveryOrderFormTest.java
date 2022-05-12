package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderFormTest {

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

    @Test                                               // Успешная отправка формы
    public void shouldReturnSuccess() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Встреча успешно забронирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test                                               // Поле Город заполнено некорректно
    public void shouldShowMassageIfCityIsInvalid() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Малые Васюки");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test                                               // Поле Город не заполнено
    public void shouldShowMassageIfCityIsEmpty() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test                                               // Поле Имя заполнено не корректно
    public void shouldShowMassageIfNameIsInvalid() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("No Name");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave
                (exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test                                               // Поле Имя не заполнено
    public void shouldShowMassageIfNameIsEmpty() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test                                               // Поле Телефон заполнено не корректно
    public void shouldShowMassageIfPhoneIsInvalid() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+7917330443");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test                                               // Поле Телефон не заполнено
    public void shouldShowMassageIfPhoneIsEmpty() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test                                               // Согласие с условиями не получено
    public void shouldShowMassageIfCheckBoxIsNotCheck() {
        String actualDate = dateSetUp(3);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys("\uE009" + "\uE011", "\uE017");
        $("[data-test-id=date] input").setValue(actualDate);
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+79173304434");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}

