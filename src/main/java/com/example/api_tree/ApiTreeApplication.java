package com.example.api_tree;

import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Relatii;
import com.example.api_tree.model.SourceData;
import com.example.api_tree.repository.RelationsRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiTreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTreeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RelationsRepo relationsRepo) {
		return args -> {
			SourceData s1=new SourceData();
			s1.setIdSource(0L);

			s1.setDescriere("Acesta este Root");
			s1.setFel("R");
			s1.setQnt(100.00);
			s1.setLabel("Label ROOT");

			SourceData s2=new SourceData();
			s2.setIdSource(1L);
			s2.setDescriere("Copil 01");
			s2.setFel("C");
			s2.setQnt(7.00);
			s2.setLabel("Label01 de copil");
			Relatii r=new Relatii();
			r.setSource1(s1);
			r.setSource2(s2);
			r.setRelationType("parinte-copil");
			List<CustomField> listaF=new ArrayList<CustomField>();
			CustomField f1=new CustomField();
			f1.setIdOwner(0L);
			f1.setCustomKey("pnume");
			f1.setType("string");
			f1.setValue("Numele ROOT");
			listaF.add(f1);
			CustomField f2=new CustomField();
			f2.setIdOwner(1L);
			f2.setCustomKey("camp_copil");
			f2.setType("string");
			f2.setValue("Campul copilului 2kldskls ");
			listaF.add(f2);
			r.setCustomFieldList(listaF);
			relationsRepo.save(r);

//-----------------------------\
			SourceData s3=new SourceData();
			s3.setIdSource(1L);

			s3.setDescriere("Copil 01");
			s3.setFel("C");
			s3.setQnt(7.00);
			s3.setLabel("Label01 de copil");

			SourceData s4=new SourceData();
			s4.setIdSource(2L);
			s4.setDescriere("Copil 01");
			s4.setFel("C");
			s4.setQnt(7.00);
			s4.setLabel("Label01 de copil");
			Relatii rr=new Relatii();
			rr.setSource1(s3);
			rr.setSource2(s4);
			rr.setRelationType("parinte-copil");

			List<CustomField> listaFF=new ArrayList<CustomField>();

			CustomField f=new CustomField();
			f.setIdOwner(2L);
			f.setCustomKey("camp_copil");
			f.setType("string");
			f.setValue("Campul 1.1");
			listaFF.add(f);
			rr.setCustomFieldList(listaFF);
			relationsRepo.save(rr);



		};
	}
}
