@111 // push constant 111
D=A
@SP
A=M
M=D
@SP
M=M+1
@333 // push constant 333
D=A
@SP
A=M
M=D
@SP
M=M+1
@888 // push constant 888
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // pop static 8
A=M-1
D=M
@StaticTest.vm.8
A=M
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
M=D
@SP
M=M-1
@SP // pop static 3
A=M-1
D=M
@StaticTest.vm.3
A=M
A=A+1
A=A+1
A=A+1
M=D
@SP
M=M-1
@SP // pop static 1
A=M-1
D=M
@StaticTest.vm.1
A=M
A=A+1
M=D
@SP
M=M-1
@StaticTest.vm.3 // push static 3
A=M
A=A+1
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
@StaticTest.vm.1 // push static 1
A=M
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
@StaticTest.vm.8 // push static 8
A=M
A=A+1
A=A+1
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
@107 // close stream
0;JMP
