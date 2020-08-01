package com.iris.ramilton.iris.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper INSTANCIA_CONEXAO;
    private static int DATABASE_VERSION = 17;
    private static String DB_FILE_NAME = "policiacivil.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_FILE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstancia(Context context){
        if(INSTANCIA_CONEXAO == null){
            INSTANCIA_CONEXAO = new DatabaseHelper(context);
        }

        return INSTANCIA_CONEXAO;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Cria a tabela cvli
        String sqlCvli = "create table if not exists cvlis (id integer not null primary key autoincrement, "+
                         "dsNaturezaFato varchar(60), dtFato date, ckbDataFatoIndefinido int(1), hsFato varchar(5)," +
                         "ckbHorarioFatoIndefinido int(1), rbEnderecoFatoDefinido int(1), rbEnderecoFatoNIndefinido int(1), dsLogradouroFato varchar(30)," +
                         "dsRuaFato varchar(50), dsNRuaFato varchar(15), dsReferenciaLocalFato varchar(50), dsBairroFato varchar(30)," +
                         "dsDistVilPovoFato varchar(30), dsDescrDistVilPovoFato varchar(30), dsMunicipioFato varchar(50), dsEstadoFato char(2)," +
                         "dsZonaFato varchar(30), dsDetalhamentoLocal varchar(30), dsOutroDetalhamento varchar(30), cbkTortura int(1), cbkAfogamentoFato int(1)," +
                         "cbkAsfixiaFato int(1), cbkTropelamentoFato int(1), cbkDisparoArmaFato int(1), cbkEmpurraoAlturaFato int(1)," +
                         "cbkEvenenadoFato int(1), cbkEstanaduraFato int(1)," +
                         "cbkEstrangulamentoFato int(1), cbkEmpurraoSobVeiculoFato int(1), cbkGolpeFacaoFato int(1), cbkGolpeFacaFato int(1)," +
                         "cbkPauladaFato int(1), cbkOmissaoSocorroFato int(1), cbkQueimaduraFato int(1), cbkPedradaFato int(1), cbkPerfuracoesFato int(1)," +
                         "cbkIncendioFato int(1), cbkSocosPontapesFato int(1), cbkQueimarudasAcidoFato int(1), cbkNaoIdentificadoFato int(1)," +
                         "cbkOutrosNaoRelacionadoFato int(1), dsMotivacaoFato varchar(60), dtDataSilc date, hsHorarioSilc varchar(5), " +
                         "dsLogradouroSilc varchar(30), dsRuaSilc varchar(50), dsNRuaSilc varchar(15), dsReferenciaLocalSilc varchar(50), dsBairroSilc varchar(30)," +
                         "dsDistVilPovoSilc varchar(30), dsDescrDistVilPovoSilc varchar(30), dsMunicipioSilc varchar(50), dsEstadoSilc char(2)," +
                         "dsResumoFato text, dsNBO varchar(10), rbExpedidoGuiaPericial int(1), rbNExpedidoGuiaPericial int(1), dsNIP varchar(15)," +
                         "cbkBuscaApreensao int(1), cbkPrisaoTemporaria int(1), cbkPrisaoPreventiva int(1), cbkQuebraSigilo int(1), cbMedidaProtetivadeUrgencia int(1), cbSemCautelares int(1), dsDestinacaoInvestigacao varchar(50)," +
                         "EstatusCVLI int(2), unidade_id int(11), Controle int(2), idUnico text, dsTituloResumo text, servidor_id int(11), dsProvidencia text, rbEquipePreservacaoLocalDefinido int(1), " +
                         "rbEquipePreservacaoLocalSilcNDefinido int(1), rbEquipeLevantamentoSilcDefinido int(1), rbEquipeLevantamentoSilcNDefinido int(1), rbEquipePeritosSilcDefinido int(1)," +
                         "rbEquipePeritosSilcNDefinido int(1), rbObjetosArrecadadosSilcDefinido int(1), rbObjetosArrecadadosSilcNDefinido int(1), validarcvli int(1), dsResFato text, unidadece_id int(11), responsavelCVLI varchar(100))";

        db.execSQL(sqlCvli);

        //cria a tabela cvli equipe preservação local
        String sqlEquipePreservacao = "create table if not exists cvliequipepreservacaolocals (id integer not null primary key autoincrement," +
                                      "fkCvli integer not null, dsCargoEquipepreservacaolocal varchar(30), dsNomeEquipepreservacaolocal varchar(50), Controle int(2), idUnico text," +
                                      "foreign key (fkCvli) references cvlis(id))";
        db.execSQL(sqlEquipePreservacao);


        //cria a tabela vistoria
        String sqlVistoria = "create table if not exists vistoria (id integer," +
                "descricao text, vistoriador_id int(10), estepe int(1), chaveRodas int(1), triangulo int(1), macaco int(1), tapetes int(1), chaveVeiculo int(1)," +
                "CRV int(1), CRLV int(1), rodaAro varchar(50), rodaTipo varchar(50), nivelCombustivel varchar(50)," +
                "quilometragem varchar(50), estadoVeiculo int(1), avarias text,  FotoFrente blob, FotoFundo blob," +
                "FotoLateralDireita blob, FotoLateralEsquerda blob, FotoTeto blob, FotoChassi blob, FotoMotor blob, FotoVidro blob, " +
                "FotoEtiquetaSeguranca blob, " +
                "Controle int(2))";

        db.execSQL(sqlVistoria);


        //cria a tabela cvli equipe levantamento
        String sqlEquipeLevantamento = "create table if not exists cvliequipelevantamentos(id integer not null primary key autoincrement," +
                                       "fkCvli integer not null, dsCargoEquipeLevantamento varchar(30), dsNomeEquipeLevantamento varchar(50), Controle int(2), idUnico text, " +
                                       "foreign key(fkCvli) references cvlis(id))";

        db.execSQL(sqlEquipeLevantamento);


        //cria a tabela cvli equipe perito
        String sqlEquipePerito = "create table if not exists cvliequipeperitos(id integer not null primary key autoincrement," +
                                 "fkCvli integer not null, dsCargoEquipePerito varchar(30), dsNomeEquipePerito varchar(50), Controle int(2), idUnico text," +
                                 "foreign key(fkCvli) references cvlis(id))";
        db.execSQL(sqlEquipePerito);


        //cria a tabela cvli vitima
        String sqlVitima = "create table if not exists cvlivitimas(id integer not null primary key autoincrement," +
                           "fkCvli integer not null, ckbIdentificadaVitima int(1), cbkNaoIdentificadaVitima int(1), dsNomeVitima varchar(100), dsRGVitima varchar(15), dsOrgaoExpRGVitima varchar(10)," +
                           "dsSexoVitima varchar(15), dsCPFVitima varchar(14),dsNomeMaeVitima varchar(50)," +
                           "dsNomePaiVitima varchar(50), dsNascionalidadeVitima varchar(30), dsNaturalidadeVitima varchar(30), dsOrientacaoSexualVitima varchar(30)," +
                           "dsProfissaoVitima varchar(30), dsCondicaoSaudeVitima varchar(30)," +
                           "Controle int(2), idUnico text, dsEstadoVitima varchar(10), dsCondicaoVitima varchar(40), dsDtNascimentoVitima varchar(11), " +
                           "foreign key(fkCvli) references cvlis(id))";
        db.execSQL(sqlVitima);


        //cria a tabela cvli autoria
        String sqlAutoria = "create table if not exists cvliautorias(id integer not null primary key autoincrement," +
                            "fkCvli integer not null, cbkAutoriaDefinida int(1), cbkAutoriaNDefinida int(1), cbkAutoriaSuspeita int(1)," +
                            "dsNomeAutoria varchar(100), dsRGAutoria varchar(15), dsOrgaoExpRGAutoria varchar(10), dsSexoAutoria varchar(20)," +
                            "dsCPFAutoria varchar(14), dsIdadeAutoria int(3), dsCurtisAutoria varchar(20), dsNomeMaeAutoria varchar(50), dsNomePaiAutoria varchar(50)," +
                            "dsNascionalidadeAutoria varchar(30), dsNaturalidadeAutoria varchar(30), dsCondicaoAutoria varchar(50), dtPrisaoAutoria varchar(10)," +
                            "dsLocalPrisaoAutoria varchar(30), hsHorarioPrisaoAutoria varchar(5), dsNaturezaPrisaoAutoria varchar(20), dsResponsavelPrisaoAutoria varchar(30), Controle int(2), idUnico text," +
                            "dsVulgoAutoria varchar(100), " +
                            "foreign key(fkCvli) references cvlis(id))";
        db.execSQL(sqlAutoria);


        //cria a tabela cvli carro
        String sqlCarro = "create table if not exists cvlicarros(id integer not null primary key autoincrement," +
                          "fkCvli integer not null, dsTipoCarro varchar(30), dsMarcaCarro varchar(30), dsModeloCarro varchar(30), dsDescricaoCarro varchar(255), " +
                          "dsCorCarro varchar(20), dsPlacaCarro varchar(15), ckbNIdentificadoMarcaCarroCrime int(1), ckbNIdentificadoModeloCarroCrime int(1)," +
                          "ckbNIdentificadoCorCarroCrime int(1), ckbNidentificadoPlacaCarroCrime int(1), Controle int(2), idUnico text," +
                          "dsDescricaoBiscicleta varchar(50), foreign key(fkCvli) references cvlis(id))";

        db.execSQL(sqlCarro);

        //criando tabela de objetos apreendidos
        String sqlObjetosAprendidos = "create table if not exists cvliobjetosapreendidos(id integer not null primary key autoincrement," +
                "fkCvli integer not null, dsTipoObjetoApreendido varchar(30), dsDescricaoObjetoApreendido varchar(100), Controle int(2), idUnico text," +
                "foreign key(fkCvli) references cvlis(id))";

        db.execSQL(sqlObjetosAprendidos);

        //criando tabela de guias periciais
        String  SqlGuiasPericiais = "create table if not exists cvliguiaspericiais(id integer not null primary key autoincrement, " +
                "fkCvli integer not null, dsGuiaPericial varchar(50), dsNGuiaPericial varchar(30), Controle int(2), idUnico text," +
                "foreign key(fkCvli) references cvlis(id))";

        db.execSQL(SqlGuiasPericiais);

        //criando tabela de Municipio
        String  SqlMunicipio = "create table if not exists cvlimunicipios(id integer not null primary key autoincrement, dsMunicipio varchar(100))";

        db.execSQL(SqlMunicipio);

        //criando tabela de marca de carro
        String  SqlMarcadecarro = "create table if not exists marcacarros(id integer not null primary key autoincrement, dsMarcaCarro varchar(100))";

        db.execSQL(SqlMarcadecarro);

        //criando tabela de unidade bo
        String  SqlUnidadeBO = "create table if not exists unidadebo(id integer not null primary key autoincrement, dsUnidadeBO varchar(100))";

        db.execSQL(SqlUnidadeBO);

        //criando a tabela Ação Policial
        String SqlAcaoPolicial = "create table if not exists AcaoPolicial(id integer not null primary key autoincrement, dsClassificacaoAcaoPolicial varchar(100)," +
                "dsNomeOperacaoPolicial varchar(100), cbkOperacaoInominada int(1), dtAcaoPolicial date, hsAcaoPolicial varchar(5), LocalAcaoPolicial varchar(150), NBuscaJudiciaisAcaoPolicial int(3)," +
                "dsCrimePrincipalAcaoPolicial varchar(150), cbkMedidasCautelares int(1), cbkInvestigacoesOrdinarias int(1), cbkRegistroPolicial int(1), cbkAbordagemRotina int(1)," +
                "cbkColaborador int(1), cbkDenuncia int(1), cbkOutros int(1), dsUnidadeResponsavelAcaoPolicial varchar(150), NEquipeLocaisAcaoPolicial int(3), NEquipeUnidadeCoorpinAcaoPolicial int(3)," +
                "NEquipeDetCoorCoorpinAcaoPolicial int(3), NOutraInstituicaoAcaoPolicial int(3), dsOutraInstituicaoAcaoPolicial varchar(150), dsNomeUnidadeEspAcaoPolicial varchar(150)," +
                "NEquipeEspAcaoPolicial int(3), dsNomeOutraInstituicaoAcaoPolicial varchar(150), NEnvolvidosOutrasInstituicaoAcaoPolicial int(3), EstatusAcaoPolicial int(2), Controle int(2), servidor_id int(11), unidade_id int(11), validaracaopolicial int(1), idUnico text, dstituloResumo text, dsResumo text, dsNIPAcaoPolicial char(8), dsNomeDelegadoAcaoPolicial varchar(150)" +
                ", dsMunicipioReferenciaAcaoPolicial text, cbkPresoAcaoPolicial int(1), cbkBensAprendidos int(1), dsSubitulo1 text, dsTexto1 text," +
                "dsSubitulo2 text, dsTexto2 text, dsSubitulo3 text, dsTexto3 text, dsSubitulo4 text, dsTexto4 text)";

        db.execSQL(SqlAcaoPolicial);

        //criando tabela Equipes Conduzido Ação Policial
        String SqlConduzidoAcaoPolicial = "create table if not exists ConduzidoAcaoPolicial(id integer not null primary key autoincrement, " +
                "fkAcaoPolicial integer not null, dsNomeConduzidoAcaoPolicial varchar(200), IdadeConduzidoAcaoPolicial int(3), dsTipoProcedimentoAcaoPolcial varchar(100)," +
                "dsAtoInfracionalAcaoPolicial varchar(150), dsTipoConducaoAcaoPolicial varchar(150), Controle int(2), idUnico text, dsNomeJuizAcaoPolicial varchar(100), dsComarcaAcaoPolicial varchar(100)," +
                "foreign key(fkAcaoPolicial) references AcaoPolicial(id))";

        db.execSQL(SqlConduzidoAcaoPolicial);

        //criando tabela objetos apreendidos arma ação policial
        String SqlObjApreendidoArmaAcaoPolicial = "create table if not exists ObjApreendidoArmaAcaoPolicial(id integer not null primary key autoincrement," +
                "fkAcaoPolicial integer not null, dsTipoArmaAcaoPolicial varchar(100), dsModeloArmaAcaoPolicial varchar(100), Controle int(2), idUnico text, dsCalibreArmaAcaoPolicial varchar(100)," +
                "QtdArmaAcaoPolicial int(3), foreign key(fkAcaoPolicial) references AcaoPolicial(id))";

        db.execSQL(SqlObjApreendidoArmaAcaoPolicial);

        //criando a tabela objetos apreendidos drogas acao policial
        String SqlObjApreendidosDrogaAcaoPolicial = "create table if not exists ObjApreendidoDrogaAcaoPolicial(id integer not null primary key autoincrement," +
                "fkAcaoPolicial integer not null, dsTipoDrogaAcaoPolicial varchar(100), dsModoApresentacaoDrogaAcaoPolicial varchar(100), Controle int(2), idUnico text, QtdDrogaAcaoPolicial int(3)," +
                "dsPesoGramaAcaoPolicial varchar(100), dsOutrasDrogasAcaoPolicial varhcar(100), foreign key(fkAcaoPolicial) references AcaoPolicial(id))";

        db.execSQL(SqlObjApreendidosDrogaAcaoPolicial);

        //criando a tabela objetos apreendidos veiculos acao policial
        String SqlObjApreendidosCarroAcaoPolicial = "create table if not exists ObjApreendidoCarroAcaoPolicial(id integer not null primary key autoincrement," +
                "fkAcaoPolicial integer not null, dsTipoVeiculoAcaoPolicial varchar(100), Controle int(2), idUnico text, dsMarcaVeiculoAcaoPolicial varchar(100), dsModeloVeiculoAcaoPolicial varhcar(100)," +
                "dsPlacaOstentadaAcaoPolicial varchar(20), dsCorVeiculoAcaoPolicial varchar(30), foreign key(fkAcaoPolicial) references AcaoPolicial(id))";

        db.execSQL(SqlObjApreendidosCarroAcaoPolicial);

        //criando a tabela de subtitulos acao policial
        String SqlSubtitulosAcaoPolicial = "create table if not exists SubtitulosAcaoPolicial(id integer not null primary key autoincrement," +
                "fkAcaoPolicial integer not null, dsSubtituloAbertoAcaoPolicial text, TextoSubtituloAcaoPolicial text," +
                "Controle int(2), idUnico text, foreign key(fkAcaoPolicial) references AcaoPolicial(id))";

        db.execSQL(SqlSubtitulosAcaoPolicial);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ApagarTabelaCvli = "drop table cvlis";
        db.execSQL(ApagarTabelaCvli);

        String ApagarTabelaEquipePreservacao = "drop table cvliequipepreservacaolocals";
        db.execSQL(ApagarTabelaEquipePreservacao);

        String ApagarTabelaEquipeLevantamento = "drop table cvliequipelevantamentos";
        db.execSQL(ApagarTabelaEquipeLevantamento);

        String ApagarTabelaEquipePerito = "drop table cvliequipeperitos";
        db.execSQL(ApagarTabelaEquipePerito);

        String ApagarTabelaVitima = "drop table cvlivitimas";
        db.execSQL(ApagarTabelaVitima);

        String ApagarTabelaAutoria = "drop table cvliautorias";
        db.execSQL(ApagarTabelaAutoria);

        String ApagarTabelaCarro = "drop table cvlicarros";
        db.execSQL(ApagarTabelaCarro);

        String ApagarTableObjetosApreendidos = "drop table cvliobjetosapreendidos";
        db.execSQL(ApagarTableObjetosApreendidos);

        String ApagarTableGuiaPericial = "drop table cvliguiaspericiais";
        db.execSQL(ApagarTableGuiaPericial);

        String ApagarTableAcaoPolicial = "drop table AcaoPolicial";
        db.execSQL(ApagarTableAcaoPolicial);

        String ApagarTableConduzidoAcaoPolicial = "drop table ConduzidoAcaoPolicial";
        db.execSQL(ApagarTableConduzidoAcaoPolicial);

        String ApagarTableArmaAcaoPolicial = "drop table ObjApreendidoArmaAcaoPolicial";
        db.execSQL(ApagarTableArmaAcaoPolicial);

        String ApagarTableDrogaAcaoPolicial = "drop table ObjApreendidoDrogaAcaoPolicial";
        db.execSQL(ApagarTableDrogaAcaoPolicial);

        String ApagarTableCarroAcaoPolicial = "drop table ObjApreendidoCarroAcaoPolicial";
        db.execSQL(ApagarTableCarroAcaoPolicial);

        String ApagarTableSubtitulosAcaoPolical = "drop table SubtitulosAcaoPolicial";
        db.execSQL(ApagarTableSubtitulosAcaoPolical);

       // String ApagarTabelaUnidadeBo = "drop table unidadebo";
       // db.execSQL(ApagarTabelaUnidadeBo);

    //   String ApagarTabelaMarcacarros = "drop table marcacarros";
      //  db.execSQL(ApagarTabelaMarcacarros);

      //  String ApagarTabelaVistoria = "drop table vistoria";
     //   db.execSQL(ApagarTabelaVistoria);


        onCreate(db);

    }
}
