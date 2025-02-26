package com.register.controller;

import com.register.model.FormModel;
import com.register.services.FormServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/form")
public class FormController {
    @Autowired
    private FormServices formServices;
    //create form
    @PostMapping
    public FormModel createModel(@RequestBody FormModel formModel){
        return formServices.add(formModel);
    }
    //get form
    @GetMapping
    public List<FormModel> getallform(){
        return formServices.getList();
    }
    //delete form

}
