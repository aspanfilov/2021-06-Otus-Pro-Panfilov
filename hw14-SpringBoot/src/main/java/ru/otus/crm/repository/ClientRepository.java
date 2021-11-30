package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    @Query(value = """
            select
                c.id as client_id,
                c.name as client_name,
                a.id as address_id,
                a.country as address_country,
                a.city as address_city,
                a.street as address_street,
                a.house_number as address_house_number,
                a.building_number as address_building_number,
                a.apartment_number as address_apartment_number,
                p.id as phone_id,
                p.number as phone_number
            from client s
                left outer join address a
                on c.id = a.client.id
                left outer join phone p
                on c.id = p.client_id
            order by c.id
            """,
    );

    List<Client> findAll();

}
