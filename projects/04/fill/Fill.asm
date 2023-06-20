// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
// reference: https://github.com/tamarl02/nand2tetris/blob/master/04/fill/Fill.asm
(INIT)
// 스크린은 512 * 256 = 131,072개의 비트로 이루어짐.
// 핵 OS에서 메모리는 16비트 단위로 관리하므로 메모리주소를 131072 / 16 = 8192번 증가시키면 모든 스크린 루프 가능
@8192
D=A // D = 8192
@count
M=D // count = D

(LOOP) // 무한루프 시작
@count
M=M-1 // 루프마다 count를 1씩 차감
D=M
@INIT
D;JLT // count가 0보다 작으면 루프 종료
@KBD
D=M
@WHITE
D;JEQ // 키보드가 눌리지 않으면 흰색으로 칠함
@BLACK
0;JMP // 키보드가 눌리면 검은색으로 칠함

(BLACK)
@SCREEN
D=A
@count
A=D+M // 메모리주소 screen + count 로 이동
M=-1 // 해당 메모리주소부터 16bit를 1111 1111 1111 1111로 채움
@LOOP
0;JMP // LOOP checkPoint로 이동

(WHITE)
@SCREEN
D=A
@count
A=D+M // 메모리주소 screen + count 로 이동
M=0 // 해당 메모리주소부터 16bit를 1111 1111 1111 1111로 채움
@LOOP
0;JMP // LOOP checkPoint로 이동