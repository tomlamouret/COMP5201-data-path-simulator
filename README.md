# COMP5201-data-path-simulator

**COMP5201 Computer Organization & Design**

Write a simulator for the nonpipelined 'fdxmw' data path that can handle
'mul.d', 'add.d', 'l.d', 's.d', and 'bne'---in a restricted form---but none of
the other instructions such as 'addi', 'subi', and 'mul'.  In large part, this
assignment is simple coding of the box job descriptions in Lecture 5 plus
knowledge about how instructions are laid out.  

You are given a template of the simulator below.  Points of interest are the
following:

1) There are ten registers in the register file.  While I pretend that some
are f-registers and some are r-registers, in the simulation they are all
simply integer registers, namely, Reg : 'array'[0..9] 'of' integer; .

2) The object code for the test program has already been generated and
stored in memory for you.  Recall the instruction formats from Lecture 3.

3) Each of the five boxes generates data/control for subsequent boxes.
Each time a useful result is generated, both write code to generate that
result, and indicate having done so with a print statement.  To avoid chaos,
stick with the variable names appearing in the template.  Example: The global
d-box variables are 'opc', 'D_Out1', 'D_Out2', 'dreg', and 'sval', while the
local variables are 'arg1', 'arg2', and 'arg3'.  Note that updating the
register file, updating the memory, or making a branch decision, is a useful
result, and deserves a print statement.

4) 'bne' has special emulation rules.  If the branch is not taken, continue
with the next instruction.  If the branch is taken, skip all remaining
instructions, and terminate the program normally.

5) Although the template code uses case statements, this is not required.
It is, however, easier to code and to read.  

6) In the code, register designators are decimal digits.  For example, f2
is really just 2 (the print statements tell white lies!).

7) Full output for the first instruction is shown to give you some idea
of what kind of output is expected.

8) Submissions must contain all source code and all output (as an appended
comment) in a _single_ machine-readable ASCII text file.  A formal
submission requires electronic transmission of the text file to a system
to be designated.  Call it "Programming Assignment 3".

9) If a box does nothing, print "<box name>: Did nothing."

---
                                                                                                                                                                           
/*

  l.d   f2,4(r1)                            // program to be  
  l.d   f4,8(r3)                            // simulated;
  mul.d f6,f2,f4                            // no pipelining
  add.d f8,f4,f6
  bne   r5,r5,target
  s.d   f6,8(r3)
  s.d   f8,4(r5)
  bne   r7,r9,target
  l.d   f2,8(r7)
  s.d   f2,8(r7)
target:

*/

#include <iostream>  // for std::cout

int main () {
  // all submissions must contain this prelude, or equivalent

  // register file
  int Reg[10];
  Reg[1] = 105; Reg[3] = 203; Reg[5] = 301; // r-register values
  Reg[7] = 148; Reg[9] = 156;

  // main memory
  int  Mem[500];
  char XMem[] = "llmabssbls";               // ten opcodes, and
  Mem[0] = 124; Mem[1] = 348; Mem[2] = 246; // their ten argument
  Mem[3] = 468; Mem[4] = 550; Mem[5] = 368; // lists
  Mem[6] = 584; Mem[7] = 790; Mem[8] = 728;
  Mem[9] = 728;

  Mem[109] = 19; Mem[156] = 25;             // memory values
  Mem[211] = 43;

  bool branch;                              // for bne results
  std:: cout << "\n";                       // blank line

  // loop over instructions; pretend next instruction is at PC + 1
  for( int PC = 0; PC < 10; PC++ ) {

    // f-box
    char head = XMem[PC];
    int  tail = Mem[PC];
    std::cout << "f: Fetched instruction: " << head << "|" << tail << ".\n";

    // d-box
    char opc = head;                        // decompose instruction
    int arg3 = tail % 10;                   // into its opcode and
    tail = tail / 10;                       // its three arguments
    int arg2 = tail % 10;                   // inst = {opc,arg1,...}
    tail = tail / 10;
    int arg1 = tail;
    std::cout << "d: Set opc to '" << opc << "'.\n";

    // all code, or equivalent, above this line is mandatory

    int D_Out1, D_Out2, dreg, sval;         // You may imitate this style.
    switch( opc ) {
      case 'a':
      case 'm':
        D_Out1 = Reg[arg1];                 // localize and latch
        std::cout << "d: Set D_Out1 to " << D_Out1 << ".\n";
        D_Out2 = Reg[arg2];                 // localize and latch     
        std::cout << "d: Set D_Out2 to " << D_Out2 << ".\n";
        dreg = arg3;                        // record dest. register
        std::cout << "d: Set dreg to f" << dreg << ".\n";
        break;
        ...
    }
 
    // x-box
    int X_Out;
    switch( opc ) {
    ...
    }

    // m-box
    int M_Out;
    switch( opc ) {
    ...
    }

    // w-box
    switch( opc ){
    ...
    }

    std::cout << "\n";
    ...
  }                                         // end 'for' loop
  ...
}
/*

f: Fetched instruction: l|124.
d: Set opc to 'l'.
d: Set D_Out1 to 105.
d: Set D_Out2 to 4.
d: Set dreg to f2.
x: Set X_Out to 109.
m: Set M_Out to 19.
w: Set f2 to 19.

...

*/
