//Mario Camara Neto
//Brunno Navarro

package main;

public class Main {

	static long tempo_inicial = 0;
	static long tempo_final = 0;
	static long soma_tempos = 0;
	static long tempo_execucao = 0;
	static int qtd_execucoes = 0;
	static int num_threads;
	static int num_trapezios;
	static double soma_area = 0;

	public static void main(String[] args) {

		boolean flag_parada = false;

		while (!flag_parada) {

			num_threads = 1;
			num_trapezios = 1000;

			while (num_threads < 32) {

				System.out.println("\nUsando " + num_threads + " threads.");

				while (num_trapezios <= 10000000) {

					System.out.println("Num de trapezios: " + num_trapezios);
					
					if ( num_trapezios != 100000 || num_threads != 4) {
						// faz o teste de convergencia de tempo de execução
						tempo_inicial = System.currentTimeMillis();
					}
					
					if (num_threads == 4 && num_trapezios == 100000) {
						// faz o teste de convergencia de tempo de execução
						tempo_inicial = System.currentTimeMillis();
						qtd_execucoes++;
					}

					inicia_thr(); // inicia as threads

					if (num_trapezios != 100000 || num_threads != 4){
						tempo_final = System.currentTimeMillis();
						tempo_execucao = tempo_final - tempo_inicial;
						
						System.out.println("------------------------here---------->>>tempo execução" + tempo_execucao);
						
					}
					
					if (num_threads == 4 && num_trapezios == 100000) {

						tempo_final = System.currentTimeMillis();
						tempo_execucao = tempo_final - tempo_inicial;
						
						System.out.println("Tempo de execução: " + tempo_execucao);
						
						if (qtd_execucoes > 1 && condicao_parada_alcancada()) {
							// se a condição de parada foi alcançada então não
							// há necessidade de reexecução dos testes
							flag_parada = true;
						}
						soma_tempos += tempo_execucao;
					}
					// contador para quantidade de trapezios
					num_trapezios *= 10;
				}
				num_threads *= 2;
				num_trapezios = 1000;
			}
		}
		System.out.println("Fim da execucao.");
		System.out.println("\nForam necessarias " + qtd_execucoes
				+ " execuções para convergir os tempos .");
	}// fim da main

	public static boolean condicao_parada_alcancada() {
		// função verifica se a diferença Media dos tempos anteriores e da Media
		// atual tem uma diferença menor que 1%
		double media_atual = ((soma_tempos + tempo_execucao) / qtd_execucoes);
		double media_anterior = (soma_tempos / (qtd_execucoes - 1));

		System.out.println("Diferença percentual da media dos tempos: "
				+ media_anterior / media_atual);
		

		if (media_anterior / media_atual > 0.99
				&& media_anterior / media_atual < 1.01) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>  Media do tempo: " + media_atual);
			return true;
		}
		return false;
	}

	public static void inicia_thr() {
		Integral_Paralelo[] list_parts_integral = new Integral_Paralelo[num_threads];
		Thread[] list_thread = new Thread[num_threads];
		double tam_intervalo = 2 / (double) num_threads;

		for (int i = 0; i < num_threads; i++) {
			//calcula o intervalo de integração para cada thread 
			double x1 = -1 + tam_intervalo * i;
			double x2 = -1 + tam_intervalo * (i + 1);
			list_parts_integral[i] = new Integral_Paralelo(x1, x2,
					num_trapezios/num_threads);

			//instancia threads e as inicia
			list_thread[i] = new Thread(list_parts_integral[i]);
			list_thread[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			//barreira de sincronismo com todas as threads
			try {
				list_thread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//soma os valores parciais da integral apartir das threads 
			soma_area += list_parts_integral[i].getArea_total();

		}
		

		System.out.println("O valor da integral é " + soma_area);
		soma_area = 0;
	}
}