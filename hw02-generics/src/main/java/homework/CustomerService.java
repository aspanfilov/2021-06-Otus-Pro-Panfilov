package homework;

import java.util.*;

public class CustomerService {
    private TreeMap<Customer, String> customerData;

    public CustomerService() {
        this.customerData = new TreeMap<>(Comparator.comparingLong(o -> o.getScores()));
    }

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> result = this.customerData.firstEntry();
        return (result == null) ? null :
                new AbstractMap.SimpleImmutableEntry<>(result.getKey().clone(), result.getValue());
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
    }

    public Map.Entry<Customer, String> getNext(Customer customer) throws CloneNotSupportedException {
        Map.Entry<Customer, String> result = this.customerData.higherEntry(customer);
        return (result == null) ? null :
                new AbstractMap.SimpleImmutableEntry<>(result.getKey().clone(), result.getValue());
    }

    public void add(Customer customer, String data) {
        this.customerData.put(customer, data);
    }

}
