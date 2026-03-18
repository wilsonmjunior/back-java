package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.pimscs.repository.FrenteTrabalhoRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.FrenteTrabalho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrenteTrabalhoService {

    private final FrenteTrabalhoRepository repository;

    public List<FrenteTrabalho> findAll() {
        return repository.findAll();
    }
}
