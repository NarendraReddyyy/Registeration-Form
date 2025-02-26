package com.register.services;

import com.register.model.FormModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormServices {
    private List<FormModel> list = new ArrayList<>();

    public FormServices() {
        list.add(FormModel.builder()
                        .abc("hello")
                .build());
    }
    //create

    public FormModel add( FormModel formModel){
        list.add(formModel);
        return formModel;
    }

    //get all


    public List<FormModel> getList() {
        return list;
    }
}
