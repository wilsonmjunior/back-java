package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CC_CSTG")
public class CentroCusto {

    @Id
    @Column(name = "cd_ccusto", nullable = false)
    private Long cdCcusto;

    @Column(name = "cd_cc_estr", length = 20)
    private String cdCcEstr;

    @Column(name = "de_ccusto", length = 25, nullable = false)
    private String deCcusto;

    @Column(name = "cd_cc_niv1", nullable = false)
    private Integer cdCcNiv1;

    @Column(name = "cd_cc_niv2", nullable = false)
    private Integer cdCcNiv2;

    @Column(name = "cd_cc_niv3", nullable = false)
    private Integer cdCcNiv3;

    @Column(name = "cd_cc_niv4", nullable = false)
    private Integer cdCcNiv4;

    @Column(name = "cd_ramo", length = 2, nullable = false)
    private String cdRamo;

    @Column(name = "fg_tp_ccusto", length = 1, nullable = false)
    private String fgTpCcusto;

    @Column(name = "cd_fase", nullable = false)
    private Integer cdFase;

    @Column(name = "cd_cta_sin", length = 4)
    private String cdCtaSin;

    @Column(name = "fg_tp_cta", length = 2)
    private String fgTpCta;

    @Column(name = "fg_cobr_serv", length = 1)
    private String fgCobrServ;

    @Column(name = "no_cta_contb", length = 23)
    private String noCtaContb;

    @Column(name = "cd_hist")
    private Long cdHist;

    @Column(name = "cd_cta_mdo", length = 11)
    private String cdCtaMdo;

    @Column(name = "cd_int_erp", length = 15)
    private String cdIntErp;

    @Column(name = "fg_intcm", length = 1, nullable = false)
    private String fgIntcm;

    @Column(name = "fg_interp", length = 1, nullable = false)
    private String fgInterp;

    @Column(name = "cd_maparat_erp", length = 15)
    private String cdMaparatErp;

    @Column(name = "cd_empr_erp", length = 12)
    private String cdEmprErp;

    @Column(name = "cd_inst_orig", length = 5)
    private String cdInstOrig;

    @Column(name = "cd_original", nullable = false)
    private Long cdOriginal;

    @Column(name = "cd_empresa", length = 5, nullable = false)
    private String cdEmpresa;

    @Column(name = "fg_replica", length = 1, nullable = false)
    private String fgReplica;

    @Column(name = "cd_int_folha", length = 15)
    private String cdIntFolha;

    @Column(name = "cd_unimed", length = 3)
    private String cdUnimed;

    @Column(name = "rowversion")
    private Integer rowVersion;

    @Column(name = "fg_ativo", length = 1)
    private String fgAtivo;

    @Column(name = "cd_cc_mestre")
    private Long cdCcMestre;

    @Column(name = "cd_emp_mestre", length = 5)
    private String cdEmpMestre;

    @Column(name = "fg_recebe_rat_bautodef", length = 1)
    private String fgRecebeRatBAutodef;

    @Column(name = "cd_empresa_agr_x_ind", length = 5)
    private String cdEmpresaAgrXInd;
}
