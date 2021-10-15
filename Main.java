
public class Main {

	public static void main(String[] args) {
		// all submissions must contain this prelude, or equivalent

		// register file
		int[] Reg = new int[10];
		Reg[1] = 105; Reg[3] = 203; Reg[5] = 301; // r-register values
		Reg[7] = 148; Reg[9] = 156;

		// main memory
		int[] Mem = new int[500];
		char[] XMem = {'l','l','m','a','b','s','s','b','l','s'};               // ten opcodes, and
		Mem[0] = 124; Mem[1] = 348; Mem[2] = 246; // their ten argument
		Mem[3] = 468; Mem[4] = 550; Mem[5] = 368; // lists
		Mem[6] = 584; Mem[7] = 790; Mem[8] = 728;
		Mem[9] = 728;

		Mem[109] = 19; Mem[156] = 25;             // memory values
		Mem[211] = 43;

		boolean branch;                              // for bne results
		System.out.println();                       // blank line

		// loop over instructions; pretend next instruction is at PC + 1
		for( int PC = 0; PC < 10; PC++ ) {

			// f-box
			char head = XMem[PC];
			int  tail = Mem[PC];
			System.out.print("f: Fetched instruction: " + head + "|" + tail + ".\n");

			// d-box
			char opc = head;                        // decompose instruction
			int arg3 = tail % 10;                   // into its opcode and
			tail = tail / 10;                       // its three arguments
			int arg2 = tail % 10;                   // inst = {opc,arg1,...}
			tail = tail / 10;
			int arg1 = tail;
			System.out.print("d: Set opc to '" + opc + "'.\n");

			// all code, or equivalent, above this line is mandatory

			int D_Out1 = 0, D_Out2 = 0, dreg = 0, sval = 0;         // You may imitate this style.
			switch( opc ) {
			case 'a':
				D_Out1 = Reg[arg1];                 // localize and latch
				System.out.print("d: Set D_Out1 to " + D_Out1 + ".\n");
				D_Out2 = Reg[arg2];                 // localize and latch     
				System.out.print("d: Set D_Out2 to " + D_Out2 + ".\n");
				dreg = arg3;                        // record dest. register
				System.out.print("d: Set dreg to f" + dreg + ".\n");
				break;
			case 'b':
				D_Out1 = Reg[arg1];                 // localize and latch
				System.out.print("d: Set D_Out1 to " + D_Out1 + ".\n");
				D_Out2 = Reg[arg2];                 // localize and latch 
				System.out.print("d: Set D_Out2 to " + D_Out2 + ".\n");
				branch = D_Out1 == D_Out2; 			// compare
				if (!branch) {						// take the branch
					System.out.println("d: Compared r"+arg1+" and r"+arg2+".\nd: r"+arg1+"!=r"+arg2);
					System.out.println("d: Branch taken.");
					System.out.println("Program terminated.");
					System.exit(0);                 // terminate the program
				} else {							// do not take the branch
					System.out.println("d: Compared r"+arg1+" and r"+arg2+".\nd: r"+arg1+"==r"+arg2);
					System.out.println("d: Branch not taken.");
					break;                          // proceed to the next step 
				}
			case 'l':
				D_Out1 = Reg[arg1];                 // localize and latch
				System.out.print("d: Set D_Out1 to " + D_Out1 + ".\n");
				D_Out2 = arg3;                      // localize and latch     
				System.out.print("d: Set D_Out2 to " + D_Out2 + ".\n");
				dreg = arg2;                        // record dest. register
				System.out.print("d: Set dreg to f" + dreg + ".\n");
				break;
			case 'm':
				D_Out1 = Reg[arg1];                 // localize and latch
				System.out.print("d: Set D_Out1 to " + D_Out1 + ".\n");
				D_Out2 = Reg[arg2];                 // localize and latch     
				System.out.print("d: Set D_Out2 to " + D_Out2 + ".\n");
				dreg = arg3;                        // record dest. register
				System.out.print("d: Set dreg to f" + dreg + ".\n");
				break;
			case 's':
				D_Out1 = Reg[arg1];                 // localize and latch
				System.out.print("d: Set D_Out1 to " + D_Out1 + ".\n");
				D_Out2 = arg3;                      // localize and latch     
				System.out.print("d: Set D_Out2 to " + D_Out2 + ".\n");
				sval = Reg[arg2];                  	// record value to be stored
				System.out.print("d: Set sval to the value of f" + arg2 + ", " + sval +".\n");
				break;
			}

			// x-box
			int X_Out = 0;
			switch( opc ) {
			case 'a':
				X_Out = D_Out1 + D_Out2;            // perform the addition and latch
				System.out.print("x: Set X_Out to " + X_Out + ".\n");
				break;
			case 'b':
				System.out.print("x: Did nothing.\n");
				break;
			case 'l':
				X_Out = D_Out1 + D_Out2;            // perform the addition and latch
				System.out.print("x: Set X_Out to " + X_Out + ".\n");
				break;
			case 'm':
				X_Out = D_Out1 * D_Out2;            // perform the multiplication and latch
				System.out.print("x: Set X_Out to " + X_Out + ".\n");
				break;
			case 's':
				X_Out = D_Out1 + D_Out2;            // perform the addition and latch
				System.out.print("x: Set X_Out to " + X_Out + ".\n");
				break;
			}

			// m-box
			int M_Out = 0;
			switch( opc ) {
			case 'a':
				System.out.print("m: Did nothing.\n");
				break;
			case 'b':
				System.out.print("m: Did nothing.\n");
				break;
			case 'l':
				M_Out = Mem[X_Out];				    // load from memory and latch
				System.out.print("m: Set M_Out to " + M_Out + ".\n");
				break;
			case 'm':
				System.out.print("m: Did nothing.\n");
				break;
			case 's':
				Mem[X_Out] = sval;				    // store the value into memory
				System.out.print("m: Stored "+sval+" into Mem[" + X_Out + "].\n");
				break;
			}

			// w-box
			switch( opc ){
			case 'a':
				Reg[dreg] = X_Out;				   // put the content of latch in the register file
				System.out.print("w: Set f" + dreg + " to " + X_Out + ".\n");
				break;
			case 'b':
				System.out.print("w: Did nothing.\n");
				break;
			case 'l':
				Reg[dreg] = M_Out;			   	   // put the content of latch in the register file
				System.out.print("w: Set f" + dreg + " to " + M_Out + ".\n");
				break;
			case 'm':
				Reg[dreg] = X_Out;			       // put the content of latch in the register file
				System.out.print("w: Set f" + dreg + " to " + X_Out + ".\n");
				break;
			case 's':
				System.out.print("w: Did nothing.\n");
				break;
			}
			System.out.println();
		}                                         // end 'for' loop
	}
}