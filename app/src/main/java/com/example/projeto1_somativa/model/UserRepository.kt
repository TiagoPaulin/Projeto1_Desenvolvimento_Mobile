package com.example.projeto1_somativa.model

import javax.inject.Inject

class UserRepository @Inject constructor(var dao : UserDao) {

    fun add(user: User){

        dao.insert(user);

    }

    fun request(username : String) : User {

        return dao.getByUsername(username)

    }

    fun update(user: User){
        
        dao.update(user)

    }

    fun delete(user: User){

        dao.delete(user)

    }

}