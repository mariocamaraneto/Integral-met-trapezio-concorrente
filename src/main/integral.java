package main;

import trapezio.trapezio;

public class integral {

	double x1, x2;
	int num_trapezio;
	trapezio[] parts_trapezio;

	public integral(double x1, double x2, int num_trapezio) {
		this.x1 = x1;
		this.x2 = x2;
		this.num_trapezio = num_trapezio;
		preenche_array_trapezio();
	}

	private void preenche_array_trapezio() {

		parts_trapezio = new trapezio[num_trapezio];

		double tamanho_passo = (x2 - x1) / num_trapezio;
		double valor_inicial = x1;

		for (int i = 0; i < num_trapezio; i++) {

			parts_trapezio[i] = new trapezio(valor_inicial, valor_inicial
					+ tamanho_passo);
			valor_inicial = valor_inicial + tamanho_passo;
		}

	}

	public double get_valor() {
		// retorna a soma de todos os trapezios
		// portanto retorna o valor da integral no intervalo x1 e x2 fornecido
		double valor_total = 0;
		for (int i = 0; i < num_trapezio; i++) {
			valor_total = valor_total + parts_trapezio[i].get_area();
		}
		return valor_total;
	}
}
