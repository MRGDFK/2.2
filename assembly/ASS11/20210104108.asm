.MODEL SMALL
.STACK 100H  
.DATA
    FIRST DB 13,10,"ENTER A STRING: $"
    STR1 DB 100 DUP (?)
    STR2 DB 100 DUP (?)
    FORWARD DB 13,19, "THE STRING IS: $"
    PAL DB 13,10, "IT IS A PALINDROME.$"
    NONPAL DB 13,10,"IT IS NOT A PALINDROME.$"
    ERROR DB 13,10,"ERROR: EMPTY INPUT! $" 
.CODE  
MAIN PROC
    MOV AX,@DATA
    MOV DS,AX
    MOV ES,AX
    
    CLD
    
    MOV AH,9
    LEA DX,FIRST
    INT 21H
    
    XOR CX,CX
    MOV AH,1
    
    LEA S1,STR1
    
    INPUT:
        
        INT 21H
        CMP AL,ODH
        JE ENTER_PRESSED
        
        CMP AL,5BH
        JE INPUT
        
        CMP AL,7DH
        JE INPUT
        
        CMP AL,28H
        JE INPUT
        
        CMP AL,22H
        JE INPUT
        
        CMP AL,27H
        JE INPUT
        
        CMP AL,''
        JE INPUT
        
        CMP AL,2DH
        JE INPUT
        
        CMP AL,2EH
        JE INPUT
        
        CMP AL,3AH
        JE INPPUT
        
        CMP AL,3BH
        JE INPUT
        
        CMP AL,5F
        JE INPUT       
        
        CMP AL,60H
        JE INPUT
        
        PUSH AX
        INC CX
        MOV [S1],AL
        INC SI
        
        JMP INPUT
            
            ENTER_PRESSED:  
            CMP CX,0
            JE ERROR
            
            LEA D1,STR2
            MOV BX,CX
            
            REVERSE:
                
                POP DX
                MOV [D1],DL
                INC DI
                LOOP REVERSE
                
            MOV AH,9
            LEA DX,FORWARD
            INT 21H
            
            MOV AH,9
            LEA DX,STR1
            INT 21H
            
            LEA S1,STR1
            LEA D1,STR2
            MOV CX,BX
            
            REPE CMPSW
            
            JZ PALINDROME
            
            MOV AH,9 
            LEA DX,NONPAL
            INT 21H
            JMP EXIT
            
            PLAINDROME:
                MOV AH,9                                                                      
                LEA DX,PAL
                INT 21H
                
            EXIT:  
          
                 MOV AH,4CH
                 INT 21H
                 MAIN ENDP
                 END MAIN
