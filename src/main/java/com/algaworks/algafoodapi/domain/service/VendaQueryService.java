package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

}
