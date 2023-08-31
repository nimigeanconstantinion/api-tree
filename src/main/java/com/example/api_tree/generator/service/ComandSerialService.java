package com.example.api_tree.generator.service;

import com.example.api_tree.generator.GenaratorCounterType;
import com.example.api_tree.repository.GeneratorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public interface ComandSerialService {
    String getNextSerial(String current, String type);
}
