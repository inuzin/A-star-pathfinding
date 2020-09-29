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
	int posX = 0;
	int posY = 0;
	int runfromenemy = 0;
	int holdvarx1, holdvarx2, holdvary1, holdvary2 = 0;
	Boolean Explore = false;
	Boolean FoundCoin = false;
	Boolean FoundBanco = false;
	Boolean RunningAway = false;
	Boolean Stuck = false;
	int[][] map = new int[6][6];
	int[][] world = new int[50][50];
	int tile = 0;
	Stack<Node> Path = new Stack<Node>();
	
	
	public int acao() {		
			
		System.out.println("#------------------------------------------------------------------#");
		System.out.println("World map");
		for(int y = 0; y <= 30; y++)
		{
			for(int x = 0; x <= 30; x++)
			{
				System.out.print("" + world[x][y] + ",");
			}
			System.out.println("");
		}
		System.out.println("#------------------------------------------------------------------#");
		System.out.println("Bird view");
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
		
		AutoUnstuck();
		posX = (int)sensor.getPosicao().getX();
		posY = (int)sensor.getPosicao().getY();
		round += 1;
		return (int) (movimento);
	}
	
	public void AutoUnstuck()
	{
		if(round >= 1)
		{
		if(posX == (int)sensor.getPosicao().getX())	
		{
			if(posY == (int)sensor.getPosicao().getY())	
			{
				movimento = (int)(Math.random() * 5);
				round = 0;
			}
		}
		}
	}
	
	public void Scan()
	{	
		tile = 0;
		int counter = 0;
		FoundCoin = false;
		RunningAway = false;
		Explore = false;
		FoundBanco = false;
			
		
		for(int y = 0; y <= 4; y++)
		{
			for(int x = 0; x <= 4; x++)
			{
				
				if(counter != 12)
				{
				//if(sensor.getVisaoIdentificacao()[tile] == 4)
				//System.out.println("[" + x + "," + y + "] - " + tile + " - " + sensor.getVisaoIdentificacao()[tile]);
					
				if((int)sensor.getPosicao().getX() != 0)	
				holdvarx1 = map[1][2];
				if((int)sensor.getPosicao().getX() != 29)
				holdvarx2 = map[3][2];
				if((int)sensor.getPosicao().getY() != 0)
				holdvary1 = map[2][1];
				if((int)sensor.getPosicao().getY() != 29)
				holdvary2 = map[2][3];			
				
				map[x][y] = sensor.getVisaoIdentificacao()[tile];	
				tile += 1;
				counter += 1;
				}
				else
				{
					map[x][y] = 6;
					counter += 1;
				}
																
				if(map[x][y] == 3)
				{
					
					
					if(sensor.getNumeroDeMoedas() >= 10)
					{
					FoundBanco = true;	
					Node start = new Node(2,2);
					Node end = new Node(x,y);	
					
					Astar(start, end, map);
					
					MovePoupador(Path.lastElement());
					}
				}
				
				if(FoundBanco == false)
				{
				if(map[x][y] == 200 || map[x][y] == 210 || map[x][y] == 220 || map[x][y] == 230)
				{
					RunningAway = true;
					Node start = new Node(2,2);
					Node end = new Node(Math.abs(x - 4),Math.abs(y - 4));
					
					if(map[Math.abs(x - 4)][Math.abs(y - 4)] != 1 && map[Math.abs(x - 4)][Math.abs(y - 4)] != -2 && map[Math.abs(x - 4)][Math.abs(y - 4)] != 5)
					{
					end = new Node(Math.abs(x - 4),Math.abs(y - 4));
					}
					else if(map[Math.abs(x - 4)][y] != 1 && map[Math.abs(x - 4)][y] != -2 && map[Math.abs(x - 4)][y] != 5)
					{
					end = new Node(Math.abs(x - 4),Math.abs(y));	
					}
					else if(map[x][Math.abs(y - 4)] != 1 && map[Math.abs(x - 4)][y] != -2 && map[Math.abs(x - 4)][y] != 5)
					{
					end = new Node(Math.abs(x),Math.abs(y - 4));	
					}
					else
					{
					System.out.println("CAMINHO IMPOSSIVEL------------------------------------------------------------------------------");
					movimento = (int)(Math.random() * 5);
					return;
					}
					
					Astar(start, end, map);
					MovePoupador(Path.lastElement());
				}
				}
				
				if(RunningAway == false)
				{
					if(FoundBanco == false)
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
		}
		
		for(int y = 0; y <= 30; y++)
		{
			for(int x = 0; x <= 30; x++)
			{
				//world[x][y] = 0;
				
				world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY()] = 2;
				if(holdvarx1 != 0 && (int)sensor.getPosicao().getX() != 0)
				world[(int)sensor.getPosicao().getX() - 1][(int)sensor.getPosicao().getY()] = holdvarx1;
				if(holdvarx2 != 0 && (int)sensor.getPosicao().getX() != 29)
				world[(int)sensor.getPosicao().getX() + 1][(int)sensor.getPosicao().getY()] = holdvarx2;
				if(holdvary1 != 0 && (int)sensor.getPosicao().getY() != 0)
				world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() - 1] = holdvary1;
				if(holdvary2 != 0 && (int)sensor.getPosicao().getY() != 29)
				world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() + 1] = holdvary2;
				
				if(RunningAway == false)
				{
					if(FoundCoin == false)
					{
						if(FoundBanco == false)
						{
							Explore = true;
						
							
						if(world[(int)sensor.getPosicao().getX() + 1][(int)sensor.getPosicao().getY()] != 1 && world[(int)sensor.getPosicao().getX() + 1][(int)sensor.getPosicao().getY()] != -1 && world[(int)sensor.getPosicao().getX() + 1][(int)sensor.getPosicao().getY()] != 2)
						{
							MoveRight();
						}
						else if((int)sensor.getPosicao().getX() != 0 && world[(int)sensor.getPosicao().getX() - 1][(int)sensor.getPosicao().getY()] != 1 && world[(int)sensor.getPosicao().getX() - 1][(int)sensor.getPosicao().getY()] != -1 && world[(int)sensor.getPosicao().getX() - 1][(int)sensor.getPosicao().getY()] != 2)
						{
							
							MoveLeft();
						}
						else if((int)sensor.getPosicao().getY() != 0 && world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() - 1] != 1 && world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() - 1] != -1 && world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() - 1] != 2)
						{
							
							MoveUp();
						}
						else if(world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() + 1] != 1 && world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() + 1] != -1 && world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() + 1] != 2)
						{
							MoveDown();
						}
						else
						{
							movimento = (int)(Math.random() * 5);	
						}
						}
						
					}
				}
				
				//movimento = (int)(Math.random() * 5);
				
				/*
				if((int)sensor.getPosicao().getX() != 29 && (int)sensor.getPosicao().getY() != 29)
				{
					if((int)sensor.getPosicao().getX() != 0 &&(int)sensor.getPosicao().getY() != 0)
					{
				if(world[(int)sensor.getPosicao().getX() - 1][(int)sensor.getPosicao().getY()] != 1)
				{
					movimento = (int)(Math.random() * 5);	
				}
				else if(world[(int)sensor.getPosicao().getX() + 1][(int)sensor.getPosicao().getY()] != 1)
				{
					movimento = (int)(Math.random() * 4);
				}
				else if(world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() + 1] != 1)
				{
					movimento = (int)(Math.random() * 3);
				}
				else if(world[(int)sensor.getPosicao().getX()][(int)sensor.getPosicao().getY() - 1] != 1)
				{
					movimento = (int)(Math.random() * 2);	
				}
				else
				{
					movimento = (int)(Math.random() * 5);	
				}
					}
					else
					{
						movimento = (int)(Math.random() * 5);	
					}
				}
				else
				{
					movimento = (int)(Math.random() * 5);	
				}
				*/
				
			}
		}
		
		
		if(FoundCoin == false)
		{
			if(Explore == false)
			{
				if(FoundBanco == false)
				{
		movimento = (int)(Math.random() * 5);
				}
			}
		}
		
		if(RunningAway == true)
		{
			//movimento = 0;
			System.out.println("Enemy Detected" );
		}
		
		if(Explore == true)
		{
			if(sensor.getPosicao().getY() == 0)
			{
				MoveDown();
			}
			
			if(sensor.getPosicao().getX() == 0)
			{
				MoveRight();
			}
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
        if(map[n.x - 1][n.y] != 1 && map[n.x - 1][n.y] != 5)
        { 
        	
        	
        //System.out.println("Node(" + (n.x - 1) + "," + n.y + ") adicionado para a lista de vizinhos.");
        temp.add(new Node(n.x - 1, n.y));	
        	
        }
		}
		
		if(!(n.y == 0))
		{
        if (map[n.x][n.y - 1] != 1 && map[n.x][n.y - 1] != 5)
        {
        	
        //System.out.println("Node(" + n.x + "," + (n.y - 1) + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x, n.y - 1));
        	
        }
		}
		
		if(!(n.x == 5))
		{
        if (map[n.x + 1][n.y] != 1 && map[n.x + 1][n.y] != 5)
        {
        	
        //System.out.println("Node(" + (n.x + 1) + "," + n.y + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x + 1, n.y));
        	
        }
		}
		
		if(!(n.y == 5))
		{
        if (map[n.x][n.y + 1] != 1 && map[n.x][n.y + 1] != 5)
        {
        	
        //System.out.println("Node(" + n.x + "," + (n.y + 1) + ") adicionado para a lista de vizinhos.");	
        temp.add(new Node(n.x, n.y + 1));
        	
        }
		}	
		

        return temp;
	}
	
	

}
