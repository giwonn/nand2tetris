@17 // push constant 17
D=A
@SP
A=M
M=D
@SP
M=M+1
@17 // push constant 17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // eq
A=M-1
D=M
A=A-1
D=M-D
M=0
@26
D;JNE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@17 // push constant 17
D=A
@SP
A=M
M=D
@SP
M=M+1
@16 // push constant 16
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // eq
A=M-1
D=M
A=A-1
D=M-D
M=0
@54
D;JNE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@16 // push constant 16
D=A
@SP
A=M
M=D
@SP
M=M+1
@17 // push constant 17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // eq
A=M-1
D=M
A=A-1
D=M-D
M=0
@82
D;JNE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@892 // push constant 892
D=A
@SP
A=M
M=D
@SP
M=M+1
@891 // push constant 891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // lt
A=M-1
D=M
A=A-1
D=M-D
M=0
@110
D;JGE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@891 // push constant 891
D=A
@SP
A=M
M=D
@SP
M=M+1
@892 // push constant 892
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // lt
A=M-1
D=M
A=A-1
D=M-D
M=0
@138
D;JGE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@891 // push constant 891
D=A
@SP
A=M
M=D
@SP
M=M+1
@891 // push constant 891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // lt
A=M-1
D=M
A=A-1
D=M-D
M=0
@166
D;JGE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@32767 // push constant 32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766 // push constant 32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // gt
A=M-1
D=M
A=A-1
D=M-D
M=0
@194
D;JLE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@32766 // push constant 32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32767 // push constant 32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // gt
A=M-1
D=M
A=A-1
D=M-D
M=0
@222
D;JLE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@32766 // push constant 32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766 // push constant 32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // gt
A=M-1
D=M
A=A-1
D=M-D
M=0
@250
D;JLE
@SP
A=M-1
A=A-1
M=-1
@SP
M=M-1
@57 // push constant 57
D=A
@SP
A=M
M=D
@SP
M=M+1
@31 // push constant 31
D=A
@SP
A=M
M=D
@SP
M=M+1
@53 // push constant 53
D=A
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
@112 // push constant 112
D=A
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
@SP // neg
A=M-1
M=-M
@SP // and
A=M-1
D=M
A=A-1
M=M&D
@SP
M=M-1
@82 // push constant 82
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP // or
A=M-1
D=M
A=A-1
M=M|D
@SP
M=M-1
@SP // not
A=M-1
M=!M
@321 // close stream
0;JMP
