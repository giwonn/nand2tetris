@3030 // push constant 3030
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // pop pointer 0
A=M-1
D=M
@3
M=D
@SP
M=M-1
@3040 // push constant 3040
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // pop pointer 1
A=M-1
D=M
@4
M=D
@SP
M=M-1
@32 // push constant 32
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // pop this 2
A=M-1
D=M
@THIS
A=M
A=A+1
A=A+1
M=D
@SP
M=M-1
@46 // push constant 46
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // pop that 6
A=M-1
D=M
@THAT
A=M
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
M=D
@SP
M=M-1
@3 // push pointer 0
D=M
@SP
A=M
M=D
@SP
M=M+1
@4 // push pointer 1
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP // add
A=M-1
D=M
A=A-1
M=M+D
@SP
M=M-1
@THIS // push this 2
A=M
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP // sub
A=M-1
D=M
A=A-1
M=M-D
@SP
M=M-1
@THAT // push that 6
A=M
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP // add
A=M-1
D=M
A=A-1
M=M+D
@SP
M=M-1
@125 // close stream
0;JMP
