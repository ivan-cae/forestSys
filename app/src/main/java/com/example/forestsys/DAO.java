package com.example.forestsys;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert (ClasseUsers classeUsers);
    @Insert
    void insert(ClasseEncarregados classeEncarregados);
    @Insert
    void insert(ClasseFazenda classeFazenda);
    @Insert
    void insert(ClasseSetor classeSetor);
    @Insert
    void insert(ClasseRegional classeRegional);
    @Insert
    void insert(ClasseUpdate classeUpdate);
    @Insert
    void insert(ClasseOsInsumos osInsumos);
    @Insert
    void insert(ClasseOs classeOs);


    @Update
    void update(ClasseUsers classeUsers);
    @Update
    void update(ClasseEncarregados classeEncarregados);
    @Update
    void update(ClasseFazenda classeFazenda);
    @Update
    void update(ClasseSetor classeSetor);
    @Update
    void update(ClasseRegional classeRegional);
    @Update
    void update(ClasseUpdate classeUpdate);
    @Update
    void update(ClasseOsInsumos osInsumos);
    @Update
    void update(ClasseOs classeOs);


    @Delete
    void delete(ClasseUsers classeUsers);
    @Delete
    void delete(ClasseEncarregados classeEncarregados);
    @Delete
    void delete(ClasseFazenda classeFazenda);
    @Delete
    void delete(ClasseSetor classeSetor);
    @Delete
    void delete(ClasseRegional classeRegional);
    @Delete
    void delete(ClasseUpdate classeUpdate);
    @Delete
    void delete(ClasseOsInsumos classeOsInsumos);
    @Delete
    void delete(ClasseOs classeOs);

    //Scripts ClasseUsers
    @Query("SELECT * FROM ClasseUsers ORDER BY id asc")
    LiveData<List<ClasseUsers>> todosUsers();

    @Query("SELECT * FROM ClasseUsers WHERE id=:taskId")
    LiveData<ClasseUsers> selecionaUser(int taskId);

    @Query("SELECT * FROM ClasseUsers WHERE login=:taskLogin AND senha=:taskSenha")
    ClasseUsers valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM ClasseUsers WHERE login=:taskLogin AND senha=:taskSenha AND nivelAcesso=1")
    ClasseUsers validaAdmin(String taskLogin, String taskSenha);

    @Query("SELECT * FROM ClasseUsers WHERE login=:taskLogin")
    ClasseUsers validaLogin(String taskLogin);

    @Query("SELECT * FROM ClasseUsers WHERE matricula=:taskMatricula")
    ClasseUsers validaMatricula(String taskMatricula);


    //Scripts ClasseEncarregados
    @Query("SELECT * FROM ClasseEncarregados ORDER BY id asc")
    LiveData<List<ClasseEncarregados>> todosEncarregados();

    @Query("SELECT * FROM ClasseEncarregados WHERE id=:taskId")
    LiveData<ClasseEncarregados> selecionaEncarregado(int taskId);


    //Scripts ClasseFazenda
    @Query("SELECT * FROM ClasseFazenda ORDER BY id asc")
    LiveData<List<ClasseFazenda>> todasFazendas();

    @Query("SELECT * FROM ClasseFazenda WHERE id=:taskId")
    LiveData<ClasseFazenda> selecionaFazenda(int taskId);


    //Scripts ClasseSetor
    @Query("SELECT * FROM ClasseSetor ORDER BY id asc")
    LiveData<List<ClasseSetor>> todosSetores();

    @Query("SELECT * FROM ClasseSetor WHERE id=:taskId")
    LiveData<ClasseSetor> selecionaSetor(int taskId);


    //Scripts ClasseRegional
    @Query("SELECT * FROM ClasseRegional ORDER BY id asc")
    LiveData<List<ClasseRegional>> todosRegionais();

    @Query("SELECT * FROM ClasseRegional WHERE id=:taskId")
    LiveData<ClasseRegional> selecionaRegional(int taskId);


    //Scripts ClasseUpdate
    @Query("SELECT * FROM ClasseUpdate ORDER BY id asc")
    LiveData<List<ClasseUpdate>> todosUpdates();

    @Query("SELECT * FROM ClasseUpdate WHERE id=:taskId")
    LiveData<ClasseUpdate> selecionaUpdate(int taskId);

    @Query("SELECT * FROM ClasseUpdate WHERE id_os=:osId")
    LiveData<List<ClasseUpdate>> selecionaOsUpdate(int osId);


    //Scripts ClasseUpdate
    @Query("SELECT * FROM ClasseOsInsumos ORDER BY id asc")
    LiveData<List<ClasseOsInsumos>> todosInsumos();

    @Query("SELECT * FROM ClasseOsInsumos WHERE id=:taskId")
    LiveData<ClasseOsInsumos> selecionaInsumo(int taskId);


    //Scripts ClasseOs
    @Query("SELECT * FROM ClasseOs ORDER BY id asc")
    LiveData<List<ClasseOs>> todasOs();

    @Query("SELECT * FROM ClasseOs WHERE id=:taskId")
    LiveData<ClasseOs> selecionaOs(int taskId);
}
