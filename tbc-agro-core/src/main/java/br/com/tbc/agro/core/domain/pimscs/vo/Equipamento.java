package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "EQUIPTOS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {

    @Id
    @Column(name = "cd_equipto", nullable = false)
    private Long cdEquipto;

    @Column(name = "cd_modelo")
    private Integer cdModelo;

    @Column(name = "no_anofabr")
    private Integer noAnoFabr;

    @Column(name = "fg_tp_equip", length = 1)
    private String fgTpEquip;

    @Column(name = "cd_patrimo", length = 18)
    private String cdPatrimo;

    @Column(name = "cd_transp")
    private Long cdTransp;

    @Column(name = "cd_ccusto")
    private Long cdCcusto;

    @Column(name = "fg_disponib", length = 1)
    private String fgDisponib;

    @Column(name = "qt_h_jornada", precision = 5, scale = 2)
    private BigDecimal qtHJornada;

    @Column(name = "cd_tp_recurso", length = 2)
    private String cdTpRecurso;

    @Column(name = "no_placa", length = 8)
    private String noPlaca;

    @Column(name = "qt_max_etiq")
    private Integer qtMaxEtiq;

    @Column(name = "qt_etiq_disp")
    private Integer qtEtiqDisp;

    @Column(name = "qt_tara_padr")
    private Integer qtTaraPadr;

    @Column(name = "cd_cc_resp")
    private Long cdCcResp;

    @Column(name = "no_chassi", length = 20)
    private String noChassi;

    @Column(name = "no_mes_lic")
    private Integer noMesLic;

    @Column(name = "qt_tara_min")
    private Integer qtTaraMin;

    @Column(name = "qt_tara_max")
    private Integer qtTaraMax;

    @Column(name = "qt_max_vao")
    private Integer qtMaxVao;

    @Column(name = "cd_sist_apli")
    private Integer cdSistApli;

    @Column(name = "qt_volume", precision = 8, scale = 2)
    private BigDecimal qtVolume;

    @Column(name = "cd_fren_tran")
    private Integer cdFrenTran;

    @Column(name = "cd_empresa_erp", length = 15)
    private String cdEmpresaErp;

    @Column(name = "cd_unidneg_erp", length = 15)
    private String cdUnidnegErp;

    @Column(name = "fg_trafego", length = 1)
    private String fgTrafego;

    @Column(name = "cd_equivalen", length = 5)
    private String cdEquivalen;

    @Column(name = "cd_oper_preco")
    private Integer cdOperPreco;

    @Column(name = "cd_tp_compo")
    private Integer cdTpCompo;

    @Column(name = "qt_tara_ult")
    private Integer qtTaraUlt;

    @Column(name = "qt_tara_ct", precision = 8, scale = 2)
    private BigDecimal qtTaraCt;

    @Column(name = "cd_categ_oper")
    private Integer cdCategOper;

    @Column(name = "fg_tp_carga", length = 1)
    private String fgTpCarga;

    @Column(name = "qt_max_km_dia", precision = 8, scale = 3)
    private BigDecimal qtMaxKmDia;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column(name = "cd_cor")
    private Integer cdCor;

    @Column(name = "cd_comb")
    private Integer cdComb;

    @Column(name = "cd_carroc")
    private Integer cdCarroc;

    @Column(name = "cd_classific")
    private Integer cdClassific;

    @Column(name = "cd_renavam")
    private Long cdRenavam;

    @Column(name = "obs", length = 50)
    private String obs;

    @Column(name = "de_usuario", length = 40)
    private String deUsuario;

    @Column(name = "cd_operacao")
    private Integer cdOperacao;

    @Column(name = "cd_tp_servico")
    private Integer cdTpServico;

    @Column(name = "vl_inicial", precision = 13, scale = 2)
    private BigDecimal vlInicial;

    @Column(name = "vl_residual", precision = 13, scale = 2)
    private BigDecimal vlResidual;

    @Column(name = "vl_deprecia", precision = 13, scale = 2)
    private BigDecimal vlDeprecia;

    @Column(name = "vida_util")
    private Integer vidaUtil;

    @Column(name = "dt_aquisicao")
    private LocalDate dtAquisicao;

    @Column(name = "pe_dif_tara", precision = 5, scale = 2)
    private BigDecimal peDifTara;

    @Column(name = "qt_lim_peso")
    private Integer qtLimPeso;

    @Column(name = "fg_tp_operador", length = 1)
    private String fgTpOperador;

    @Column(name = "cd_ins_h2o")
    private Long cdInsH2o;

    @Column(name = "fg_alien", length = 1)
    private String fgAlien;

    @Column(name = "dt_venc_alien")
    private LocalDate dtVencAlien;

    @Column(name = "de_favorecido", length = 40)
    private String deFavorecido;

    @Column(name = "de_prop_ant", length = 40)
    private String dePropAnt;

    @Column(name = "cd_certif")
    private Long cdCertif;

    @Column(name = "dt_emis_certif")
    private LocalDate dtEmisCertif;

    @Column(name = "cd_apolice", length = 15)
    private String cdApolice;

    @Column(name = "dt_apolice")
    private LocalDate dtApolice;

    @Column(name = "cd_uf", length = 2)
    private String cdUf;

    @Column(name = "cd_modal_equ")
    private Integer cdModalEqu;

    @Column(name = "cd_usuario", length = 10)
    private String cdUsuario;

    @Column(name = "dt_hr_altera")
    private LocalDate dtHrAltera;

    @Column(name = "fg_veiculo", length = 1)
    private String fgVeiculo;

    @Column(name = "qt_max_km_safra")
    private Integer qtMaxKmSafra;

    @Column(name = "dt_referencia")
    private LocalDate dtReferencia;

    @Column(name = "vl_tx_remcap", precision = 5, scale = 2)
    private BigDecimal vlTxRemcap;

    @Column(name = "vl_rem_capital", precision = 13, scale = 2)
    private BigDecimal vlRemCapital;

    @Column(name = "qt_vol_transp", precision = 8, scale = 3)
    private BigDecimal qtVolTransp;

    @Column(name = "qt_tempo_volta")
    private Integer qtTempoVolta;

    @Column(name = "qt_lamina_bruta", precision = 7, scale = 2)
    private BigDecimal qtLaminaBruta;

    @Column(name = "pc_eficiencia", precision = 5, scale = 2)
    private BigDecimal pcEficiencia;

    @Column(name = "qt_tempo_tot_irr")
    private Integer qtTempoTotIrr;

    @Column(name = "cd_lotacao", length = 10)
    private String cdLotacao;

    @Column(name = "qt_pesoliq_max")
    private Integer qtPesoLiqMax;

    @Column(name = "pe_dif_pesoliq", precision = 5, scale = 2)
    private BigDecimal peDifPesoLiq;

    @Column(name = "rowversion")
    private Integer rowVersion;

    @Column(name = "cd_ctf")
    private Long cdCtf;

    @Column(name = "cd_equipto_subst")
    private Long cdEquiptoSubst;

    @Column(name = "cd_usuario_subst", length = 10)
    private String cdUsuarioSubst;

    @Column(name = "dt_susbt")
    private LocalDate dtSusbt;

    @Column(name = "fg_concluiu_subst", length = 1)
    private String fgConcluiuSubst;

    @Column(name = "dt_subst")
    private LocalDate dtSubst;

    @Column(name = "no_cartao_transp")
    private Long noCartaoTransp;

    @Column(name = "qt_altura", precision = 4, scale = 2)
    private BigDecimal qtAltura;

    @Column(name = "qt_comprimento", precision = 4, scale = 2)
    private BigDecimal qtComprimento;

    @Column(name = "qt_largura", precision = 4, scale = 2)
    private BigDecimal qtLargura;

    @Column(name = "cd_usuario_susbt", length = 10)
    private String cdUsuarioSusbt;

    @Column(name = "cd_equipto_erp", length = 20)
    private String cdEquiptoErp;

    @Column(name = "fg_abastec", length = 1)
    private String fgAbastec;

    @Column(name = "no_cracha", length = 16)
    private String noCracha;

    @Column(name = "no_via_cracha")
    private Integer noViaCracha;

    @Column(name = "cd_id_telemetria", length = 20)
    private String cdIdTelemetria;

    @Column(name = "cd_tag_ravo", length = 50)
    private String cdTagRavo;

    @Column(name = "cd_tag_ravo_2", length = 19)
    private String cdTagRavo2;

    @Column(name = "dt_lim_gar")
    private LocalDate dtLimGar;

    @Column(name = "fg_contr_gar", length = 1)
    private String fgContrGar;

    @Column(name = "fg_tag_ravo", length = 1)
    private String fgTagRavo;

    @Column(name = "fg_telemetria", length = 1)
    private String fgTelemetria;

    @Column(name = "qt_km_lim_gar")
    private Integer qtKmLimGar;

    @Column(name = "cd_categoria")
    private Integer cdCategoria;

    @Column(name = "cd_clas_deter", length = 1)
    private String cdClasDeter;

    @Column(name = "cd_clas_priori", length = 1)
    private String cdClasPriori;

    @Column(name = "cd_especie")
    private Integer cdEspecie;

    @Column(name = "cd_motor", length = 18)
    private String cdMotor;

    @Column(name = "cd_munic_lic")
    private Integer cdMunicLic;

    @Column(name = "cd_nfiscalc")
    private Long cdNfiscalc;

    @Column(name = "cd_ponto")
    private Integer cdPonto;

    @Column(name = "dt_ult_reforma")
    private LocalDate dtUltReforma;

    @Column(name = "fg_contr_entressafra", length = 1)
    private String fgContrEntressafra;

    @Column(name = "fg_criticidade", length = 1)
    private String fgCriticidade;

    @Column(name = "fg_grau_det", length = 1)
    private String fgGrauDet;

    @Column(name = "fg_tp_improdutiva")
    private Integer fgTpImprodutiva;

    @Column(name = "fg_tp_produtiva")
    private Integer fgTpProdutiva;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Column(name = "mot_bloqueio_abastec", length = 250)
    private String motBloqueioAbastec;

    @Column(name = "vl_aquisicao", precision = 12, scale = 4)
    private BigDecimal vlAquisicao;

    @Column(name = "cd_faixa_ipva")
    private Long cdFaixaIpva;

    @Column(name = "fg_impr_etq", length = 1)
    private String fgImprEtq;

    @Column(name = "no_item")
    private Long noItem;

    @Column(name = "vl_premio", precision = 15, scale = 2)
    private BigDecimal vlPremio;

    @Column(name = "fg_seg_obg", length = 1)
    private String fgSegObg;

    @Column(name = "fg_integrado", length = 1)
    private String fgIntegrado;

    @Column(name = "mob_lastupdate")
    private LocalDate mobLastUpdate;

    @Column(name = "mob_status", length = 2)
    private String mobStatus;
}

