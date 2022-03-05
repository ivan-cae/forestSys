package com.example.forestsys.Assets;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forestsys.Classes.ATIVIDADES;
import com.example.forestsys.Classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.ESPACAMENTOS;
import com.example.forestsys.Classes.GEO_LOCALIZACAO;
import com.example.forestsys.Classes.GEO_REGIONAIS;
import com.example.forestsys.Classes.GEO_SETORES;
import com.example.forestsys.Classes.GGF_DEPARTAMENTOS;
import com.example.forestsys.Classes.GGF_FUNCOES;
import com.example.forestsys.Classes.GGF_USUARIOS;
import com.example.forestsys.Classes.IMPLEMENTOS;
import com.example.forestsys.Classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.Classes.INSUMOS;
import com.example.forestsys.Classes.INSUMO_ATIVIDADES;
import com.example.forestsys.Classes.MANEJO;
import com.example.forestsys.Classes.MAQUINAS;
import com.example.forestsys.Classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.Classes.MATERIAL_GENETICO;
import com.example.forestsys.Classes.OPERADORES;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.Classes.PRESTADORES;
import com.example.forestsys.Classes.Joins.Join_MAQUINA_IMPLEMENTO;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;

import java.util.List;

/*
 * Interface DAO(Data Access Object) utilizada para executar as querys no BD
 */
