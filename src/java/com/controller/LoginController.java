/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.query.DataQuery;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author admin
 */
@ManagedBean(name = "login")
public class LoginController {
    private String username;
    private String password;
    private final DataQuery query = new DataQuery();

    public LoginController() {
    }
    
    public String loginString(){
        if(query.loginControl(username, password))
            return "list.xhtml?faces-redirect=true";
        return "newxhtml";
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
