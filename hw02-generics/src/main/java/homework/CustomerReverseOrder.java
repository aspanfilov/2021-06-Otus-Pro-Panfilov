package homework;


import java.util.*;

public class CustomerReverseOrder {
    Stack<Customer> customers;

    public CustomerReverseOrder() {
        this.customers = new Stack<>();
    }

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        this.customers.push(customer);
    }

    public Customer take() {
        return this.customers.pop();
    }

    public static void main(String[] args) {
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(3, "Petr", 11);
        Customer customer3 = new Customer(2, "Pavel", 888);

        CustomerReverseOrder customerReverseOrder = new CustomerReverseOrder();
        customerReverseOrder.add(customer1);
        customerReverseOrder.add(customer2);
        customerReverseOrder.add(customer3);

        //when
        Customer customerLast = customerReverseOrder.take();
        //then
        assert customerLast.equals(customer3);

        //when
        Customer customerMiddle = customerReverseOrder.take();
        //then
        assert customerMiddle.equals(customer2);

        //when
        Customer customerFirst = customerReverseOrder.take();
        //then
        assert customerFirst.equals(customer1);
    }
}
