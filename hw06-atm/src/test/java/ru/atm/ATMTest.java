package ru.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ATMTest {

    private MoneyStorage moneyStorage;
    private ATM atm;

    @BeforeEach
    void setUp(){
        MoneyStorage moneyStorage = new MoneyStorageImpl();
        this.atm = new ATMImpl(moneyStorage);
    }

    @Test
    void when_ATMisEmpty_expected_balance0() {
        long actualBalance= this.atm.getBalance();
        long expectedBalance = 0;
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void when_put10of50_expected_balance500() {

        this.atm.putBanknotes(Denomination.VAL_50, 10L);
        long actualBalance= this.atm.getBalance();
        long expectedBalance = 500;
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void when_put7of5000And3of2000And10of500And1of50_expected_balance46050() {
        this.atm.putBanknotes(Denomination.VAL_5000, 7L);
        this.atm.putBanknotes(Denomination.VAL_2000, 3L);
        this.atm.putBanknotes(Denomination.VAL_500, 10L);
        this.atm.putBanknotes(Denomination.VAL_50, 1L);
        long actualBalance= this.atm.getBalance();
        long expectedBalance = 46050;
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void when_require11750_expected_get2of5000And2of500And5of100And5of50() {
        this.atm.putBanknotes(Denomination.VAL_5000, 7L);
        this.atm.putBanknotes(Denomination.VAL_2000, 3L);
        this.atm.putBanknotes(Denomination.VAL_500, 2L);
        this.atm.putBanknotes(Denomination.VAL_100, 5L);
        this.atm.putBanknotes(Denomination.VAL_50, 10L);
        var actualBanknotes = this.atm.getBanknotes(11750);

        Map<Denomination, Long> expectedBanknotes = new HashMap<>();
        expectedBanknotes.put(Denomination.VAL_5000, 2L);
        expectedBanknotes.put(Denomination.VAL_500, 2L);
        expectedBanknotes.put(Denomination.VAL_100, 5L);
        expectedBanknotes.put(Denomination.VAL_50, 5L);

        Assertions.assertEquals(expectedBanknotes, actualBanknotes);
    }

    @Test
    void when_getOut11750_expected_balance34300() {
        this.atm.putBanknotes(Denomination.VAL_5000, 7L);
        this.atm.putBanknotes(Denomination.VAL_2000, 3L);
        this.atm.putBanknotes(Denomination.VAL_500, 2L);
        this.atm.putBanknotes(Denomination.VAL_100, 5L);
        this.atm.putBanknotes(Denomination.VAL_50, 10L);
        this.atm.getBanknotes(11750);

        long actualBalance= this.atm.getBalance();
        long expectedBalance = 31250;
        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void when_require11750_expected_AmountIsNotMultipleToTheExistingMinimumDenominationException() {
        this.atm.putBanknotes(Denomination.VAL_5000, 7L);
        this.atm.putBanknotes(Denomination.VAL_2000, 3L);
        this.atm.putBanknotes(Denomination.VAL_500, 2L);
        this.atm.putBanknotes(Denomination.VAL_100, 5L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.atm.getBanknotes(11750));
    }

    @Test
    void when_require11750_expected_InsufficientFundsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.atm.getBanknotes(11750));
    }
}