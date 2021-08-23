package ru.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class MoneyStorageImplTest {

    private MoneyStorageOldImpl moneyStorage;

    @BeforeEach
    void setUp(){
        moneyStorage = new MoneyStorageOldImpl();
    }

    @Test
    void when_MoneyStorageEmpty_expected_total0() {
        long actualTotal= moneyStorage.getTotal();
        long expectedTotal = 0;
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void when_put10of50_expected_total500() {
        moneyStorage.put(Denomination.VAL_50, 10);
        long actualTotal= moneyStorage.getTotal();
        long expectedTotal = 500;
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void when_put7of5000And3of2000And10of500And1of50_expected_total46050() {
        moneyStorage.put(Denomination.VAL_5000, 7);
        moneyStorage.put(Denomination.VAL_2000, 3);
        moneyStorage.put(Denomination.VAL_500, 10);
        moneyStorage.put(Denomination.VAL_50, 1);
        long actualTotal= moneyStorage.getTotal();
        long expectedTotal = 46050;
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void when_require11750_expected_get2of5000And2of500And5of100And5of50() {
        moneyStorage.put(Denomination.VAL_5000, 7);
        moneyStorage.put(Denomination.VAL_2000, 3);
        moneyStorage.put(Denomination.VAL_500, 2);
        moneyStorage.put(Denomination.VAL_100, 5);
        moneyStorage.put(Denomination.VAL_50, 10);
        var actualBanknotes = moneyStorage.getOut(11750);

        Map<Denomination, Long> expectedBanknotes = new HashMap<>();
        expectedBanknotes.put(Denomination.VAL_5000, 2L);
        expectedBanknotes.put(Denomination.VAL_500, 2L);
        expectedBanknotes.put(Denomination.VAL_100, 5L);
        expectedBanknotes.put(Denomination.VAL_50, 5L);

        Assertions.assertEquals(expectedBanknotes, actualBanknotes);
    }

    @Test
    void when_getOut11750_expected_total34300() {
        moneyStorage.put(Denomination.VAL_5000, 7);
        moneyStorage.put(Denomination.VAL_2000, 3);
        moneyStorage.put(Denomination.VAL_500, 2);
        moneyStorage.put(Denomination.VAL_100, 5);
        moneyStorage.put(Denomination.VAL_50, 10);
        moneyStorage.getOut(11750);

        long actualTotal= moneyStorage.getTotal();
        long expectedTotal = 31250;
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void when_require11750_expected_AmountIsNotMultipleToTheExistingMinimumDenominationException() {
        moneyStorage.put(Denomination.VAL_5000, 7);
        moneyStorage.put(Denomination.VAL_2000, 3);
        moneyStorage.put(Denomination.VAL_500, 2);
        moneyStorage.put(Denomination.VAL_100, 5);
        Assertions.assertThrows(IllegalArgumentException.class, () -> moneyStorage.getOut(11750));
    }

    @Test
    void when_require11750_expected_InsufficientFundsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> moneyStorage.getOut(11750));
    }
}