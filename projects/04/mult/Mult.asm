// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.
// operand = R0
@R0
D=M
@operand
M=D
// count = R1
@R1
D=M
@count
M=D
// R2 = 0
@R2
M=0
// LOOP
(LOOP)
// if count == 0 goto END
@count
D=M
@END
D;JEQ
// R2 = R2 + operand(R0)
@operand
D=M
@R2
M=M+D
// count = count - 1
@count
M=M-1
// goto LOOP
@LOOP
0;JMP
// END 무한루프
(END)
@END
0;JMP