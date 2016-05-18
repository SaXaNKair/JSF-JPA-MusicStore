/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import com.entity.Product;
import com.entity.User;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */

public class DataQuery {
    EntityManagerFactory emf;
    EntityManager em;

    public DataQuery() {
        emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        em = emf.createEntityManager();
    }
    
    public boolean loginControl(String username, String password){
        try {
            em.getTransaction().begin();
            User u = em.createNamedQuery("User.control", User.class).
                    setParameter("username", username).
                    setParameter("password", password).getSingleResult();
            em.close();
            if(u != null) {
                HttpSession sess = (HttpSession) 
                        FacesContext.getCurrentInstance().getExternalContext().
                                getSession(false);
                sess.setAttribute("username", u.getUsername());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Product> findAll(){
        if(em.getTransaction() == null)
            em.getTransaction().begin();
        List<Product> products = 
                em.createNamedQuery("Product.findAll", Product.class).
                        getResultList();
        
        return products;
    }
    
    public void insert(Product p){
        em.getTransaction().begin();
        if(em.find(Product.class, p.getProductCode()) == null)
            em.persist(p);
        em.getTransaction().commit();
    }
    
    public void edit(Product p){
        em.getTransaction().begin();
        Product product = em.find(Product.class, p.getProductCode());
        product.setProductDescription(p.getProductDescription());
        product.setProductPrice(p.getProductPrice());
        em.getTransaction().commit();
    }
    
    public void delete(Product p){
        em.getTransaction().begin();
        Product product = em.find(Product.class, p.getProductCode());
        em.remove(product);
        em.getTransaction().commit();
    }
}
