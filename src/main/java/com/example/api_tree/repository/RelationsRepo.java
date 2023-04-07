package com.example.api_tree.repository;

import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.DTOSourceData;
import com.example.api_tree.model.Relatii;
import com.example.api_tree.model.SourceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationsRepo extends JpaRepository<Relatii,Long> {

    @Query(value = "select a.source1 from Relatii a where a.source1.idSource=?1")
    Optional<SourceData> getParinteByID(Long idS);

    @Query(value = "select a.source2 from Relatii a where a.source2.idSource=?1")
    Optional<SourceData> getChildByID(Long idS);



    @Query(value = "select a from CustomField a where a.idOwner=?1")
    List<CustomField> getCustomFieldyID(Long idS);

    @Query(value = "select a.source1 from Relatii a where a.source2.idSource=?1")
    Optional<SourceData> getParentOfChildByID(Long idC);

    @Query(value = "select a.source2 from Relatii a where a.source1.idSource=?1")
    List<SourceData> getAllChildrenByIDParinte(Long idP);




}
