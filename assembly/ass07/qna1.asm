.MODEL SMALL 
.STACK 100H
.DATA
    M1 DB 'TYPE A CHARACTER: ','$'
    M2 DB 0AH,0DH,'THE ASCII CODE OF '
    C1 DB ?,' IN BINARY IS: ' , '$'
    M3 DB 0AH,0DH,'THE NUMBER OF 1 BIT IS ' 
    C2 DB ?,'$'
.CODE
MAIN PROC
    MOV AX,@DATA
    MOV DS,AX
    
    MOV AH,9
    LEA DX,M1
    INT 21H
    
    MOV AH,1   ;user inpur nilam
    INT 21H
    
    MOV BL,AL
    MOV C1,AL
    
    MOV AH,9
    LEA DX,M2  ;print korlam 2ta line with j character inpur disi
    INT 21H
    
    MOV BH,0   ;setting counter value
    MOV CX,8
    MOV AH,2
    
    L1: SHL BL,1
        JC L2
        MOV DL,'0'
        INT 21H
        JMP L4
        
    L2: MOV DL,'1'
        INT 21H
        INC BH
        
    L4: LOOP L1           ;counter decrement kortese each time
        
        ADD BH,30H
        MOV C2,BH
        MOV AH,9
        LEA DX,M3
        INT 21H   
        
 EXIT:
        
    MOV AH,4CH
    INT 21H
    
    MAIN ENDP
    END MAIN
