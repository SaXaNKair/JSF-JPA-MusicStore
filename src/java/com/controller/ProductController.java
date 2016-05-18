/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.entity.Product;
import com.query.DataQuery;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
/**
 *
 * @author admin
 */
@ManagedBean(name="product")
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;    
    private Product product;
    private List<Product> products;
    private transient DataQuery query = new DataQuery();
    private String message = "";
    private String messagePrice = "";

    public String getMessage() {
        return message;
    }

    public String getMessagePrice() {
        return messagePrice;
    }
    
    boolean dataVisible = false;
    
    public ProductController() {}
    
    private void readObject(java.io.ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        this.query = new DataQuery();
    }
    
    @PostConstruct
    public void init(){
        String find = findAll();
    }
    
    public String findAll(){
        products = query.findAll();
        dataVisible = !products.isEmpty();
        return "list";
    }
    
    public String add(){
        product = new Product();
        message = "";
        return "util/add?faces-redirect=true";
    }
    
    //ajax method to check if the code of the new product already exists
    public void checkCode(){
        boolean check = false;
        for(Product p : products){
            if(p.getProductCode().equals(product.getProductCode())){
                message =  " Product with this code already exists in the database!";
                check = true;
            }
        }
        if(!check)
            message = "";
    }
    
    //ajax method to check if the code of the new product already exists
    public void checkPrice(AjaxBehaviorEvent e){
        boolean fail = false;
        try{
            Integer.parseInt(product.getProductPrice());
        }catch(Exception ex){
            messagePrice = " Price must be an integer value (in cents)";
            fail = true;
        }
        if(!fail)
            messagePrice = "";
    }
    
    public String insertProduct(){
        if(!message.equals("") || !messagePrice.equals(""))
            return "add";
        query.insert(product);
        return findAll();
    }
    
    public String editProduct(){
        query.edit(product);
        return findAll();
    }
    
    public String deleteProduct(){
        query.delete(product);
        products.remove(product);
        return findAll();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
