package com.example.projeto1_somativa.Models

import javax.inject.Inject

class UserRepository @Inject constructor(var dao : UserDao) {

    fun add(user: User){

        dao.insert(user);

    }

    fun update(user: User){
        
        dao.update(user)

    }

    fun delete(user: User){

        dao.delete(user)

    }

}