package algoritmo;

import java.awt.Point;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Poupador extends ProgramaPoupador {
	
	int estado = 0;
	int direcao = 1;
	int movimento = 0;
	int round = 0;
	Boolean FoundCoin = false;
	Boolean RunningAway = false;
	Boolean Stuck = false;
	int[][] map = new int[6][6];
	int tile = 0;
	Stack<Node> Path = new Stack<Node>();
	
	
	public int acao() {		
			
		
		System.out.println("#------------------------------------------------------------------#");								
		Scan();						
		for(int y = 0; y <= 4; y++)
		{
			for(int x = 0; x <= 4; x++)
			{
				System.out.print("" + map[x][y] + ",");
			}
			System.out.println("");
		}		
		System.out.println("Pos:" + sensor.getPosicao().getX() + "," + sensor.getPosicao().getY());
		
		System.out.print("(2,2)");
		for(int i = 0; i <= Path.size() - 1; i++)
		{
			System.out.print(" ---> ");
			System.out.print("(" + Path.get(i).x + "," + Path.get(i).y + ")");			
		}
		System.out.println("");
													
		//SearchForCoins();
		//System.out.println("Movimento: " + movimento);
		
		//Node start = new Node(2,2);
		//Node end = new Node(0,0);
		
		//Astar(start, end, map);				
		
		//System.out.println("Ultimo elemento do caminho: " + Path.lastElement());
		
		//MovePoupador(Path.lastElement());
		
		
		return (int) (movimento);
	}
	
	public void Scan()
	{	
		tile = 0;
		int counter = 0;
		FoundCoin = false;
		RunningAway = false;
		
		for(int y = 0; y <= 4; y++)
		{
			for(int x = 0; x <= 4; x++)
			{
				
				if(counter != 12)
				{
				//if(sensor.getVisaoIdentificacao()[tile] == 4)
				//System.out.println("[" + x + "," + y + "] - " + tile + " - " + sensor.getVisaoIdentificacao()[tile]);
				
				map[x][y] = sensor.getVisaoIdentificacao()[tile];	
				tile += 1;
				counter += 1;
				}
				else
				{
					map[x][y] = 6;
					counter += 1;
				}
				
				if(map[x][y] == 200 || map[x][y] == 210 || map[x][y] == 220 || map[x][y] == 230)
				{
					RunningAway = true;
					Node start = new Node(2,2);
					Node end = new Node(Math.abs(x - 4),Math.abs(y - 4));
					
					Astar(start, end, map);
					
					MovePoupador(Path.lastElement());
				}
				
				if(RunningAway == false)
				{
				if(map[x][y] == 4)
				{
					FoundCoin = true;
					Node start = new Node(2,2);
					Node end = new Node(x,y);
					
					Astar(start, end, map);
					
					//for(int i = 0; i <= Path.size() - 1; i++)
						//System.out.print("(" + Path.get(i).x + "," + Path.get(i).y + ")");
					
					//System.out.println("Ultimo elemento do caminho: " + Path.lastElement());
					
					MovePoupador(Path.lastElement());			
				}
				}
			}
		}
		
		
		if(FoundCoin == false)
		{
		movimento = (int)(Math.random() * 5);
		}
		
		if(RunningAway == true)
		{
			//movimento = 0;
			System.out.println("Enemy Detected" );
		}
	}
	
	public void MovePoupador(Node n)
	{
		if(n.x == 1 && n.y == 2)
		{
			MoveLeft();
		}		
		else if(n.x == 3 && n.y == 2)
		{
		   MoveRight();	
		}		
		else if(n.x == 2 && n.y == 1)
				{
				   MoveUp();	
				}				
		else if(n.x == 2 && n.y == 3)
				{
				   MoveDown();	
				}
		
				
	}
	
	public void SearchForCoins()
	{		
		
		if(RunningAway == false)
		{
			//Procurando Moedas
		if(map[2][1] == 4)
		{
			System.out.println("Move UP");
			MoveUp();
		}		
		else if(map[2][3] == 4)
		{
			System.out.println("Move DOWN");
			MoveDown();
		}		
		else if(map[1][2] == 4)
		{
			System.out.println("Move LEFT");
			MoveLeft();
		}		
		else if(map[2][3] == 4)
		{
			System.out.println("Move RIGHT");
			MoveRight();
		}
		else
		{
			MoveRandom();
		}
		
		}		
		
	
		
	}
	
	public void MoveUp()
	{
		movimento = 1;
	}
	
	public void MoveDown()
	{
		movimento = 2;
	}
	
	public void MoveLeft()
	{
		movimento = 4;
	}
	
	public void MoveRight()
	{
		movimento = 3;
	}
	
	public void MoveRandom()
	{
		movimento = (int)(Math.random() * 5);
	}
	
	
	public void MapScan()
	{
		
	}
	
	class Node
	{
		public int x;
		public int y;
		public int f;
		public int g;
		public int h;
		public Node Parent;
		
		public Node(int X, int Y)
		{
			Parent = null;
			x = X;
			y = Y;
		}
		
	
	
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		
		if(!(o instanceof Node))
		{
			return false;
		}
		
		Node n = (Node) o;
		
		return Double.compare(x, n.x) == 0 && Double.compare(y, n.y) == 0;
	}
	
	}
	
	public void Astar(Node origin, Node destination, int[][] map)
	{				
		Path.clear();
		List<Node> OpenList = new ArrayList<Node>();
		List<Node> ClosedList = new ArrayList<Node>();
		List<Node> Vizinhos = new ArrayList<Node>();		
		
		//System.out.println("Start tile: (" + origin.x + "," + origin.y + ")");
		
		Node current = origin;
		
		//System.out.println("Current tile: (" + current.x + "," + current.y + ")");
		//System.out.println("End tile: (" + destination.x + "," + destination.y + ")");
		OpenList.add(origin);
		
		while(!OpenList.isEmpty())
		{
			//System.out.print("OpenList Nodes: ");
			for(int i = 0; i <= OpenList.size() - 1; i++)
			{				
				//System.out.print(" (" + OpenList.get(i).x + "," + OpenList.get(i).y + "),");
			}
			//System.out.println("");
			
			//System.out.print("ClosedList Nodes: ");
			for(int i = 0; i <= ClosedList.size() - 1; i++)
			{				
				//System.out.print(" (" + ClosedList.get(i).x + "," + ClosedList.get(i).y + "),");
			}
			
			//System.out.println("");
			
			//System.out.println("Current Node: (" + OpenList.get(0).x + "," + OpenList.get(0).y + ")");
			current = OpenList.get(0);
			
			//System.out.println("Removed Current Node: (" + current.x + "," + current.y + ")");
			OpenList.remove(current);
									
			//System.out.println("Added to Closed List Node: (" + current.x + "," + current.y + ")");
			ClosedList.add(current);
			
			//System.out.println("Checking neighbors of Node: (" + current.x + "," + current.y + ")");
			Vizinhos = GetVizinhos(current, map);					
		

		for (Node n : Vizinhos)
        {
			if(!ClosedList.contains(n))
			{
				if(!OpenList.contains(n))
				{
					n.Parent = current;
					n.g = current.g + Math.abs(n.x - current.x) + Math.abs(n.y - current.y);
		            n.h = Math.abs(n.x - destination.x) + Math.abs(n.y - destination.y);
		            n.f = n.g + n.h;
		            //System.out.println("Added Node(" + n.x + "," + n.y + ") to the open list.");
		            OpenList.add(n);
				}
			}
			
					
            
        }
		
		if(current.equals(destination))
		{
			//System.out.println("Found path");
			
			Node temp = ClosedList.get(ClosedList.size() - 1);
						

            do
            {
            	Path.push(temp);
            	temp = temp.Parent;
            }
            while(temp != origin && temp != null);
            
		}
						
		
		}
		
		
		
		
		
	}
	
	public List<Node> GetVizinhos(Node n, int[][] map)
	{
		List<Node> temp = new ArrayList<Node>();
        //Falta saber o motivo do programa crashar quando considera pastilha do poder e parede como vizinhos nao possiveis.
		if(!(n.x == 0))
		{
        if(map[n.x - 1][n.y] != 1)
        { 
        	
        	
        //System.out.println("Node(" + (n.x - 1) + "," + n.y + ") adicionado para a lista de vizinhos.");
        temp.add(new Node(n.x - 1, n.y));	
        	
        }
		}
		
		if(!(n.y == 0))
		{
        if (map[n.x][n.y - 1] != 1)
        {
        	
        //System.out.println("Node(" + n.x + "," + (n.y - 1) + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x, n.y - 1));
        	
        }
		}
		
		if(!(n.x == 5))
		{
        if (map[n.x + 1][n.y] != 1)
        {
        	
        //System.out.println("Node(" + (n.x + 1) + "," + n.y + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x + 1, n.y));
        	
        }
		}
		
		if(!(n.y == 5))
		{
        if (map[n.x][n.y + 1] != 1)
        {
        	
        //System.out.println("Node(" + n.x + "," + (n.y + 1) + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x, n.y + 1));
        	
        }
		}	
		

        return temp;
	}
	
	

}
