package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		preco = sessao.getPreco();
		
		BigDecimal multiplicador = null;
		TipoDeEspetaculo tipoDeEspetaculoDaSessao = sessao.getEspetaculo().getTipo();
		
		boolean espetaculoEhCinemaOuShow = tipoDeEspetaculoDaSessao.equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW);
		boolean espetaculoEhBalletOuOrquestra= tipoDeEspetaculoDaSessao.equals(TipoDeEspetaculo.BALLET) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA);
		
		if(espetaculoEhCinemaOuShow) {
			if (sessao.ingressosEstaoAcabando(0.05))
				multiplicador = BigDecimal.valueOf(0.10);	
			
   	    } else if(espetaculoEhBalletOuOrquestra) { 
			if (sessao.ingressosEstaoAcabando(0.50))
				multiplicador = BigDecimal.valueOf(0.20);	
   	    }
		
		if (multiplicador != null) {
			
			if (espetaculoEhBalletOuOrquestra) {
				if(sessao.getDuracaoEmMinutos() > 60){
					preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
				} else {
					preco = preco.add(sessao.getPreco().multiply(multiplicador));
				}
			} else {
				preco = preco.add(sessao.getPreco().multiply(multiplicador));
			}
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}