import ru.atm.Denomination;

import java.util.Map;

public interface MoneyStorageOld {

    long getTotal();

    void put(Denomination denomination, long amount);

    Map<Denomination, Long> getOut(long amount);

}
