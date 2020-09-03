package com.example.forestsys.assets;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forestsys.classes.ATIVIDADES;
import com.example.forestsys.classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.classes.CADASTRO_FLORESTAL;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.ESPACAMENTOS;
import com.example.forestsys.classes.GEO_REGIONAIS;
import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.classes.INSUMOS;
import com.example.forestsys.classes.MANEJO;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.MATERIAL_GENETICO;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.classes.join.Join_MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.santalu.maskedittext.MaskEditText;

import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert (GGF_USUARIOS GGFUSUARIOS);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(GEO_SETORES GEOSETORES);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(GEO_REGIONAIS GEOREGIONAIS);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(ESPACAMENTOS espacamentos);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(MANEJO manejo);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(MATERIAL_GENETICO material_genetico);
    @Insert(onConflict  = OnConflictStrategy.IGNORE)
    void insert(O_S_ATIVIDADES o_s_atividades);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(CADASTRO_FLORESTAL cadastro_florestal);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(MAQUINAS maquinas);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(IMPLEMENTOS implementos);
    @Insert(onConflict  = OnConflictStrategy.IGNORE)
    void insert(CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(PRESTADORES prestadores);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(MAQUINA_IMPLEMENTO maquinaImplemento);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(OPERADORES operadores);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADES_DIA oSAtividadesDia);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(INSUMOS insumos);
    @Insert(onConflict  = OnConflictStrategy.IGNORE)
    void insert(O_S_ATIVIDADE_INSUMOS o_s_atividade_insumos);
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADE_INSUMOS_DIA o_s_atividade_insumos_dia);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ATIVIDADES atividades);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ATIVIDADE_INDICADORES atividade_indicadores);
    @Insert(onConflict  = OnConflictStrategy.IGNORE)
    void insert(AVAL_PONTO_SUBSOLAGEM aval_ponto_subsolagem);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AVAL_SUBSOLAGEM aval_subsolagem);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(INDICADORES_SUBSOLAGEM indicadores_subsolagem);

    @Update
    void update(GGF_USUARIOS GGFUSUARIOS);
    @Update
    void update(GEO_SETORES GEOSETORES);
    @Update
    void update(GEO_REGIONAIS GEOREGIONAIS);
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
    @Update
    void update(INSUMOS insumos);
    @Update
    void update(O_S_ATIVIDADE_INSUMOS o_s_atividade_insumos);
    @Update
    void update(O_S_ATIVIDADE_INSUMOS_DIA o_s_atividade_insumos_dia);
    @Update
    void update(ATIVIDADES atividades);
    @Update
    void update(ATIVIDADE_INDICADORES atividade_indicadores);
    @Update
    void update(AVAL_PONTO_SUBSOLAGEM aval_ponto_subsolagem);
    @Update
    void update(ESPACAMENTOS espacamentos);
    @Update
    void update(AVAL_SUBSOLAGEM aval_subsolagem);
    @Update
    void update(INDICADORES_SUBSOLAGEM indicadores_subsolagem);
    @Update
    void update(MANEJO manejo);
    @Update
    void update(MATERIAL_GENETICO material_genetico);

    @Delete
    void delete(GGF_USUARIOS GGFUSUARIOS);
    @Delete
    void delete(GEO_SETORES GEOSETORES);
    @Delete
    void delete(GEO_REGIONAIS GEOREGIONAIS);
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
    @Delete
    void delete(INSUMOS insumos);
    @Delete
    void delete(O_S_ATIVIDADE_INSUMOS o_s_atividade_insumos);
    @Delete
    void delete(O_S_ATIVIDADE_INSUMOS_DIA o_s_atividade_insumos_dia);
    @Delete
    void delete(ATIVIDADES atividades);
    @Delete
    void delete(ATIVIDADE_INDICADORES atividade_indicadores);
    @Delete
    void delete(AVAL_PONTO_SUBSOLAGEM aval_ponto_subsolagem);
    @Delete
    void delete (ESPACAMENTOS espacamentos);
    @Delete
    void delete (AVAL_SUBSOLAGEM aval_subsolagem);
    @Delete
    void delete(INDICADORES_SUBSOLAGEM indicadores_subsolagem);
    @Delete
    void delete(MANEJO manejo);
    @Delete
    void delete(MATERIAL_GENETICO material_genetico);


    //Scripts GGF_USUARIOS
    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    List<GGF_USUARIOS> todosUsers();

    @Query("SELECT * FROM GGF_USUARIOS WHERE ID_USUARIO=:taskId")
    GGF_USUARIOS selecionaUser(int taskId);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin AND senha=:taskSenha")
    GGF_USUARIOS valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin")
    LiveData<GGF_USUARIOS> validaLogin(String taskLogin);

    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    List<GGF_USUARIOS> listaUsuarios();


    //Scripts GEO_SETORES
    @Query("SELECT * FROM GEO_SETORES ORDER BY ID_SETOR asc")
    List<GEO_SETORES> todosSetores();

    @Query("SELECT * FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    GEO_SETORES selecionaSetor(int taskId);

    @Query("SELECT DESCRICAO FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    String selecionaDescSetor(int taskId);


    //Scripts GEO_REGIONAIS
    @Query("SELECT * FROM GEO_REGIONAIS ORDER BY ID_REGIONAL asc")
    LiveData<List<GEO_REGIONAIS>> todosRegionais();

    @Query("SELECT * FROM GEO_REGIONAIS WHERE ID_REGIONAL=:taskId")
    LiveData <GEO_REGIONAIS> selecionaRegional(int taskId);


    //Scripts O_S_ATIVIDADES
    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY DATE(DATA_PROGRAMADA) ASC")
    List<O_S_ATIVIDADES> listaOsDataAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY DATE(DATA_PROGRAMADA) DESC")
    List<O_S_ATIVIDADES> listaOsDataDesc();


    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY PRIORIDADE ASC")
    List<O_S_ATIVIDADES> listaOsPrioridadeAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY PRIORIDADE DESC")
    List<O_S_ATIVIDADES> listaOsPrioridadeDesc();


    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR" +
            " ORDER BY GEO_SETORES.DESCRICAO ASC")
    List<O_S_ATIVIDADES> listaOsSetorAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR" +
            " ORDER BY GEO_SETORES.DESCRICAO desc")
    List<O_S_ATIVIDADES> listaOsSetorDesc();


    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY CAST(TALHAO AS INTEGER) ASC")
    List<O_S_ATIVIDADES> listaOsTalhaoAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY CAST(TALHAO AS INTEGER) DESC")
    List<O_S_ATIVIDADES> listaOsTalhaoDesc();


    @Query("SELECT * FROM O_S_ATIVIDADES WHERE ID_PROGRAMACAO_ATIVIDADE=:taskId")
    O_S_ATIVIDADES selecionaOs(int taskId);

    @Query("SELECT * FROM O_S_ATIVIDADES")
    List<O_S_ATIVIDADES> todasOs();



    //Scripts CADASTRO_FLORESTAL
    @Query("SELECT * FROM CADASTRO_FLORESTAL WHERE ID_REGIONAL =:idReg AND ID_SETOR=:idSet AND TALHAO=:talhao" +
            " AND CICLO=:ciclo AND ID_MANEJO=:idManejo")
    CADASTRO_FLORESTAL selecionaCadFlorestal(int idReg, int idSet, String talhao, int ciclo, int idManejo);

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    List<CADASTRO_FLORESTAL> listaCadFlorestal();

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    LiveData<List<CADASTRO_FLORESTAL>> todosCadFlorestal();


    //Scripts IMPLEMENTOS
    @Query("SELECT * FROM IMPLEMENTOS WHERE ID_IMPLEMENTO=:id")
    IMPLEMENTOS selecionaImplemento(int id);

    @Query("SELECT * FROM IMPLEMENTOS")
    List<IMPLEMENTOS> listaImplementos();

    @Query("SELECT * FROM IMPLEMENTOS")
    LiveData<List<IMPLEMENTOS>> todosImplementos();


    //Scripts MAQUINAS
    @Query("SELECT * FROM MAQUINAS WHERE ID_MAQUINA=:id")
    MAQUINAS selecionaMaquina(int id);

    @Query("SELECT * FROM MAQUINAS")
    List<MAQUINAS> listaMaquinas();

    @Query("SELECT * FROM MAQUINAS")
    LiveData<List<MAQUINAS>> todasMaquinas();

    @Query("SELECT ID_MAQUINA FROM MAQUINAS WHERE DESCRICAO_MAQUINA=:desc")
    int selecionaIdMaquina(String desc);




    //Scripts PRESTADORES
    @Query("SELECT * FROM PRESTADORES WHERE ID_PRESTADOR=:id")
    PRESTADORES selecionaPrestador(int id);

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

    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY strftime('%d-%m-%Y') DESC")
    List<CALIBRAGEM_SUBSOLAGEM> listaCalibragem(int idProg);


    //Scripts MAQUINA_IMPLEMENTO
    @Query("SELECT * FROM MAQUINA_IMPLEMENTO")
    LiveData<List<MAQUINA_IMPLEMENTO>> todosMaquinaImplementos();

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO WHERE ID_MAQUINA_IMPLEMENTO=:id")
    MAQUINA_IMPLEMENTO selecionaMaquinaImplemento(int id);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO")
    List<MAQUINA_IMPLEMENTO> listaMaquinaImplemento();


    //Scripts OPERADORES
    @Query("SELECT * FROM OPERADORES")
    List<OPERADORES> todosOperadores();

    @Query("SELECT * FROM OPERADORES WHERE ID_OPERADORES=:id")
    OPERADORES selecionaOperador(int id);


    //Scripts O_S_ATIVIDADES_DIA
    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data")
    O_S_ATIVIDADES_DIA selecionaOsAtividadesDia(int idProg, String data);

    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY DATE(DATA) DESC")
    List<O_S_ATIVIDADES_DIA> listaAtividadesDia(int idProg);

    @Query("SELECT * FROM O_S_ATIVIDADES_DIA")
    List<O_S_ATIVIDADES_DIA> todasOsAtividadesDia();


    //Scritps INSUMOS
    @Query("SELECT * FROM INSUMOS WHERE ID_INSUMO=:id")
    INSUMOS selecionaInsumo(int id);

    @Query("SELECT * FROM INSUMOS")
    LiveData<List<INSUMOS>> todosInsumos();

    @Query("SELECT ID_INSUMO FROM INSUMOS WHERE DESCRICAO=:desc")
    int consultaDesc(String desc);

    @Query("SELECT ID_INSUMO FROM INSUMOS WHERE ID_INSUMO_RM=:idInsRm")
    int selecionaInsumoPorRm(String idInsRm);


    //Scritps O_S_ATIVIDADE_INSUMOS
    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idInsumo")
    List<O_S_ATIVIDADE_INSUMOS> selecionaOsAtividadeInsumo(int idProg, int idInsumo);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS")
    LiveData<List<O_S_ATIVIDADE_INSUMOS>> todosOsAtividadeInsumos();

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY ID_INSUMO")
    List<O_S_ATIVIDADE_INSUMOS> listaInsumosatividade(int idProg);


    //Scripts O_S_ATIVIDADE_INSUMOS_DIA
    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data ORDER BY ID_INSUMO")
    List<O_S_ATIVIDADE_INSUMOS_DIA> listaOsAtividadeInsumosDia(int idProg, String data);

    @Query("DELETE FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data")
    void apagaOsAtividadeInsumosDia(int idProg, String data);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg GROUP BY DATA")
    List<O_S_ATIVIDADE_INSUMOS_DIA> checaOsInsumosDia(int idProg);

    @Query("SELECT QTD_APLICADO FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns")
        List<Double> todosQTDApl(int idProg, int idIns);

    @Query("SELECT QTD_APLICADO FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns AND DATA=:data")
        double qtdAplInsDia(int idProg, int idIns, String data);

    @Query("SELECT SUM(QTD_APLICADO) FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns")
        double qtdAplicadaTodosInsumos(int idProg, int idIns);


    //Scripts ATIVIDADES
    @Query("SELECT * FROM ATIVIDADES WHERE ID_ATIVIDADE=:idAtv")
    ATIVIDADES selecionaAtividade(int idAtv);

    @Query("SELECT * FROM ATIVIDADES ORDER BY ID_ATIVIDADE")
    List<ATIVIDADES> todasAtividades();


    //Scripts ATIVIDADE_INDICADORES
    @Query(("SELECT * FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND VERION=:verion ORDER BY ORDEM_INDICADOR"))
    List<ATIVIDADE_INDICADORES> listaAtividadeIndicadores(int idAtv, String verion);

    @Query("SELECT DESCRICAO FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd")
    String descricaoIndicador(int idAtv, int idInd);

    @Query("SELECT INDICADOR_CORRIGIVEL FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd")
    int indicadorCorrigivel(int idAtv, int idInd);


    //Scripts AVAL_PONTO_SUBSOLAGEM
    @Query("SELECT *FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv GROUP BY PONTO ORDER BY PONTO")
    List<AVAL_PONTO_SUBSOLAGEM> listaAvalSubsolagem(int idAtv);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_ATIVIDADE=:idAtv AND PONTO=:ponto")
    List<AVAL_PONTO_SUBSOLAGEM> listaPontoCorrecoes(int idProg, int idAtv, int ponto);

    //Checa quantidade de cada indicador não conforme
    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR<:valor")
    int qtdNaoConformeMenor(int idAtv, int idInd, double valor);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR=1")
    int qtdNaoConformebool(int idAtv, int idInd);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR NOT BETWEEN (:valor1) AND (:valor2)")
    int qtdNaoConformeForaDaFaixa(int idAtv, int idInd, double valor1, double valor2);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INDICADOR=:idInd")
    int qtdIndicador(int idProg, int idInd);

    //checa qual indicador não está conforme para cada ponto
    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR<:valor AND PONTO=:ponto")
    boolean valorNaoConformeMenor(int idAtv, int idInd, double valor, int ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR=1 AND PONTO=:ponto")
    boolean valorNaoConformebool(int idAtv, int idInd, int ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR NOT BETWEEN (:valor1) AND (:valor2)  AND PONTO=:ponto")
    boolean valorNaoConformeForaDaFaixa(int idAtv, int idInd, double valor1, double valor2, int ponto);





    //Scripts ESPACAMENTOS
    @Query("SELECT * FROM ESPACAMENTOS WHERE ID_ESPACAMENTO=:idEspacamento")
    ESPACAMENTOS selecionaEspacamento(int idEspacamento);

    @Query("SELECT * FROM ESPACAMENTOS")
    List<ESPACAMENTOS> listaEspacamentos();


    //Scripts AVAL_SUBSOLAGEM
    @Query("SELECT * FROM AVAL_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg")
    AVAL_SUBSOLAGEM selecionaAvalSubsolagem(int idProg);

    @Query("SELECT * FROM AVAL_SUBSOLAGEM")
    List<AVAL_SUBSOLAGEM> listaAvalSubsolagem();


    //Scripts INDICADORES_SUBSOLAGEM
    @Query("SELECT * FROM INDICADORES_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_ATIVIDADE=:idAtv ORDER BY ID_INDICADOR")
    List<INDICADORES_SUBSOLAGEM> listaIndicadoresSubsolagem(int idProg, int idAtv);


    //Scripts MANEJO
    @Query("SELECT * FROM MANEJO WHERE ID_MANEJO=:idManejo")
    MANEJO selecionaManejo(int idManejo);


    //Scripts MATERIAL_GENETICO
    @Query("SELECT * FROM MATERIAL_GENETICO WHERE ID_MATERIAL_GENETICO=:idMatGen")
    MATERIAL_GENETICO selecionaMaterialGenetico(int idMatGen);


    //JOINS
    @Query("SELECT DISTINCT * FROM INSUMOS INNER JOIN O_S_ATIVIDADE_INSUMOS ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS.ID_INSUMO " +
            "LEFT JOIN O_S_ATIVIDADE_INSUMOS_DIA ON O_S_ATIVIDADE_INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO" +
            " AND O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE = O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE" +
            " WHERE O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE=:idProg GROUP BY INSUMOS.ID_INSUMO ORDER BY ID_INSUMO")
    List<Join_OS_INSUMOS> listaJoinInsumoAtividades(int idProg);

    @Query("SELECT DISTINCT * FROM INSUMOS INNER JOIN O_S_ATIVIDADE_INSUMOS_DIA ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO " +
            "LEFT JOIN O_S_ATIVIDADE_INSUMOS" +
            " ON O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO = O_S_ATIVIDADE_INSUMOS.ID_INSUMO " +
            "AND O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE = O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE " +
            "WHERE O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE=:idProg AND O_S_ATIVIDADE_INSUMOS_DIA.DATA=:data ORDER BY ID_INSUMO")
    List<Join_OS_INSUMOS> listaJoinInsumoAtividadesdia(int idProg, String data);

    @Query("SELECT QTD_APLICADO FROM INSUMOS LEFT JOIN O_S_ATIVIDADE_INSUMOS_DIA" +
            " ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO " +
            "WHERE O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE=:idProg " +
            "AND O_S_ATIVIDADE_INSUMOS_DIA.DATA=:data " +
            "AND O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO=:idInsumo")
    double retornaQtdApl(int idProg, String data, int idInsumo);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO JOIN MAQUINAS " +
            "ON MAQUINA_IMPLEMENTO.ID_MAQUINA = MAQUINAS.ID_MAQUINA " +
            "JOIN IMPLEMENTOS ON MAQUINA_IMPLEMENTO.ID_IMPLEMENTO = IMPLEMENTOS.ID_IMPLEMENTO " +
            "WHERE MAQUINA_IMPLEMENTO.ID_MAQUINA =:idMaq")
    List<Join_MAQUINA_IMPLEMENTO> listaJoinMaquinaImplemento(int idMaq);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO JOIN MAQUINAS " +
            "ON MAQUINA_IMPLEMENTO.ID_MAQUINA = MAQUINAS.ID_MAQUINA " +
            "JOIN IMPLEMENTOS ON MAQUINA_IMPLEMENTO.ID_IMPLEMENTO = IMPLEMENTOS.ID_IMPLEMENTO " +
            "WHERE MAQUINA_IMPLEMENTO.ID_MAQUINA_IMPLEMENTO =:idMaqImpl")
    Join_MAQUINA_IMPLEMENTO selecionaJoinMaquinaImplemento(int idMaqImpl);

    @Query("SELECT CALIBRAGEM_SUBSOLAGEM.ID_MAQUINA_IMPLEMENTO FROM CALIBRAGEM_SUBSOLAGEM " +
            "WHERE CALIBRAGEM_SUBSOLAGEM.ID_PROGRAMACAO_ATIVIDADE=:idProg AND " +
            "CALIBRAGEM_SUBSOLAGEM.DATA=:data AND CALIBRAGEM_SUBSOLAGEM.TURNO=:turno AND" +
            " ID_MAQUINA_IMPLEMENTO =:idMaqImp")
    int checaMaquinaImplemento(int idProg, String data, String turno, int idMaqImp);
}