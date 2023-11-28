import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	Scanner sc = new Scanner(System.in);
	Random rand= new Random();
	
	//Game Data
	String Username;
	Integer Money;
	Integer Difficulty;
	Integer OrdersDone;
	Integer CurrentOrder;
	Integer LevelUpTarget;
	String[] toys = {"Teddy Bear", "Toy Car", "Toy Plane", "RC Car", "Train Set", "Transform Robot"};
	int[] toyn = {0, 0, 0, 0, 0, 0};
	int[] workers = {0, 0, 0, 0, 0};
	
	String toy;
	Integer Countdown;
	Integer Level;
	Integer Quantity;
	Integer TotalWorkers;
	Integer x;
	
	/*toys array list:
	 * Teddy Bear [0]
	 * Toy Car [1]
	 * Toy Plane [2]
	 * RC Car [3]
	 * Train Set [4]
	 * Transform Robot [5] */
	
	/*workers array list:
	 * Level 1 worker [0]
	 * Level 2 worker [1]
	 * Level 3 worker [2]
	 * Level 4 worker [3]
	 * Level 5 worker [4]*/
	
	void Cls() {  
		for(int i=0; i<50; i++) {
			System.out.println();
		}
	} 
	
	void Title() {
		System.out.println(" ___            _ _           ___                 __ __ ");
		System.out.println("|_ _|___  _ _  | | | ___  ___|_ _|___  _ _  _ _  |  \\  \\ ___ ._ _  ___  ___  ___  _ _ ");
		System.out.println(" | |/ . \\| | | | ' |<_> |/ | '| |/ . \\| '_>| | | |     |<_> || ' |<_> |/ . |/ ._>| '_>");
		System.out.println(" |_|\\___/`_. | |__/ <___|\\_|_.|_|\\___/|_|  `_. | |_|_|_|<___||_|_|<___|\\_. |\\___.|_|  ");
		System.out.println("         <___'                             <___'                       <___'          ");
		System.out.println("");
	}
	
	void Menu() throws IOException {
		
		Cls();
		int inp;
		do {
			Cls();
			Title();
			System.out.println("1. New Game");
			System.out.println("2. Load Game");
			System.out.println("3. High Score");
			System.out.println("4. Exit");
			System.out.print(">> ");
			inp = sc.nextInt();
			sc.nextLine();
		} while (inp < 1 || inp > 4);
		
		switch (inp) {
		case 1:
			Cls();
			NewGame();
			break;
			
		case 2:
			Cls();
			LoadGame();
			break;
			
		case 3:
			Cls();
			HighScore();
			break;
			
		case 4:
			Cls();
			System.out.println("Thank you for playing the game.");
			System.exit(0);
			break;

		default:
			break;
		}
		
	}
	
	boolean checkExists(String filename, String name) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		boolean exists = false;
		
		while((line = reader.readLine()) != null) {
			if(line.equals(name)) {
				exists = true;
				break;
			}
		}
		reader.close();
		return exists;
	}
	
	void NewGame() throws IOException {
		String name;
		boolean exists;
		
		do {
			Cls();
		
			System.out.print("Input player's name [0 to go back]: ");
			name = sc.nextLine();
			
			if(name.equals("0") == true) {
				Cls();
				Menu();
			}
			
			
		}while (name.length() < 3 || name.length() > 20);
		
	/* If the name inputted is already in the save file (save.txt) or in the score file (hiscore.txt), 
	ask the player to input another name.*/
		
		File saveFile = new File("save.txt");
		if(!saveFile.exists()) {
			saveFile.createNewFile();
		}
		
		File hiscore = new File("hiscore.txt");
		if(!hiscore.exists()) {
			hiscore.createNewFile();
		}
		
		exists = checkExists("save.txt", name);
		
		if(!exists) {
			exists = checkExists("hiscore.txt", name);
		}
		
		if(exists == true) {
			NewGame();
		}
		else {
			
			try {
				FileWriter saveFile1;
				saveFile1 = new FileWriter("save.txt", true);
				saveFile1.write(name + "\n");
				saveFile1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			try {
				FileWriter gameFile = new FileWriter(name + ".txt");
				PrintWriter printfile = new PrintWriter(name + ".txt");
				String pname = name;
				int money = 0;
				int diff = 1;
				int ordersdone = 0;
				int w1 = 5;
				workers[0] = w1;
				int w2 = workers[1];
				int w3 = workers[2];
				int w4 = workers[3];
				int w5 = workers[4];
				int t1 = toyn[0];
				int t2 = toyn[1];
				int t3 = toyn[2];
				int t4 = toyn[3];
				int t5 = toyn[4];
				int t6 = toyn[5];
				int currorder = 0;
				int lvlup = levelUp(diff);
				
				printfile.printf("%s#%d#%d#%d", pname, money, diff, ordersdone);
				printfile.printf("#%d#%d#%d#%d#%d", w1, w2, w3, w4, w5);
				printfile.printf("#%d#%d#%d#%d#%d#%d", t1, t2, t3, t4, t5, t6);
				printfile.printf("#%d#%d", currorder, lvlup);
				printfile.close();
				gameFile.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}	
		String gameFile = name + ".txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(gameFile))) {
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] split = line.split("#");
				Username = split[0];
				Money = Integer.parseInt(split[1]);
				Difficulty = Integer.parseInt(split[2]);
				OrdersDone = Integer.parseInt(split[3]);
				workers[0] = Integer.parseInt(split[4]);
				workers[1] = Integer.parseInt(split[5]);
				workers[2] = Integer.parseInt(split[6]);
				workers[3] = Integer.parseInt(split[7]);
				workers[4] = Integer.parseInt(split[8]);
				toyn[0] = Integer.parseInt(split[9]);
				toyn[1] = Integer.parseInt(split[10]);
				toyn[2] = Integer.parseInt(split[11]);
				toyn[3] = Integer.parseInt(split[12]);
				toyn[4] = Integer.parseInt(split[13]);
				toyn[5] = Integer.parseInt(split[14]);
				CurrentOrder = Integer.parseInt(split[15]);
				LevelUpTarget = Integer.parseInt(split[16]);
				
				setOrder();
//				reader.close();
			}
			
		} catch (FileNotFoundException e) {
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
		
		Cls();
		GameMenu();
	}
	
	int levelUp(int x) {
		if(x == 1 || x == 2) {
			return 3;
		}
		else {
			double n = 0.5 * levelUp(x-1);
			n = (int) Math.floor(n);
			int target = (int) (levelUp(x-1) + levelUp(x-2) + n);
			return target;
		}
	}
	
	ArrayList<String> getFile(String filename){
		ArrayList<String> savedGames = new ArrayList<String>();
		try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String gameName = fileScanner.nextLine();
                savedGames.add(gameName);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
           
        }
        return savedGames;
    }
	
	void LoadGame() throws IOException {
		ArrayList<String> savedGames = getFile("save.txt");
		int count = savedGames.size();
		int n;
		
	/* Display all the ongoing games previously created from the save file (save.txt) and 
	prompt the player to input which game to load. Validate that the input must be 
	between 1 and the number of saved games.
	*/	
		Title();
		System.out.println("=================");
		System.out.println("Save files");
		System.out.println("=================");
		
		if(count == 0) {
			System.out.println("No save files yet");
			System.out.println("");
			System.out.println("Press Enter to continue...");
			sc.nextLine();
			Cls();
			Menu();
		}else {
			for(int i=0; i<count; i++) {
				System.out.println(i+1 + "." + " " + savedGames.get(i));
			}
		}
		
		do {
			System.out.println("");
			System.out.print("Please select a save file to load [0 to go back]: ");
			n = sc.nextInt();
			sc.nextLine();
			
			if(n == 0) {
				Cls();
				Menu();
			}
		}while (n < 1 || n > count);
		
		String gameName = savedGames.get(n-1);
		String gameFile = gameName + ".txt";
				
		try (BufferedReader reader = new BufferedReader(new FileReader(gameFile))) {
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] split = line.split("#");
				Username = split[0];
				Money = Integer.parseInt(split[1]);
				Difficulty = Integer.parseInt(split[2]);
				OrdersDone = Integer.parseInt(split[3]);
				workers[0] = Integer.parseInt(split[4]);
				workers[1] = Integer.parseInt(split[5]);
				workers[2] = Integer.parseInt(split[6]);
				workers[3] = Integer.parseInt(split[7]);
				workers[4] = Integer.parseInt(split[8]);
				toyn[0] = Integer.parseInt(split[9]);
				toyn[1] = Integer.parseInt(split[10]);
				toyn[2] = Integer.parseInt(split[11]);
				toyn[3] = Integer.parseInt(split[12]);
				toyn[4] = Integer.parseInt(split[13]);
				toyn[5] = Integer.parseInt(split[14]);
				CurrentOrder = Integer.parseInt(split[15]);
				LevelUpTarget = Integer.parseInt(split[16]);
				
				setOrder();
//				reader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Cls();
		GameMenu();
		
	}
	
	void HighScore() throws IOException {
        ArrayList<String> scoreData = new ArrayList<>();
           try {
               BufferedReader reader = new BufferedReader(new FileReader("hiscore.txt"));
               String line;
               while ((line = reader.readLine()) != null) {
                   scoreData.add(line);
               }
               reader.close();
           } catch (IOException e) {
               e.printStackTrace();
           }

           // sort the score data based on the most orders done using the bubble sort algorithm
           for (int i = 0; i < scoreData.size() - 1; i++) {
               for (int j = 0; j < scoreData.size() - i - 1; j++) {
                   String[] score1 = scoreData.get(j).split("#");
                   String[] score2 = scoreData.get(j + 1).split("#");
                   int ordersDone1 = Integer.parseInt(score1[1]);
                   int ordersDone2 = Integer.parseInt(score2[1]);
                   if (ordersDone1 < ordersDone2) {
                       // swap the two scores if they are in the wrong order
                       String temp = scoreData.get(j);
                       scoreData.set(j, scoreData.get(j + 1));
                       scoreData.set(j + 1, temp);
                   }
               }
           }
           

           // print the sorted score data
           if (scoreData.size() > 0) {
        	   Title();
        	   System.out.println("========================================");
               System.out.println("| Name		    | Orders | Money   |");
               System.out.println("========================================");
               for (String score : scoreData) {
                   String[] split = score.split("#");
                   String name = split[0];
                   int ordersdone = Integer.parseInt(split[1]);
                   int moola = Integer.parseInt(split[2]);
                   System.out.printf("| %-17s | %-6d | %-7d |\n", name, ordersdone, moola);
               }
               System.out.println("========================================");
               
               System.out.println("");
               System.out.println("Press Enter to Continue...");
               sc.nextLine();
               Menu();
               
           } else {
        	   	Title();
        	   	System.out.println("No high score data");
	   			System.out.println("");
	   			System.out.println("Press Enter to Continue...");
	   			sc.nextLine();
	   			Menu();
           }
   }
	

	
	int countdown(int totalworker, int diff) {
		int x = rand.nextInt(diff) + 1;
		int res = totalworker/x;
		
		return (int) res;
	}
	
	int level(int difficulty) {
		int z = rand.nextInt(difficulty)+1;	
		
		return (int) z;
	}
	
	int qty(int diff) {
		if(diff == 1) {
			int x = rand.nextInt(55) + 100;
			return (int) x;
		}
		else {
			int z = rand.nextInt(diff-1) + rand.nextInt(55+diff-1) + 100;
			return (int) z;
		}
	}
	
	void setOrder() {
		int temp = rand.nextInt(6);
		x = temp;
		toy = toys[x];
		Level = level(Difficulty);
		Quantity = qty(Difficulty);
	
		int total=0;
		for(int i=0; i<workers.length; i++) {
			total += workers[i];
		}	
		TotalWorkers = total;
		
		Countdown = countdown(TotalWorkers, Difficulty);
	}
	
	void ShowCurrOrder() {
		
		if(Countdown == 0) {
			GameOverScreen();
		}
		
		System.out.println("========================================");
		System.out.println("Current Order");
		System.out.println("========================================");
		System.out.printf("Toy\t\t: %s\n", toy);
		System.out.printf("Level\t\t: %d\n", Level);
		System.out.printf("Quantity\t: %d\n", Quantity);
		System.out.printf("Time\t\t: %d\n", Countdown);
		System.out.printf("Difficulty\t: %d\n", Difficulty);
		System.out.printf("Money\t\t: %d\n", Money);
		System.out.printf("Orders Done\t: %d\n", OrdersDone);
		System.out.println("========================================");
		System.out.println("");
	}
	
	void ShowList() {
		System.out.println("======================================");
		System.out.println("|       Toy       | Price | Quantity |");
		System.out.println("======================================");
		System.out.printf("| Teddy Bear      |   2   |    %-5d |\n", toyn[0]);
		System.out.printf("| Toy Car         |   5   |    %-5d |\n", toyn[1]);
		System.out.printf("| Toy Plane       |   7   |    %-5d |\n", toyn[2]);
		System.out.printf("| RC Car          |   15  |    %-5d |\n", toyn[3]);
		System.out.printf("| Train Set       |   25  |    %-5d |\n", toyn[4]);
		System.out.printf("| Transform Robot |   55  |    %-5d |\n", toyn[5]);
		System.out.println("======================================");
		System.out.println("");
	}
	
	void ProduceToys() {
		int workhour = rand.nextInt(6) + 7;
		int y = Level;
		int n1;
		int n2;
		int n3;
		int n4;
		int n5;
		int total=0;
		
		//level 1 worker
		n1 = (int) Math.floor(workhour/y);
		//level 2 worker
		n2 = (int) Math.floor(2*workhour/y);
		//level 3 worker
		n3 = (int) Math.floor(3*workhour/y);
		//level 4 worker
		n4 = (int) Math.floor(4*workhour/y);
		//level 5 worker
		n5 = (int) Math.floor(5*workhour/y);
		
		total = workers[0]*n1 + workers[1]*n2 + workers[2]*n3 + workers[3]*n4 + workers[4]*n5;
		
		int totalz = 100;
		int progress = 0;
		int prev = 0;
		int percent;
		
		int prodtime = workhour*10;
				
		while(progress <= totalz) {
			percent = (progress * 100) / totalz; 
			
			int num = (percent/5) - prev;
			
			StringBuilder progressbar = new StringBuilder("[");
			
			for(int i=0; i<num; i++) {
				progressbar.append("#");
			}
			
			for(int i=num; i<20; i++) {
				progressbar.append(" ");
			}
			progressbar.append("]");
			
			System.out.println("\r" + progressbar.toString() + " " + percent + "%");
			try {
				Thread.sleep(prodtime);
				Cls();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			progress++;
			
		}
		Cls();
		
		ShowCurrOrder();
		
		System.out.println("Your factory has produced " + total + " " + toy + " " + "in " + workhour + " " + "hours");
		toyn[x] += total;
		
		int plus=0;
		
		if(toy.equals("Teddy Bear") == true && toyn[x] >= Quantity) {
			plus = (int) 2 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else if(toy.equals("Toy Car") == true && toyn[x] >= Quantity) {
			plus = (int) 5 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else if(toy.equals("Toy Plane") == true && toyn[x] >= Quantity) {
			plus = (int) 7 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else if(toy.equals("RC Car") == true && toyn[x] >= Quantity) {
			plus = (int) 15 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else if(toy.equals("Train Set") == true && toyn[x] >= Quantity) {
			plus = (int) 25 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else if(toy.equals("Transform Robot") == true && toyn[x] >= Quantity) {
			plus = (int) 55 * Quantity * ((100+Level-1)*10)/100;
			finishOrder(plus);
		}
		else {
			Countdown--;
			System.out.println("Press Enter to Continue...");
			sc.nextLine();
			Cls();
			GameMenu();
		}
	}
	
	void finishOrder(int plus) {
		System.out.println("You have finished an order and earned " + plus);
		Money += plus;
		OrdersDone += 1;
		toyn[x] -= Quantity;
		Countdown--;
		
	
		if(OrdersDone == LevelUpTarget) {
			Difficulty++;
			LevelUpTarget = level(Difficulty);
			setOrder();
			System.out.println("Press Enter to Continue...");
			sc.nextLine();
			Cls();
			GameMenu();
		}
		else {
			setOrder();
			System.out.println("Press Enter to Continue...");
			sc.nextLine();
			Cls();
			GameMenu();
		}
	}
	
	void GameOver() {
		// Delete [playername].txt
		File gameFile = new File(Username + ".txt");
		if(gameFile.exists()) {
			gameFile.delete();
		}
		
		// Remove player from save.txt
		File saveFile = new File("save.txt");
		try {
			 BufferedReader reader = new BufferedReader(new FileReader(saveFile));
			 String line;
			 StringBuilder newSave = new StringBuilder();
			 
			 try {
				 while((line = reader.readLine()) != null) {
					 if(!line.startsWith(Username)) {
						 newSave.append(line).append("\n");
					 }
				 }
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
			 
			 reader.close();
			 FileWriter writer = new FileWriter(saveFile);
			 writer.write(newSave.toString());
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Add to hiscore.txt
		File scoreFile = new File("hiscore.txt");
		try {
			FileWriter writer = new FileWriter(scoreFile, true);
			writer.write(Username + "#" + OrdersDone + "#" + Money + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	void GameOverScreen() {
		GameOver();
		System.out.println("--Game Over--");
		System.out.println("");
		System.out.println("Final Result");
		System.out.println("Orders Done   : " + OrdersDone);
		System.out.println("Final Money   : " + Money);
		System.out.println();
		System.out.println("Press Enter to Continue...");
		sc.nextLine();
		Cls();
		try {
			Menu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void GameMenu(){
		int inp;
		String valid;

		/*This menu will show the current order the factory needs to fulfill*/	
		do {
			Cls();
			Title();
			ShowCurrOrder();
			System.out.println("");
			System.out.println("1. Show Toy List");
			System.out.println("2. Produce Toys");
			System.out.println("3. Manage Workers");
			System.out.println("4. Save and Exit Game");
			System.out.println("5. End Game");
			System.out.print(">> ");
			inp = sc.nextInt();
			sc.nextLine();
		} while (inp < 1 || inp > 5);
		
		switch (inp) {
		case 1: // SHOW TOY LIST
			Cls();
			Title();
			ShowCurrOrder();
			ShowList();
			System.out.println("Press Enter to Continue...");
			sc.nextLine();
			Cls();
			GameMenu();
			break;
			
		case 2: // PRODUCE TOYS
			Title();
			ShowCurrOrder();
			ProduceToys();
			break;
			
		case 3: //MANAGE WORKERS	
			ManageWorkers();
			break;
		
		case 4: // SAVE AND EXIT GAME
			Cls();
			Title();
			System.out.println("You finished " + OrdersDone + " orders");
			try {
				FileWriter gameFile = new FileWriter(Username + ".txt");
				PrintWriter printfile = new PrintWriter(Username  + ".txt");
				String pname = Username;
				int money = Money;
				int diff = Difficulty;
				int ordersdone = OrdersDone;
				int w1 = workers[0];
				int w2 = workers[1];
				int w3 = workers[2];
				int w4 = workers[3];
				int w5 = workers[4];
				int t1 = toyn[0];
				int t2 = toyn[1];
				int t3 = toyn[2];
				int t4 = toyn[3];
				int t5 = toyn[4];
				int t6 = toyn[5];
				int currorder = CurrentOrder;
				int lvlup = Level;
				
				printfile.printf("%s#%d#%d#%d", pname, money, diff, ordersdone);
				printfile.printf("#%d#%d#%d#%d#%d", w1, w2, w3, w4, w5);
				printfile.printf("#%d#%d#%d#%d#%d#%d", t1, t2, t3, t4, t5, t6);
				printfile.printf("#%d#%d", currorder, lvlup);
				printfile.close();
				gameFile.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			System.out.println("Your progress is successfuly saved!" + "\n");
			System.out.println("Press Enter to continue...");
			sc.nextLine();
			Cls();
			try {
				Menu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		case 5: // END GAME
			end();
			break;
		}
	}
	
	void end() {
		String valid;
		do {
			System.out.print("Are you sure you want to end game (You cannot load this game after this!) ? [Y/N]: ");
			valid = sc.nextLine();
			
			if(valid.equals("Y") == true) {
				GameOver();
				System.out.println("");
				System.out.println("Press Enter to continue...");
				sc.nextLine();
				Cls();
				try {
					Menu();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(valid.equals("N") == true){
				Cls();
				GameMenu();
			}		
		}while(valid.equals("Y") == false && valid.equals("N") == false);
	}
	
	void ShowWorker() {
		System.out.println("===================");
		System.out.println("| Level | Workers |");
		System.out.println("===================");
		System.out.printf("| 1     | %-7d |\n", workers[0]);
		System.out.printf("| 2     | %-7d |\n", workers[1]);
		System.out.printf("| 3     | %-7d |\n", workers[2]);
		System.out.printf("| 4     | %-7d |\n", workers[3]);
		System.out.printf("| 5     | %-7d |\n", workers[4]);
		System.out.println("===================");
		System.out.println("");
		System.out.println("Money : " + Money);
		System.out.println("");
	}
	
	void ManageWorkers() {
		int inp;
		int inpx;
		String inp1;
		
		Cls();
		Title();
		ShowWorker();
		// show list of workers in the factory and the player's money
		do {
			System.out.println("Manage Workers");
			System.out.println("=================");
			System.out.println("1. Hire new worker");
			System.out.println("2. Upgrade worker");
			System.out.println("3. Go back");
			System.out.print(">> ");
			inp = sc.nextInt();
			sc.nextLine();	
		}while(inp < 1 || inp > 3);
		
		switch (inp) {
		case 1: // HIRE NEW WORKER
			Cls();
			ShowWorker();

			System.out.print("Hire a level 1 worker for 500 [Y/N] : ");
			inp1 = sc.nextLine();
			
			if(inp1.equals("Y") == true) {
				if(Money < 500) {
					System.out.println("You don't have enough money");
					System.out.println("Press Enter to continue...");
					sc.nextLine();
					Cls();
					ManageWorkers();
				}
				else {
					Money -= 500;
					workers[0] += 1;
					System.out.println("Thank you for your purchase.");
					System.out.println("Press Enter to continue...");
					sc.nextLine();
					Cls();
					ManageWorkers();
				}
			}
			else if(inp1.equals("N") == true) {
				Cls();
				ManageWorkers();
			}
			else {
				Cls();
				ManageWorkers();
			}
			
			break;
			
		case 2: //UPGRADE WORKER
			Cls();
			ShowWorker();
			System.out.println("Upgrade Price List");
			System.out.println("=========================");
			System.out.println("| No. | Upgrade | Price |");
			System.out.println("=========================");
			System.out.println("|  1  | 1 -> 2  | 600   |");
			System.out.println("|  2  | 2 -> 3  | 800   |");
			System.out.println("|  3  | 3 -> 4  | 1100  |");
			System.out.println("|  4  | 4 -> 5  | 1500  |");
			System.out.println("=========================");
			
			do {
				System.out.print("Input which upgrade you want to buy [only applies to one worker]: ");
				inpx = sc.nextInt();
				sc.nextLine();
				
				if(inpx == 1) {			
					if(Money < 600) {
						System.out.println("You don't have enough money");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else if(workers[0] == 0) {
						System.out.println("");
						System.out.println("You don't have any worker with that level");
						System.out.println("");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else {
						System.out.println("You have successfully upgraded one worker from level 1 to level 2");
						workers[0]--;
						workers[1]++;
						Money -= 600;
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
				}
				else if(inpx == 2) {
					if(Money < 800) {
						System.out.println("You don't have enough money");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else if(workers[1] == 0) {
						System.out.println("");
						System.out.println("You don't have any worker with that level");
						System.out.println("");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else {
						System.out.println("You have successfully upgraded one worker from level 2 to level 3");
						workers[1]--;
						workers[2]++;
						Money -= 800;
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
				}
				else if(inpx == 3) {
					if(Money < 1100) {
						System.out.println("You don't have enough money");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else if(workers[2] == 0) {
						System.out.println("");
						System.out.println("You don't have any worker with that level");
						System.out.println("");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else {
						System.out.println("You have successfully upgraded one worker from level 3 to level 4");
						workers[2]--;
						workers[3]++;
						Money -= 1100;
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
				}
				else if(inpx == 4) {
					if(Money < 1500) {
						System.out.println("You don't have enough money");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else if(workers[3] == 0) {
						System.out.println("");
						System.out.println("You don't have any worker with that level");
						System.out.println("");
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
					else {
						System.out.println("You have successfully upgraded one worker from level 4 to level 5");
						workers[3]--;
						workers[4]++;
						Money -= 1500;
						System.out.println("Press Enter to continue...");
						sc.nextLine();
						Cls();
						ManageWorkers();
					}
				}	
			}while(inpx < 1 || inpx > 4);
			break;
			
		case 3: //GO BACK
			Cls();
			GameMenu();
			break;

		default:
			break;
		}
	}
	
	
	public Main() throws IOException {
		Menu();
	}

	public static void main(String[] args) throws IOException {
		new Main();
	}

}
