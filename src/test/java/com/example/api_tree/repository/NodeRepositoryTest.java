package com.example.api_tree.repository;
import com.example.api_tree.ApiTreeApplication;
import com.example.api_tree.generator.GenaratorCounterType;
import com.example.api_tree.generator.service.ComandSerialService;
import com.example.api_tree.generator.service.SerialService;
import com.example.api_tree.model.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiTreeApplication.class)

class NodeRepositoryTest {
    @Autowired
    NodeRepository nodeRepository;
    @Autowired

    CustomFieldRepository customFieldRepository;

    @Autowired
    ComandSerialService comandSerialService=new SerialService();



//    @Test
//    @Transactional
//    void testAddNod() {
//       Node n=new Node("Root 1");
//       Node lista=nodeRepository.findNodeByLabel("Root 1").get();
//        System.out.println(lista.getId());
//    }
//
//    @Test
//    void testSerial() {
//
//        System.out.println(comandSerialService.getNextSerial("00A","ALPHANUMERIC"));
//
//
//   //    System.out.println(srv.getNextSerial());
////         srv=new SerialService("00A","ALPHANUMERIC");
////        System.out.println(srv.getNextSerial());
//    }

}