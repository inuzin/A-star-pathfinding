package algoritmo;

import java.util.ArrayList;
import java.util.List;

public class Poupador extends ProgramaPoupador {
	
	int estado = 0;
	int[][] map = new int[6][6];
	int tile = 0;
	
	
	
	public int acao() {		
				
		System.out.println("Pos:" + sensor.getPosicao().getX() + "," + sensor.getPosicao().getY());
		Scan();
		for(int x = 0; x <= 4; x++)
		{
			for(int y = 0; y <= 4; y++)
			{
				System.out.print("" + map[x][y] + ",");
			}
			System.out.println("");
		}
		System.out.println("#------------------------------------------------------------------#");
		return (int) (Math.random() * 5);
	}
	
	public void Scan()
	{	
		tile = 0;
		
		for(int x = 0; x <= 4; x++)
		{
			for(int y = 0; y <= 4; y++)
			{
				if(tile <= 23)
				{
				map[x][y] = sensor.getVisaoIdentificacao()[tile];
				
				tile += 1;
				}
			}
		}

	}
	
	public void Astar()
	{
		
		class Node
		{
			public int x;
			public int y;
			public int fcost;
			
			public Node(int X, int Y, int Fcost)
			{
				x = X;
				y = Y;
				fcost = Fcost;
			}
			
		}
		
		List<Node> OpenList = new ArrayList<Node>();
		List<Node> ClosedList = new ArrayList<Node>();
		List<Node> Vizinhos = new ArrayList<Node>();
		
		Node start = new Node((int)sensor.getPosicao().getX(), (int)sensor.getPosicao().getY(), 0);
		
		//Temporario
		Node end = new Node((int)sensor.getPosicao().getX() - 2, (int)sensor.getPosicao().getY() - 2, 0);
		
		Node current = start;
		
		OpenList.add(start);
		
		while(!OpenList.isEmpty())
		{
			current = OpenList.get(0);
			OpenList.remove(current);
			ClosedList.add(current);
			
			if(current == end)
			{
				//Caminho encontrado
			}
		}
		
	}
	
	

}