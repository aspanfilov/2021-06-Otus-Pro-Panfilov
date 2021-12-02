package ru.otus.crm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ClientResultSetExtractorClass implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        var prevClientId = 0L;
        while (rs.next()) {
            var clientId = rs.getLong("client_id");
            Client client = null;
            if (prevClientId != clientId) {
                client = new Client(clientId,
                        rs.getString("client_name"),
                        null,
                        new HashSet<Phone>());

                var address = getAddress(rs);
                address.ifPresent(client::setAddress);

                clientList.add(client);
                prevClientId = clientId;
            }
            if (client != null) {
                var phone = getPhone(rs);
                phone.ifPresent(client.getPhones()::add);
            }
        }
        return clientList;
    }

    private Optional<Address> getAddress(ResultSet rs) throws SQLException {
        Optional<Address> address = Optional.empty();
        long addressId = rs.getLong("address_id");
        if (addressId != 0) {
            address = Optional.of(new Address(
                    addressId,
                    rs.getString("address_country"),
                    rs.getString("address_city"),
                    rs.getString("address_street"),
                    rs.getInt("address_house_number"),
                    rs.getInt("address_building_number"),
                    rs.getInt("address_apartment_number"),
                    rs.getLong("client_id")));
        }
        return address;
    }

    private Optional<Phone> getPhone(ResultSet rs) throws SQLException {
        Optional<Phone> phone = Optional.empty();
        long phoneId = rs.getLong("phone_id");
        if (phoneId != 0) {
            phone = Optional.of(new Phone(
                    phoneId,
                    rs.getString("phone_number"),
                    rs.getLong("client_id")));
        }
        return phone;
    }
}