@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GGF_USUARIOS GGFUSUARIOS);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GEO_SETORES GEOSETORES);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GEO_REGIONAIS GEOREGIONAIS);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ESPACAMENTOS espacamentos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MANEJO manejo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MATERIAL_GENETICO material_genetico);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADES o_s_atividades);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CADASTRO_FLORESTAL cadastro_florestal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MAQUINAS maquinas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IMPLEMENTOS implementos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PRESTADORES prestadores);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MAQUINA_IMPLEMENTO maquinaImplemento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OPERADORES operadores);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADES_DIA oSAtividadesDia);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(INSUMOS insumos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADE_INSUMOS o_s_atividade_insumos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(O_S_ATIVIDADE_INSUMOS_DIA o_s_atividade_insumos_dia);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ATIVIDADES atividades);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ATIVIDADE_INDICADORES atividade_indicadores);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AVAL_PONTO_SUBSOLAGEM aval_ponto_subsolagem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AVAL_SUBSOLAGEM aval_subsolagem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(INDICADORES_SUBSOLAGEM indicadores_subsolagem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Configs configs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GGF_FUNCOES ggf_funcoes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GGF_DEPARTAMENTOS ggf_departamentos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(INSUMO_ATIVIDADES insumoAtividades);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GEO_LOCALIZACAO geoLocalizacao);

    @Insert
    void insert(FOREST_LOG forest_log);


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

    @Update
    void update(Configs configs);

    @Update
    void update(GGF_FUNCOES ggf_funcoes);

    @Update
    void update(GGF_DEPARTAMENTOS ggf_departamentos);

    @Update
    void update(INSUMO_ATIVIDADES insumoAtividades);

    @Update
    void update(GEO_LOCALIZACAO geoLocalizacao);


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
    void delete(ESPACAMENTOS espacamentos);

    @Delete
    void delete(AVAL_SUBSOLAGEM aval_subsolagem);

    @Delete
    void delete(INDICADORES_SUBSOLAGEM indicadores_subsolagem);

    @Delete
    void delete(MANEJO manejo);

    @Delete
    void delete(MATERIAL_GENETICO material_genetico);

    @Delete
    void delete(Configs configs);

    @Delete
    void delete(GGF_FUNCOES ggf_funcoes);

    @Delete
    void delete(GGF_DEPARTAMENTOS ggf_departamentos);

    @Delete
    void delete(INSUMO_ATIVIDADES insumoAtividades);

    @Delete
    void delete(GEO_LOCALIZACAO geoLocalizacao);

    @Delete
    void delete(FOREST_LOG forest_log);


    //Scripts GEO_LOCALIZACAO
    @Query("SELECT * FROM GEO_LOCALIZACAO WHERE TALHAO=:talhao")
    GEO_LOCALIZACAO selecionaGeoLocal(String talhao);

    @Query("DELETE FROM GEO_LOCALIZACAO")
    void apagaTodasGeoLocal();

    @Query("SELECT * FROM GEO_LOCALIZACAO")
    List<GEO_LOCALIZACAO> todasGeoLocalizacao();


    //Scripts Configurações
    @Query("SELECT * FROM Configs WHERE idConfig=1")
    Configs selecionaConfigs();

    @Query("DELETE FROM Configs")
    void apagaConfigs();

    //Scripts FOREST_LOG
    @Query("SELECT * FROM FOREST_LOG")
    List<FOREST_LOG> todosLogs();


    //Scripts GGF_USUARIOS
    @Query("SELECT * FROM GGF_USUARIOS ORDER BY ID_USUARIO asc")
    List<GGF_USUARIOS> todosUsers();

    @Query("SELECT * FROM GGF_USUARIOS WHERE ID_USUARIO=:taskId")
    GGF_USUARIOS selecionaUser(Integer taskId);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin AND senha=:taskSenha AND ATIVO=1")
    GGF_USUARIOS valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM GGF_USUARIOS WHERE EMAIL=:taskLogin")
    LiveData<GGF_USUARIOS> validaLogin(String taskLogin);

    @Query("SELECT * FROM GGF_USUARIOS WHERE ATIVO=1 ORDER BY ID_USUARIO asc")
    List<GGF_USUARIOS> listaUsuarios();


    //Scripts GEO_SETORES
    @Query("SELECT * FROM GEO_SETORES ORDER BY ID_SETOR asc")
    List<GEO_SETORES> todosSetores();

    @Query("SELECT * FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    GEO_SETORES selecionaSetor(Integer taskId);

    @Query("SELECT DESCRICAO FROM GEO_SETORES WHERE ID_SETOR=:taskId")
    String selecionaDescSetor(Integer taskId);


    //Scripts GEO_REGIONAIS
    @Query("SELECT * FROM GEO_REGIONAIS ORDER BY ID_REGIONAL asc")
    LiveData<List<GEO_REGIONAIS>> todosRegionais();

    @Query("SELECT * FROM GEO_REGIONAIS WHERE ID_REGIONAL=:taskId")
    LiveData<GEO_REGIONAIS> selecionaRegional(Integer taskId);


    //Scripts O_S_ATIVIDADES
    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY DATE(DATA_PROGRAMADA) ASC")
    List<O_S_ATIVIDADES> listaOsDataSemPrioridadeAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY DATE(DATA_PROGRAMADA) DESC")
    List<O_S_ATIVIDADES> listaOsDataSemPrioridadeDesc();

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE PRIORIDADE =:prioridade ORDER BY DATE(DATA_PROGRAMADA) ASC")
    List<O_S_ATIVIDADES> listaOsDataAsc(Integer prioridade);

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE PRIORIDADE =:prioridade  ORDER BY DATE(DATA_PROGRAMADA) DESC")
    List<O_S_ATIVIDADES> listaOsDataDesc(Integer prioridade);


    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR" +
            " ORDER BY GEO_SETORES.DESCRICAO ASC")
    List<O_S_ATIVIDADES> listaOsSetorSemPrioridadeAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR" +
            " ORDER BY GEO_SETORES.DESCRICAO DESC")
    List<O_S_ATIVIDADES> listaOsSetorSemPrioridadeDesc();

    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR AND PRIORIDADE =:prioridade" +
            " ORDER BY GEO_SETORES.DESCRICAO ASC")
    List<O_S_ATIVIDADES> listaOsSetorAsc(Integer prioridade);

    @Query("SELECT * FROM O_S_ATIVIDADES JOIN GEO_SETORES WHERE O_S_ATIVIDADES.ID_SETOR = GEO_SETORES.ID_SETOR AND PRIORIDADE =:prioridade" +
            " ORDER BY GEO_SETORES.DESCRICAO DESC")
    List<O_S_ATIVIDADES> listaOsSetorDesc(Integer prioridade);


    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY CAST(TALHAO AS INTEGER) ASC")
    List<O_S_ATIVIDADES> listaOsTalhaoSemPrioridadeAsc();

    @Query("SELECT * FROM O_S_ATIVIDADES ORDER BY CAST(TALHAO AS INTEGER) DESC")
    List<O_S_ATIVIDADES> listaOsTalhaoSemPrioridadeDesc();

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE PRIORIDADE =:prioridade  ORDER BY CAST(TALHAO AS INTEGER) ASC")
    List<O_S_ATIVIDADES> listaOsTalhaoAsc(Integer prioridade);

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE PRIORIDADE =:prioridade  ORDER BY CAST(TALHAO AS INTEGER) DESC")
    List<O_S_ATIVIDADES> listaOsTalhaoDesc(Integer prioridade);


    @Query("SELECT * FROM O_S_ATIVIDADES WHERE ID_PROGRAMACAO_ATIVIDADE=:taskId")
    O_S_ATIVIDADES selecionaOs(Integer taskId);

    @Query("SELECT * FROM O_S_ATIVIDADES")
    List<O_S_ATIVIDADES> todasOs();

    @Query("SELECT * FROM O_S_ATIVIDADES WHERE PRIORIDADE=:prioridade")
    List<O_S_ATIVIDADES> filtraPrioridade(Integer prioridade);

    @Query("DELETE FROM O_S_ATIVIDADES WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv")
    void apagaAtividade(Integer idAtv);


    //Scripts CADASTRO_FLORESTAL
    @Query("SELECT * FROM CADASTRO_FLORESTAL WHERE ID_REGIONAL =:idReg AND ID_SETOR=:idSet AND TALHAO=:talhao" +
            " AND CICLO=:ciclo AND ID_MANEJO=:idManejo")
    CADASTRO_FLORESTAL selecionaCadFlorestal(Integer idReg, Integer idSet, String talhao, Integer ciclo, Integer idManejo);

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    List<CADASTRO_FLORESTAL> listaCadFlorestal();

    @Query("SELECT * FROM CADASTRO_FLORESTAL")
    LiveData<List<CADASTRO_FLORESTAL>> todosCadFlorestal();


    //Scripts IMPLEMENTOS
    @Query("SELECT * FROM IMPLEMENTOS WHERE ID_IMPLEMENTO=:id")
    IMPLEMENTOS selecionaImplemento(Integer id);

    @Query("SELECT * FROM IMPLEMENTOS")
    List<IMPLEMENTOS> listaImplementos();

    @Query("SELECT * FROM IMPLEMENTOS")
    LiveData<List<IMPLEMENTOS>> todosImplementos();


    //Scripts MAQUINAS
    @Query("SELECT * FROM MAQUINAS WHERE ID_MAQUINA=:id")
    MAQUINAS selecionaMaquina(Integer id);

    @Query("SELECT * FROM MAQUINAS WHERE ATIVO=1")
    List<MAQUINAS> listaMaquinas();

    @Query("SELECT * FROM MAQUINAS")
    LiveData<List<MAQUINAS>> todasMaquinas();

    @Query("SELECT ID_MAQUINA FROM MAQUINAS WHERE DESCRICAO_MAQUINA=:desc")
    Integer selecionaIdMaquina(String desc);


    //Scripts PRESTADORES
    @Query("SELECT * FROM PRESTADORES WHERE ID_PRESTADOR=:id")
    PRESTADORES selecionaPrestador(Integer id);

    @Query("SELECT * FROM PRESTADORES WHERE ATIVO=1")
    List<PRESTADORES> listaPrestadores();

    @Query("SELECT * FROM PRESTADORES")
    LiveData<List<PRESTADORES>> todosPrestadores();


    //Scripts CALIBRAGEM_SUBSOLAGEM
    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND " +
            "TURNO=:turno AND ID_MAQUINA_IMPLEMENTO=:idMaqImp")
    LiveData<CALIBRAGEM_SUBSOLAGEM> selecionaCalibragem(Integer idProg, String data, String turno, Integer idMaqImp);

    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND " +
            "TURNO=:turno")
    CALIBRAGEM_SUBSOLAGEM checaCalibragem(Integer idProg, String data, String turno);

    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY strftime('%d-%m-%Y') DESC")
    List<CALIBRAGEM_SUBSOLAGEM> listaCalibragem(Integer idProg);

    @Query("SELECT * FROM CALIBRAGEM_SUBSOLAGEM")
    List<CALIBRAGEM_SUBSOLAGEM> todasCalibragens();


    //Scripts MAQUINA_IMPLEMENTO
    @Query("SELECT * FROM MAQUINA_IMPLEMENTO")
    LiveData<List<MAQUINA_IMPLEMENTO>> todosMaquinaImplementos();

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO WHERE ID_MAQUINA_IMPLEMENTO=:id")
    MAQUINA_IMPLEMENTO selecionaMaquinaImplemento(Integer id);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO")
    List<MAQUINA_IMPLEMENTO> listaMaquinaImplemento();


    //Scripts OPERADORES
    @Query("SELECT * FROM OPERADORES WHERE ATIVO=1")
    List<OPERADORES> todosOperadores();

    @Query("SELECT * FROM OPERADORES WHERE ID_OPERADORES=:id")
    OPERADORES selecionaOperador(Integer id);


    //Scripts O_S_ATIVIDADES_DIA
    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data")
    O_S_ATIVIDADES_DIA selecionaOsAtividadesDia(Integer idProg, String data);

    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY DATE(DATA) DESC")
    List<O_S_ATIVIDADES_DIA> listaAtividadesDia(Integer idProg);

    @Query("SELECT * FROM O_S_ATIVIDADES_DIA")
    List<O_S_ATIVIDADES_DIA> todasOsAtividadesDia();

    @Query("SELECT * FROM O_S_ATIVIDADES_DIA WHERE ID=:idOracle")
    O_S_ATIVIDADES_DIA selecionaAtvDiaOracle(Integer idOracle);

    @Query("SELECT ROUND (SUM(CAST(AREA_REALIZADA AS DECIMAL)), 2) FROM O_S_ATIVIDADES_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg")
    double somaAreaRealizada(Integer idProg);

    @Query("SELECT ROUND (SUM(CAST(HM AS DECIMAL)), 2) FROM O_S_ATIVIDADES_DIA")
    double somaHoraMaquinaTotal();

    @Query("SELECT ROUND (SUM(CAST(HO AS DECIMAL)), 2) FROM O_S_ATIVIDADES_DIA")
    double somaHoraOperadorTotal();

    @Query("SELECT ROUND (SUM(CAST(HH AS DECIMAL)), 2) FROM O_S_ATIVIDADES_DIA")
    double somaHoraAjudanteTotal();

    //Scritps INSUMOS
    @Query("SELECT * FROM INSUMOS WHERE ID_INSUMO=:id")
    INSUMOS selecionaInsumo(Integer id);

    @Query("SELECT * FROM INSUMOS")
    LiveData<List<INSUMOS>> todosInsumos();

    @Query("SELECT ID_INSUMO FROM INSUMOS WHERE DESCRICAO=:desc")
    Integer consultaDesc(String desc);

    @Query("SELECT ID_INSUMO FROM INSUMOS WHERE ID_INSUMO_RM=:idInsRm")
    Integer selecionaInsumoPorRm(String idInsRm);


    //Scritps O_S_ATIVIDADE_INSUMOS
    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idInsumo")
    List<O_S_ATIVIDADE_INSUMOS> selecionaOsAtividadeInsumo(Integer idProg, Integer idInsumo);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS")
    List<O_S_ATIVIDADE_INSUMOS> todosOsAtividadeInsumos();

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg ORDER BY ID_INSUMO")
    List<O_S_ATIVIDADE_INSUMOS> listaInsumosatividade(Integer idProg);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns ")
    O_S_ATIVIDADE_INSUMOS selecionaAtividadeInsumos(Integer idProg, Integer idIns);


    //Scripts O_S_ATIVIDADE_INSUMOS_DIA
    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data ORDER BY ID_INSUMO")
    List<O_S_ATIVIDADE_INSUMOS_DIA> listaOsAtividadeInsumosDia(Integer idProg, String data);

    @Query("DELETE FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND ID_INSUMO=:idIns")
    void apagaOsAtividadeInsumosDia(Integer idProg, String data, Integer idIns);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg GROUP BY DATA")
    List<O_S_ATIVIDADE_INSUMOS_DIA> checaOsInsumosDia(Integer idProg);

    @Query("SELECT QTD_APLICADO FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns")
    List<Double> todosQTDApl(Integer idProg, Integer idIns);

    @Query("SELECT QTD_APLICADO FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns AND DATA=:data")
    double qtdAplInsDia(Integer idProg, Integer idIns, String data);

    @Query("SELECT ROUND(SUM(QTD_APLICADO), 2) FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INSUMO=:idIns")
    double qtdAplicadaTodosInsumos(Integer idProg, Integer idIns);

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA")
    List<O_S_ATIVIDADE_INSUMOS_DIA> todasOsAtvInsumosDia();

    @Query("SELECT * FROM O_S_ATIVIDADE_INSUMOS_DIA WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND DATA=:data AND ID_INSUMO=:idIns")
    O_S_ATIVIDADE_INSUMOS_DIA selecionaAtvInsDia(Integer idProg, Integer idIns, String data);


    //Scripts ATIVIDADES
    @Query("SELECT * FROM ATIVIDADES WHERE ID_ATIVIDADE=:idAtv")
    ATIVIDADES selecionaAtividade(Integer idAtv);

    @Query("SELECT * FROM ATIVIDADES ORDER BY ID_ATIVIDADE")
    List<ATIVIDADES> todasAtividades();


    //Scripts ATIVIDADE_INDICADORES
    @Query(("SELECT * FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND VERION=:verion ORDER BY ORDEM_INDICADOR"))
    List<ATIVIDADE_INDICADORES> listaAtividadeIndicadores(Integer idAtv, String verion);

    @Query("SELECT DESCRICAO FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd")
    String descricaoIndicador(Integer idAtv, Integer idInd);

    @Query("SELECT INDICADOR_CORRIGIVEL FROM ATIVIDADE_INDICADORES WHERE ID_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd")
    Integer indicadorCorrigivel(Integer idAtv, Integer idInd);

    @Query("SELECT * FROM ATIVIDADE_INDICADORES WHERE REFERENCIA=:ref")
    ATIVIDADE_INDICADORES selecionaIndicadorPeloRef(String ref);


    //Scripts AVAL_PONTO_SUBSOLAGEM

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND " +
            "PONTO=:ponto ORDER BY ID_INDICADOR")
    List<AVAL_PONTO_SUBSOLAGEM> todosIndicadoresPonto(Integer idAtv, Integer ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND " +
            "PONTO=:ponto ORDER BY ID_INDICADOR")
    AVAL_PONTO_SUBSOLAGEM selecionaAvalPontoSubsolagem(Integer idAtv, Integer idInd, Integer ponto);

    @Query("SELECT *FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv GROUP BY PONTO ORDER BY PONTO")
    List<AVAL_PONTO_SUBSOLAGEM> listaAvalPontoSubsolagem(Integer idAtv);

    @Query("SELECT DISTINCT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_ATIVIDADE=:idAtv AND PONTO=:ponto")
    List<AVAL_PONTO_SUBSOLAGEM> listaPontoCorrecoes(Integer idProg, Integer idAtv, Integer ponto);

    //Checa quantidade de cada indicador não conforme
    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR<:valor")
    Integer qtdNaoConformeMenor(Integer idAtv, Integer idInd, double valor);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR=1")
    Integer qtdNaoConformebool(Integer idAtv, Integer idInd);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR NOT BETWEEN (:valor1) AND (:valor2)")
    Integer qtdNaoConformeForaDaFaixa(Integer idAtv, Integer idInd, double valor1, double valor2);

    @Query("SELECT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_INDICADOR=:idInd")
    Integer qtdIndicador(Integer idProg, Integer idInd);

    //checa qual indicador não está conforme para cada ponto
    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR<:valor AND PONTO=:ponto")
    boolean valorNaoConformeMenor(Integer idAtv, Integer idInd, double valor, Integer ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR=1 AND PONTO=:ponto")
    boolean valorNaoConformebool(Integer idAtv, Integer idInd, Integer ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idAtv AND ID_INDICADOR=:idInd AND VALOR_INDICADOR NOT BETWEEN (:valor1) AND (:valor2)  AND PONTO=:ponto")
    boolean valorNaoConformeForaDaFaixa(Integer idAtv, Integer idInd, double valor1, double valor2, Integer ponto);

    @Query("SELECT * FROM AVAL_PONTO_SUBSOLAGEM")
    List<AVAL_PONTO_SUBSOLAGEM> todosPontos();


    //Scripts ESPACAMENTOS
    @Query("SELECT * FROM ESPACAMENTOS WHERE ID_ESPACAMENTO=:idEspacamento")
    ESPACAMENTOS selecionaEspacamento(Integer idEspacamento);

    @Query("SELECT * FROM ESPACAMENTOS")
    List<ESPACAMENTOS> listaEspacamentos();


    //Scripts AVAL_SUBSOLAGEM
    @Query("SELECT * FROM AVAL_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg")
    AVAL_SUBSOLAGEM selecionaAvalSubsolagem(Integer idProg);

    @Query("SELECT * FROM AVAL_SUBSOLAGEM")
    List<AVAL_SUBSOLAGEM> listaAvalPontoSubsolagem();


    //Scripts INDICADORES_SUBSOLAGEM
    @Query("SELECT * FROM INDICADORES_SUBSOLAGEM WHERE ID_PROGRAMACAO_ATIVIDADE=:idProg AND ID_ATIVIDADE=:idAtv ORDER BY ID_INDICADOR")
    List<INDICADORES_SUBSOLAGEM> listaIndicadoresSubsolagem(Integer idProg, Integer idAtv);

    @Query("SELECT * FROM INDICADORES_SUBSOLAGEM")
    List<INDICADORES_SUBSOLAGEM> todosIndicadoresSubsolagem();


    //Scripts MANEJO
    @Query("SELECT * FROM MANEJO WHERE ID_MANEJO=:idManejo")
    MANEJO selecionaManejo(Integer idManejo);


    //Scripts MATERIAL_GENETICO
    @Query("SELECT * FROM MATERIAL_GENETICO WHERE ID_MATERIAL_GENETICO=:idMatGen")
    MATERIAL_GENETICO selecionaMaterialGenetico(Integer idMatGen);


    //JOINS
    @Query("SELECT DISTINCT * FROM INSUMOS INNER JOIN O_S_ATIVIDADE_INSUMOS ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS.ID_INSUMO " +
            "LEFT JOIN O_S_ATIVIDADE_INSUMOS_DIA ON O_S_ATIVIDADE_INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO" +
            " AND O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE = O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE" +
            " WHERE O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE=:idProg GROUP BY INSUMOS.ID_INSUMO ORDER BY ID_INSUMO")
    List<Join_OS_INSUMOS> listaJoinInsumoAtividades(Integer idProg);

    @Query("SELECT DISTINCT * FROM INSUMOS LEFT JOIN O_S_ATIVIDADE_INSUMOS_DIA ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO " +
            "LEFT JOIN O_S_ATIVIDADE_INSUMOS " +
            "ON O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO = O_S_ATIVIDADE_INSUMOS.ID_INSUMO " +
            "AND O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE = O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE " +
            "WHERE O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE=:idProg " +
            "AND O_S_ATIVIDADE_INSUMOS.ID_PROGRAMACAO_ATIVIDADE=:idProg" +
            " AND O_S_ATIVIDADE_INSUMOS_DIA.DATA=:data ORDER BY ID_INSUMO")
    List<Join_OS_INSUMOS> listaJoinInsumoAtividadesdia(Integer idProg, String data);

    @Query("SELECT QTD_APLICADO FROM INSUMOS LEFT JOIN O_S_ATIVIDADE_INSUMOS_DIA" +
            " ON INSUMOS.ID_INSUMO = O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO " +
            "WHERE O_S_ATIVIDADE_INSUMOS_DIA.ID_PROGRAMACAO_ATIVIDADE=:idProg " +
            "AND O_S_ATIVIDADE_INSUMOS_DIA.DATA=:data " +
            "AND O_S_ATIVIDADE_INSUMOS_DIA.ID_INSUMO=:idInsumo")
    double retornaQtdApl(Integer idProg, String data, Integer idInsumo);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO JOIN MAQUINAS " +
            "ON MAQUINA_IMPLEMENTO.ID_MAQUINA = MAQUINAS.ID_MAQUINA " +
            "JOIN IMPLEMENTOS ON MAQUINA_IMPLEMENTO.ID_IMPLEMENTO = IMPLEMENTOS.ID_IMPLEMENTO " +
            "WHERE MAQUINA_IMPLEMENTO.ID_MAQUINA =:idMaq")
    List<Join_MAQUINA_IMPLEMENTO> listaJoinMaquinaImplemento(Integer idMaq);

    @Query("SELECT * FROM MAQUINA_IMPLEMENTO JOIN MAQUINAS " +
            "ON MAQUINA_IMPLEMENTO.ID_MAQUINA = MAQUINAS.ID_MAQUINA " +
            "JOIN IMPLEMENTOS ON MAQUINA_IMPLEMENTO.ID_IMPLEMENTO = IMPLEMENTOS.ID_IMPLEMENTO " +
            "WHERE MAQUINA_IMPLEMENTO.ID_MAQUINA_IMPLEMENTO =:idMaqImpl")
    Join_MAQUINA_IMPLEMENTO selecionaJoinMaquinaImplemento(Integer idMaqImpl);

    @Query("SELECT CALIBRAGEM_SUBSOLAGEM.ID_MAQUINA_IMPLEMENTO FROM CALIBRAGEM_SUBSOLAGEM " +
            "WHERE CALIBRAGEM_SUBSOLAGEM.ID_PROGRAMACAO_ATIVIDADE=:idProg AND " +
            "CALIBRAGEM_SUBSOLAGEM.DATA=:data AND CALIBRAGEM_SUBSOLAGEM.TURNO=:turno AND" +
            " ID_MAQUINA_IMPLEMENTO =:idMaqImp")
    Integer checaMaquinaImplemento(Integer idProg, String data, String turno, Integer idMaqImp);

    @Query("SELECT DISTINCT COUNT(*) FROM AVAL_PONTO_SUBSOLAGEM INNER JOIN ATIVIDADE_INDICADORES ON AVAL_PONTO_SUBSOLAGEM.ID_INDICADOR = " +
            "ATIVIDADE_INDICADORES.ID_INDICADOR AND AVAL_PONTO_SUBSOLAGEM.ID_ATIVIDADE = ATIVIDADE_INDICADORES.ID_ATIVIDADE " +
            "WHERE AVAL_PONTO_SUBSOLAGEM.ID_ATIVIDADE=:idAtv AND AVAL_PONTO_SUBSOLAGEM.ID_PROGRAMACAO_ATIVIDADE=:idProg AND " +
            "ATIVIDADE_INDICADORES.INDICADOR_CORRIGIVEL=1 AND " +
            "AVAL_PONTO_SUBSOLAGEM.NC_TRATADA=0")
    Integer NCNaoTratadas(Integer idAtv, Integer idProg);
}