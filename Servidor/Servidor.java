import java.util.*;
public class Servidor
{
	public static void main(String[] args)
	{
		if(args.length > 1)
		{
			System.err.println("Uso esperado: java Servidor [porta]");
			return;
		}

		String porta = null;
		if(args.length == 1)
			porta = args[0];
		ArrayList<Parceiro> jogadores = new ArrayList<Parceiro>();

		AceitadoraDeConexao aceitadoraDeConexao = null;
		try
		{
			aceitadoraDeConexao = new AceitadoraDeConexao(porta, jogadores);
			aceitadoraDeConexao.start();
		}
		catch(Exception ex)
		{
			System.err.println("Escolha uma porta livre e apropriada para o uso!\n");
			return;
		}

		for(;;)
		{
			System.out.println ("O servidor esta ativo! Para desativa-lo,");
			System.out.println ("use o comando \"desativar\"\n");
            System.out.print   ("> ");

            String comando = null;
            try
            {
				comando = Teclado.getUmString();
			}
			catch(Exception ex)
			{}

			if(comando.toLowerCase().equals("desativar"))
			{
				synchronized(jogadores)
				{
					for(Parceiro jogador: jogadores)
					{
						ComunicadoDeDesligamento com = new ComunicadoDeDesligamento();
						try
						{
							jogador.receba(com);
							jogador.adeus();
						}
						catch(Exception ex)
						{}
					}
				}
				System.out.println("O servidor foi desativado!\n");
				System.exit(0);
			}
			else
				System.out.println("Comando inv�lido!\n");
		}
	}
}