package com.caglayan.ems.service.impl;

import com.caglayan.ems.model.Address;
import com.caglayan.ems.model.dto.AddressDto;
import com.caglayan.ems.repository.AddressRepository;
import com.caglayan.ems.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address saveAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .title(addressDto.getTitle())
                .location(addressDto.getLocation())
                .build();

        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        Optional<Address> oldAddress = addressRepository.findById(address.getId());

        if (oldAddress.isPresent())
            return addressRepository.save(address);
        else
            throw new NullPointerException("Address Not Found");
    }

    public void deleteAddress(long id) {
        addressRepository.findById(id).orElseThrow(
                () -> new NullPointerException("This id doesn't belong the any address!")
        );

        addressRepository.deleteById(id);
    }

    public Address getById(long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new NullPointerException("This id doesn't belong the any address!")
        );
    }
}
