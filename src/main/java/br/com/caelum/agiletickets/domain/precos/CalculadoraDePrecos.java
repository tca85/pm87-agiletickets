package br.com.caelum.agiletickets.domain.precos;
import java.math.BigDecimal;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
				
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			preco = aumentarPrecoCinemaShow(sessao);
			
		} else if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = aumentarPrecoBalletOrquestra(sessao);
			
		} else {
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal aumentarPrecoBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		double totalIngressos;
		double valorAumento;
		
		totalIngressos = 0.50;
		valorAumento = 0.20;
		
		preco = aumentarPrecoUltimosIngressos(sessao, totalIngressos, valorAumento);
		preco = aumentarPrecoDuracaoMaior60Min(sessao, preco);
		return preco;
	}

	private static BigDecimal aumentarPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		double totalIngressos;
		double valorAumento;
		
		totalIngressos = 0.05;
		valorAumento = 0.10;
		
		preco = aumentarPrecoUltimosIngressos(sessao, totalIngressos, valorAumento);
		return preco;
	}

	private static BigDecimal aumentarPrecoUltimosIngressos(Sessao sessao, double totalIngressos, double valorAumento) {
		BigDecimal preco;
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= totalIngressos) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(valorAumento)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}
	
	private static BigDecimal aumentarPrecoDuracaoMaior60Min(Sessao sessao, BigDecimal preco) {
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}
	

}