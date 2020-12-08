import java.util.*; 
import java.lang.*;

public class Bank {
	
	public static ArrayList<Customer> cus = new ArrayList<Customer>(); 
	
	public static ArrayList<GiftCard> gift = new ArrayList<GiftCard>(); 
	
	public static ArrayList<Transaction> trans = new ArrayList<Transaction>(); 
	
	Scanner sc = new Scanner(System.in);
	
	
	public static void accountSummary()
	{	
		System.out.println("\nCustomer Id               "+ "Balance       ");
		for(Customer c:cus)
			System.out.println(c.customerId+"                                "+c.balance+ "       ");
		
		System.out.println("");
	}
	
	
	public static void giftCardSummary()
	{	
		System.out.println("\nCard No     "+ "Cust Id    "+"PIN   " + "Gift Card Balance   "+"Status   " + "Reward Points" +"Gift card Type" );
		for(GiftCard g:gift)
		{	
			
			
			if(g.rewardPoints >= 200 && g.cardType.equals("Silver"))
			{
				g.rewardPoints=0; 
				g.cardType="Gold";
				
			} 
			else if(g.rewardPoints >= 200 && g.cardType.equals("Gold"))
			{
				g.rewardPoints=0; 
				g.cardType="Platinum";
				
			}
			
			
			String statusTemp="Active";
			if(g.status == 1)
				statusTemp = "Active"; 
			else if(g.status == 0)
				statusTemp = "Closed"; 
			else if(g.status == 2)
				statusTemp="Blocked";
			System.out.println(g.cardNo + "          "+g.customerId+"            " + g.pin+ "     " + g.giftCardBalance+"        "+statusTemp+"              "+g.rewardPoints +g.cardType);
		}
		
		System.out.println("");
	}
	
	
	public static void transactionSummary()
	{	
		System.out.println("\nTransaction       "+ "Card No       " + " Id      ");
		for(Transaction t:trans)
			System.out.println(t.txtNo+"             "+t.cardNo+ "       "+t.purchaseAmount+"     ");
		
		System.out.println("");
	}
	
	
	
