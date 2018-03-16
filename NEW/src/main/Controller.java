package main;
import guest.Guest;
import guest.Lecturer;
import guest.Student;
import room.Room;
import room.Double;
import room.Single;
import room.Suite;
import room.RoomList;
import java.util.Scanner;

import exception.MyException;

public class Controller {
	private Guest guest;
	private Room room;
	Scanner keyboard = new Scanner(System.in);

	int userChoice;

	public int chooseFromReservationMenu() {
		final int NEW_RESERVATION =1;
		final int VIEW_RESERVATION =2;
		final int CANCEL_RESERVATION =3;
		
		userChoice = keyboard.nextInt();

		int scenario = 0; 
		if (userChoice == NEW_RESERVATION)
			scenario = 1;
		else if(userChoice == VIEW_RESERVATION)
			scenario = 2;
		else if(userChoice == CANCEL_RESERVATION)
			scenario = 3;
		else
			scenario = 4;

		return scenario;
	}


	public Guest addGuest() {
		String name;
		keyboard.nextLine();
		System.out.println("What is the name of the new guest?");
		name = keyboard.nextLine();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);		//makes first letter of name capital


		System.out.println("Is "+name+" ......\n\t1. A Lecturer\n\t   Or\n\t2. A Student");
		userChoice = keyboard.nextInt();



		switch(userChoice) {
		case 1:	//is Lecturer
			guest = new Lecturer(name);
			break;

		case 2:	// is Student
			guest = new Student(name);
			break;
		}		
		return guest;
	}

	public void addExtraGuests(Room room) {
		keyboard.nextLine();
		if(room.isNotFull(room)) {
			System.out.println("Do you wish to add extra guests to this "+room.getRoomType(room)+" (Yes/No) ?");
			String choice; 
			choice = keyboard.nextLine();
			Guest g = null;

			if(choice.toUpperCase().contentEquals("YES") || choice.toUpperCase().contentEquals("Y")){

				if(room.getRoomType(room)=="suite") {				//IF ROOM IS SUITE
					System.out.println("Would you like to add ......\n\t1. One more guest\n\t      Or\n\t2. Two more guests");
					userChoice = keyboard.nextInt();


					switch(userChoice) {
					case 1:{										//Add one extra guest to suite
						g = addGuest();
						try {
							room.addGuest(g);
						} catch (MyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case 2:{										//Add two extra guests to suite
						for(int i=0; i<2; i++) {
							g = addGuest();
							try {
								room.addGuest(g);
							} catch (MyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					}//case2
					}//switch

				}//if room is Suite
				else if(room.getRoomType(room)=="double room"){
					System.out.println("");
					g = addGuest();
					try {
						room.addGuest(g);
					} catch (MyException e) {
						e.printStackTrace();
					}
				}
			}
		}//if(ROOMISNOTFULL)
	}


	public Room chooseRoom(RoomList roomList) {

		System.out.println("Which type of room do you wish to stay in?\n\t1. A Suite\n\t2. A Double\n\t3. A Single");
		userChoice = keyboard.nextInt();

		switch(userChoice) {
		case 1:	//is Suite
			room = roomList.getAvailableSuite();
			return room;
		case 2:	//is Double
			room = roomList.getAvailableDoubles();
			return room;
		case 3:	//is Single
			room = roomList.getAvailableSingles();
			return room;
		}
		return null;
	}

	public void viewReservationList(RoomList roomList) {
		System.out.println("");										//...message to the screen
		System.out.println("\t\tRESERVATION LIST				  ");
		System.out.println("**********************************************\n");
		for(Room r: roomList.getList()){
			if(!r.isEmpty(r)) {
				r.reservationList(r);
			}
		}
		System.out.println("\n**********************************************\n");
	}

	public void displayAllGuests(RoomList roomList) {
		System.out.println("Display All Current Guests By.....\n\t1. Lecturer\n\t   Or\n\t2. Student");
		int type = 0;

		type=keyboard.nextInt();
		if(type == 1) {
			System.out.println("\n   ALL LECTURERS CHECKED IN TO THE HOTEL\n*******************************************");
		}
		else if(type == 2) {
			System.out.println("\n   ALL STUDENTS CHECKED IN TO THE HOTEL\n*******************************************");
		}

		for(Room r: roomList.getList()){
			if(!r.isEmpty(r)) {
				if(type == 1) {
					r.getNamesOfLecturers(r);
				}
				else if(type == 2)
					r.getNamesOfStudents(r);
			}
		}
	}//END OF THE METHOD



	public void cancelReservation(RoomList roomList){
		System.out.println("Which guest is cancelling their reservation?");
		keyboard.nextLine();
		String name = keyboard.nextLine();
		Room matched = null;

		for(Room r: roomList.getList()){
			if(!r.isEmpty(r)) {
				matched = r.findRoomByName(r, name);
				if(matched!=null) {
					System.out.println("\n           RESERVATION CANCELLED           ");
					matched.removeAllGuestsFromRoom(matched);
				}
			}
		}
	}		

	public void payments(RoomList rooms, Guest guest, int type) {		//int type is set in menu.				
		System.out.println("Please enter the room number of the Guest wishing to pay their bill?\nROOM NO:");
		int roomNum = keyboard.nextInt();
		room = rooms.getRoomByRoomNumber(roomNum, rooms);
		double roomPrice = room.Price(guest, room, roomNum);
		if(type == 2) {
			makePayment(roomPrice, room, 0);
		}
		//return roomPrice;
	}

	public void makePayment(double roomPrice, Room room, int counter) {
		System.out.println("\nPlease enter outstanding balance being payed by customer.");
		System.out.println("DUE: �"+roomPrice+"0");
		double moneyPayed = keyboard.nextDouble();

		if(counter < 2) {															//A payment can be attempted 3 times. 
			if((roomPrice - moneyPayed) <= 0) {										//
				System.out.println("\n             GUESTS CHECKED-OUT           ");
				room.removeAllGuestsFromRoom(room);
				System.out.println("*******  THANK YOU FOR YOUR PAYMENT  *********");
				if(moneyPayed - roomPrice > 0) {
					System.out.println("\tCHANGE DUE TO CUSTOMER : �"+(moneyPayed-roomPrice)+"0");
				}
				System.out.println("**********************************************\n");
			}
			else {
				System.out.println("\n*******  YOU HAVE NOT PAYED ENOUGH  **********");
				System.out.println("**********************************************\n");
				counter++;
				roomPrice = roomPrice - moneyPayed;
				makePayment(roomPrice, room , counter);
			}
		}
		else {
			System.out.println("*******  UNSUCCESSFULL PAYMENT  **********");
			System.out.println("*******     NO MONEY TAKEN      **********");
			System.out.println("******************************************\n");
			System.out.println("****  PLEASE TRY TO CHECK-OUT AGAIN  *****");
		}
	}
}			


