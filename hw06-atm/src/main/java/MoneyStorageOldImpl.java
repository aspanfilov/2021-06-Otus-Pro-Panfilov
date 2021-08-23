import ru.atm.Denomination;

import java.util.*;

public class MoneyStorageOldImpl implements MoneyStorageOld {
    Map<Denomination, Long> denominationValues = new HashMap<>();
    SortedMap<Denomination, Long> moneyStorage = new TreeMap<>();
    long total = 0;

    public MoneyStorageOldImpl() {
        for (Denomination denomination : Denomination.values()) {
            this.denominationValues.put(denomination, Long.parseLong(denomination.name().substring(4)));
            this.moneyStorage.put(denomination, 0L);
        }
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public void put(Denomination denomination, long count) {
        if (count <= 0L) throw new IllegalArgumentException("attempt to put an amount equal to zero or less");

        Long currentCount = Objects.requireNonNullElse(this.moneyStorage.get(denomination), 0L);
        this.moneyStorage.put(denomination, currentCount + count);

        this.total = this.total + count * this.denominationValues.get(denomination);
    }

    @Override
    public Map<Denomination, Long> getOut(long amount) {
        var banknotesToGet = calculateBanknotes(amount);
        withdrawBanknotes(banknotesToGet);

        return banknotesToGet;
    }

    private Map<Denomination, Long> calculateBanknotes(long amount) {
        if (amount > this.total) throw new IllegalArgumentException("insufficient funds");

        SortedMap<Denomination, Long> banknotes = new TreeMap<>();

        for (Map.Entry denominationCell : this.moneyStorage.entrySet()) {
            Denomination denomination = (Denomination) denominationCell.getKey();
            long denominationValue = this.denominationValues.get(denomination);
            if (denominationValue <= amount) {
                Long banknoteCount = Math.min(amount / denominationValue, (Long) denominationCell.getValue());
                if (banknoteCount == 0L) continue;
                banknotes.put(denomination, banknoteCount);
                amount = amount - (banknoteCount * denominationValue);
            }
        }

        if (amount != 0) {
            throw new IllegalArgumentException("amount is not multiple to the existing minimum denomination");
        }

        return banknotes;
    }

    private void withdrawBanknotes(Map<Denomination, Long> banknotesToGet) {
        long amount = 0L;

        for (Map.Entry banknoteValue : banknotesToGet.entrySet()) {
            Denomination denomination = (Denomination) banknoteValue.getKey();
            this.moneyStorage.put(denomination, this.moneyStorage.get(denomination) - (long) banknoteValue.getValue());
            amount += this.denominationValues.get(denomination) * (long) banknoteValue.getValue();
        }

        this.total -= amount;
    }

}