	public void getChoice()
	{	
		System.out.println("");
		System.out.println("Choose an option");
		System.out.println("1.Create a new Account"); 
		System.out.println("2.Create Gift Card");
		System.out.println("3.Top-Up Gift Card"); 
		System.out.println("4.Close Gift Card");
		System.out.println("5.Purchase Item"); 
		System.out.println("6.Block/UnBlock Account");
		//System.out.println("6.Block/UnBlock Account");
		
		System.out.println("Press -1 to exit");
		
		
		int ch=sc.nextInt(); 
		
		if(ch == -1)
			return;
		
		switch(ch)
		{
		case 1:	System.out.println(" ");
				Customer newAccount;
				newAccount = new Customer(); 
				System.out.println("Your Account is created and Your Account id is "+newAccount.customerId); 
				System.out.println("Enter the Amount to be deposited"); 
				
				int bal=sc.nextInt(); 
				while(true)
				{
					if(bal > 0)
					{
						newAccount.balance = bal;
						break;
					} 
					else {
						System.out.println("Enter amount is invalid.Please enter a valid amount"); 
						bal=sc.nextInt();
					}
				}
				
				System.out.println("Your Account balance is "+ newAccount.balance); 
				cus.add(newAccount);  
				accountSummary(); 
				getChoice();
				break; 
				
		case 2:System.out.println(" "); 
			   System.out.println("Enter the Account id");  
			   int id = sc.nextInt(); 
			   
			   while(id < 0 || id > cus.size())
			   {
				   System.out.println("Enter a valid account id"); 
				   id = sc.nextInt();
			   }
			   
			   Customer cust = cus.get(id-1); 
			   
			   GiftCard newGiftCard = new GiftCard(cust); 
			   newGiftCard.createNewGiftCard(cust);
			   
			   gift.add(newGiftCard);	//Adding in Bank Class
			   
			   cust.gift.add(newGiftCard); //Adding in Customer Class
			   
			   giftCardSummary();
			   accountSummary();
			   getChoice();
			   
			   break; 
			   
			   
		case 3: System.out.println(" ");
				
				System.out.println("Enter the Gift id"); 
				int giftId=sc.nextInt(); 
				
				
				while(giftId < 0 || giftId > gift.size())
				   {
					   System.out.println("Enter a valid id");
					   giftId=sc.nextInt();
				   }
				
				GiftCard topUpGiftCard = gift.get(giftId-1);
				
				Customer temp = topUpGiftCard.cusInGiftCard;
				
				System.out.println("Please Enter the Top-Up Amount");
					int topupAmount=sc.nextInt(); 
					
					if(topupAmount > temp.balance)
					{	System.out.println("Your balance is "+ temp.balance);
						System.out.println("Sorry,Top-Up Amount requested is greater than Account Balance"); 
						break;
					}
					else {
						topUpGiftCard.giftCardBalance+=topupAmount;
						temp.balance-=topupAmount; 
					} 
	
				
				giftCardSummary(); 
				accountSummary();
				getChoice();
				break; 
				
				
		case 4: System.out.println(" "); 
				System.out.println("Enter the Gift Card Id to be closed");
				int giftCardCloseId = sc.nextInt(); 
				
				  while(giftCardCloseId < 0 || giftCardCloseId > gift.size())
				   {
					   System.out.println("Enter a valid id"); 
					   giftCardCloseId = sc.nextInt();
				   }
				
				GiftCard giftCard;
				giftCard= gift.get(giftCardCloseId-1); 
				giftCard.status = 0;
				System.out.println("Gift card with id:"+giftCardCloseId+" is closed.");
				
				giftCardSummary(); 
				accountSummary();
				getChoice();
				break; 
		
		case 5: System.out.println(" ");
				System.out.println("Enter the Card Id"); 
				int giftCardId=sc.nextInt(); 
				
				 while(giftCardId < 0 || giftCardId > gift.size())
				   {
					   System.out.println("Enter a valid id"); 
					   giftCardId = sc.nextInt();
				   }
				
				
				GiftCard g = gift.get(giftCardId-1);  
				
				if(g.status == 0)
				{
					System.out.println("Your Gift Card is closed.");
					getChoice();
					break;
				} 
				
				if(g.status == 2)
				{
					System.out.println("This card is Blocked.");
					getChoice();
					break;
				}
				
				System.out.println("Enter the Gift Card Pin"); 
				
				int enteredPin;
				enteredPin = sc.nextInt();
				
				while(enteredPin != g.pin)
				{
					System.out.println("The Entered Pin is wrong.Please ReEnter the pin"); 
					enteredPin = sc.nextInt();
				} 
				
				if(enteredPin == g.pin)
				{
					System.out.println("Enter the amount of Purchase");
					int purchase = sc.nextInt(); 
					
					if(purchase > g.giftCardBalance)
					{
						System.out.println("Purchase Amount is above the Giftcard balance. Please Top-up");
						break;
					} 
					
					Transaction t = new Transaction(g.cardNo,purchase); 
					
					int n = purchase/500; 
					
					int rewardPoints = n*50; 
					
					g.giftCardBalance -=purchase;
					
					g.rewardPoints += rewardPoints;
					
					trans.add(t);
				}
				
				transactionSummary();
				giftCardSummary(); 
				accountSummary();
				getChoice();
				break; 
				
				
		case 6: System.out.println(" "); 
				System.out.println("Enter the Gift Card id ");
				int tempGiftCardId;
				tempGiftCardId = sc.nextInt(); 
				
				 while(tempGiftCardId < 0 || tempGiftCardId > cus.size())
				   {
					   System.out.println("Enter a valid id"); 
					   tempGiftCardId = sc.nextInt();
				   }
				
				
				GiftCard blockGiftCard = gift.get(tempGiftCardId-1); 
				
				System.out.println("Enter the pin"); 
				
				int pin = sc.nextInt();
				
				if(blockGiftCard.pin != pin)
				{
					System.out.println("Entered pin is wrong");  
					getChoice();
					break;
				} 
				
				blockGiftCard.status=2; 
				giftCardSummary(); 
				accountSummary();
				getChoice();
				break;
			
		default: 
			System.out.println("Enter the Correct option"); 
			break;
		
		} 
	}
	
	
	public static void main(String args[])
	{
		
		Bank bank = new Bank(); 
		bank.getChoice();	
	}	
} 



class Customer {
	int customerId; 
	int balance;  
	ArrayList<GiftCard> gift = new ArrayList<GiftCard>(); 
	static int id=1; 
	
	Customer()
	{
		this.customerId=id; 
		id++;
	}
	
}


class GiftCard {
	static int id=1;
	Customer cusInGiftCard;
	int cardNo;
	int pin; 
	int customerId;
	int giftCardBalance;
	int status;
	int blocked;
	int rewardPoints;
	String cardType; 
	
	Scanner sc=new Scanner(System.in);
	
	GiftCard(Customer cus)
	{	
		cardType="Silver";
		cardNo=id;
		this.cusInGiftCard=cus;
		this.customerId = cus.customerId;
		id++;
	} 
	
	
	public void createNewGiftCard(Customer cus)
	{	
		status=1;
		System.out.println("Your unique card id is " + cardNo);
		
		System.out.println("Set the Pin for the new Card"); 
		pin = sc.nextInt(); 
		
		System.out.println("Enter the Amount for GiftCard");
		int getAmount;
		getAmount=sc.nextInt(); 
//		
		while(true)
		{
			if(getAmount < 0)
			{
				System.out.println("Amount cant't be negative");
				System.out.println("Enter the valid Amount"); 
				getAmount=sc.nextInt();
				
			}
			
			else if(getAmount > cus.balance)
			{
				System.out.println("Your Account balance is low"); 
				System.out.println("Please Enter the amount less than or equal to your account balance "+ cus.balance);
				getAmount=sc.nextInt(); 
			}
			else{
				
				giftCardBalance = getAmount; 
				cus.balance-=giftCardBalance;   
				break;
			}
		}
		
		
	}
	
	
	
} 

class Transaction {
	static int id=1;
	int txtNo;
	int cardNo;
	int purchaseAmount;
	
	Transaction(int cardNo,int purchaseAmount)
	{
		txtNo=id;
		id++;  
		
		this.cardNo = cardNo; 
		this.purchaseAmount=purchaseAmount;	
	}
}