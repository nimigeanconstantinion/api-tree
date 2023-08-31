package com.example.api_tree.generator.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.api_tree.generator.GenaratorCounterType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Generator")
@Table(name = "generator")
public class Generator {
    @Id
    @SequenceGenerator( name="gen_sequence",
                sequenceName = "gen_sequence",
                allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gen_sequence")
    private Long id;
    private String pattern;
    private String minValue;
    private String maxValue;
    private String nextValue;
    @Enumerated()
    private GenaratorCounterType generationType  ;
    private LocalDateTime generatedOn;



    public Generator(String pattern,String minValue,String maxValue){
        this.pattern=pattern;
        this.minValue=minValue;
        this.maxValue=maxValue;

    }




//    public String getContor(String id){
//        List<String> comp=List.of(this.pattern.split("[/{{|\\}\\}/]"));
//        int startCnt=0;
//        int indx=0;
//        for(int i=0;i<comp.size();i++){
//            if(comp.get(i).contains("#")){
//                    break;
//            }
//            indx++;
//            startCnt+=comp.get(i).length();
//        }
//        return id.substring(startCnt,comp.get(indx).length());
//    }




//    public String getNextID(){
//        String startValue=getContor(this.crtValue);
//        List<String> outv=List.of(crtValue.split(startValue));
//        if(startValue.contains("#")){
//            int startInt=Integer.parseInt(startValue);
//            int next=startInt++;
//            this.nextValue= String.format("%s%0" + startValue.length() +"d%s%", outv.get(0), startInt++,outv.get(1));
//            return this.nextValue;
//        }
//        return "";
//    }

//    ->c1->crearea generatorului initial value si max value useru
//    next->initialvalue
//      ->c2->generare
//

}
// 1234A+1 =>1234B
