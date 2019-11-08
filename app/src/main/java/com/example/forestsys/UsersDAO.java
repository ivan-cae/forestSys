package com.example.forestsys;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UsersDAO {

    @Insert
    void insert (Users users);

    @Update
    void update(Users users);

    @Delete
    void delete(Users users);


    @Query("SELECT * FROM Users ORDER BY id asc")
    LiveData<List<Users>> todosUsers();

    @Query("SELECT * FROM Users WHERE id=:taskId")
    LiveData<Users> selecionaTodos(int taskId);

    @Query("SELECT * FROM Users WHERE login=:taskLogin AND senha=:taskSenha")
    Users valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM Users WHERE login=:taskLogin AND senha=:taskSenha AND nivelAcesso=1")
    Users validaAdmin(String taskLogin, String taskSenha);

    @Query("SELECT * FROM Users WHERE login=:taskLogin")
    Users validaLogin(String taskLogin);

    @Query("SELECT * FROM Users WHERE matricula=:taskMatricula")
    Users validaMatricula(String taskMatricula);
}
