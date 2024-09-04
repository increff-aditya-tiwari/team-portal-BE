package com.increff.teamer.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertUtil {
    @Autowired
    private ModelMapper modelMapper;

    public <D, T> D convertPojoToData(T pojo, Class<D> dataClass) {
        return modelMapper.map(pojo, dataClass);
    }

    public <D, T> T convertDataToPojo(D data, Class<T> pojoClass) {
        return modelMapper.map(data, pojoClass);
    }
}
