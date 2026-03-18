package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "FREN_TRAB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrenteTrabalho {

    @Id
    @Column(name = "cd_fren_trab", nullable = false)
    private Long cdFrenTrab;

    @Column(name = "de_fren_trab", length = 50)
    private String deFrenTrab;

    @Column(name = "de_resp_fren_trab", length = 50)
    private String deRespFrenTrab;

    @Column(name = "dt_validade")
    private LocalDate dtValidade;

    @Column(name = "cd_fren_tran")
    private Integer cdFrenTran;

    @Column(name = "cd_fren_corte")
    private Integer cdFrenCorte;

    @Column(name = "cd_fren_plan")
    private Integer cdFrenPlan;

    @Column(name = "dt_criacao", nullable = false)
    private LocalDate dtCriacao;

    @Column(name = "cd_usuario_cri", length = 30)
    private String cdUsuarioCri;

    @Column(name = "dt_alteracao")
    private LocalDate dtAlteracao;

    @Column(name = "cd_usuario_alt", length = 30)
    private String cdUsuarioAlt;

    @Column(name = "fg_ativo", length = 1, nullable = false)
    private String fgAtivo;
}

