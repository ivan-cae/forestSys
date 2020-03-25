package com.example.forestsys;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.forestsys.classes.CADASTRO_FLORESTAL;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.ESPACAMENTOS;
import com.example.forestsys.classes.GEO_REGIONAIS;
import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.classes.ClasseUpdate;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MANEJO;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.MATERIAL_GENETICO;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert (GGF_USUARIOS GGFUSUARIOS);
    @Insert
    void insert(GEO_SETORES GEOSETORES);
    @Insert
    void insert(GEO_REGIONAIS GEOREGIONAIS);
    @Insert
    void insert(ClasseUpdate classeUpdate);
    @Insert
    void insert(ESPACAMENTOS espacamentos);
    @Insert
    void insert(MANEJO manejo);
    @Insert
    void insert(MATERIAL_GENETICO material_genetico);
    @Insert
    void insert(O_S_ATIVIDADES o_s_atividades);
    @Insert
    void insert(CADASTRO_FLORESTAL cadastro_florestal);
    @Insert
    void insert(MAQUINAS maquinas);
    @Insert
    void insert(IMPLEMENTOS implementos);
    @Insert
    void insert(CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem);
    @Insert
    void insert(PRESTADORES prestadores);
    @Insert
    void insert(MAQUINA_IMPLEMENTO maquinaImplemento);
    @Insert
    void insert(OPERADORES operadores);
    @Insert
    void insert(O_S_ATIVIDADES_DIA oSAtividadesDia);


    @Update
    void update(GGF_USUARIOS GGFUSUARIOS);
    @Update
    void update(GEO_SETORES GEOSETORES);
    @Update
    void update(GEO_REGIONAIS GEOREGIONAIS);
    @Update
    void update(ClasseUpdate classeUpdate);
    @Update
    void update(O_S_ATIVIDADES o_s_atividades);
    @Update
    void update(CADASTRO_FLORESTAL cadastro_florestal);
    @Update
    void update(MAQUINAS maquinas);
    @Update
    void update(IMPLEMENTOS implementos);
    @Update
    void update(CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem);
    @Update
    void update(PRESTADORES prestadores);
    @Update
    void update(MAQUINA_IMPLEMENTO maquinaImplemento);
    @Update
    void update(OPERADORES operadores);
    @Update
    void update(O_S_ATIVIDADES_DIA oSAtividadesDia);


    @Delete
    void delete(GGF_USUARIOS GGFUSUARIOS);
    @Delete
    void delete(GEO_SETORES GEOSETORES);
    @Delete
    void delete(GEO_REGIONAIS GEOREGIONAIS);
    @Delete
    void delete(ClasseUpdate classeUpdate);
    @Delete
    void delete(O_S_ATIVIDADES o_s_atividades);
    @Delete
    void delete(CADASTRO_FLORESTAL cadastro_florestal);
    @Delete
    void delete(MAQUINAS maquinas);
    @Delete
    void delete(IMPLEMENTOS implementos);
    @Delete
    void delete(CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem);
    @Delete
    void delete(PRESTADORES prestadores);
    @Delete
    void delete(MAQUINA_IMPLEMENTO maquinaImplemento);
    @Delete
    void delete(OPERADORES operadores);
    @Delete
    void delete(O_S_ATIVIDADES_DIA oSAtividadesDia);

    //Scripts GGF_USUARIOS
    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    LiveData<List<GGF_USUARIOS>> todosUsers();

    @Query("SELECT * FROM GGF_USUARIOS WHERE ID_USUARIO=:taskId")
    LiveData<GGF_USUARIOS> selecionaUser(int taskId);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin AND senha=:taskSenha")
    LiveData<GGF_USUARIOS> valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin")
    LiveData<GGF_USUARIOS> validaLogin(String taskLogin);

    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    List<GGF_USUARIOS> listaUsuarios();



    //Scripts GEO_SETORES
    @Query("SELECT * FROM GEO_SETORES ORDER BY ID_SETOR asc")
    LiveData<List<GEO_SETORES>> todosSetores();

    @Query("SELECT * FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    LiveData<GEO_SETORES> selecionaSetor(int taskId);


    //Scripts GEO_REGIONAIS
    @Query("SELECT * FROM GEO_REGIONAIS ORDER BY ID_REGIONAL asc")
    LiveData<List<GEO_REGIONAIS>> todosRegionais();

    @Query("SELECT * FROM GEO_REGIONAIS WHERE ID_REGIONAL=:taskId")
    LiveData <GEO_REGIONAIS> selecionaRegional(int taskId);


    //Scripts ClasseUpdate
    @Query("SELECT * FROM ClasseUpdate ORDER BY id asc")
    LiveData<List<ClasseUpdate>> todosUpdates();

    @Query("SELECT * FROM ClasseUpdate WHERE id=:taskId")
    LiveData<ClasseUpdate> selecionaUpdate(int taskId);

    @Query("SELECT * FROM ClasseUpdate WHERE id_os=:osId")
    LiveData<List<ClasseUpdate>> selecionaOsUpdate(int osId);


    //Scripts ClasseOs
    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY ID_PROGRAMACAO_ATIVIDADE asc")
    LiveData<List<O_S_ATIVIDADES>> todasOs();

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE ID_PROGRAMACAO_ATIVIDADE=:taskId")
    O_S_ATIVIDADES selecionaOs(int taskId);

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY ID_PROGRAMACAO_ATIVIDADE asc")
    LiveData<List<O_S_ATIVIDADES>> selecionaListaOs();


    //Scripts CADASTRO_FLORESTAL
    @Query("SELECT * FROM CADASTRO_FLORESTAL WHERE ID_REGIONAL =:id AND ID_SETOR=:id AND ID_ESPACAMENTO=:id" +
            " AND ID_MANEJO=:id AND ID_MATERIAL_GENETICO=:id")
    LiveData<CADASTRO_FLORESTAL> selecionaCadFlorestal(int id);

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    List<CADASTRO_FLORESTAL> listaCadFlorestal();

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    LiveData<List<CADASTRO_FLORESTAL>> todosCadFlorestal();


    //Scripts IMPLEMENTOS
    @Query("SELECT * FROM IMPLEMENTOS WHERE ID_IMPLEMENTO=:id")
    LiveData<IMPLEMENTOS> selecionaImplemento(int id);

    @Query("SELECT * FROM IMPLEMENTOS")
    List<IMPLEMENTOS> listaImplementos();

    @Query("SELECT * FROM IMPLEMENTOS")
    LiveData<List<IMPLEMENTOS>> todosImplementos();


    //Scripts MAQUINAS
    @Query("SELECT * FROM MAQUINAS WHERE ID_MAQUINA=:id")
    LiveData<MAQUINAS> selecionaMaquina(int id);

    @Query("SELECT * FROM MAQUINAS")
    List<MAQUINAS> listaMaquinas();

    @Query("SELECT * FROM MAQUINAS")
    LiveData<List<MAQUINAS>> todasMaquinas();


    //Scripts PRESTADORES
    @Query("SELECT * FROM PRESTADORES WHERE ID_PRESTADOR=:id")
    LiveData<PRESTADORES> selecionaPrestador(int id);

    @Query("SELECT * FROM PRESTADORES")
    List<PRESTADORES> listaPrestadores();

    @Query("SELECT * FROM PRESTADORES")
    LiveData<List<PRESTADORES>> todosPrestadores();


    //Scripts CALIBRAGEM_SUBSOLAGEM
    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND " +
            "TURNO=:turno AND ID_MAQUINA_IMPLEMENTO=:idMaqImp")
    LiveData<CALIBRAGEM_SUBSOLAGEM> selecionaCalibragem(int idProg, String data, String turno, int idMaqImp);

    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND " +
            "TURNO=:turno")
            CALIBRAGEM_SUBSOLAGEM checaCalibragem(int idProg, String data, String turno);


    //Scripts MAQUINA_IMPLEMENTO
    @Query("SELECT * FROM MAQUINA_IMPLEMENTO")
    LiveData<List<MAQUINA_IMPLEMENTO>> todosMaquinaImplementos();

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO WHERE ID_MAQUINA_IMPLEMENTO=:id")
    LiveData<MAQUINA_IMPLEMENTO>selecionaMaquinaImplemento(int id);


    //Scripts OPERADORES
    @Query("SELECT * FROM OPERADORES")
    List<OPERADORES> todosOperadores();

    @Query("SELECT * FROM OPERADORES WHERE ID_OPERADORES=:id")
    LiveData<OPERADORES>selecionaOperador(int id);


    //Scripts O_S_ATIVIDADES_DIA
    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data")
    O_S_ATIVIDADES_DIA selecionaOsAtividadesDia(int idProg, String data);
}