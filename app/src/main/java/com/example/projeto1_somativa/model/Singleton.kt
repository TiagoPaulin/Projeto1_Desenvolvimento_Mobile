package com.example.projeto1_somativa.model

import android.content.Context

object Singleton {


    private lateinit var dao: UserDao

    fun setContext(context: Context){

        UserDatabase.getInstance(context)?.apply {
            dao = userDao()

        }

    }

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