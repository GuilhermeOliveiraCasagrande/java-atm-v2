package maquininha;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Conta{

	private String user;
	private String senha;
	private double saldo;
	private Scanner s;
	private BufferedWriter bw;
	private ArrayList<String> hist = new ArrayList<String>();

	public Conta(String user, String senha, double saldo) throws IOException {
		this.user = user;
		this.senha = senha;
		this.saldo = saldo;
		this.bw = new BufferedWriter(new FileWriter(new File("C:\\\\Users\\\\gui64\\\\Desktop\\\\Códigos\\\\Java\\\\eclipse_workspaces\\\\meus-portfolios-workspace\\\\maquininha\\\\src\\\\" + this.user + ".txt")));
		this.s = new Scanner( new File("C:\\\\Users\\\\gui64\\\\Desktop\\\\Códigos\\\\Java\\\\eclipse_workspaces\\\\meus-portfolios-workspace\\\\maquininha\\\\src\\\\" + this.user + ".txt"));
		readHist();
	}

	public String toString() {
		return user + "," + senha + "," + saldo;
	}
	
	public String getUser(){
		return this.user;
	}

	public String getSenha() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void addSaldo(double saldo) throws IOException {
		this.saldo += saldo;
		if(saldo > 0) {
			hist.add("+" + Double.toString(saldo));
		} else {
			hist.add("-" + Double.toString(saldo));
		}
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public ArrayList<String> getHist(){
		return this.getHist();
	}
	public void escreveHist() throws IOException {
		for(String s: hist) {
			bw.write(s);
		}
	}
	public void readHist() throws IOException {
		while(s.hasNext()) {
			hist.add(s.next());
		}
	}
	
}
