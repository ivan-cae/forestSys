package com.example.forestsys;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forestsys.classes.ClasseEncarregados;
import com.example.forestsys.classes.ClasseFazenda;
import com.example.forestsys.classes.ClasseOs;
import com.example.forestsys.classes.ClasseOsInsumos;
import com.example.forestsys.classes.GEO_REGIONAIS;
import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.classes.ClasseUpdate;
import com.example.forestsys.classes.GGF_USUARIOS;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert (GGF_USUARIOS GGFUSUARIOS);
    @Insert
    void insert(ClasseEncarregados classeEncarregados);
    @Insert
    void insert(ClasseFazenda classeFazenda);
    @Insert
    void insert(GEO_SETORES GEOSETORES);
    @Insert
    void insert(GEO_REGIONAIS GEOREGIONAIS);
    @Insert
    void insert(ClasseUpdate classeUpdate);
    @Insert
    void insert(ClasseOsInsumos osInsumos);
    @Insert
    void insert(ClasseOs classeOs);



    @Update
    void update(GGF_USUARIOS GGFUSUARIOS);
    @Update
    void update(ClasseEncarregados classeEncarregados);
    @Update
    void update(ClasseFazenda classeFazenda);
    @Update
    void update(GEO_SETORES GEOSETORES);
    @Update
    void update(GEO_REGIONAIS GEOREGIONAIS);
    @Update
    void update(ClasseUpdate classeUpdate);
    @Update
    void update(ClasseOsInsumos osInsumos);
    @Update
    void update(ClasseOs classeOs);



    @Delete
    void delete(GGF_USUARIOS GGFUSUARIOS);
    @Delete
    void delete(ClasseEncarregados classeEncarregados);
    @Delete
    void delete(ClasseFazenda classeFazenda);
    @Delete
    void delete(GEO_SETORES GEOSETORES);
    @Delete
    void delete(GEO_REGIONAIS GEOREGIONAIS);
    @Delete
    void delete(ClasseUpdate classeUpdate);
    @Delete
    void delete(ClasseOsInsumos classeOsInsumos);
    @Delete
    void delete(ClasseOs classeOs);



    //Scripts GGF_USUARIOS
    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    LiveData<List<GGF_USUARIOS>> todosUsers();

    @Query("SELECT * FROM GGF_USUARIOS WHERE ID_USUARIO=:taskId")
    LiveData<GGF_USUARIOS> selecionaUser(int taskId);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin AND senha=:taskSenha")
    GGF_USUARIOS valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin")
    GGF_USUARIOS validaLogin(String taskLogin);


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


    //Scripts GEO_SETORES
    @Query("SELECT * FROM GEO_SETORES ORDER BY ID_SETOR asc")
    LiveData<List<GEO_SETORES>> todosSetores();

    @Query("SELECT * FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    GEO_SETORES selecionaSetor(int taskId);


    //Scripts GEO_REGIONAIS
    @Query("SELECT * FROM GEO_REGIONAIS ORDER BY ID_REGIONAL asc")
    LiveData<List<GEO_REGIONAIS>> todosRegionais();

    @Query("SELECT * FROM GEO_REGIONAIS WHERE ID_REGIONAL=:taskId")
    GEO_REGIONAIS selecionaRegional(int taskId);


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

    @Query("SELECT * FROM ClasseOs ORDER BY id asc")
    List<ClasseOs> selecionaListaOs();
}
