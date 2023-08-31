package com.example.api_tree.generator.service;

import com.example.api_tree.generator.GenaratorCounterType;
import com.example.api_tree.repository.GeneratorRepository;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class SerialService  implements ComandSerialService {
     GenaratorCounterType genaratorCounterType;
     GeneratorRepository generatorRepository;
     String crtSerial;
     List<String> serials=new ArrayList<>();
     final List<Character> alphaNum = List.of('0',
             '1','2','3',
             '4','5','6',
             '7','8','9',
             'A',
             'B',
             'C',
             'D',
             'E',
             'F',
             'G',
             'H',
             'J',
             'K',
             'L',
             'M',
             'N',
             'P',
             'Q',
             'R',
             'S',
             'T',
             'U',
             'V',
             'W',
             'X',
             'Y',
             'Z'
);

    public SerialService(String crtSerial,String type){
        this.crtSerial=crtSerial;
        this.genaratorCounterType=GenaratorCounterType.valueOf(type);

    }


    public void setCrtSerial(String crtSerial) {
        this.crtSerial = crtSerial;
    }

    @Override
    public String getNextSerial(String crt,String type) {
        boolean isAlpha = type.equals(GenaratorCounterType.ALPHANUMERIC.name());
        boolean isHex = type.equals(GenaratorCounterType.ALPHANUMERIC.name());
        String next="";

        if (isAlpha == true) {
                int crtindex=alphaToInt(crt);
                next = generateAlphanumericStringFromNumber(crtindex+1);
        }  else if (isHex == true) {
            if (crt.length() > 0) {
                next = generateHexStringFromNumber(Integer.parseInt(crt, 16) + 1);
            } else {
                next = "";
            }
        } else {
            next = String.valueOf(Integer.parseInt(crt) + 1);

        }
        String rezult="";
        for(int i=0;i<crt.length()-next.length();i++){
            rezult+="0";
        }
    return rezult+next;

    }

    public int alphaToInt(String crtCont){
        int crtindex=0;
        int index = 0;
        int iout=-1;
        for(int i=0;i<crtCont.length();i++){
            if(crtCont.charAt(i)=='0'){
                iout=i+1;
                break;
            }
        }
        if (crtCont.length()-iout > 0) {
            for (int i = 0; i < crtCont.length()-iout; i++) {
                index = alphaNum.indexOf(crtCont.charAt(crtCont.length() - (i + 1)));
                if (i > 0) {
                    index = index * (int)Math.pow(alphaNum.size(),i);
                }
                crtindex = crtindex + index;
            }
        }
        return crtindex;
    }

    public String generateAlphanumericStringFromNumber(int value) {
        String serial="";

        int indx=0;
        int n=(int) Math.floor(value/alphaNum.size());
        int k=n;
        int cnt=n;
        while (value>0){
            serial=alphaNum.get(value-(alphaNum.size()*(int) Math.floor(value/alphaNum.size())))+serial;
            value=(int) Math.floor(value/alphaNum.size());

        }
        return serial;
    }

    public String generateHexStringFromNumber(int value) {

        return Integer.toHexString(value).toUpperCase();
    }



    //SI-1234    S1-12345
    // s1-1234 +1->S1->12345
    //PATTERTN->CONTOR->INITAL+1->NEXT-VALUE




}