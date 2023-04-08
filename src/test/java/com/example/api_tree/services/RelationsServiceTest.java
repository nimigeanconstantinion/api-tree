package com.example.api_tree.services;

import com.example.api_tree.ApiTreeApplication;
import com.example.api_tree.repository.RelationsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ApiTreeApplication.class)

class RelationsServiceTest {
    @Mock
    private RelationsRepo relationsRepo;



    @Test
    void getAllChildrenOf() {

        System.out.println(relationsRepo.getChildByID(0L));

    }
}