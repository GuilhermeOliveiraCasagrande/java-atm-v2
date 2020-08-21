package maquininha;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("*******************************************");
		Run();	
	}
	
	static void Run() throws IOException {
		Scanner usrInput = new Scanner(System.in);
		File arq = new File("C:\\Users\\gui64\\Desktop\\C�digos\\Java\\eclipse_workspaces\\meus-portfolios-workspace\\maquininha\\src\\users.txt");
		HashMap<String, Conta> db = ReadToHashMap(arq);
		//Conta usada = new Conta("supimpa", "1234567", 2156.0);
		Conta usada = logarConta(db, usrInput);
		Menu(usada, db, usrInput);
		Sair(db, arq);
		usrInput.close();
	}
	
	// Transforma o arquivo de usu�rios em HashMap
	static HashMap<String, Conta> ReadToHashMap(File f) throws IOException {

		Scanner leitor = new Scanner(f);
		Scanner input = new Scanner(System.in);
		HashMap<String, Conta> hs = new HashMap<String, Conta>();
		if(!leitor.hasNext()) {
			System.out.println("Nenhuma conta encontrada no banco de dados");
			System.out.println("Criando uma nova conta");
			CriarConta(hs, input);
		}
		while (leitor.hasNext()) {
			String dado = leitor.nextLine();
			String[] dArray = dado.split(",");
			Conta c = new Conta(dArray[0], dArray[1], Double.parseDouble(dArray[2]));
			hs.put(c.getUser(), c);
		}
		leitor.close();
		return hs;
	}

	// Faz o login da conta
	static Conta logarConta(HashMap<String, Conta> db, Scanner in) throws IOException {
		System.out.println("*******************************************");
		// Primeiro verifica se n�o precisa criar uma nova conta
		System.out.println("Criar novo usu�rio? S/n");
		while (true) {
			String conf = in.next();
			if (conf.matches("S")) {
				CriarConta(db, in);
				break;
			} else if (conf.matches("n")) {
				System.out.println("N�o criando conta");
				break;
			} else {
				System.out.println("Por favor escreva S ou n");
				continue;
			}
		}

		// Depois v� se o usu�rio e senha provido existe no Banco de Dados (db)
		while (true) {
			System.out.print("USER: ");
			String userInput = in.next();

			Set<String> users = db.keySet();
			if (!users.contains(userInput)) {
				System.out.println("Esse usu�rio n�o existe");
				continue;
			}
			Conta c = db.get(userInput);

			System.out.print("SENHA: ");
			String senhaInput = in.next();
			if (!c.getSenha().matches(senhaInput)) {
				System.out.println("Senha est� incorreta");
				continue;
			}
			return c;

		}
	}

	// Op��es de como modificar valores da conta
	static void Menu(Conta c, HashMap<String, Conta> db, Scanner in) throws IOException {
		boolean sair = false;
		while (sair == false) {
			System.out.println("*****************************************************");
			System.out.println("O que quer fazer " + c.getUser() + "?");
			System.out.println("SALDO DEPOSITADO: " + c.getSaldo());
			System.out.println("1 - Adicionar saldo");
			System.out.println("2 - Remover saldo");
			System.out.println("3 - Mudar usu�rio");
			System.out.println("4 - Mudar senha");
			System.out.println("5 - remover conta");
			System.out.println("6 - hist�rico de transa��es");
			System.out.println("7 - sair");
			System.out.print("OP��O: ");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();
			switch (option) {
			case 1: // add saldo
				System.out.println("Qual a quantidade de saldo a adinicionar?");
				c.addSaldo(s.nextDouble());
				break;
			case 2:// tira saldo
				System.out.println("Qual a quantidade de saldo a ser retirada?");
				double rm = s.nextDouble();
				if (c.getSaldo() < rm) {
					System.out.println("Saldo insuficiente para a transa��o");
				} else {
					c.addSaldo(-rm);
				}
				break;
			case 3: // Mudar username
				System.out.print("Novo nome de usu�rio: ");
				String newNome = s.next();
				c.setUser(newNome);
				break;
			case 4: // Mudar senha
				System.out.print("Nova senha: ");
				String newSenha = s.next();
				c.setSenha(newSenha);
				break;
			case 5: // Deletar conta
				System.out.println("Voc� tem certeza? S/n ");
				String conf = s.next();
				if (conf.matches("S")) {
					db.remove(c.getUser(), c);
					sair = true;
				} else {
					System.out.println("Cancelando...");
					continue;
				}

				break;
			case 6:
				System.out.println("Hist�rico de transa��es dessa conta");
				System.out.println("Ordenados da mais velha pra mais recente");
				break;
			case 7:
				c.escreveHist();
				System.out.println("Saindo...");
				sair = true;
				break;
			default:
				System.out.println("Escolha uma op��o v�lida");
				break;
			}
		}
	}

	// Salvar tudo no arquivo de usu�rios e encerrar
	static void Sair(HashMap<String, Conta> db, File f) throws IOException {
		System.out.println("Salvando...");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

		Set<String> users = db.keySet();
		for (String user : users) {
			Conta c = db.get(user);
			bw.write(c.toString());
			bw.newLine();
		}
		System.out.println("Fechando...");
		bw.close();
	}

	// Criar uma nova conta e coloca no db
	static void CriarConta(HashMap<String, Conta> db, Scanner s) throws IOException {
		System.out.print("USER: ");String nome = s.next();
		System.out.print("SENHA: ");String senha = s.next();
		Conta c = new Conta(nome, senha, 0.0);
		db.put(c.getUser(), c);
	}

}