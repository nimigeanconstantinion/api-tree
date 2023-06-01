package com.example.api_tree.system;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class StringSequenceGenerator implements IdentifierGenerator {

    private static final String PREFIX = "source-";
    private static final int INITIAL_VALUE = 1;
    private static final int PADDING_LENGTH = 4;

    private int counter = INITIAL_VALUE;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String sequence = String.format("%s%0" + PADDING_LENGTH + "d", PREFIX, counter++);
        return sequence;
    }
}