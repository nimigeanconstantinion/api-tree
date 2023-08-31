package com.example.api_tree;

import com.example.api_tree.generator.service.SerialService;
import com.example.api_tree.model.Node;
import com.example.api_tree.repository.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication

public class ApiTreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTreeApplication.class, args);
	}
	@Bean
	public SerialService myBean(){
		return new SerialService();
	}


//
//	@Bean
//	@Transactional
//	CommandLineRunner  commandLineRunner(NodeRepository nodeRepository){return  args -> {
////		Node parentNode = new Node();
////		parentNode.setLabel("Parent Node");
////		parentNode.setDescriere("Description of the parent node");
////		parentNode.setCfields(new ArrayList<>());
//
////		List<Node> subordinates = new ArrayList<>();
////
////		for (int i = 1; i <= 3; i++) {
////			Node subordinateNode = new Node();
////			subordinateNode.setLabel("Subordinate Node " + i);
////			subordinateNode.setDescriere("Description of subordinate node " + i);
////			subordinateNode.setCfields(new ArrayList<>());
////
////			subordinates.add(subordinateNode);
////		}
////
////		parentNode.setSubordinates(subordinates);
////
////		nodeRepository.save(parentNode);
//		Optional<Node> parinte=nodeRepository.findById("source-0001");
//
//		if(parinte.isPresent()){
//			Node node = parinte.get();
//			Node copil=new Node();
//			copil.setLabel("copil 1");
//			copil.setDescriere("mdmd;");
//			node.addSubordonate(copil);
//			nodeRepository.save(node);
//
//		}
//
//	};
//
//	}
}
